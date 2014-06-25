package ee.ioc.phon.g2p;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizer for Estonian words. Can tokenize words to components
 * that are pronounced independently.
 * 
 * @author tanel
 *
 */
public class Tokenizer {

	private Pattern tokPattern;
	
	public Tokenizer(Collection<String> specialTokens) {
		StringBuilder sb = new StringBuilder();
		sb.append("\\p{P}?(");
		
		for (String s: specialTokens) {
			sb.append("(");
			sb.append(s);
			sb.append(")|");
		}
		
		sb.append("((?<=(\\b|\\p{Ll}))\\p{Lu}\\p{Ll}+)|[1-9][0-9]{0,3}|[0-9]|\\p{Lu}|\\p{Ll}+)");
		//tokPattern = Pattern.compile("\\p{P}?((\\b\\p{Lu}\\p{Ll}+)|[0-9]+|\\p{Lu}|\\p{Ll}+)");
		tokPattern = Pattern.compile(sb.toString());
	}
	
	public String[] tokenize(String word) {
		Matcher m = tokPattern.matcher(word);
		List<String> result = new LinkedList<String>();
		while(m.find()) {
			result.add(m.group(1));
		}
		return result.toArray(new String[0]);
	}
}
