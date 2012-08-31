package ee.ioc.phon.g2p;

import java.util.List;

import org.junit.Test;

import ee.ioc.phon.g2p.G2P;
import static org.junit.Assert.assertEquals;

public class G2PTest {
	G2P g2p;
	
	public G2PTest() {
		g2p = new G2P();
	}
	
	String expandP(List<String[]> pronounciations) {
		StringBuffer result = new StringBuffer("");
		for (String[] phonemes : pronounciations ) {
			result.append("[");
			result.append(Utils.arrayToString(phonemes));
			result.append("]");
		}
		return result.toString();
			
	}

	
	@Test
	public void simple() throws TooComplexWordException {
		assertEquals("[t a n e l]", expandP(g2p.graphemes2Phonemes("Tanel")));
		assertEquals("[p a n a a n]", expandP(g2p.graphemes2Phonemes("banaan")));
		assertEquals("[p a n kk]", expandP(g2p.graphemes2Phonemes("pank")));
		assertEquals("[k a r a a sh]", expandP(g2p.graphemes2Phonemes("garaaž")));
	}

	@Test
	public void variants() throws TooComplexWordException {
		assertEquals("[l ae i n u t][l ae i n t]", expandP(g2p.graphemes2Phonemes("läinud")));
	}
	
	@Test
	public void foreign() throws TooComplexWordException {
		assertEquals("[k r i s t j a n]", expandP(g2p.graphemes2Phonemes("Christian")));
		assertEquals("[sh a a f]", expandP(g2p.graphemes2Phonemes("Schaaf")));
		assertEquals("[s a s a v i]", expandP(g2p.graphemes2Phonemes("Zazawy")));
		assertEquals("[r e n e]", expandP(g2p.graphemes2Phonemes("René")));
		assertEquals("[v a l e n t s j a]", expandP(g2p.graphemes2Phonemes("Valencia")));
		assertEquals("[m l a t i t sh i]", expandP(g2p.graphemes2Phonemes("Mladići")));
	}

	@Test
	public void specialChars() throws TooComplexWordException {
		assertEquals("[h a m p a k i v i]", expandP(g2p.graphemes2Phonemes("hamba_kivi")));
	}
	
	@Test
	public void abbr() throws TooComplexWordException {
		assertEquals("[k a a k e e p e e l e]", expandP(g2p.graphemes2Phonemes("KGB-le")));
	}
	
}
