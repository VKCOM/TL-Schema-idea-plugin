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

package net.pavelk.tlschema.search;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaUcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaVarIdent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TLSchemaChooseByNameContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        List<String> result = new ArrayList<>();
        result.addAll(TLSchemaSearchUtils.findUcIdents(project).stream().map(TLSchemaUcIdentNs::getName).collect(Collectors.toList()));
        result.addAll(TLSchemaSearchUtils.findLcIdents(project).stream().map(TLSchemaLcIdentNs::getName).collect(Collectors.toList()));
        result.addAll(TLSchemaSearchUtils.findVarIdents(project).stream().map(TLSchemaVarIdent::getName).collect(Collectors.toList()));
        return result.toArray(new String[result.size()]);
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        List<NavigationItem> result = new ArrayList<>();
        result.addAll(TLSchemaSearchUtils.findUcIdents(project, name));
        result.addAll(TLSchemaSearchUtils.findLcIdents(project, name));
        result.addAll(TLSchemaSearchUtils.findVarIdents(project, name));
        return result.toArray(new NavigationItem[result.size()]);
    }
}
