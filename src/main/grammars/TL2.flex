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
INT_NUMBER = [0-9] [0-9_]*

LINE_COMMENT = "//" [^\r\n]*

%%
<YYINITIAL> {
  ","                          { return COMMA; }

  {INT_NUMBER}                 { return INT_NUMBER; }

  {LINE_COMMENT}               { return LINE_COMMENT; }

  {WHITE_SPACE}                { return WHITE_SPACE; }
}

[^] { return BAD_CHARACTER; }
