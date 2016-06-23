package net.pavelk.tlschema.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import net.pavelk.tlschema.psi.TLSchemaNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class TLSchemaNamedElementImpl extends ASTWrapperPsiElement implements TLSchemaNamedElement {
    public TLSchemaNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}