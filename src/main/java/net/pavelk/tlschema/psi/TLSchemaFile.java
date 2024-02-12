package net.pavelk.tlschema.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import net.pavelk.tlschema.TLSchemaFileType;
import net.pavelk.tlschema.TLSchemaLanguage;
import org.jetbrains.annotations.NotNull;

public class TLSchemaFile extends PsiFileBase {
    public TLSchemaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, TLSchemaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return TLSchemaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "TLSchema File";
    }

}