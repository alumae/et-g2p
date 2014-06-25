package ee.ioc.phon.g2p;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class ExpanderTest {

	Expander expander;
	
	public ExpanderTest() {
		Map rulesMap = Utils.loadYamlFile("rules.yaml");
		Map numbersMap = Utils.loadYamlFile("numbers.yaml");
		expander = new Expander(rulesMap, numbersMap);
	}
	
	private Set<String> stringsToSet(String... args) {
		return new HashSet<String>(Arrays.asList(args));
	}
	
	@Test
	public void numbers() throws TooComplexWordException {
		assertEquals(stringsToSet("kaks kümmend neli"), expander.expand(new String[]{"24"}));
		assertEquals(stringsToSet("kahe kümne neljale"), expander.expand(new String[]{"24", "le"}));
		assertEquals(stringsToSet("null üks"), expander.expand(new String[]{"0", "1"}));
		
	}
	
	@Test
	public void abbr() throws TooComplexWordException {
		assertEquals(stringsToSet("kaa gee bee"), expander.expand(new String[]{"K", "G", "B"}));
		assertEquals(stringsToSet("kaa gee beeta"), expander.expand(new String[]{"K", "G", "B", "ta"}));
		assertEquals(stringsToSet("sii enn enn"), expander.expand(new String[]{"CNN"}));
		assertEquals(stringsToSet("natos"), expander.expand(new String[]{"NATO", "s"}));
		assertEquals(stringsToSet("fissi"), expander.expand(new String[]{"FIS", "i"}));
		assertEquals(stringsToSet("emm"), expander.expand(new String[]{"m"}));
		assertEquals(stringsToSet("vee tee aa", "kaksisvee tee aa"), expander.expand(new String[]{"W", "T", "A"}));
		
		
	}

	@Test
	public void foreign() throws TooComplexWordException {
		assertEquals(stringsToSet("äppl"), expander.expand(new String[]{"Apple"}));
		assertEquals(stringsToSet("äppliga"), expander.expand(new String[]{"Apple", "iga"}));
		assertEquals(stringsToSet("feissbuki"), expander.expand(new String[]{"Facebooki"}));
	}

	
}
