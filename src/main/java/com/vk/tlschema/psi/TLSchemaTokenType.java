package com.vk.tlschema.psi;

import com.intellij.psi.tree.IElementType;
import com.vk.tlschema.TLSchemaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


public class TLSchemaTokenType extends IElementType {
    public TLSchemaTokenType(@NotNull @NonNls String debugName) {
        super(debugName, TLSchemaLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "TLSchemaTokenType." + super.toString();
    }

}
