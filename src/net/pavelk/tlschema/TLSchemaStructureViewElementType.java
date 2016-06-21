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

import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import net.pavelk.tlschema.psi.TLSchemaLcIdentNs;
import net.pavelk.tlschema.psi.TLSchemaResultType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TLSchemaStructureViewElementType implements StructureViewTreeElement, SortableTreeElement {
    String type;
    TLSchemaLcIdentNs[] combinators;

    public TLSchemaStructureViewElementType(Project project, String type) {
        this.type = type;
        List<TLSchemaResultType> decls = TLSchemaUtils.findType(project, type);
        List<TLSchemaLcIdentNs> combinators = new ArrayList<>();
        for (TLSchemaResultType decl : decls) {
            if (decl.getDeclaration().getCombinator() != null) {
                combinators.add(decl.getDeclaration().getCombinator());
            }
        }
        this.combinators = combinators.toArray(new TLSchemaLcIdentNs[combinators.size()]);
        Arrays.sort(this.combinators, new Comparator<TLSchemaLcIdentNs>() {
            @Override
            public int compare(TLSchemaLcIdentNs o1, TLSchemaLcIdentNs o2) {
                return o1.getText().compareTo(o2.getText());
            }
        });
    }

    @Override
    public Object getValue() {
        return type;
    }

    @Override
    public void navigate(boolean requestFocus) {
        if (combinators.length != 1) throw new AssertionError();
        ((NavigationItem) combinators[0]).navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return combinators.length == 1;
    }

    @Override
    public boolean canNavigateToSource() {
        return combinators.length == 1;
    }

    @NotNull
    @Override
    public String getAlphaSortKey() {
        return type;
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        return new ItemPresentation() {
            @Nullable
            @Override
            public String getPresentableText() {
                return type;
            }

            @Nullable
            @Override
            public String getLocationString() {
                return null;
            }

            @Nullable
            @Override
            public Icon getIcon(boolean b) {
                return null;
            }
        }
                ;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        if (combinators.length <= 1)
            return new TreeElement[0];
        TreeElement[] result = new TreeElement[combinators.length];
        for (int i = 0; i < combinators.length; i++)
            result[i] = new TLSchemaStructureViewElementCombinator(combinators[i]);
        return result;
    }
}
