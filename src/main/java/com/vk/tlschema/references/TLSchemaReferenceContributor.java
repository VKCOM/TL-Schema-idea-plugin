package com.vk.tlschema.references;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.vk.tlschema.psi.TLSchemaAttributeNode;
import com.vk.tlschema.psi.TLSchemaLcIdentNs;
import com.vk.tlschema.psi.TLSchemaUcIdentNs;
import com.vk.tlschema.psi.TLSchemaVarIdent;
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