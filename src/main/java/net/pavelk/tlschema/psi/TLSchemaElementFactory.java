package net.pavelk.tlschema.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import net.pavelk.tlschema.TLSchemaFileType;

public class TLSchemaElementFactory {
    public static TLSchemaLcIdentNs createLcIdentNs(Project project, String name) {
        final TLSchemaFile file = createFile(project, String.format("%s = A;", name));
        return ((TLSchemaConstructorDeclarations) file.getFirstChild())
                .getDeclarationList()
                .get(0)
                .getCombinator();
    }

    public static TLSchemaUcIdentNs createUcIdentNs(Project project, String name) {
        final TLSchemaFile file = createFile(project, String.format("a = %s;", name));
        return ((TLSchemaConstructorDeclarations) file.getFirstChild())
                .getDeclarationList()
                .get(0)
                .getResultType()
                .getBoxedTypeIdent()
                .getUcIdentNs();
    }

    public static TLSchemaVarIdent createVarIdent(Project project, String name) {
        final TLSchemaFile file = createFile(project, String.format("a %s:int = A;", name));
        return ((TLSchemaConstructorDeclarations) file.getFirstChild())
                .getDeclarationList()
                .get(0)
                .getCombinatorDecl()
                .getArgList()
                .get(0)
                .getArgNamed()
                .getVarIdentOpt()
                .getVarIdent();
    }

    private static TLSchemaFile createFile(Project project, String text) {
        String name = "dummy.tlschema";
        return (TLSchemaFile) PsiFileFactory.getInstance(project).
                createFileFromText(name, TLSchemaFileType.INSTANCE, text);
    }
}