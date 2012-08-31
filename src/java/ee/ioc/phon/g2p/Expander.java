package ee.ioc.phon.g2p;

import java.util.Map;

public class Expander {

	private Map<String, String> dict;
	private Map<String, String> spell;
	private Map<String, String> abbreviations;
	private Map<String, String> numbersNimetav;
	private Map<String, String> numbersOmastav;

	public Expander(Map rulesMap, Map numbersMap) {
		spell = Utils.linesToMap(rulesMap, "spell");
		dict = Utils.linesToMap(rulesMap, "dict");
		abbreviations =  Utils.linesToMap(rulesMap, "abbreviations");
		numbersNimetav = Utils.linesToMap(numbersMap, "nimetav");
		numbersOmastav = Utils.linesToMap(numbersMap, "omastav");
		
	}

	public String expand(String [] tokens) throws TooComplexWordException {
		StringBuilder result = new StringBuilder();
		
		for (int i=0; i<tokens.length; i++) {
			if (i > 0) {
				result.append(" ");
			}
		
			if (tokens[i].matches("\\d+")) {
				if (Integer.parseInt(tokens[i]) <= 9000) {
					// [20 le] -> [<20 omastav> le]
					if ((i == tokens.length - 2) && (tokens[i+1].matches("\\p{Ll}{1,3}"))) {
						result.append(numbersOmastav.get(tokens[i]));
						
					} else {
						result.append(numbersNimetav.get(tokens[i]));
					}
				} else {
					throw new TooComplexWordException();
				}
				continue;
			}
			
			if ((tokens[i].length() == 1) &&
					(spell.containsKey(tokens[i])) ||
					((i == 0) && (spell.containsKey(tokens[i].toUpperCase())))) {
				result.append(spell.get(tokens[i].toUpperCase()));
				continue;
			}
			
			if (abbreviations.containsKey(tokens[i])) {
				String abbr = abbreviations.get(tokens[i]);
				if (abbr.equals("?")) {
					result.append(tokens[i].toLowerCase());
				} else {
					result.append(abbr);
				}
				continue;
			}
			
			if (dict.containsKey(tokens[i])) {
				result.append(dict.get(tokens[i]));
				continue;
			}
			
			boolean found = false;
			for (String foreign: dict.keySet()) {
				if (tokens[i].matches(foreign + "\\p{Ll}{1,3}")) {
					result.append(dict.get(foreign));
					result.append(tokens[i].substring(foreign.length()));
					found = true;
					break;
				}
			}
			if (found) continue;
			
			// HACK: last short lowercase part of multipart word is probably a suffix
			if ((i >0) && (i == tokens.length - 1) && (tokens[i].matches("\\p{Ll}{1,3}"))) {
				result.deleteCharAt(result.length() - 1);
			}
			result.append(tokens[i]);
			
			
			
		}
		return result.toString();
	}
		
}
