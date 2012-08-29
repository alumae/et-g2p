package ee.ioc.phon.g2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;


public class G2P {
	private List<SubstitionRule> l2pRules;
	private List<SubstitionRule> wordVariantRules;
	private Map<String, String> p2pRules;
	private Set<String> validPhonemes;
	
	
	static String arrayToString(String[] phonemes) {
		StringBuffer result = new StringBuffer();
		boolean first = true;
		for (String ph : phonemes) {
			if (!first) {
				result.append(" ");
			}
			result.append(ph);
			first = false;
		}
		return result.toString();
	}
	
	
	private static class SubstitionRule {
		Pattern pattern;
		String replacement;
		
		public SubstitionRule(Pattern pattern, String replacement) {
			super();
			this.pattern = pattern;
			this.replacement = replacement;
		}
	}
	
	public G2P() {
		InputStream rulesInput = getClass().getResourceAsStream("/rules.yaml");
		Yaml yaml = new Yaml();
	    Map rulesMap = (Map) yaml.load(rulesInput);
	    l2pRules = buildSubstitionRules(rulesMap, "l2p_rules");
	    wordVariantRules = buildSubstitionRules(rulesMap, "word_variants");
	    p2pRules = new HashMap<String, String>();
	    for (String ruleStr : ((String)rulesMap.get("phon2phon")).split("\n")) {
	    	String[] ruleParts = ruleStr.split("\\s+", 2);
	    	if (ruleParts.length > 1) {
		    	p2pRules.put(ruleParts[0], ruleParts[1]);
	    	}
	    }
	    validPhonemes = new HashSet<String>(Arrays.asList(((String)rulesMap.get("phonemes")).split("\\s+")));
	}
	
	private List<SubstitionRule> buildSubstitionRules(Map conf, String rulesName) {
	    List<SubstitionRule> result = new ArrayList<SubstitionRule>();
	    for (String ruleStr : ((String)conf.get(rulesName)).split("\n")) {
	    	String[] ruleParts = ruleStr.split("\\s+", 2);
	    	if (ruleParts.length > 1) {
		    	SubstitionRule sr = new SubstitionRule(Pattern.compile(ruleParts[0]), ruleParts[1]);
		    	result.add(sr);
	    	}
	    }
		return result;
	}
	
	public List<String[]> graphemes2Phonemes(String word) {
		List<String[]> result = new LinkedList<String[]>();
		
		List<String> allVariants = new LinkedList<String>();
		allVariants.add(word);
		for (SubstitionRule wvr: wordVariantRules) {
			Matcher m = wvr.pattern.matcher(word);
			if (m.matches()) {
				allVariants.add(m.replaceAll(wvr.replacement));
			}
		}
		for (String variant: allVariants) {
			String graphemes = variant.toLowerCase();
			for (SubstitionRule sr : l2pRules) {
				Matcher m = sr.pattern.matcher(graphemes);
				graphemes = m.replaceAll(sr.replacement);
				
			}
			List<String> phonemes = new LinkedList<String>();
			for (char ch:graphemes.toCharArray()) {
				String phon = String.valueOf(ch);
				String realPhoneme = p2pRules.containsKey(phon) ? p2pRules.get(phon) : phon;
				if (validPhonemes.contains(realPhoneme)) {
					phonemes.add(realPhoneme);
				}
			}
			result.add(phonemes.toArray(new String[0]));
		}
		return result;
		
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		G2P g2p = new G2P();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		while ((line = br.readLine()) != null) {
			String word = line.trim();
			List<String[]> pronunciations = g2p.graphemes2Phonemes(word);
			for (int i = 0; i < pronunciations.size(); i++) {
				System.out.print(word);
				if (i > 0) {
					System.out.print("(" + (i+1) + ")");
				}
				System.out.print("\t");
				System.out.println(arrayToString(pronunciations.get(i)));
			}
		}

	}

}
