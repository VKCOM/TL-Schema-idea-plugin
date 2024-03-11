triangle n:# a:3*[a:int b:int] = Triangle;

// TODO: correct, after correcting the grammar
funnyAnon {n:#} {X:Type}
  k:#
  a:k*[
//    b:n.0?k*[(Pair int X)]
//    c:k.0?n*[int]
  ] = FunnyAnon n X;
