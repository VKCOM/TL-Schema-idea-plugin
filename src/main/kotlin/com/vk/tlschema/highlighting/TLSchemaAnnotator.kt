package com.vk.tlschema.highlighting

import com.intellij.lang.annotation.Annotation
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.SyntaxHighlighterColors
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.vk.tlschema.Constants
import com.vk.tlschema.psi.TLSchemaCombinatorDecl
import com.vk.tlschema.psi.impl.*
import com.vk.tlschema.search.TLSchemaSearchUtils
import java.util.*

class TLSchemaAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is TLSchemaResultTypeImpl) {
            ColorType(element.boxedTypeIdent, holder, false, true)
            return
        }
        if (element is TLSchemaFullCombinatorIdImpl) {
            val identFull = element.lcIdentFull
            if (identFull != null) {
                val ident = identFull.lcIdentNs
                val range = ident.textRange
                var annotation = holder.createInfoAnnotation(range, null)
                annotation.textAttributes = TLSchemaSyntaxHighlighter.Constructor
                if (ident.textLength != identFull.textLength) {
                    val range_num =
                        TextRange(ident.textOffset + ident.textLength, identFull.textOffset + identFull.textLength)
                    val annotation_num = holder.createInfoAnnotation(range_num, null)
                    annotation_num.textAttributes = TLSchemaSyntaxHighlighter.ConstructorHash
                } else {
                    if (TLSchemaPsiImplUtil.getDeclaration(ident).haveConditionalArgs()) {
                        annotation = holder.createWarningAnnotation(range, "Constructor hash should be specified")
                    }
                }
            }
        }
        if (element is TLSchemaTypeTermImpl) {
            AnnotateType(element.term as TLSchemaTermImpl, holder, false)
        }
        if (element is TLSchemaConditionalDefImpl) {
            val range = element.getTextRange()
            val annotation = holder.createInfoAnnotation(range, null)
            annotation.textAttributes = TLSchemaSyntaxHighlighter.FieldsMask
        }
        if (element is TLSchemaAttributeNodeImpl) {
            val parent = element.getParent()
            if (parent.firstChild === element) {
                var rw_attrs = false
                for (child in parent.children) {
                    if (child is TLSchemaAttributeNodeImpl) {
                        var is_rw = false
                        for (name in Constants.rwAnnotations) {
                            if (child.getText() == name) {
                                is_rw = true
                                break
                            }
                        }
                        if (is_rw) {
                            if (!rw_attrs) {
                                rw_attrs = true
                            } else {
                                holder.createErrorAnnotation(
                                    child.getTextRange(),
                                    "Only one read/write attribute allowed"
                                )
                            }
                        }
                    }
                }
            }
            val range = element.getTextRange()
            val annotation: Annotation
            var known = false
            for (name in Constants.validAnnotations) {
                if (element.getText() == name) {
                    known = true
                    break
                }
            }
            annotation = if (known) holder.createInfoAnnotation(range, null) else holder.createErrorAnnotation(
                range,
                "Unknown Attribute"
            )
            if (known) {
                annotation.textAttributes = TLSchemaSyntaxHighlighter.Attribute
            }
        }
    }

    private fun AnnotateType(element: TLSchemaTermImpl, holder: AnnotationHolder, percent: Boolean) {
        var percent = percent
        if (element.expr != null) {
            val expr = element.expr as TLSchemaExprImpl?
            for (t in expr!!.termList) {
                AnnotateType(t as TLSchemaTermImpl, holder, percent)
                percent = false
            }
            return
        }

        if (element.natExpr != null) {
            val expr = element.natExpr
            val annotation: Annotation
            val range = expr!!.textRange
            annotation = if (percent) {
                holder.createErrorAnnotation(range, "Percent can't be applied to number")
            } else {
                holder.createInfoAnnotation(range, null)
            }
            annotation.textAttributes = SyntaxHighlighterColors.NUMBER
            return
        }

        if (element.percentTerm != null) {
            if (percent) {
                val range = element.textRange
                holder.createErrorAnnotation(range, "Double percent")
                return
            }
            AnnotateType(element.percentTerm!!.term as TLSchemaTermImpl, holder, true)
            return
        }

        if (element.typeIdent != null) {
            ColorType(element.typeIdent!!, holder, percent, false)
            return
        }

        if (element.varIdent != null) {
            throw AssertionError()
        }

        if (element.typeWithTriangleBraces != null) {
            val type = element.typeWithTriangleBraces
            val ident = type!!.typeIdent
            ColorType(ident, holder, percent, false)
            for (e in type.typeExprList) {
                for (t in e.typeTermList) {
                    AnnotateType(t.term as TLSchemaTermImpl, holder, false)
                }
            }
            return
        }

        throw AssertionError()
    }

    private fun ColorType(ident: PsiElement, holder: AnnotationHolder, percent: Boolean, no_bare: Boolean) {
        var type = ident.text
        val range = ident.textRange
        var bare = false
        val combinator = TLSchemaSearchUtils.findCombinator(ident.project, type)
        if (combinator != null) {
            bare = true
            type = combinator.declaration.resultType.boxedTypeIdent.text
        }
        val cons = TLSchemaSearchUtils.findType(ident.project, type)
        var is_simple_type = cons.size == 1
        if (is_simple_type) {
            val resultType = cons[0]
            val parent = resultType.parent
            if (parent is TLSchemaCombinatorDecl) {
                val decl = parent
                if (decl.fullCombinatorId.lcIdentFull != null) {
                    val name = decl.fullCombinatorId.lcIdentFull!!.lcIdentNs.name
                    val type_name = resultType.boxedTypeIdent.ucIdentNs.name
                    if (name != null && type_name != null && name.lowercase(Locale.getDefault()) != type_name.lowercase(
                            Locale.getDefault()
                        )
                    ) {
                        is_simple_type = false
                    }
                }
            }
        }
        val decl = TLSchemaPsiImplUtil.getDeclaration(ident) ?: throw AssertionError()
        val numVars = decl.numVars
        if (numVars != null) {
            for (id in numVars) {
                if (id.text == type) {
                    val annotation = holder.createInfoAnnotation(range, null)
                    annotation.textAttributes = TLSchemaSyntaxHighlighter.NumericVar
                    return
                }
            }
        }
        var type_arg = false
        val typeVars = decl.typeVars
        if (typeVars != null) {
            for (id in typeVars) {
                if (id.text == type) {
                    type_arg = true
                }
            }
        }
        if (cons.size == 0 && type != "#" && type != "Type" && !type_arg) {
            holder.createErrorAnnotation(range, "Unknown type")
        } else if (is_simple_type && !percent && !bare && !no_bare) {
            holder.createWarningAnnotation(range, "This type can be made bare")
        } else if ((!is_simple_type || no_bare || type_arg) && (percent || bare)) {
            holder.createErrorAnnotation(range, "This type can't be made bare")
        } else {
            val annotation = holder.createInfoAnnotation(range, null)
            if (percent || bare) {
                annotation.textAttributes = TLSchemaSyntaxHighlighter.BareType
            } else {
                annotation.textAttributes = TLSchemaSyntaxHighlighter.BoxedType
            }
        }
    }
}
