package MyPackage;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestExprCheck {
	
	
	@Test
	public void getDefaultTokensTest() {
		exprCheck mock = mock(exprCheck.class);
		exprCheck spy = spy(new exprCheck());
		
		int[] tok = {TokenTypes.EXPR};
		
		assertArrayEquals(tok, spy.getDefaultTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END }).when(mock).getDefaultTokens();
		assertNotSame(spy.getDefaultTokens(),mock.getDefaultTokens());
	}
	
	@Test
	public void getAcceptableTokensTest() {
		exprCheck mock = mock(exprCheck.class);
		exprCheck spy = spy(new exprCheck());
		
		int[] tok = {TokenTypes.EXPR};
		
		assertArrayEquals(tok, spy.getAcceptableTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END }).when(mock).getDefaultTokens();
		assertNotSame(spy.getAcceptableTokens(),mock.getAcceptableTokens());
	}
	
	@Test
	public void getRequiredTokensTest() {
		exprCheck mock = mock(exprCheck.class);
		exprCheck spy = spy(new exprCheck());
		
		int[] tok = {TokenTypes.EXPR};
		
		assertArrayEquals(tok, spy.getRequiredTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END }).when(mock).getDefaultTokens();
		assertNotSame(spy.getRequiredTokens(),mock.getRequiredTokens());
	}
	
	@Test
	public void visitTokenTest() {
		exprCheck spy = spy(new exprCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.metrics.resetExpressions();
		
		// Checks if expr is increased for valid data
		Mockito.when(mock.getType()).thenReturn(TokenTypes.EXPR);
		spy.visitToken(mock);
		assertEquals(1, spy.metrics.getExprs());
		
		// Shouldnt add to expression
		Mockito.when(mock.getType()).thenReturn(TokenTypes.COMMENT_CONTENT);
		spy.visitToken(mock);
		assertEquals(1,spy.metrics.getExprs());
		
	}
	
	@Test
	public void checkExpressionTest() {
		exprCheck spy = spy(new exprCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.metrics.resetExpressions();
		// Checks if expr is returned true for valid data
		Mockito.when(mock.getType()).thenReturn(TokenTypes.EXPR);
		boolean ans = spy.checkExpression(mock);
		assertEquals(true, ans);
		// Return false
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BAND);
		ans = spy.checkExpression(mock);
		assertFalse(ans);
	}
	
	@Test
	public void beginTreeTest() {
		exprCheck spy = spy(new exprCheck());
		DetailAST mock = mock(DetailAST.class);

		spy.metrics.addExprs();
		spy.beginTree(mock);
		assertEquals(0, spy.metrics.getExprs());
	}
	
	@Test
	public void finishTreeTest() {
		/*
		 * DetailAST mock = mock(DetailAST.class); exprCheck mockExpr =
		 * mock(exprCheck.class);
		 * 
		 * mockExpr.metrics.addExprs();
		 * 
		 * Mockito.doAnswer(invocation -> { Object a1 = invocation.getArgument(0);
		 * Object a2 = invocation.getArgument(1);
		 * 
		 * assertEquals("Expr:",a2); assertEquals(1,a1); return null;
		 * }).when(mockExpr).log(Mockito.any(Integer.class), Mockito.any(String.class));
		 * 
		 * mockExpr.finishTree(mock); mockExpr.log(1, "Expr:");
		 */
		
		DetailAST mock = mock(DetailAST.class);
		exprCheck spy = spy(new exprCheck());
		
		// Set some default data
		spy.metrics.addExprs();
		
		Mockito.when(mock.getLineNo()).thenReturn(1);
		
		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);
		
		assertEquals(1, spy.metrics.getExprs());
		//assertEquals(4, spy.metrics.getHVocab());
		
	}
	

}
