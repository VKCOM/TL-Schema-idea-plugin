package com.vk.tlschema.psi;

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
