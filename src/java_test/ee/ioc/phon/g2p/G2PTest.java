package ee.ioc.phon.g2p;

import java.util.List;

import org.junit.Test;

import ee.ioc.phon.g2p.G2P;
import static org.junit.Assert.assertEquals;

public class G2PTest {
	
	String expandP(List<String[]> pronounciations) {
		StringBuffer result = new StringBuffer("");
		for (String[] phonemes : pronounciations ) {
			result.append("[");
			result.append(G2P.arrayToString(phonemes));
			result.append("]");
		}
		return result.toString();
			
	}

	
	@Test
	public void simple() {
		G2P g2p = new G2P();
		assertEquals("[t a n e l]", expandP(g2p.graphemes2Phonemes("Tanel")));
		assertEquals("[p a n a a n]", expandP(g2p.graphemes2Phonemes("banaan")));
		assertEquals("[p a n kk]", expandP(g2p.graphemes2Phonemes("pank")));
		assertEquals("[k a r a a sh]", expandP(g2p.graphemes2Phonemes("garaaž")));
	}

	@Test
	public void variants() {
		G2P g2p = new G2P();
		assertEquals("[l ae i n u t][l ae i n t]", expandP(g2p.graphemes2Phonemes("läinud")));
	}
	
	@Test
	public void foreign() {
		G2P g2p = new G2P();
		assertEquals("[k r i s t j a n]", expandP(g2p.graphemes2Phonemes("Christian")));
		assertEquals("[sh a a f]", expandP(g2p.graphemes2Phonemes("Schaaf")));
		assertEquals("[s a s a v i]", expandP(g2p.graphemes2Phonemes("Zazawy")));
	}

	@Test
	public void specialChars() {
		G2P g2p = new G2P();
		assertEquals("[h a m p a k i v i]", expandP(g2p.graphemes2Phonemes("hamba_kivi")));
	}

}
