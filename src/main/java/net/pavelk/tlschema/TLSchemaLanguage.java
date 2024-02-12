package net.pavelk.tlschema;

import com.intellij.lang.Language;

public class TLSchemaLanguage extends Language {
    public static final TLSchemaLanguage INSTANCE = new TLSchemaLanguage();

    private TLSchemaLanguage() {
        super("TLSchema");
    }

}
