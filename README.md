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


The tool supports user-defined transliteration dictionaries. This allows to define pronunciations for 
words that are pronounced differently from the Estonian rules and which are not defined in the 
built-in exception list.

Each line in the dictionary contains the original word and its transliteration (i.e., its
probable ortographic form as if it was an Estonian word). Multiple transliterations can be given,
seperated by commas. For example:

    Jules      žül
    Henri      henri, anrii
    Poincaré   puangarree


Use the -dict option to set the user dictionary:

    echo "Henri" | ./run.sh -dict tmp.dict
    Henri	a n r i i
    Henri(2)	h e n r i



Bugs
----

Pronunciation of foreign names is based on a short list of exceptions. As a result, for most
English and French names, pronunciation is generated according to Estonian rules, which is 
of course wrong (see `Poincaré` above).


Use as a library
----------------

See `src/java_test` for samples.	 
