package net.pavelk.tlschema;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import net.pavelk.tlschema.psi.TLSchemaTypes;
import com.intellij.psi.TokenType;

%%

%class TLSchemaLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

WHITE_SPACE=[\ \t\f\n\r]
END_OF_LINE_COMMENT=("//")[^\r\n]*


LC_LETTER = [a-z]
UC_LETTER = [A-Z]
DIGIT = [0-9]
HEX_DIGIT = [0-9a-f]
LETTER = [a-zA-Z]
IDENT_CHAR = [a-zA-Z0-9_]
SHARP = [#]
AT = [@]


%state YYINITIAL
%state YYTMINUS

%%

<YYINITIAL> --- { yybegin(YYTMINUS); return TLSchemaTypes.TRIPPLE_MINUS; }
<YYTMINUS> types { yybegin(YYTMINUS); return TLSchemaTypes.TYPES_KW; }
<YYTMINUS> functions { yybegin(YYTMINUS); return TLSchemaTypes.FUNCTIONS_KW; }
<YYTMINUS> --- { yybegin(YYINITIAL); return TLSchemaTypes.TRIPPLE_MINUS; }


<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return TLSchemaTypes.COMMENT; }

<YYINITIAL> {LC_LETTER} {IDENT_CHAR}*                       { yybegin(YYINITIAL); return TLSchemaTypes.LC_IDENT; }

<YYINITIAL> {UC_LETTER} {IDENT_CHAR}*                       { yybegin(YYINITIAL); return TLSchemaTypes.UC_IDENT; }

<YYINITIAL> {DIGIT}+                                        { yybegin(YYINITIAL); return TLSchemaTypes.NAT_CONST; }

<YYINITIAL> {SHARP} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT}                     { yybegin(YYINITIAL); return TLSchemaTypes.SHARP_HEX_NUMBER; }

<YYINITIAL> {WHITE_SPACE}+                                  { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

<YYINITIAL> {SHARP}                                         { yybegin(YYINITIAL); return TLSchemaTypes.SHARP; }

<YYINITIAL> {AT} {LC_LETTER}*                               { yybegin(YYINITIAL); return TLSchemaTypes.ATTRIBUTE; }


<YYINITIAL> \) { yybegin(YYINITIAL); return TLSchemaTypes.BRCLOSE; }
<YYINITIAL> \( { yybegin(YYINITIAL); return TLSchemaTypes.BROPEN; }
<YYINITIAL> : { yybegin(YYINITIAL); return TLSchemaTypes.COLON; }
<YYINITIAL> , { yybegin(YYINITIAL); return TLSchemaTypes.COMA; }
<YYINITIAL> ] { yybegin(YYINITIAL); return TLSchemaTypes.SQBRCLOSE; }
<YYINITIAL> \[ { yybegin(YYINITIAL); return TLSchemaTypes.SQBROPEN; }
<YYINITIAL> \. { yybegin(YYINITIAL); return TLSchemaTypes.DOT; }
<YYINITIAL> = { yybegin(YYINITIAL); return TLSchemaTypes.EQMARK; }
<YYINITIAL> \! { yybegin(YYINITIAL); return TLSchemaTypes.EXCMARK; }
<YYINITIAL> \> { yybegin(YYINITIAL); return TLSchemaTypes.GEQ; }
<YYINITIAL> \< { yybegin(YYINITIAL); return TLSchemaTypes.LEQ; }
<YYINITIAL> \% { yybegin(YYINITIAL); return TLSchemaTypes.PERCENT; }
<YYINITIAL> \? { yybegin(YYINITIAL); return TLSchemaTypes.QMARK; }
<YYINITIAL> ; { yybegin(YYINITIAL); return TLSchemaTypes.SEMICOLON; }
<YYINITIAL> } { yybegin(YYINITIAL); return TLSchemaTypes.CRBRCLOSE; }
<YYINITIAL> \{ { yybegin(YYINITIAL); return TLSchemaTypes.CRBROPEN; }
<YYINITIAL> _ { yybegin(YYINITIAL); return TLSchemaTypes.UNDERSCORE; }
<YYINITIAL> \* { yybegin(YYINITIAL); return TLSchemaTypes.ASTERISK; }
<YYINITIAL> \+ { yybegin(YYINITIAL); return TLSchemaTypes.PLUS; }


.                                                           { return TokenType.BAD_CHARACTER; }