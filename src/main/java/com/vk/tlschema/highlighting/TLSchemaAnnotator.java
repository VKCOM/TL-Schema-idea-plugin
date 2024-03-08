package com.vk.tlschema.highlighting;

import com.intellij.lang.annotation.*;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.vk.tlschema.Constants;
import com.vk.tlschema.psi.*;
import com.vk.tlschema.psi.impl.*;
import com.vk.tlschema.search.TLSchemaSearchUtils;
import org.bouncycastle.util.Arrays;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TLSchemaAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof TLSchemaResultTypeImpl) {
            ColorType(((TLSchemaResultTypeImpl) element).getBoxedTypeIdent(), holder, false, true);
            return;
        }
        if (element instanceof TLSchemaFullCombinatorIdImpl) {
            TLSchemaLcIdentFull identFull = ((TLSchemaFullCombinatorIdImpl) element).getLcIdentFull();
            if (identFull != null) {
                TLSchemaLcIdentNs ident = identFull.getLcIdentNs();
                TextRange range = ident.getTextRange();

                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                        .range(range)
                        .textAttributes(TLSchemaSyntaxHighlighter.Constructor)
                        .create();
                if (ident.getTextLength() != identFull.getTextLength()) {
                    TextRange range_num = new TextRange(ident.getTextOffset() + ident.getTextLength(), identFull.getTextOffset() + identFull.getTextLength());
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .range(range_num)
                            .textAttributes(TLSchemaSyntaxHighlighter.ConstructorHash)
                            .create();
                } else {
                    if (TLSchemaPsiImplUtil.getDeclaration(ident).haveConditionalArgs()) {
                        holder.newAnnotation(HighlightSeverity.WARNING, "Constructor hash should be specified")
                                .range(range)
                                .create();
                    }
                }
            }
        }
        if (element instanceof TLSchemaTypeTermImpl) {
            AnnotateType((TLSchemaTermImpl) ((TLSchemaTypeTermImpl) element).getTerm(), holder, false);
        }
        if (element instanceof TLSchemaConditionalDefImpl) {
            TextRange range = element.getTextRange();
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(range)
                    .textAttributes(TLSchemaSyntaxHighlighter.FieldsMask)
                    .create();
        }
        if (element instanceof TLSchemaAttributeNodeImpl) {
            PsiElement parent = element.getParent();
            if (parent.getFirstChild() == element) {
                boolean rw_attrs = false;
                for (PsiElement child: parent.getChildren()) {
                    if (child instanceof TLSchemaAttributeNodeImpl) {
                        boolean is_rw = false;
                        for (String name: Constants.rwAnnotations) {
                            if (child.getText().equals(name)) {
                                is_rw = true;
                                break;
                            }
                        }
                        if (is_rw) {
                            if (!rw_attrs) {
                                rw_attrs = true;
                            } else {
                                holder.newAnnotation(HighlightSeverity.ERROR, "Only one read/write attribute allowed")
                                        .range(child.getTextRange())
                                        .create();
                            }
                        }
                    }
                }
            }
            TextRange range = element.getTextRange();
            AnnotationBuilder annotation;
            boolean known = false;
            for (String name: Constants.validAnnotations) {
                if (element.getText().equals(name)) {
                    known = true;
                    break;
                }
            }

            if (known) {
                annotation = holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range);
            } else {
                annotation = holder.newAnnotation(HighlightSeverity.ERROR, "Unknown Attribute").range(range);
            }

            if (known) {
                annotation = annotation.textAttributes(TLSchemaSyntaxHighlighter.Attribute);
            }

            annotation.create();
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
            AnnotationBuilder annotation;
            TextRange range = expr.getTextRange();
            if (percent) {
                annotation = holder.newAnnotation(HighlightSeverity.ERROR, "Percent can't be applied to number").range(range);
            } else {
                annotation = holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range);
            }
            annotation.textAttributes(SyntaxHighlighterColors.NUMBER).create();
            return;
        }

        if (element.getPercentTerm() != null) {
            if (percent) {
                TextRange range = element.getTextRange();
                holder.newAnnotation(HighlightSeverity.ERROR, "Double percent")
                        .range(range)
                        .create();
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
        TLSchemaLcIdentNs combinator = TLSchemaSearchUtils.findCombinator(ident.getProject(), type);
        if (combinator != null) {
            bare = true;
            type = combinator.getDeclaration().getResultType().getBoxedTypeIdent().getText();
        }
        List<TLSchemaResultType> cons = TLSchemaSearchUtils.findType(ident.getProject(), type);
        boolean is_simple_type = cons.size() == 1;
        if (is_simple_type) {
            TLSchemaResultType resultType = cons.get(0);
            PsiElement parent = resultType.getParent();
            if (parent instanceof TLSchemaCombinatorDecl) {
                TLSchemaCombinatorDecl decl = (TLSchemaCombinatorDecl) parent;
                if (decl.getFullCombinatorId().getLcIdentFull() != null) {
                    String name = decl.getFullCombinatorId().getLcIdentFull().getLcIdentNs().getName();
                    String type_name = resultType.getBoxedTypeIdent().getUcIdentNs().getName();
                    if (name != null && type_name != null && !name.toLowerCase().equals(type_name.toLowerCase())) {
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
                    holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                            .range(range)
                            .textAttributes(TLSchemaSyntaxHighlighter.NumericVar)
                            .create();
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
                }
            }
        }
        if (cons.size() == 0 && !type.equals("#") && !type.equals("Type") && !type_arg) {
            holder.newAnnotation(HighlightSeverity.ERROR, "Unknown type").range(range).create();
        } else if (is_simple_type && !percent && !bare && !no_bare) {
            holder.newAnnotation(HighlightSeverity.WARNING, "This type can be made bare").range(range).create();
        } else if ((!is_simple_type || no_bare || type_arg) && (percent || bare)) {
            holder.newAnnotation(HighlightSeverity.ERROR, "This type can't be made bare").range(range).create();
        } else {
            AnnotationBuilder annotation = holder.newSilentAnnotation(HighlightSeverity.INFORMATION).range(range);
            if (percent || bare) {
                annotation = annotation.textAttributes(TLSchemaSyntaxHighlighter.BareType);
            } else {
                annotation = annotation.textAttributes(TLSchemaSyntaxHighlighter.BoxedType);
            }
            annotation.create();
        }
    }
}
