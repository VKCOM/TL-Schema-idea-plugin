package net.pavelk.tlschema.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import net.pavelk.tlschema.psi.TLSchemaAttributeNode;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaUcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaVarIdent;
import org.jetbrains.annotations.NotNull;

public class TLSchemaReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(TLSchemaLcIdentNs.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return new PsiReference[]{new TLSchemaLcIdentNsReference((TLSchemaLcIdentNs) element)};
                    }
                });
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(TLSchemaUcIdentNs.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return new PsiReference[]{new TLSchemaUcIdentNsReference((TLSchemaUcIdentNs) element)};
                    }
                });
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(TLSchemaVarIdent.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        return new PsiReference[]{new TLSchemaVarIdentReference((TLSchemaVarIdent) element)};
                    }
                });
    }
}