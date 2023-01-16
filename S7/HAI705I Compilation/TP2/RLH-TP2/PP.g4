// Robin L'Huillier 17/10/2022

grammar PP;

progDeclarationExpr returns [PPProg value]:
    (e1 = multipleVarExpr)
    (e2 = multipleDefExpr)
    (e3 = instruction)
    {$value = new PPProg($e1.value, $e2.value, $e3.value);};

multipleDefExpr returns [ArrayList<PPDef> value] @init{$value = new ArrayList<PPDef>();}:
    ( e1 = functionDeclarationExpr {$value.add($e1.value);}
    | e2 = procDeclarationExpr {$value.add($e2.value);}
    )*;

functionDeclarationExpr returns [PPDef value]:
    (e1 = NAME)
    OPENPARENTHESIS (e2 = multipleArgumentsDeclarationExpr) CLOSEPARENTHESIS
    COLON (e3 = typeExpr)
    (e4 = multipleVarExpr)
    (e5 = instruction)
    {$value = new PPFun($e1.text, $e2.value, $e4.value, $e5.value, $e3.value);};

procDeclarationExpr returns [PPDef value]:
    (e1 = NAME)
    OPENPARENTHESIS (e2 = multipleArgumentsDeclarationExpr) CLOSEPARENTHESIS
    (e3 = multipleVarExpr)
    (e4 = instruction)
    {$value = new PPProc($e1.text, $e2.value, $e3.value, $e4.value);};

multipleVarExpr returns [ArrayList<Pair<String,Type>> value] @init{$value = new ArrayList<Pair<String,Type>>();}:
    (e = varDeclarationExpr {$value.add($e.value);})*;

multipleArgumentsDeclarationExpr returns [ArrayList<Pair<String,Type>> value] @init{$value = new ArrayList<Pair<String,Type>>();}:
    e1 = argumentDeclarationExpr {$value.add($e1.value);}
    (COMMA e2 = argumentDeclarationExpr {$value.add($e2.value);})*;

argumentDeclarationExpr returns [Pair<String,Type> value]:
    (e1 = NAME) COLON (e2 = typeExpr) {$value = new Pair($e1.text, $e2.value);};

varDeclarationExpr returns [Pair<String,Type> value]:
    VAR (e1 = NAME) COLON (e2 = typeExpr) {$value = new Pair($e1.text, $e2.value);};

instruction returns [PPInst value]:
    (
    ( e1 = assignExpr {$value = $e1.value;}
    | e2 = skipExpr {$value = $e2.value;}
    | e3 = condExpr {$value = $e3.value;}
    | e4 = procCallExpr {$value = $e4.value;}
    | e5 = whileExpr {$value = $e5.value;}
    ) (SEMICOLON e9 = instruction {$value = new PPSeq($value, $e9.value);})*
    )*;

procCallExpr returns [PPInst value]:
    (e1 = functionNameExpr) OPENPARENTHESIS (e2 = argumentsExpr) CLOSEPARENTHESIS {$value = new PPProcCall($e1.value, $e2.value);};

whileExpr returns [PPInst value]:
    WHILE (e1 = anyValueExpr)
    DO (e2 = instruction)
    {$value = new PPWhile($e1.value, $e2.value);};

skipExpr returns [PPInst value]:
    SKIPWHILE {$value = new PPSkip();};

condExpr returns [PPInst value]:
    IF (e1 = anyValueExpr)
    THEN (e2 = instruction)
    ELSE (e3 = instruction)
    {$value = new PPCond($e1.value, $e2.value, $e3.value);};

assignExpr returns [PPInst value]:
    e1 = assignVarExpr {$value = $e1.value;}
    | e2 = assignArraySetExpr {$value = $e2.value;};

assignArraySetExpr returns [PPInst value]:
    (e1 = varNameExpr)
    (e2 = arrayBracketExpr)
    ASSIGN
    (e3 = anyValueExpr)
    {$value = new PPArraySet($e1.value, $e2.value, $e3.value);};

assignVarExpr returns [PPInst value]:
    (e1 = NAME) ASSIGN (e2 = anyValueExpr) {$value = new PPAssign($e1.text, $e2.value);};

functionCallExpr returns [PPExpr value]:
    (e1 = functionNameExpr) OPENPARENTHESIS (e2 = argumentsExpr) CLOSEPARENTHESIS {$value = new PPFunCall($e1.value, $e2.value);};

functionNameExpr returns [Callee value]:
    READ {$value = new Read();}
    | WRITE {$value = new Write();}
    | f = NAME {$value = new User($f.text);};

argumentsExpr returns [ArrayList<PPExpr> value] @init{$value = new ArrayList<PPExpr>();}:
     (e = anyValueExpr {$value.add($e.value);} (COMMA)?)*;

