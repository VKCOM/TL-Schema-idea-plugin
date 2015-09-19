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
import com.intellij.psi.PsiElement;
import net.pavelk.tlschema.psi.TLSchemaConstructorDeclarations;
import net.pavelk.tlschema.psi.TLSchemaFile;
import net.pavelk.tlschema.psi.TLSchemaFunDeclarations;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class TLSchemaStructureViewElementFile implements StructureViewTreeElement, SortableTreeElement {
    private TLSchemaFile element;

    public TLSchemaStructureViewElementFile(PsiElement element) {
        this.element = (TLSchemaFile) element;
    }

    @Override
    public Object getValue() {
        return element;
    }

    @Override
    public void navigate(boolean requestFocus) {
        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        return element.getName();
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation result = element.getPresentation();
        if (result == null) throw new AssertionError();
        return result;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        HashSet<String> set = new HashSet<>();
        TLSchemaConstructorDeclarations[] constructorDeclarationsList = element.findChildrenByClass(TLSchemaConstructorDeclarations.class);
        for (TLSchemaConstructorDeclarations declarations : constructorDeclarationsList) {
            declarations.getNamespaces(set);
        }
        TLSchemaFunDeclarations[] functionDeclarationsList = element.findChildrenByClass(TLSchemaFunDeclarations.class);
        for (TLSchemaFunDeclarations declarations : functionDeclarationsList) {
            declarations.getNamespaces(set);
        }
        String[] namespaces = set.toArray(new String[set.size()]);
        TreeElement[] result = new TreeElement[namespaces.length];
        for (int i = 0; i < namespaces.length; i++)
        result[i] = new TLSchemaStructureViewElementNamespace(element, namespaces[i], false);
        return result;
    }
}