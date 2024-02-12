package com.vk.tlschema.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.vk.tlschema.TLSchemaIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class TLSchemaColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Bare Type", TLSchemaSyntaxHighlighter.BareType),
            new AttributesDescriptor("Boxed Type", TLSchemaSyntaxHighlighter.BoxedType),
            new AttributesDescriptor("Numeric variable", TLSchemaSyntaxHighlighter.NumericVar),
            new AttributesDescriptor("Constructor", TLSchemaSyntaxHighlighter.Constructor),
            new AttributesDescriptor("Constructor Hash", TLSchemaSyntaxHighlighter.ConstructorHash),
            new AttributesDescriptor("Fields Mask", TLSchemaSyntaxHighlighter.FieldsMask),
            new AttributesDescriptor("Attributes", TLSchemaSyntaxHighlighter.Attribute),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return TLSchemaIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new TLSchemaSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "TLSchema";
    }
}