arrayGetExpr returns [PPExpr value]:
    e1 = varNameExpr {$value = $e1.value;}
    (e2 = arrayBracketExpr {$value = new PPArrayGet($value, $e2.value);})*;

arrayAllocExpr returns [PPExpr value]:
    NEW (e1 = typeExpr) (e2 = arrayBracketExpr) {$value = new PPArrayAlloc($e1.value, $e2.value);}; 

arrayBracketExpr returns [PPExpr value]:
    OPENARRAY (e1 = anyValueContentExpr) CLOSEARRAY {$value = $e1.value;};

varNameExpr returns [PPExpr value]:
    e1 = NAME {$value = new PPVar($e1.text);};

typeExpr returns [Type value]:
    INTEGER {$value = new Int();}
    | BOOL {$value = new Bool();}
    | ARRAY OF e1 = typeExpr {$value = new Array($e1.value);};

anyValueExpr returns [PPExpr value]:
    e5 = comparisonExpr {$value = $e5.value;};

comparisonExpr returns [PPExpr value]:
    e1 = anyValueContentExpr {$value = $e1.value;}
    ( CPLT e2 = anyValueContentExpr {$value = new PPLt($value, $e2.value);}
    | CPLE e2 = anyValueContentExpr {$value = new PPLe($value, $e2.value);}
    | CPEQ e2 = anyValueContentExpr {$value = new PPEq($value, $e2.value);}
    | CPNE e2 = anyValueContentExpr {$value = new PPNe($value, $e2.value);}
    | CPGE e2 = anyValueContentExpr {$value = new PPGe($value, $e2.value);}
    | CPGT e2 = anyValueContentExpr {$value = new PPGt($value, $e2.value);}
    )?;

anyValueContentExpr returns [PPExpr value]:
    e1 = additionExpr {$value = $e1.value;}
    | e2 = booleanExpr {$value = $e2.value;};

booleanExpr returns [PPExpr value]:
    e1 = atomExpr {$value = $e1.value;}
    ( AND e2 = atomExpr {$value = new PPAnd($value, $e2.value);}
    | OR e2 = atomExpr {$value = new PPOr($value, $e2.value);}
    )*;

additionExpr returns [PPExpr value]:
    e1 = multiplyExpr {$value = $e1.value;}
    ( '+' e2 = multiplyExpr {$value = new PPAdd($value, $e2.value);}
    | '-' e2 = multiplyExpr {$value = new PPSub($value, $e2.value);}
    )*;

multiplyExpr returns [PPExpr value]:
    e1 = atomExpr {$value = $e1.value;}
    ('*' e2 = atomExpr {$value = new PPMul($value, $e2.value);}
    |'/' e2 = atomExpr {$value = new PPDiv($value, $e2.value);})*;

atomExpr returns [PPExpr value]:
    n = INT {$value = new PPCte(Integer.parseInt($n.text));}
    | b = boolCte {$value = $b.value;}
    | OPENPARENTHESIS e1 = anyValueExpr CLOSEPARENTHESIS {$value = $e1.value;}
    | '-' f = atomExpr {$value = new PPInv($f.value);}
    | NOT no = atomExpr {$value = new PPNot($no.value);}
    | v = varNameExpr {$value = $v.value;}
    | a = arrayGetExpr {$value = $a.value;}
    | fc = functionCallExpr {$value = $fc.value;}
    ;

boolCte returns [PPExpr value]:
    TRUE {$value = new PPTrue();}
    | FALSE {$value = new PPFalse();};

INT: ('0'..'9')+;
OR: 'or';
AND: 'and';
NOT: 'not';
TRUE: 'true';
FALSE: 'false';
CPLT: '<';
CPLE: '<=';
CPEQ: '==';
CPNE: '!=';
CPGE: '>=';
CPGT: '>';
INTEGER: 'integer';
BOOL: 'boolean';
ARRAY: 'array';
OF: 'of';
NEW: 'new';
OPENARRAY: '[';
CLOSEARRAY: ']';
OPENPARENTHESIS: '(';
CLOSEPARENTHESIS: ')';
ASSIGN: ':=';
SEMICOLON: ';';
IF: 'if';
THEN: 'then';
ELSE: 'else';
WHILE: 'while';
DO: 'do';
READ: 'read';
WRITE: 'write';
SKIPWHILE: 'skip';
COLON: ':';
COMMA: ',';
VAR: 'var';
fragment CHAR: ('a'..'z') | ('A'..'Z');
fragment ADVANCEDCHAR: CHAR | '_' | INT;
NAME: CHAR(ADVANCEDCHAR)*;
WS: [ \t\n\r]+ -> skip;
