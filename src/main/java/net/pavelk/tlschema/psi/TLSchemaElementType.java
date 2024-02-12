package net.pavelk.tlschema.psi;

import com.intellij.psi.tree.IElementType;
import net.pavelk.tlschema.TLSchemaLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class TLSchemaElementType extends IElementType {
    public TLSchemaElementType(@NotNull @NonNls String debugName) {
        super(debugName, TLSchemaLanguage.INSTANCE);
    }
}