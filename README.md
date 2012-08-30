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
	
The script reads words from stdin and writes words with their pronunciations back to stdout.	
	
The script can be also executed from any other directory:

	~/tools/et-g2p/run.sh < vocab.txt


Use as a library
----------------

See `src/java_test` for samples.	 
