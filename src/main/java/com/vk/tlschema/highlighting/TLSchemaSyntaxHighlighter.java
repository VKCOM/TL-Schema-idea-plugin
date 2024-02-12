package com.vk.tlschema.highlighting;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;
import com.vk.tlschema.TLSchemaLexerAdapter;
import com.vk.tlschema.psi.TLSchemaTypes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

class TLSchemaSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey BareType = createTextAttributesKey("TLSCHEMA_BARE_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey BoxedType = createTextAttributesKey("TLSCHEMA_BOXED_TYPE", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey NumericVar = createTextAttributesKey("TLSCHEMA_NUMERIC_VAR", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey Constructor = createTextAttributesKey("TLSCHEMA_CONSTRUCTOR", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey ConstructorHash = createTextAttributesKey("TLSCHEMA_CONSTRUCTOR_HASH", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey FieldsMask = createTextAttributesKey("TLSCHEMA_FIELDS_MASK", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey Attribute = createTextAttributesKey("TLSCHEMA_ATTRIBUTE",  DefaultLanguageHighlighterColors.METADATA);
    private static final TextAttributesKey COMMENT = createTextAttributesKey("TLSCHEMA_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    private static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("TLSCHEMA_BAD_CHARACTER", new TextAttributes(JBColor.RED, null, null, null, Font.BOLD));
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new TLSchemaLexerAdapter();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (tokenType.equals(TLSchemaTypes.COMMENT)) {
            return COMMENT_KEYS;
        } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
            return BAD_CHAR_KEYS;
        } else {
            return EMPTY_KEYS;
        }
    }
}