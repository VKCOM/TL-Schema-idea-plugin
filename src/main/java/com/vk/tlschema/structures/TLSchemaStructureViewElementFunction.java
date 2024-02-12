package com.vk.tlschema.structures;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.vk.tlschema.psi.TLSchemaCombinatorDeclBuiltin;
import com.vk.tlschema.psi.TLSchemaDeclaration;
import com.vk.tlschema.psi.TLSchemaLcIdentNs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TLSchemaStructureViewElementFunction implements StructureViewTreeElement, SortableTreeElement {
    TLSchemaDeclaration declaration;

    public TLSchemaStructureViewElementFunction(TLSchemaDeclaration declaration) {
        this.declaration = declaration;
    }

    private String getText() {
        final TLSchemaCombinatorDeclBuiltin builtin = declaration.getCombinatorDeclBuiltin();
        if (builtin != null) {
            throw new AssertionError();
        }
        TLSchemaLcIdentNs ident = declaration.getCombinator();
        if (ident == null)
            throw new AssertionError();
        String name = ident.getText();
        if (ident.getNamespaceIdent() != null)
            return name.substring(ident.getNamespaceIdent().getTextLength() + 1);
        return name;
    }

    @Override
    public Object getValue() {
        if (declaration.getCombinatorDeclBuiltin() != null) {
            throw new AssertionError();
        }
        return declaration;
    }

    @Override
    public void navigate(boolean requestFocus) {
        ((NavigationItem) declaration).navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return true;
    }

    @Override
    public boolean canNavigateToSource() {
        return true;
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        return getText();
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return getText() + " = " + declaration.getResultType().getText();
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
        return new TreeElement[0];
    }
}
