package com.vk.tlschema.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TLSchemaNamedElementReferenceBase extends PsiReferenceBase<PsiNamedElement> implements PsiPolyVariantReference {
    final String myName;

    TLSchemaNamedElementReferenceBase(PsiNamedElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        myName = element.getName();
    }

    @Override
    public boolean isReferenceTo(@NotNull PsiElement element) {
        if (!(element instanceof PsiNamedElement)) {
            return false;
        }
        String name1 = ((PsiNamedElement) element).getName();
        String myName = myElement.getName();
        if (name1 == null || !name1.equals(myName)) {
            return false;
        }

        // TODO wtf?!?!
        PsiReference[] references = ReferenceProvidersRegistry.getReferencesFromProviders(element);
        if (references.length == 0) {
            return false;
        }
        PsiReference reference = references[0];
        if (reference instanceof PsiPolyVariantReferenceBase) {
            ResolveResult[] resolveResults = ((PsiPolyVariantReferenceBase) reference).multiResolve(false);
            if (resolveResults.length == 0) {
                return false;
            }
            element = resolveResults[0].getElement();
        } else {
            element = references[0].resolve();
        }
        if (element == null) {
            return false;
        }
        return super.isReferenceTo(element);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {
        return myElement.setName(newElementName);
    }
}
