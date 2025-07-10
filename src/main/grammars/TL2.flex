package com.vk.tl2;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.vk.tl2.psi.TL2Types;
import com.intellij.psi.TokenType;

%%

%class TL2Lexer
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

//<YYINITIAL> --- { yybegin(YYTMINUS); return TL2Types.TRIPPLE_MINUS; }
//<YYTMINUS> types { yybegin(YYTMINUS); return TL2Types.TYPES_KW; }
//<YYTMINUS> functions { yybegin(YYTMINUS); return TL2Types.FUNCTIONS_KW; }
//<YYTMINUS> --- { yybegin(YYINITIAL); return TL2Types.TRIPPLE_MINUS; }
//
//
//<YYINITIAL> {END_OF_LINE_COMMENT}                           { yybegin(YYINITIAL); return TL2Types.COMMENT; }
//
//<YYINITIAL> {LC_LETTER} {IDENT_CHAR}*                       { yybegin(YYINITIAL); return TL2Types.LC_IDENT; }
//
//<YYINITIAL> {UC_LETTER} {IDENT_CHAR}*                       { yybegin(YYINITIAL); return TL2Types.UC_IDENT; }
//
<YYINITIAL> {DIGIT}+                                        { yybegin(YYINITIAL); return TL2Types.NAT_CONST; }
//
//<YYINITIAL> {SHARP} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT} {HEX_DIGIT}                     { yybegin(YYINITIAL); return TL2Types.SHARP_HEX_NUMBER; }
//
//<YYINITIAL> {WHITE_SPACE}+                                  { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }
//
//<YYINITIAL> {SHARP}                                         { yybegin(YYINITIAL); return TL2Types.SHARP; }
//
//<YYINITIAL> {AT} {LC_LETTER}*                               { yybegin(YYINITIAL); return TL2Types.ATTRIBUTE; }
//
//
//<YYINITIAL> \) { yybegin(YYINITIAL); return TL2Types.BRCLOSE; }
//<YYINITIAL> \( { yybegin(YYINITIAL); return TL2Types.BROPEN; }
//<YYINITIAL> : { yybegin(YYINITIAL); return TL2Types.COLON; }
<YYINITIAL> , { yybegin(YYINITIAL); return TL2Types.COMA; }
//<YYINITIAL> ] { yybegin(YYINITIAL); return TL2Types.SQBRCLOSE; }
//<YYINITIAL> \[ { yybegin(YYINITIAL); return TL2Types.SQBROPEN; }
//<YYINITIAL> \. { yybegin(YYINITIAL); return TL2Types.DOT; }
//<YYINITIAL> = { yybegin(YYINITIAL); return TL2Types.EQMARK; }
//<YYINITIAL> \! { yybegin(YYINITIAL); return TL2Types.EXCMARK; }
//<YYINITIAL> \> { yybegin(YYINITIAL); return TL2Types.GEQ; }
//<YYINITIAL> \< { yybegin(YYINITIAL); return TL2Types.LEQ; }
//<YYINITIAL> \% { yybegin(YYINITIAL); return TL2Types.PERCENT; }
//<YYINITIAL> \? { yybegin(YYINITIAL); return TL2Types.QMARK; }
//<YYINITIAL> ; { yybegin(YYINITIAL); return TL2Types.SEMICOLON; }
//<YYINITIAL> } { yybegin(YYINITIAL); return TL2Types.CRBRCLOSE; }
//<YYINITIAL> \{ { yybegin(YYINITIAL); return TL2Types.CRBROPEN; }
//<YYINITIAL> _ { yybegin(YYINITIAL); return TL2Types.UNDERSCORE; }
//<YYINITIAL> \* { yybegin(YYINITIAL); return TL2Types.ASTERISK; }
//<YYINITIAL> \+ { yybegin(YYINITIAL); return TL2Types.PLUS; }


.                                                           { return TokenType.BAD_CHARACTER; }
