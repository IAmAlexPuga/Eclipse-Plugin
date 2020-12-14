package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestStructuralMetricsLoopsCheck {
	
	@Test
	public void getDefaultTokensTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		int[] tok = {TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE}; 
		assertArrayEquals(tok, spy.getDefaultTokens());
	}
	
	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		int[] tok = {TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE}; 
		assertArrayEquals(tok, spy.getAcceptableTokens());
	}
	
	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		int[] tok = {TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE}; 
		assertArrayEquals(tok, spy.getRequiredTokens());
	}
	
	@Test
	public void getLoopCountTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		spy.addLoopCount();
		assertEquals(1,spy.getLoopCount());
	}
	
	@Test
	public void addLoopCountTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		assertEquals(0,spy.getLoopCount());
		spy.addLoopCount();
		assertEquals(1,spy.getLoopCount());
	}
	
	@Test
	public void resetLoopCountTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		spy.addLoopCount();
		assertEquals(1,spy.getLoopCount());
		spy.resetLoopCount();
		assertEquals(0,spy.getLoopCount());
	}
	
	@Test
	public void finishTreeTest() {
		
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		
		// Set some default data
		spy.addLoopCount();
		spy.addLoopCount();
		spy.addLoopCount();
		
		Mockito.when(mock.getLineNo()).thenReturn(1);
		
		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);
		assertEquals(3, spy.getLoopCount());
		
		String msgs[] = {"Number Looping statements: 3"};
		int count = 0;
		for (LocalizedMessage lm : spy.getMessages()) {
			assertEquals(msgs[count], lm);
			count++;
		}
		
	}
	
	@Test
	public void visitTokenTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		// adds loop for while
		Mockito.when(mock.getType()).thenReturn(TokenTypes.LITERAL_WHILE);
		spy.visitToken(mock);
		assertEquals(1, spy.getLoopCount());
		
		// adds loop for for
		Mockito.when(mock.getType()).thenReturn(TokenTypes.LITERAL_FOR);
		spy.visitToken(mock);
		assertEquals(2, spy.getLoopCount());
		
		// adds loop for do while
		Mockito.when(mock.getType()).thenReturn(TokenTypes.DO_WHILE);
		spy.visitToken(mock);
		assertEquals(3, spy.getLoopCount());
		
		// does not add any
		Mockito.when(mock.getType()).thenReturn(TokenTypes.NUM_DOUBLE);
		spy.visitToken(mock);
		assertEquals(3, spy.getLoopCount());
		
	}
	
	@Test
	public void isLoopTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		// true for loop for while
		Mockito.when(mock.getType()).thenReturn(TokenTypes.LITERAL_WHILE);
		assertTrue(spy.isLoop(mock));
		
		// true for loop for do while
		Mockito.when(mock.getType()).thenReturn(TokenTypes.DO_WHILE);
		assertTrue(spy.isLoop(mock));
		
		// true for loop for for
		Mockito.when(mock.getType()).thenReturn(TokenTypes.LITERAL_FOR);
		assertTrue(spy.isLoop(mock));
		
		// false for non loop
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BSR_ASSIGN);
		assertFalse(spy.isLoop(mock));
		
	}
	
	@Test
	public void beginTreeTest() {
		StructuralMetricsLoopsCheck spy = spy(new StructuralMetricsLoopsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.addLoopCount();
		spy.beginTree(mock);
		assertEquals(0, spy.getLoopCount());
	}
	
	
	
	
}
