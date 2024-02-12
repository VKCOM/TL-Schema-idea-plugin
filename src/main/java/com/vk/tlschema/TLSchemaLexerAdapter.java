package com.vk.tlschema;

import com.intellij.lexer.FlexAdapter;

public class TLSchemaLexerAdapter extends FlexAdapter {
    public TLSchemaLexerAdapter() {
        super(new TLSchemaLexer(null));
    }
}