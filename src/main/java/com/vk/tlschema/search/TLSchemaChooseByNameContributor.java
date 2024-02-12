package com.vk.tlschema.search;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.vk.tlschema.psi.TLSchemaLcIdentNs;
import com.vk.tlschema.psi.TLSchemaUcIdentNs;
import com.vk.tlschema.psi.TLSchemaVarIdent;
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
