package net.pavelk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaNamedElement;
import net.pavelk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TLSchemaLcIdentNsReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private final String name;
    private final TLSchemaVarIdentReference varIdentReference;

    public TLSchemaLcIdentNsReference(@NotNull TLSchemaLcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        name = element.getName();
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
        List<TLSchemaLcIdentNs> idents = TLSchemaSearchUtils.findLcIdents(project, name);
        List<ResolveResult> results = new ArrayList<>();
        for (TLSchemaLcIdentNs ident : idents) {
            results.add(new PsiElementResolveResult(ident));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return ((TLSchemaNamedElement) myElement).setName(newElementName);
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