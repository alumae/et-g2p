Grapheme to phoneme rules for Estonian
======================================

Implements a naive rule-based G2P converter for Estonian. 

Note:
* Compound words should have their compound constituents separated with and underscore in input (e.g. 'hamba_kivi')
 


Compile
-------

	ant 
	
Test
----

	ant test
	
Run
---
	
	$ echo "kesk_pank" | ./run.sh 
	kesk_pank	k e s k p a n kk
	$
	
The script reads words from stdin and writes words with their pronunciations back to stdout. Usually it's used to process 
a long list of words at a time:

	$ ./run.sh < example/sample.vocab 
    kana    k a n a
    park    p a r kk
    näinud  n ae i n u t
    näinud(2)   n ae i n t
    kontsa  k o n t s a
    kesk_pank   k e s k p a n kk
    OECD    o o e t s e e t e e
    ETV24   e t e e v e e k a k s k ue m m e n t n e l i
    ETV24-le    e t e e v e e k a h e k ue m n e n e l j a l e
    NATO    n a tt o
    ABC-pood    a a p e e t s e e p o o t
    René    r e n e
    Poincaré    p o i n kk a r e
	$
	
The script can be also executed from any other directory:

	~/tools/et-g2p/run.sh < vocab.txt

Bugs
----

Pronunciation of foreign names is based on a short list of exceptions. As a result, for most
English and French names, pronunciation is generated according to Estonian rules, which is 
of course wrong (see `Poincaré` above).


Use as a library
----------------

See `src/java_test` for samples.	 
