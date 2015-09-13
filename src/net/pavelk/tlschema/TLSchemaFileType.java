/*
 * Copyright (C) 2015 Pavel Kunyavskiy
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
