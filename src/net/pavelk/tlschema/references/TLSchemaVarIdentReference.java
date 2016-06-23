package net.pavelk.tlschema.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import net.pavelk.tlschema.TLSchemaIcons;
import net.pavelk.tlschema.psi.*;
import net.pavelk.tlschema.search.TLSchemaSearchUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TLSchemaVarIdentReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private final TLSchemaDeclaration declaration;
    private final String name;

    public TLSchemaVarIdentReference(@NotNull TLSchemaVarIdent element) {
        super(element, TextRange.create(0, element.getTextLength()));
        name = element.getName();
        declaration = element.getDeclaration();
    }

    public TLSchemaVarIdentReference(@NotNull TLSchemaLcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        name = element.getName();
        declaration = element.getDeclaration();
    }

    public TLSchemaVarIdentReference(@NotNull TLSchemaUcIdentNs element) {
        super(element, TextRange.create(0, element.getTextLength()));
        name = element.getName();
        declaration = element.getDeclaration();
    }

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        return ((TLSchemaNamedElement) myElement).setName(newElementName);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        List<TLSchemaVarIdent> idents = TLSchemaSearchUtils.findVarIdents(declaration, name);
        List<ResolveResult> results = new ArrayList<>();
        for (TLSchemaVarIdent ident : idents) {
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

    @NotNull
    @Override
    public Object[] getVariants() {
        List<TLSchemaVarIdent> idents = TLSchemaSearchUtils.findVarIdents(declaration);
        List<LookupElement> variants = new ArrayList<>();
        for (final TLSchemaVarIdent ident : idents) {
            variants.add(LookupElementBuilder.create(ident).
                    withIcon(TLSchemaIcons.FILE).
                    withTypeText(ident.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}