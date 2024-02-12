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

import com.intellij.lang.refactoring.NamesValidator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class TLSchemaNamesValidator implements NamesValidator {
    private final Pattern regexp1 = Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");
    private final Pattern regexp2 = Pattern.compile("[a-z][a-zA-Z0-9_]*\\.[a-zA-Z][a-zA-Z0-9_]*");

    @Override
    public boolean isKeyword(@NotNull String name, Project project) {
        return name.equals("_");
    }

    @Override
    public boolean isIdentifier(@NotNull String name, Project project) {
        return regexp1.matcher(name).matches() || regexp2.matcher(name).matches();
    }
}
