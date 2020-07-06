# TL Schema support for JetBrains IDE

[Plugin page](https://plugins.jetbrains.com/plugin/7947-tl-schema-support)

TL schema describes types and functions for rpc protocol and binary serialization format. You can read about it [here](https://core.telegram.org/mtproto/TL). TL is used primarily by VK and Telegram.

This plugin brings lexer/parser, highlighting, go to and other stuff for .tl files.

## Installation

Just install the plugin to any IDE from Marketplace in *Settings > Plugins*.

## Development and contribution

**Note!** This plugin was initially developed in 2016, it doesn't use Gradle, it's just plain Java project.

**How to complile .jar**. Open project in IDEA and choose *Platform Plugin SDK* as Project SDK. 
Make sure that [Grammar Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) plugin is installed. 
Generate parser from .bnf file (using context menu), then execute JFlex on .flex file (also with context menu).
Include *src/* and *gen/* folders as sources of module.
Define an artifact from compiler output adding *META-INF* folder.
Finally, produced .jar is supposed to be installed to development IDE instance.

    
