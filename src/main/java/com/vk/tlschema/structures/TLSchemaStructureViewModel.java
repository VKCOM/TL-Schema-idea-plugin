package com.vk.tlschema.structures;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import com.vk.tlschema.psi.TLSchemaFile;
import org.jetbrains.annotations.NotNull;

public class TLSchemaStructureViewModel extends StructureViewModelBase implements
        StructureViewModel.ElementInfoProvider {
    public TLSchemaStructureViewModel(PsiFile psiFile) {
        super(psiFile, new TLSchemaStructureViewElementFile(psiFile));
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }


    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return element instanceof TLSchemaFile;
    }
}
