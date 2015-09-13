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


/*
  IElementType BRCLOSE = new TLSchemaTokenType("BRCLOSE");
  IElementType BROPEN = new TLSchemaTokenType("BROPEN");
  IElementType COLON = new TLSchemaTokenType("COLON");
  IElementType COMA = new TLSchemaTokenType("COMA");
  IElementType CRBRCLOSE = new TLSchemaTokenType("CRBRCLOSE");
  IElementType CRBROPEN = new TLSchemaTokenType("CRBROPEN");
  IElementType DOT = new TLSchemaTokenType("DOT");
  IElementType EMPTY_KW = new TLSchemaTokenType("EMPTY_KW");
  IElementType EQMARK = new TLSchemaTokenType("EQMARK");
  IElementType EXCMARK = new TLSchemaTokenType("EXCMARK");
  IElementType FUNCTIONS_KW = new TLSchemaTokenType("FUNCTIONS_KW");
  IElementType GEQ = new TLSchemaTokenType("GEQ");
  IElementType LEQ = new TLSchemaTokenType("LEQ");
  IElementType PERCENT = new TLSchemaTokenType("PERCENT");
  IElementType QMARK = new TLSchemaTokenType("QMARK");
  IElementType SEMICOLON = new TLSchemaTokenType("SEMICOLON");
  IElementType SHARP = new TLSchemaTokenType("SHARP");
  IElementType SQBRCLOSE = new TLSchemaTokenType("SQBRCLOSE");
  IElementType SQBROPEN = new TLSchemaTokenType("SQBROPEN");
  IElementType TRIPPLE_MINUS = new TLSchemaTokenType("TRIPPLE_MINUS");
  IElementType TYPES_KW = new TLSchemaTokenType("TYPES_KW");
  IElementType UNDERSCORE = new TLSchemaTokenType("UNDERSCORE");
*/

LC_LETTER = [a-z]
UC_LETTER = [A-Z]
DIGIT = [0-9]
HEX_DIGIT = [0-9a-f]
LETTER = [a-zA-Z]
IDENT_CHAR = [a-zA-Z0-9_]
SHARP = [#]


%state YYINITIAL
%state YYTMINUS

%%

<YYINITIAL> Empty { yybegin(YYINITIAL); return TLSchemaTypes.EMPTY_KW; }
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