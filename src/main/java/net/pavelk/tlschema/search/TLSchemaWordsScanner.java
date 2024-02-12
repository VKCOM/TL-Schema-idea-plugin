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

import com.intellij.lang.cacheBuilder.WordOccurrence;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;

public class TLSchemaWordsScanner implements WordsScanner {
    private final Lexer lexer;
    private final TokenSet identifierTokenSet;

    TLSchemaWordsScanner(Lexer lexer, TokenSet identifierTokenSet) {
        this.lexer = lexer;
        this.identifierTokenSet = identifierTokenSet;
    }

    @Override
    public void processWords(@NotNull CharSequence fileText, @NotNull Processor<? super WordOccurrence> processor) {
        lexer.start(fileText);
        WordOccurrence occurrence = new WordOccurrence(fileText, 0, 0, null); // shared occurrence

        IElementType type;
        boolean isStarted = false;
        while ((type = lexer.getTokenType()) != null) {
            if (identifierTokenSet.contains(type)) {
                if (isStarted) {
                    occurrence.init(fileText, occurrence.getStart(), lexer.getTokenEnd(), WordOccurrence.Kind.CODE);
                } else {
                    occurrence.init(fileText, lexer.getTokenStart(), lexer.getTokenEnd(), WordOccurrence.Kind.CODE);
                    isStarted = true;
                }
            } else {
                if (isStarted) {
                    if (!processor.process(occurrence)) return;
                    isStarted = false;
                }
            }
            lexer.advance();
        }
        if (isStarted) {
            if (!processor.process(occurrence)) return;
        }
    }
}
