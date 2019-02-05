grammar Pipeline;

pipeline : ( something ( Pipe something )* )? EOF;

something : ( Assignment | WeakString | StrongString | AnythingElse )+ ;

Assignment : AnythingElse '=' ( WeakString | StrongString | AnythingElse );

WeakString : '"' (.)*? '"' ;

StrongString : '\'' .*? '\'' ;

AnythingElse : ~('|' | '"' | '\'' | '=')+ ;

Pipe : '|' ;

WS : (' ' | '\t' | '\r'| '\n') -> skip;
