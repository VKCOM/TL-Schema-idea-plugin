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

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.ui.JBColor;
import net.pavelk.tlschema.psi.TLSchemaTypes;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

class TLSchemaSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey BareType = createTextAttributesKey("TLSCHEMA_BARE_TYPE", SyntaxHighlighterColors.KEYWORD);
    public static final TextAttributesKey BoxedType = createTextAttributesKey("TLSCHEMA_BOXED_TYPE", SyntaxHighlighterColors.KEYWORD);
    public static final TextAttributesKey NumericVar = createTextAttributesKey("TLSCHEMA_NUMERIC_VAR", SyntaxHighlighterColors.NUMBER);
    public static final TextAttributesKey Constructor = createTextAttributesKey("TLSCHEMA_CONSTRUCTOR", SyntaxHighlighterColors.KEYWORD);
    public static final TextAttributesKey ConstructorHash = createTextAttributesKey("TLSCHEMA_CONSTRUCTOR_HASH", SyntaxHighlighterColors.NUMBER);
    public static final TextAttributesKey FieldsMask = createTextAttributesKey("TLSCHEMA_FIELDS_MASK", SyntaxHighlighterColors.STRING);
    private static final TextAttributesKey COMMENT = createTextAttributesKey("TLSCHEMA_COMMENT", SyntaxHighlighterColors.LINE_COMMENT);
    private static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("TLSCHEMA_BAD_CHARACTER", new TextAttributes(JBColor.RED, null, null, null, Font.BOLD));
    private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
    private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new TLSchemaLexer(null));
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