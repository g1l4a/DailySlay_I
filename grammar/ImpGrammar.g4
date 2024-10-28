grammar ImpGrammar;

TK_VARNAME : [a-zA-Z_][a-zA-Z0-9_.]*;

// Parser rules
program
    : (simpleDeclaration | routineDeclaration | statement)* EOF
    ;

// Simple Declarations
simpleDeclaration
    : variableDeclaration
    | typeDeclaration
    ;

// Variable Declaration
variableDeclaration
    : 'var' TK_VARNAME ':' type ('is' expression)?   // with type and optional expression
    | 'var' TK_VARNAME 'is' expression               // inferred type from expression
    ;

// Type Declaration
typeDeclaration
    : 'type' TK_VARNAME 'is' type
    ;

// Types
type
    : primitiveType
    | arrayType
    | recordType
    | TK_VARNAME 
    ;

// Primitive Types
primitiveType
    : 'integer'
    | 'real'
    | 'boolean'
    | 'char'
    ;

// Record Type
recordType
    : 'record' '{' variableDeclaration* '}' 'end'
    ;

// Array Type
arrayType
    : 'array' '[' expression ']' type     // with explicit size
    | 'array' '[]' type                   // sizeless for parameters
    ;

// Routine Declaration (subprograms)
routineDeclaration
    : 'routine' TK_VARNAME '(' parameters? ')' (':' type)? 'is' body 'end'
    ;

// Parameters
parameters
    : parameterDeclaration (',' parameterDeclaration)*
    ;

parameterDeclaration
    : TK_VARNAME ':' type
    ;

// The body can contain declarations and statements
body
    : (simpleDeclaration | statement)*
    ;

// Statements
statement
    : assignment
    | routineCall
    | whileLoop
    | forLoop
    | ifStatement
    | printStatement // Added print statement
    | breakStatement  // Added break statement
    | returnStatement // Added return statement
    ;

// Assignment Statement
assignment
    : modifiablePrimary ':=' expression
    ;

// Routine Call
routineCall
    : TK_VARNAME '(' (expression (',' expression)*)? ')'
    ;

// Print Statement
printStatement
    : 'print' '(' expression ')'
    ;

// Break Statement
breakStatement
    : 'break'
    ;

// Return Statement
returnStatement
    : 'return' expression?
    ;

// While Loop
whileLoop
    : 'while' expression 'loop' body 'end'
    ;

// For Loop
forLoop
    : 'for' TK_VARNAME 'in' (('reverse')? range 'loop' body 'end')
    ;

range
    : expression '..' expression
    ;

// If Statement
ifStatement
    : 'if' expression 'then' body ('else' body)? 'end'
    ;

// Expressions
expression
    : relation (('and' | 'or' | 'xor') relation)*
    ;

relation
    : simple (('<' | '<=' | '>' | '>=' | '=' | '!=') simple)?
    ;

simple
    : factor (('*' | '/' | '%') factor)*
    ;

factor
    : summand (('+' | '-') summand)*
    ;

summand
    : (primary | type)
    | '(' expression ')'
    ;

primary
    : IntegerLiteral
    | RealLiteral
    | 'true'
    | 'false'
    | CharLiteral
    | modifiablePrimary
    | routineCall
    | '(' fieldAssignment (',' fieldAssignment)* ')'
    | 'not' primary
    ;

// Modifiable primary (used for assignments)
modifiablePrimary
    : TK_VARNAME ('.' TK_VARNAME | '[' expression ']')*
    ;

fieldAssignment
    : TK_VARNAME ':' expression
    ;

// Tokens (Literals)
IntegerLiteral : [0-9]+;
RealLiteral    : [0-9]+ '.' [0-9]+;
CharLiteral    : '\'' [a-zA-Z] '\'';


// Ignore whitespace
WS : [ \t]+ -> skip;
NEWLINE : '\r'? '\n' -> channel(HIDDEN);