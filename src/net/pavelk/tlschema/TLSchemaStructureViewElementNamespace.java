/*
 * Copyright (C) 2015 Pavel Kunyavskiy
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

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import net.pavelk.tlschema.psi.TLSchemaConstructorDeclarations;
import net.pavelk.tlschema.psi.TLSchemaDeclaration;
import net.pavelk.tlschema.psi.TLSchemaFile;
import net.pavelk.tlschema.psi.TLSchemaFunDeclarations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.*;

public class TLSchemaStructureViewElementNamespace implements StructureViewTreeElement, SortableTreeElement {
    String namespace;
    boolean isTypeNode;
    TLSchemaFile file;

    public TLSchemaStructureViewElementNamespace(TLSchemaFile file, String namespace, boolean isTypeNode) {
        this.namespace = namespace;
        this.file = file;
        this.isTypeNode = isTypeNode;
    }

    @Override
    public Object getValue() {
        return namespace;
    }

    @Override
    public void navigate(boolean b) {
    }

    @Override
    public boolean canNavigate() {
        return false;
    }

    @Override
    public boolean canNavigateToSource() {
        return false;
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        return namespace;
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return isTypeNode ? "types" : namespace;
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean b) {
                return null;
            }
        }
                ;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        List<TreeElement> result = new ArrayList<>();
        TLSchemaConstructorDeclarations[] constructorDeclarationsList = file.findChildrenByClass(TLSchemaConstructorDeclarations.class);
        Set<String> foundTypes = new HashSet<>();
        for (TLSchemaConstructorDeclarations declarations : constructorDeclarationsList) {
            for (TLSchemaDeclaration decl : declarations.getDeclarationList()) {
                if (decl.getNamespace().equals(namespace)) {
                    String type = decl.getResultType().getBoxedTypeIdent().getText();
                    if (!foundTypes.contains(type)) {
                        result.add(new TLSchemaStructureViewElementType(file.getProject(), type));
                        foundTypes.add(type);
                    }
                }
            }
        }
        if (!isTypeNode) {
            if (!result.isEmpty()) {
                result.clear();
                result.add(new TLSchemaStructureViewElementNamespace(file, namespace, true));
            }
            TLSchemaFunDeclarations[] functionDeclarationsList = file.findChildrenByClass(TLSchemaFunDeclarations.class);
            for (TLSchemaFunDeclarations declarations : functionDeclarationsList) {
                for (TLSchemaDeclaration decl : declarations.getDeclarationList()) {
                    if (decl.getNamespace().equals(namespace)) {
                        result.add(new TLSchemaStructureViewElementFunction(decl));
                    }
                }
            }
        }
        TreeElement[] resultArray = result.toArray(new TreeElement[result.size()]);
        Arrays.sort(resultArray, new Comparator<Object>() {

            int getObjectClass(Object o) {
                if (o instanceof TLSchemaStructureViewElementType) return 0;
                if (o instanceof TLSchemaStructureViewElementNamespace) return 1;
                if (o instanceof TLSchemaStructureViewElementFunction) return 2;
                throw new AssertionError();
            }

            @Override
            public int compare(Object o1, Object o2) {
                int class1 = getObjectClass(o1);
                int class2 = getObjectClass(o2);
                if (class1 != class2) {
                    return class1 - class2;
                }
                String text1 = ((StructureViewTreeElement) o1).getPresentation().getPresentableText();
                String text2 = ((StructureViewTreeElement) o2).getPresentation().getPresentableText();
                if (text1 == null && text2 == null) return 0;
                if (text1 == null) return -1;
                if (text2 == null) return 1;
                return text1.compareTo(text2);
            }
        });
        return resultArray;
    }
}
