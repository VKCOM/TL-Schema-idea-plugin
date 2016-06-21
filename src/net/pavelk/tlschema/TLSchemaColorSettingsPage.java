/*
 * Copyright (C) 2015-2016 Pavel Kunyavskiy
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package net.pavelk.tlschema;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
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