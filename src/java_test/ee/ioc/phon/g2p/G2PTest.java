package ee.ioc.phon.g2p;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import ee.ioc.phon.g2p.G2P;
import static org.junit.Assert.assertEquals;

public class G2PTest {
	G2P g2p;
	
	public G2PTest() {
		Map<String, List<String>> userDict = new HashMap<String, List<String>>();
		userDict.put("Visipaque", Arrays.asList(new String[]{"visibakk"}));		
		g2p = new G2P(userDict);
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
		assertEquals("[m a i j a]", expandP(g2p.graphemes2Phonemes("majja")));
		assertEquals("[k ae i j a]", expandP(g2p.graphemes2Phonemes("käia")));
	}

	@Test
	public void variants() throws TooComplexWordException {
		assertEquals("[l ae i n u t][l ae i n t]", expandP(g2p.graphemes2Phonemes("läinud")));
		assertEquals("[p ae e v][p ae ae v]", expandP(g2p.graphemes2Phonemes("päev")));
	}
	
	@Test
	public void foreign() throws TooComplexWordException {
		assertEquals("[k r i s t j a n]", expandP(g2p.graphemes2Phonemes("Christian")));
		assertEquals("[sh a a f]", expandP(g2p.graphemes2Phonemes("Schaaf")));
		assertEquals("[s a s a v i]", expandP(g2p.graphemes2Phonemes("Zazawy")));
		assertEquals("[r e n e]", expandP(g2p.graphemes2Phonemes("René")));
		assertEquals("[v a l e n t s j a]", expandP(g2p.graphemes2Phonemes("Valencia")));
		assertEquals("[m l a t i t sh i]", expandP(g2p.graphemes2Phonemes("Mladići")));
		assertEquals("[p a tt r i t s j a]", expandP(g2p.graphemes2Phonemes("Patricia")));
		assertEquals("[t sh j u r l j o n i s e]", expandP(g2p.graphemes2Phonemes("Čiurlionise")));
		assertEquals("[p o o l tt i kk][p a l tt i kk]", expandP(g2p.graphemes2Phonemes("Baltic")));
		assertEquals("[p j oe r n]", expandP(g2p.graphemes2Phonemes("Bjørn")));
		assertEquals("[oe s t p e r k]", expandP(g2p.graphemes2Phonemes("Østberg")));
		assertEquals("[a i pp o t]", expandP(g2p.graphemes2Phonemes("iPod")));
		assertEquals("[a i pp ae ae t]", expandP(g2p.graphemes2Phonemes("iPad")));
	}

	@Test
	public void specialChars() throws TooComplexWordException {
		assertEquals("[ue l e _ j ae ae n u t][ue l e _ j ae ae n t]", expandP(g2p.graphemes2Phonemes("üle+jäänud")));
		assertEquals("[h a m p a _ k i v i]", expandP(g2p.graphemes2Phonemes("hamba_kivi")));
		
	}
	
	@Test
	public void abbr() throws TooComplexWordException {
		assertEquals("[k a a _ k e e _ p e e l e]", expandP(g2p.graphemes2Phonemes("KGB-le")));
		assertEquals("[f i s s i l e]", expandP(g2p.graphemes2Phonemes("FISile")));
		assertEquals("[e e _ n u l l _ ue k s]", expandP(g2p.graphemes2Phonemes("E01")));
		//assertEquals("[k a k s _ n u l l _ ue k s _ s e i t s e _ v i i s _ n e l i _ k a k s _ k a k s _ k u u s _ k o l m _ k a k s]", expandP(g2p.graphemes2Phonemes("20175422632")));
    assertEquals("[k a h e s a j a _ k ue m n e s t]", expandP(g2p.graphemes2Phonemes("210-st")));
	}
	
	@Test
	public void radiology() throws TooComplexWordException {
		assertEquals("[t e e _ h a a _ k a k s t e i s t][t o r a kk a a l _ k a k s t e i s t]", expandP(g2p.graphemes2Phonemes("TH12")));
	}
	
	@Test
	public void testUserDict() throws TooComplexWordException {
		assertEquals("[v i s i p a kk]", expandP(g2p.graphemes2Phonemes("Visipaque")));
	}
	
	
}
