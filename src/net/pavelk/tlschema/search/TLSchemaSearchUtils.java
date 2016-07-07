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

package net.pavelk.tlschema.search;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import net.pavelk.tlschema.TLSchemaFileType;
import net.pavelk.tlschema.psi.*;
import net.pavelk.tlschema.psi.impl.TLSchemaRecursiveVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TLSchemaSearchUtils {
    public static List<TLSchemaResultType> findType(Project project, String key) {
        List<TLSchemaResultType> result = null;
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TLSchemaFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TLSchemaFile simpleFile = (TLSchemaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                TLSchemaConstructorDeclarations[] decls = PsiTreeUtil.getChildrenOfType(simpleFile, TLSchemaConstructorDeclarations.class);
                if (decls != null) {
                    for (TLSchemaConstructorDeclarations d : decls) {
                        for (TLSchemaDeclaration decl : d.getDeclarationList()) {
                            TLSchemaResultType type = decl.getResultType();
                            if (key.equals(type.getBoxedTypeIdent().getText())) {
                                if (result == null) {
                                    result = new ArrayList<>();
                                }
                                result.add(type);
                            }
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<TLSchemaResultType>emptyList();
    }

    public static TLSchemaLcIdentNs findCombinator(Project project, String key) {
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TLSchemaFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TLSchemaFile simpleFile = (TLSchemaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                TLSchemaConstructorDeclarations[] decls = PsiTreeUtil.getChildrenOfType(simpleFile, TLSchemaConstructorDeclarations.class);
                if (decls != null) {
                    for (TLSchemaConstructorDeclarations d : decls) {
                        for (TLSchemaDeclaration decl : d.getDeclarationList()) {
                            TLSchemaLcIdentNs comb = decl.getCombinator();
                            if (comb != null && key.equals(comb.getText())) {
                                return comb;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public static List<TLSchemaUcIdentNs> findUcIdents(Project project, @NotNull String name) {
        return findUcIdents(project).stream()
                .filter(ident -> name.equals(ident.getName()))
                .collect(Collectors.toList());
    }

    public static List<TLSchemaLcIdentNs> findLcIdents(Project project, @NotNull String name) {
        return findLcIdents(project).stream()
                .filter(ident -> name.equals(ident.getName()))
                .collect(Collectors.toList());
    }

    public static List<TLSchemaVarIdent> findVarIdents(TLSchemaDeclaration declaration, String name) {
        return findVarIdents(declaration).stream()
                .filter(ident -> name.equals(ident.getName()))
                .collect(Collectors.toList());
    }

    public static List<TLSchemaVarIdent> findVarIdents(Project project, String name) {
        return findVarIdents(project).stream()
                .filter(ident -> name.equals(ident.getName()))
                .collect(Collectors.toList());
    }

    public static List<TLSchemaUcIdentNs> findUcIdents(Project project) {
        final List<TLSchemaUcIdentNs> result = new ArrayList<>();
        TLSchemaRecursiveVisitor tlSchemaVisitor = new TLSchemaRecursiveVisitor() {
            @Override
            public void visitFunDeclarations(@NotNull TLSchemaFunDeclarations o) {
                // ignore subtree
            }

            @Override
            public void visitArg(@NotNull TLSchemaArg o) {
                // ignore subtree
            }

            @Override
            public void visitOptArg(@NotNull TLSchemaOptArg o) {
                // ignore subtree
            }

            @Override
            public void visitResultType(@NotNull TLSchemaResultType o) {
                result.add(o.getBoxedTypeIdent().getUcIdentNs());
            }
        };
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TLSchemaFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TLSchemaFile tlschemaFile = (TLSchemaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (tlschemaFile != null) {
                tlSchemaVisitor.visitFile(tlschemaFile);
            }
        }
        return result;
    }

    public static List<TLSchemaLcIdentNs> findLcIdents(Project project) {
        final List<TLSchemaLcIdentNs> result = new ArrayList<>();
        TLSchemaRecursiveVisitor tlSchemaVisitor = new TLSchemaRecursiveVisitor() {
            @Override
            public void visitArg(@NotNull TLSchemaArg o) {
                // ignore subtree
            }

            @Override
            public void visitOptArg(@NotNull TLSchemaOptArg o) {
                // ignore subtree
            }

            @Override
            public void visitFullCombinatorId(@NotNull TLSchemaFullCombinatorId o) {
                if (o.getLcIdentFull() != null) {
                    result.add(o.getLcIdentFull().getLcIdentNs());
                }
            }
        };
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TLSchemaFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TLSchemaFile tlschemaFile = (TLSchemaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (tlschemaFile != null) {
                tlSchemaVisitor.visitFile(tlschemaFile);
            }
        }
        return result;
    }

    public static List<TLSchemaVarIdent> findVarIdents(TLSchemaDeclaration declaration) {
        final List<TLSchemaVarIdent> result = new ArrayList<>();
        TLSchemaRecursiveVisitor tlSchemaVisitor = new TLSchemaRecursiveVisitor() {
            @Override
            public void visitConditionalDef(@NotNull TLSchemaConditionalDef o) {
                // ignore subtree
            }

            @Override
            public void visitResultType(@NotNull TLSchemaResultType o) {
                // ignore subtree
            }

            @Override
            public void visitVarIdent(@NotNull TLSchemaVarIdent o) {
                result.add(o);
            }
        };
        tlSchemaVisitor.visitPsiElement(declaration);
        return result;
    }

    public static List<TLSchemaVarIdent> findVarIdents(Project project) {
        final List<TLSchemaVarIdent> result = new ArrayList<>();
        TLSchemaRecursiveVisitor tlSchemaVisitor = new TLSchemaRecursiveVisitor() {
            @Override
            public void visitConditionalDef(@NotNull TLSchemaConditionalDef o) {
                // ignore subtree
            }

            @Override
            public void visitVarIdent(@NotNull TLSchemaVarIdent o) {
                result.add(o);
            }
        };
        Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, TLSchemaFileType.INSTANCE,
                GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            TLSchemaFile tlschemaFile = (TLSchemaFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (tlschemaFile != null) {
                tlSchemaVisitor.visitFile(tlschemaFile);
            }
        }
        return result;
    }

}
