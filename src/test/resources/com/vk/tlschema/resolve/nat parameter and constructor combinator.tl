int ? = Int;

bar {n:#} = Bar n;
         // #target

foo {m:#} id:(%Bar m) i:int = Foo m;
            // #ref
