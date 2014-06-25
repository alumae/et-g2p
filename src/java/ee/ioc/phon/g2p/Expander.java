package ee.ioc.phon.g2p;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Expander {

	private Map<String, List<String>> dict;
	private Map<String, List<String>> spell;
	private Map<String, List<String>> abbreviations;
	private Map<String, List<String>> numbersNimetav;
	private Map<String, List<String>> numbersOmastav;

	
	public Expander(Map rulesMap, Map numbersMap, Map<String, List<String>> userDict) {
		spell = Utils.linesToMap(rulesMap, "spell");
		dict = Utils.linesToMap(rulesMap, "dict");
		dict.putAll(userDict);
		abbreviations =  Utils.linesToMap(rulesMap, "abbreviations");
		numbersNimetav = Utils.linesToMap(numbersMap, "nimetav");
		numbersOmastav = Utils.linesToMap(numbersMap, "omastav");
		
	}

	public Expander(Map rulesMap, Map numbersMap) {
		this(rulesMap, numbersMap, new HashMap<String, List<String>>());
	}
	
	private List<String> append(List<String> appendTos, String value) {
		List<String> tmp = new ArrayList<String>(1);
		tmp.add(value);
		return appendAndFork(appendTos, tmp);
	}
	
	private List<String> appendAndFork(List<String> appendTos, List<String> values) {
		List<String> result = new ArrayList<String>();
		for (String value : values) {
			for (String appendTo : appendTos) {
				result.add(appendTo  + value);
			}
		}
		return result;
	}
	
	public Set<String> expand(String [] tokens) throws TooComplexWordException {
		List<String> result = new ArrayList<String>();
		result.add("");
		
		for (int i=0; i<tokens.length; i++) {
			
			if (i > 0) {
				result = append(result, " ");
			}
		
			if (tokens[i].matches("\\d+")) {
				if ((!tokens[i].startsWith("0")) && (Long.parseLong(tokens[i]) <= 9000)) {
					// [20 le] -> [<20 omastav> le]
					if ((i == tokens.length - 2) && (tokens[i+1].matches("\\p{Ll}{1,3}"))) {
						result = appendAndFork(result, numbersOmastav.get(tokens[i]));
						
					} else {
						result = appendAndFork(result, numbersNimetav.get(tokens[i]));
					}
				} else {
					if (tokens[i].length() > 4) {
						throw new TooComplexWordException("Number too long: " + tokens[i]);
					}
					//spell by numbers
					for (char digit : tokens[i].toCharArray()) {
						result = appendAndFork(result, numbersNimetav.get(String.valueOf(digit)));
					}
					
				}
				continue;
			}
			
			if ((tokens[i].length() == 1) &&
					(spell.containsKey(tokens[i])) ||
					((i == 0) && (spell.containsKey(tokens[i].toUpperCase())))) {
				result = appendAndFork(result, spell.get(tokens[i].toUpperCase()));
				
				continue;
			}
			
			if (abbreviations.containsKey(tokens[i])) {
				List<String> abbr = abbreviations.get(tokens[i]);
				if (abbr.get(0).equals("?")) {
					result = append(result, tokens[i].toLowerCase());
				} else {
					result = appendAndFork(result, abbr);
				}
				continue;
			}
			
			if (dict.containsKey(tokens[i])) {
				result = appendAndFork(result, dict.get(tokens[i]));
				continue;
			}
			
			boolean found = false;
			for (String foreign: dict.keySet()) {
				if (tokens[i].matches(foreign + "\\p{Ll}{1,3}")) {
					
					result = appendAndFork(result, dict.get(foreign));
					result = append(result, tokens[i].substring(foreign.length()));
					
					found = true;
					break;
				}
			}
			if (found) continue;
			
			//HACK: last short lowercase part of multipart word is probably a suffix
			if ((i >0) && (i == tokens.length - 1) && (tokens[i].matches("\\p{Ll}{1,3}"))) {
				for (int j = 0; j < result.size(); j++) {
					result.set(j, result.get(j).substring(0,  result.get(j).length() - 1));
				}
				
			}
			result = append(result, tokens[i]);
			
			
			
		}
		return new HashSet<String>(result);
	}
		
}
