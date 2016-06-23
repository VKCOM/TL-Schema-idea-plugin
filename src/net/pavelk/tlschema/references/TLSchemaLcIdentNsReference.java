package net.pavelk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.ResolveResult;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.search.TLSchemaSearchUtils;
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
        List<TLSchemaLcIdentNs> idents = TLSchemaSearchUtils.findLcIdents(project, myName);
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
        List<TLSchemaLcIdentNs> idents = TLSchemaSearchUtils.findLcIdents(project);
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