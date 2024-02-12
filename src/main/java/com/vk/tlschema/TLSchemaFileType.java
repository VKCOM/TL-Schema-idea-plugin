package com.vk.tlschema;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TLSchemaFileType extends LanguageFileType {
    public static final TLSchemaFileType INSTANCE = new TLSchemaFileType();

    private TLSchemaFileType() {
        super(TLSchemaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "TL schema file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "TL schema file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "tl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return TLSchemaIcons.FILE;
    }

}
