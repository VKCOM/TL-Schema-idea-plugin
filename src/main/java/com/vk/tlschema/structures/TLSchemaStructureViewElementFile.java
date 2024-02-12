package com.vk.tlschema.structures;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.vk.tlschema.psi.TLSchemaConstructorDeclarations;
import com.vk.tlschema.psi.TLSchemaFile;
import com.vk.tlschema.psi.TLSchemaFunDeclarations;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
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
        Arrays.sort(namespaces);
        TreeElement[] result = new TreeElement[namespaces.length];
        for (int i = 0; i < namespaces.length; i++)
            result[i] = new TLSchemaStructureViewElementNamespace(element, namespaces[i], false);
        return result;
    }
}