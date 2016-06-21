/*
 * Copyright (C) 2015-2016 Pavel Kunyavskiy
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.pavelk.tlschema;

import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import net.pavelk.tlschema.psi.*;
import net.pavelk.tlschema.psi.impl.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TLSchemaAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull final PsiElement element, @NotNull AnnotationHolder holder) {
        //System.out.println(element.toString());
        if (element instanceof TLSchemaResultTypeImpl) {
            ColorType(((TLSchemaResultTypeImpl) element).getBoxedTypeIdent(), holder, false, true);
            return;
        }
        if (element instanceof TLSchemaFullCombinatorIdImpl) {
            TLSchemaLcIdentFull identFull = ((TLSchemaFullCombinatorIdImpl) element).getLcIdentFull();
            if (identFull != null) {
                TLSchemaLcIdentNs ident = identFull.getLcIdentNs();
                TextRange range = ident.getTextRange();
                Annotation annotation = holder.createInfoAnnotation(range, null);
                annotation.setTextAttributes(TLSchemaSyntaxHighlighter.Constructor);
                if (ident.getTextLength() != identFull.getTextLength()) {
                    TextRange range_num = new TextRange(ident.getTextOffset() + ident.getTextLength(), identFull.getTextOffset() + identFull.getTextLength());
                    Annotation annotation_num = holder.createInfoAnnotation(range_num, null);
                    annotation_num.setTextAttributes(TLSchemaSyntaxHighlighter.ConstructorHash);
                } else {
                    if (TLSchemaPsiImplUtil.getDeclaration(ident).haveConditionalArgs()) {
                        annotation = holder.createWarningAnnotation(range, "Constructor hash should be specified");
                    }
                }
            }
        }
        if (element instanceof TLSchemaTypeTermImpl) {
            AnnotateType((TLSchemaTermImpl) ((TLSchemaTypeTermImpl) element).getTerm(), holder, false);
        }
        if (element instanceof TLSchemaConditionalDefImpl) {
            TextRange range = element.getTextRange();
            Annotation annotation = holder.createInfoAnnotation(range, null);
            annotation.setTextAttributes(TLSchemaSyntaxHighlighter.FieldsMask);
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
            Annotation annotation;
            TextRange range = expr.getTextRange();
            if (percent) {
                annotation = holder.createErrorAnnotation(range, "Percent can't be applied to number");
            } else {
                annotation = holder.createInfoAnnotation(range, null);
            }
            annotation.setTextAttributes(SyntaxHighlighterColors.NUMBER);
            return;
        }

        if (element.getPercentTerm() != null) {
            if (percent) {
                TextRange range = element.getTextRange();
                holder.createErrorAnnotation(range, "Double percent");
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
        TLSchemaLcIdentNs combinator = TLSchemaUtils.findCombinator(ident.getProject(), type);
        if (combinator != null) {
            bare = true;
            type = combinator.getDeclaration().getResultType().getBoxedTypeIdent().getText();
        }
        int count = TLSchemaUtils.findType(ident.getProject(), type).size();
        TLSchemaDeclaration decl = TLSchemaPsiImplUtil.getDeclaration(ident);
        if (decl == null) throw new AssertionError();
        List<TLSchemaVarIdent> numVars = decl.getNumVars();
        if (numVars != null) {
            for (TLSchemaVarIdent id : numVars) {
                if (id.getText().equals(type)) {
                    Annotation annotation = holder.createInfoAnnotation(range, null);
                    annotation.setTextAttributes(TLSchemaSyntaxHighlighter.NumericVar);
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
        if (count == 0 && !type.equals("#") && !type.equals("Type") && !type_arg) {
            holder.createErrorAnnotation(range, "Unknown type");
        } else if (count == 1 && !percent && !bare && !no_bare) {
            holder.createWarningAnnotation(range, "This type can be made bare");
        } else if ((count > 1 || no_bare || type_arg) && (percent || bare)) {
            holder.createErrorAnnotation(range, "This type can't be made bare");
        } else {
            Annotation annotation = holder.createInfoAnnotation(range, null);
            if (percent || bare) {
                annotation.setTextAttributes(TLSchemaSyntaxHighlighter.BareType);
            } else {
                annotation.setTextAttributes(TLSchemaSyntaxHighlighter.BoxedType);
            }
        }
    }
}