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
