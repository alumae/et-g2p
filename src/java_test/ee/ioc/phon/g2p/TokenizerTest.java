package ee.ioc.phon.g2p;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class TokenizerTest {

	List<String> specialTokens;
	
	public TokenizerTest() {
		Map rulesMap = Utils.loadYamlFile("rules.yaml");
	    specialTokens = new LinkedList<String>();
	    for (String ruleStr : ((String)rulesMap.get("abbreviations")).split("\n")) {
	    	String [] parts = ruleStr.split("\\s+");
	    	if (parts.length > 0) {
	    		specialTokens.add(parts[0]);
	    	}
	    }
	}
	
	@Test
	public void basic() {
		Tokenizer tok = new Tokenizer(specialTokens);
		assertEquals("Tanel", Utils.arrayToString(tok.tokenize("Tanel")));
		assertEquals("Ügaaž", Utils.arrayToString(tok.tokenize("Ügaaž")));
		assertEquals("Apple iga", Utils.arrayToString(tok.tokenize("Apple`iga")));
		assertEquals("Wiki Leaks", Utils.arrayToString(tok.tokenize("WikiLeaks")));
	}

	@Test
	public void abbr() {
		Tokenizer tok = new Tokenizer(specialTokens);
		assertEquals("S K P", Utils.arrayToString(tok.tokenize("SKP")));
		assertEquals("E T V 24", Utils.arrayToString(tok.tokenize("ETV24")));
		assertEquals("E T V 24", Utils.arrayToString(tok.tokenize("ETV24")));
		assertEquals("E T V 24 le", Utils.arrayToString(tok.tokenize("ETV24-le")));
		assertEquals("Vene E L i", Utils.arrayToString(tok.tokenize("Vene-ELi")));
		assertEquals("B N S Reuters", Utils.arrayToString(tok.tokenize("BNS//Reuters")));
		assertEquals("tere K K", Utils.arrayToString(tok.tokenize("tereKK")));
		assertEquals("E 0 1", Utils.arrayToString(tok.tokenize("E01")));
		assertEquals("TH 12", Utils.arrayToString(tok.tokenize("TH12")));
		
	}
	
	@Test
	public void specials() {
		Tokenizer tok = new Tokenizer(specialTokens);
		assertEquals("NATO", Utils.arrayToString(tok.tokenize("NATO")));
		assertEquals("NATO ga", Utils.arrayToString(tok.tokenize("NATO-ga")));
		assertEquals("B N S ile", Utils.arrayToString(tok.tokenize("BNSile")));
		assertEquals("G 8 NATO ga", Utils.arrayToString(tok.tokenize("G8/NATO-ga")));
		assertEquals("SmartPOST i", Utils.arrayToString(tok.tokenize("SmartPOSTi")));
		assertEquals("iPad", Utils.arrayToString(tok.tokenize("iPad")));
		assertEquals("iPad ide", Utils.arrayToString(tok.tokenize("iPadide")));
		assertEquals("üle jäänud", Utils.arrayToString(tok.tokenize("üle+jäänud")));
		
	}
	
}
