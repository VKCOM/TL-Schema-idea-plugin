---types---

// Builtin types
int#a8509bda ? = Int;
long#22076cba ? = Long;
float#824dab22 ? = Float;    // 4 bytes -- single precision
double#2210c154 ? = Double;  // 8 bytes -- double precision
string#b5286e24 ? = String;

// Common aliases with checks
positiveInt int = PositiveInt;
positiveLong long = PositiveLong;
nonNegativeInt int = NonNegativeInt;
nonNegativeLong long = NonNegativeLong;

// Boolean emulation
boolFalse#bc799737 = Bool;
boolTrue#997275b5 = Bool;

// Boolean for diagonal queries
boolStat statTrue:int statFalse:int statUnknown:int = BoolStat;

// Vector
vector#1cb5c415 {t:Type} # [t] = Vector t;
tuple#9770768a {t:Type} {n:#} [t] = Tuple t n;
vectorTotal {t:Type} total_count:int vector:%(Vector t) = VectorTotal t;


statOne key:string value:string = StatOne;
// stat vector:%(Vector %statOne) = Stat;
stat#9d56e6b2 %(Dictionary string) = Stat;

dictionaryField {t:Type} key:string value:t = DictionaryField t;
dictionary#1f4c618f {t:Type} %(Vector %(DictionaryField t)) = Dictionary t;
