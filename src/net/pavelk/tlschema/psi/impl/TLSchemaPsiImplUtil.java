/*
 * Copyright (C)
 *     2015-2016 Pavel Kunyavskiy
 *     2016-2016 Eugene Kurpilyansky
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

package net.pavelk.tlschema.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.util.PsiTreeUtil;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TLSchemaPsiImplUtil {
    public static TLSchemaLcIdentNs getCombinator(TLSchemaDeclaration decl) {
        TLSchemaFullCombinatorId type;
        if (decl.getCombinatorDecl() != null) {
            type = decl.getCombinatorDecl().getFullCombinatorId();
        } else {
            TLSchemaCombinatorDeclBuiltin builtin = decl.getCombinatorDeclBuiltin();
            if (builtin != null) {
                if (builtin.getCombinatorDeclBuiltinType() != null) {
                    type = builtin.getCombinatorDeclBuiltinType().getFullCombinatorId();
                } else if (builtin.getCombinatorDeclBuiltinFalse() != null) {
                    return null;
                } else {
                    throw new AssertionError();
                }
            } else {
                throw new AssertionError();
            }
        }
        if (type.getLcIdentFull() != null) {
            return type.getLcIdentFull().getLcIdentNs();
        } else {
            return null;
        }
    }

    public static List<TLSchemaVarIdent> getNumVars(TLSchemaDeclaration _decl) {
        if (_decl.getCombinatorDeclBuiltin() != null) {
            return null;
        }
        List<TLSchemaVarIdent> result = null;
        TLSchemaCombinatorDecl decl = _decl.getCombinatorDecl();
        if (decl == null) throw new AssertionError();
        List<TLSchemaOptArg> opts = decl.getOptArgList();
        for (TLSchemaOptArg arg : opts) {
            if (arg.getTypeExpr().getText().equals("#")) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.addAll(arg.getVarIdentList());
            }
        }
        List<TLSchemaArg> args = decl.getArgList();
        for (TLSchemaArg _arg : args) {
            if (_arg.getArgNamed() == null)
                continue;
            TLSchemaArgNamed arg = _arg.getArgNamed();
            if (arg.getTypeTermExc().getText().equals("#")) {
                TLSchemaVarIdentOpt ident = arg.getVarIdentOpt();
                if (ident.getVarIdent() != null) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(ident.getVarIdent());
                }
            }
        }
        return result;
    }

    public static boolean haveConditionalArgs(TLSchemaDeclaration _decl) {
        if (_decl.getCombinatorDeclBuiltin() != null) {
            return false;
        }
        TLSchemaCombinatorDecl decl = _decl.getCombinatorDecl();
        if (decl == null) throw new AssertionError();
        List<TLSchemaArg> args = decl.getArgList();
        for (TLSchemaArg arg : args) {
            if (arg.getArgNamed() == null)
                continue;
            if (arg.getArgNamed().getConditionalDef() != null)
                return true;
        }
        return false;
    }


    public static List<TLSchemaVarIdent> getTypeVars(TLSchemaDeclaration _decl) {
        if (_decl.getCombinatorDeclBuiltin() != null) {
            return null;
        }
        List<TLSchemaVarIdent> result = null;
        TLSchemaCombinatorDecl decl = _decl.getCombinatorDecl();
        if (decl == null) throw new AssertionError();
        List<TLSchemaOptArg> opts = decl.getOptArgList();
        for (TLSchemaOptArg arg : opts) {
            if (arg.getTypeExpr().getText().equals("Type")) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.addAll(arg.getVarIdentList());
            }
        }
        return result;
    }


    public static TLSchemaResultType getResultType(TLSchemaDeclaration decl) {
        TLSchemaResultType type;
        if (decl.getCombinatorDecl() != null) {
            type = decl.getCombinatorDecl().getResultType();
        } else {
            TLSchemaCombinatorDeclBuiltin builtin = decl.getCombinatorDeclBuiltin();
            if (builtin != null) {
                if (builtin.getCombinatorDeclBuiltinType() != null) {
                    type = builtin.getCombinatorDeclBuiltinType().getResultType();
                } else if (builtin.getCombinatorDeclBuiltinFalse() != null) {
                    type = builtin.getCombinatorDeclBuiltinFalse().getResultType();
                } else {
                    throw new AssertionError();
                }
            } else {
                throw new AssertionError();
            }
        }
        return type;
    }

    public static TLSchemaDeclaration getDeclaration(@NotNull PsiElement element) {
        return PsiTreeUtil.getParentOfType(element, TLSchemaDeclaration.class);
    }

    public static String getNamespace(TLSchemaDeclaration decl) {
        TLSchemaLcIdentNs ident = decl.getCombinator();
        if (ident != null && ident.getNamespaceIdent() != null)
            return ident.getNamespaceIdent().getText();
        return "common";
    }

    public static void getNamespaces(@NotNull TLSchemaFunDeclarations decls, Set<String> result) {
        for (TLSchemaDeclaration decl : decls.getDeclarationList())
            result.add(decl.getNamespace());
    }

    public static void getNamespaces(@NotNull TLSchemaConstructorDeclarations decls, Set<String> result) {
        for (TLSchemaDeclaration decl : decls.getDeclarationList())
            result.add(decl.getNamespace());
    }

    public static String getName(TLSchemaVarIdent ident) {
        return ident.getText();
    }

    public static PsiElement setName(TLSchemaVarIdent ident, @NotNull String newName) {
        ASTNode curNode = ident.getNode();
        TLSchemaVarIdent newIdent = TLSchemaElementFactory.createVarIdent(ident.getProject(), newName);
        curNode.getTreeParent().replaceChild(curNode, newIdent.getNode());
        return newIdent;
    }

    public static String getName(TLSchemaUcIdentNs ident) {
        return ident.getText();
    }

    public static PsiElement setName(TLSchemaUcIdentNs ident, @NotNull String newName) {
        ASTNode curNode = ident.getNode();
        TLSchemaUcIdentNs newIdent = TLSchemaElementFactory.createUcIdentNs(ident.getProject(), newName);
        curNode.getTreeParent().replaceChild(curNode, newIdent.getNode());
        return newIdent;
    }

    public static String getName(TLSchemaLcIdentNs ident) {
        return ident.getText();
    }

    public static PsiElement setName(TLSchemaLcIdentNs ident, @NotNull String newName) {
        ASTNode curNode = ident.getNode();
        TLSchemaLcIdentNs newIdent = TLSchemaElementFactory.createLcIdentNs(ident.getProject(), newName);
        curNode.getTreeParent().replaceChild(curNode, newIdent.getNode());
        return newIdent;

    }

    @NotNull
    public static PsiReference[] getReferences(PsiElement element) {
        return ReferenceProvidersRegistry.getReferencesFromProviders(element);
    }

    public static PsiReference getReference(PsiElement element) {
        PsiReference[] references = getReferences(element);
        return references.length == 1 ? references[0] : null;
    }

    public static ItemPresentation getPresentation(final TLSchemaVarIdent element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return TLSchemaIcons.FILE;
            }
        };
    }

    public static ItemPresentation getPresentation(final TLSchemaUcIdentNs element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getDeclaration().getCombinator().getText() + " ... = " + element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return TLSchemaIcons.FILE;
            }
        };
    }

    public static ItemPresentation getPresentation(final TLSchemaLcIdentNs element) {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return element.getName();
            }

            @Nullable
            @Override
            public String getLocationString() {
                PsiFile containingFile = element.getContainingFile();
                return containingFile == null ? null : containingFile.getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return TLSchemaIcons.FILE;
            }
        };
    }
}