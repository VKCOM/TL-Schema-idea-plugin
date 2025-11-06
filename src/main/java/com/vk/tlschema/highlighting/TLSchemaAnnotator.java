package com.vk.tlschema.highlighting;

import com.intellij.codeInspection.util.InspectionMessage;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.vk.tlschema.Constants;
import com.vk.tlschema.psi.*;
import com.vk.tlschema.psi.impl.*;
import com.vk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TLSchemaAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof TLSchemaResultTypeImpl schemaResultType) {
            ColorType(schemaResultType.getBoxedTypeIdent(), holder, false, true);
            return;
        }

        if (element instanceof TLSchemaFullCombinatorIdImpl fullCombinatorId) {
            TLSchemaLcIdentFull identFull = fullCombinatorId.getLcIdentFull();
            if (identFull != null) {
                TLSchemaLcIdentNs ident = identFull.getLcIdentNs();
                TextRange range = ident.getTextRange();

                setHighlighting(holder, range, TLSchemaSyntaxHighlighter.Constructor);
                if (ident.getTextLength() != identFull.getTextLength()) {
                    TextRange range_num = new TextRange(ident.getTextOffset() + ident.getTextLength(), identFull.getTextOffset() + identFull.getTextLength());
                    setHighlighting(holder, range_num, TLSchemaSyntaxHighlighter.ConstructorHash);
                } else {
                    if (TLSchemaPsiImplUtil.getDeclaration(ident).haveConditionalArgs()) {
                        createWarningAnnotation(holder, range, "Constructor hash should be specified");
                    }
                }
            }
        }

        if (element instanceof TLSchemaTypeTermImpl typeTerm) {
            AnnotateType((TLSchemaTermImpl) typeTerm.getTerm(), holder, false);
        }

        if (element instanceof TLSchemaConditionalDefImpl) {
            TextRange range = element.getTextRange();
            setHighlighting(holder, range, TLSchemaSyntaxHighlighter.FieldsMask);
        }

        if (element instanceof TLSchemaAttributeNodeImpl) {
            PsiElement parent = element.getParent();
            if (parent.getFirstChild() == element) {
                boolean rw_attrs = false;
                for (PsiElement child : parent.getChildren()) {
                    if (child instanceof TLSchemaAttributeNodeImpl) {
                        boolean is_rw = false;
                        for (String name : Constants.rwAnnotations) {
                            if (child.getText().equals(name)) {
                                is_rw = true;
                                break;
                            }
                        }
                        if (is_rw) {
                            if (!rw_attrs) {
                                rw_attrs = true;
                            } else {
                                TextRange range = child.getTextRange();
                                createErrorAnnotation(holder, range, "Only one read/write attribute allowed");
                            }
                        }
                    }
                }
            }
            TextRange range = element.getTextRange();
            boolean known = false;
            for (String name : Constants.validAnnotations) {
                if (element.getText().equals(name)) {
                    known = true;
                    break;
                }
            }

            if (known) {
                setHighlighting(holder, range, TLSchemaSyntaxHighlighter.Attribute);
            } else {
                createErrorAnnotation(holder, range, "Unknown Attribute");
            }
        }

    }

    private void AnnotateType(TLSchemaTermImpl element, @NotNull AnnotationHolder holder, boolean percent) {

        if (element.getExpr() != null) {
            TLSchemaExprImpl expr = (TLSchemaExprImpl) element.getExpr();
            for (TLSchemaTerm t : expr.getTermList()) {
                AnnotateType((TLSchemaTermImpl) t, holder, percent);
                percent = false;
            }
            return;
        }

        if (element.getNatExpr() != null) {
            TLSchemaNatExpr expr = element.getNatExpr();
            TextRange range = expr.getTextRange();
            setHighlighting(holder, range, SyntaxHighlighterColors.NUMBER);
            if (percent) {
                createErrorAnnotation(holder, range, "Percent can't be applied to number");
            }

            return;
        }

        if (element.getPercentTerm() != null) {
            if (percent) {
                TextRange range = element.getTextRange();
                createErrorAnnotation(holder, range, "Double percent");
                return;
            }
            AnnotateType((TLSchemaTermImpl) element.getPercentTerm().getTerm(), holder, true);
            return;
        }

        if (element.getTypeIdent() != null) {
            ColorType(element.getTypeIdent(), holder, percent, false);
            return;
        }

        if (element.getVarIdent() != null) {
            throw new AssertionError();
        }

        if (element.getTypeWithTriangleBraces() != null) {
            TLSchemaTypeWithTriangleBraces type = element.getTypeWithTriangleBraces();
            TLSchemaTypeIdent ident = type.getTypeIdent();
            ColorType(ident, holder, percent, false);
            for (TLSchemaTypeExpr e : type.getTypeExprList()) {
                for (TLSchemaTypeTerm t : e.getTypeTermList()) {
                    AnnotateType((TLSchemaTermImpl) t.getTerm(), holder, false);
                }
            }
            return;
        }

        throw new AssertionError();
    }

    private void ColorType(@NotNull PsiElement ident, @NotNull AnnotationHolder holder, boolean percent, boolean no_bare) {
        String type = ident.getText();
        TextRange range = ident.getTextRange();
        boolean bare = false;
        TLSchemaLcIdentNs combinator = TLSchemaSearchUtils.findCombinator(
                ident.getProject(),
                ident.getContainingFile(),
                type
        );
        if (combinator != null) {
            bare = true;
            type = combinator.getDeclaration().getResultType().getBoxedTypeIdent().getText();
        }
        List<TLSchemaResultType> cons = TLSchemaSearchUtils.findType(ident.getProject(), ident.getContainingFile(), type);
        boolean is_simple_type = cons.size() == 1;
        if (is_simple_type) {
            TLSchemaResultType resultType = cons.get(0);
            PsiElement parent = resultType.getParent();
            if (parent instanceof TLSchemaCombinatorDecl combinatorDecl) {
                if (combinatorDecl.getFullCombinatorId().getLcIdentFull() != null) {
                    String name = combinatorDecl.getFullCombinatorId().getLcIdentFull().getLcIdentNs().getName();
                    String type_name = resultType.getBoxedTypeIdent().getUcIdentNs().getName();
                    if (name != null && type_name != null && !name.equalsIgnoreCase(type_name)) {
                        is_simple_type = false;
                    }
                }
            }
        }
        TLSchemaDeclaration decl = TLSchemaPsiImplUtil.getDeclaration(ident);
        if (decl == null) throw new AssertionError();
        List<TLSchemaVarIdent> numVars = decl.getNumVars();
        if (numVars != null) {
            for (TLSchemaVarIdent id : numVars) {
                if (id.getText().equals(type)) {
                    setHighlighting(holder, range, TLSchemaSyntaxHighlighter.NumericVar);
                    return;
                }
            }
        }
        boolean type_arg = false;
        List<TLSchemaVarIdent> typeVars = decl.getTypeVars();
        if (typeVars != null) {
            for (TLSchemaVarIdent id : typeVars) {
                if (id.getText().equals(type)) {
                    type_arg = true;
                    break;
                }
            }
        }
        if (cons.isEmpty() && !type.equals("#") && !type.equals("Type") && !type_arg) {
            createErrorAnnotation(holder, range, "Unknown type");
        } else if (is_simple_type && !percent && !bare && !no_bare) {
            createWarningAnnotation(holder, range, "This type can be made bare");
        } else if ((!is_simple_type || no_bare || type_arg) && (percent || bare)) {
            createErrorAnnotation(holder, range, "This type can't be made bare");
        } else {
            if (percent || bare) {
                setHighlighting(holder, range, TLSchemaSyntaxHighlighter.BareType);
            } else {
                setHighlighting(holder, range, TLSchemaSyntaxHighlighter.BoxedType);
            }
        }
    }

    private static void setHighlighting(
            @NotNull AnnotationHolder holder,
            @NotNull TextRange range,
            @NotNull TextAttributesKey key) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range).textAttributes(key).create();
    }

    private static void createWarningAnnotation(
            @NotNull AnnotationHolder holder,
            @NotNull TextRange range,
            @NotNull @InspectionMessage String message) {
        holder.newAnnotation(HighlightSeverity.WARNING, message).range(range).create();
    }

    private static void createErrorAnnotation(
            @NotNull AnnotationHolder holder,
            @NotNull TextRange range,
            @NotNull @InspectionMessage String message) {
        holder.newAnnotation(HighlightSeverity.ERROR, message).range(range).create();
    }
}
