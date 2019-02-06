grammar Pipeline;

pipeline : ( command ( Pipe command )* )? EOF;

command : ( Assignment | WeakString | StrongString | VariableLike )+ ;

Assignment : VariableLike '=' ( WeakString | StrongString | VariableLike );

WeakString : '"' (.)*? '"' ;

StrongString : '\'' .*? '\'' ;

VariableLike : ~('|' | '"' | '\'' | '=' | ' ' | '\t' | '\r'| '\n')+ ;

Pipe : '|' ;

WS : (' ' | '\t' | '\r'| '\n') -> skip;
