package ee.ioc.phon.g2p;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class ExpanderTest {

	Expander expander;
	
	public ExpanderTest() {
		Map rulesMap = Utils.loadYamlFile("rules.yaml");
		Map numbersMap = Utils.loadYamlFile("numbers.yaml");
		expander = new Expander(rulesMap, numbersMap);
	}
	
	@Test
	public void numbers() throws TooComplexWordException {
		assertEquals("kaks kümmend neli", expander.expand(new String[]{"24"}));
		assertEquals("kahe kümne neljale", expander.expand(new String[]{"24", "le"}));
		
	}
	
	@Test
	public void abbr() throws TooComplexWordException {
		assertEquals("kaa gee bee", expander.expand(new String[]{"K", "G", "B"}));
		assertEquals("kaa gee beeta", expander.expand(new String[]{"K", "G", "B", "ta"}));
	}

	@Test
	public void foreign() throws TooComplexWordException {
		assertEquals("äppl", expander.expand(new String[]{"Apple"}));
		assertEquals("äppliga", expander.expand(new String[]{"Apple", "iga"}));
		assertEquals("feissbuki", expander.expand(new String[]{"Facebooki"}));
		
	}

	
}
