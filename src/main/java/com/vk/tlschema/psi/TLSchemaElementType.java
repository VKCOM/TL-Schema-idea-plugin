package com.vk.tlschema.psi;

import com.intellij.psi.tree.IElementType;
import com.vk.tlschema.TLSchemaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TLSchemaElementType extends IElementType {
    public TLSchemaElementType(@NotNull @NonNls String debugName) {
        super(debugName, TLSchemaLanguage.INSTANCE);
    }
}