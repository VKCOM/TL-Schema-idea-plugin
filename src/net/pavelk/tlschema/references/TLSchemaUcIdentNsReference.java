package net.pavelk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.TLSchemaNamedElement;
import net.pavelk.tlschema.psi.TLSchemaUcIdentNs;
import net.pavelk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TLSchemaUcIdentNsReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private final String name;
    private final TLSchemaVarIdentReference varIdentReference;

    public TLSchemaUcIdentNsReference(@NotNull TLSchemaUcIdentNs element) {
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
        List<TLSchemaUcIdentNs> idents = TLSchemaSearchUtils.findUcIdents(project, name);
        List<ResolveResult> results = new ArrayList<>();
        for (TLSchemaUcIdentNs ident : idents) {
            results.add(new PsiElementResolveResult(ident));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return ((TLSchemaNamedElement) myElement).setName(newElementName);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<TLSchemaUcIdentNs> idents = TLSchemaSearchUtils.findUcIdents(project);
        List<Object> variants = new ArrayList<>(Arrays.asList(varIdentReference.getVariants()));
        for (final TLSchemaUcIdentNs ident : idents) {
            variants.add(LookupElementBuilder.create(ident).
                    withIcon(TLSchemaIcons.FILE).
                    withTypeText(ident.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}