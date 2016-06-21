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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.indexing.FileBasedIndex;
import net.pavelk.tlschema.psi.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

class TLSchemaUtils {
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

}
