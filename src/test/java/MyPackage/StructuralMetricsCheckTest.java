package MyPackage;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



import com.puppycrawl.tools.checkstyle.api.DetailAST;


public class StructuralMetricsCheckTest {
	
	@Test
	public void getDefaultTokensTest() {
		StructuralMetricsCheck spy = spy(new StructuralMetricsCheck());
		int[] empty = {}; 
		assertArrayEquals(empty, spy.getDefaultTokens());
	}
	
	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsCheck spy = spy(new StructuralMetricsCheck());
		int[] empty = {}; 
		assertArrayEquals(empty, spy.getAcceptableTokens());
	}
	
	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsCheck spy = spy(new StructuralMetricsCheck());
		int[] empty = {}; 
		assertArrayEquals(empty, spy.getRequiredTokens());
	}
	
	@Test
	public void beginTreeTest() {
		StructuralMetricsCheck spy = spy(new StructuralMetricsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.metrics.setHDiff(2);
		spy.metrics.setHEffort(1);
		spy.metrics.setHLength(4);
		spy.metrics.setHVocab(43);
		spy.metrics.setHVolume(21);
		
		spy.beginTree(mock);
		
		assertEquals(0, spy.metrics.getHDiff());
		assertEquals(0, spy.metrics.getHEffort());
		assertEquals(0, spy.metrics.getHLength());
		assertEquals(0, spy.metrics.getHVocab());
		assertEquals(0, spy.metrics.getHVolume());
	}
	
	@Test
	public void finishTreeTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsCheck spy = spy(new StructuralMetricsCheck());
		
		// reset Data before hand
		spy.beginTree(mock);
		
		
		// Set some default data
		spy.metrics.addOperands();
		spy.metrics.addOperands();
		spy.metrics.addOps();
		spy.metrics.addUniqueOperands("a", 1);
		spy.metrics.addUniqueOperands("b", 1);
		spy.metrics.addUniqueOps(1,1);
		spy.metrics.addUniqueOps(2,1);
		
		Mockito.when(mock.getLineNo()).thenReturn(1);
		
		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);
		
		assertEquals(3, spy.metrics.getHLength());
		assertEquals(4, spy.metrics.getHVocab());
		
		String hDiff = String.format("%.2f", spy.metrics.getHDiff());
		String hVol = String.format("%.2f", spy.metrics.getHVolume());
		String hEff = String.format("%.2f", spy.metrics.getHEffort());
		
		assertEquals("0.50", hDiff);
		assertEquals("4.16", hVol);
		assertEquals("2.08", hEff);
		
	}

}
