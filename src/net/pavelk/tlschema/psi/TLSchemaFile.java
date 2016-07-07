/*
 * Copyright (C)
 *     2015-2016 Pavel Kunyavskiy
 *     2016-2016 Eugene Kurpilyansky
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