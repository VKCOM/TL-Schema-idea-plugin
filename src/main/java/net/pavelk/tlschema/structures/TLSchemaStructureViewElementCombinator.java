package net.pavelk.tlschema.structures;

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TLSchemaStructureViewElementCombinator implements StructureViewTreeElement, SortableTreeElement {
    TLSchemaLcIdentNs ident;

    public TLSchemaStructureViewElementCombinator(TLSchemaLcIdentNs ident) {
        this.ident = ident;
    }

    private String getText() {
        return ident.getText();
    }

    @Override
    public Object getValue() {
        return ident;
    }

    @Override
    public void navigate(boolean requestFocus) {
        ((NavigationItem) ident).navigate(requestFocus);
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
                return getText();
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
