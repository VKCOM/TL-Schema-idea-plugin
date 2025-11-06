package com.vk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveResult;
import com.vk.tlschema.TLSchemaIcons;
import com.vk.tlschema.psi.TLSchemaLcIdentNs;
import com.vk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TLSchemaLcIdentNsReference extends TLSchemaNamedElementReferenceBase {
    private final TLSchemaVarIdentReference varIdentReference;

    public TLSchemaLcIdentNsReference(@NotNull TLSchemaLcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        varIdentReference = new TLSchemaVarIdentReference(element);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        ResolveResult[] resultsArray = varIdentReference.multiResolve(incompleteCode);
        if (resultsArray.length != 0) {
            return resultsArray;
        }
        Project project = myElement.getProject();
        PsiFile file = myElement.getContainingFile();
        List<TLSchemaLcIdentNs> idents = TLSchemaSearchUtils.findLcIdents(project, file, myName);
        List<ResolveResult> results = new ArrayList<>();
        for (TLSchemaLcIdentNs ident : idents) {
            results.add(new PsiElementResolveResult(ident));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        PsiFile file = myElement.getContainingFile();
        List<TLSchemaLcIdentNs> idents = TLSchemaSearchUtils.findLcIdents(project, file);
        List<Object> variants = new ArrayList<>(Arrays.asList(varIdentReference.getVariants()));
        for (final TLSchemaLcIdentNs ident : idents) {
            variants.add(LookupElementBuilder.create(ident).
                    withIcon(TLSchemaIcons.FILE).
                    withTypeText(ident.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}
