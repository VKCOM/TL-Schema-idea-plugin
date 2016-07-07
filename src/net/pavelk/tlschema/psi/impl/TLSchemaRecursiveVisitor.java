package net.pavelk.tlschema.psi.impl;

import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import net.pavelk.tlschema.psi.TLSchemaVisitor;
import org.jetbrains.annotations.NotNull;

public class TLSchemaRecursiveVisitor extends TLSchemaVisitor {
    @Override
    public void visitPsiElement(@NotNull PsiElement o) {
        for (PsiElement psiElement : o.getChildren()) {
            psiElement.accept(this);
            ProgressIndicatorProvider.checkCanceled();
        }
    }

    @Override
    public void visitFile(@NotNull PsiFile file) {
        for (PsiElement psiElement : file.getChildren()) {
            psiElement.accept(this);
            ProgressIndicatorProvider.checkCanceled();
        }
    }
}
