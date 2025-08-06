package com.vk.tl2;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.vk.tl2.psi.TL2Types;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.vk.tl2.psi.TL2Types.*;

%%

%{
  public _TL2Lexer() {
    this((java.io.Reader)null);
 }
%}

%class _TL2Lexer
%implements FlexLexer
%function advance
%type IElementType

%unicode

WHITE_SPACE=\s+
INT_NUMBER = [1-9][0-9_]*
LC_NAME = [a-z][a-zA-Z0-9_]*
UC_NAME = [A-Z][a-zA-Z0-9_]*
CRC32 = #[0-9a-f]{8}

LINE_COMMENT = "//" [^\r\n]*

%%
<YYINITIAL> {
    ","             { return COMMA; }
  	";"				{ return SCL; }
  	"@"				{ return AT; }
  	"."				{ return DOT; }
  	":"				{ return CL; }
  	"=>"			{ return FUNEQ; }
  	"="				{ return EQ; }
  	"<"				{ return LTS; }
  	">"				{ return GTS; }
  	"["				{ return LSB; }
  	"]"				{ return RSB; }
  	"|"				{ return VB; }
  	"?"				{ return QM; }
  	"_"				{ return USC; }

  {INT_NUMBER}                 { return INT_NUMBER; }

  {LINE_COMMENT}               { return LINE_COMMENT; }

  {WHITE_SPACE}                { return WHITE_SPACE; }

  {LC_NAME}                     { return LC_NAME; }

  {UC_NAME}                     { return UC_NAME; }

  {CRC32}                       { return CRC32; }

}

[^] { return BAD_CHARACTER; }
