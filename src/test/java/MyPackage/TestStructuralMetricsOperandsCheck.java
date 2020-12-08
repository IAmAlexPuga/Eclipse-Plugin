package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestStructuralMetricsOperandsCheck {

	@Test
	public void getDefaultTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		int[] tok = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE };
		assertArrayEquals(tok, spy.getDefaultTokens());
	}

	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		int[] tok = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE };
		assertArrayEquals(tok, spy.getAcceptableTokens());
	}

	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		int[] tok = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE };
		assertArrayEquals(tok, spy.getRequiredTokens());
	}

	@Test
	public void finishTreeTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());

		// Set some default data
		spy.metrics.addOperands();
		spy.metrics.addUniqueOperands("v", 5);

		Mockito.when(mock.getLineNo()).thenReturn(1);

		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);

		assertEquals(1, spy.metrics.getOperands());
		assertEquals(1, spy.metrics.getUniqueOperands().size());

	}

	/*
	 * @Test public void visitTokenTest() { StructuralMetricsOperandsCheck spy =
	 * spy(new StructuralMetricsOperandsCheck()); DetailAST mock =
	 * mock(DetailAST.class); DetailAST parent = mock(DetailAST.class);
	 * 
	 * Mockito.when(parent.getType()).thenReturn(TokenTypes.EXPR);
	 * Mockito.when(mock.getParent()).thenReturn(parent);
	 * 
	 * // adds operand path1 Mockito.when(spy.checkNum(mock)).thenReturn(true);
	 * Mockito.when(spy.checkOperator(mock)).thenReturn(false);
	 * Mockito.when(spy.isValidIdent(mock)).thenReturn(false); spy.visitToken(mock);
	 * assertEquals(1, spy.metrics.getOperands());
	 * 
	 * // adds operand path2
	 * Mockito.when(parent.getType()).thenReturn(TokenTypes.NUM_DOUBLE);
	 * Mockito.when(mock.getParent()).thenReturn(parent);
	 * Mockito.when(spy.checkOperator(mock)).thenReturn(true);
	 * Mockito.when(spy.isValidIdent(mock)).thenReturn(false); spy.visitToken(mock);
	 * assertEquals(2, spy.metrics.getOperands());
	 * 
	 * // adds unique op Mockito.when(spy.checkNum(mock)).thenReturn(false);
	 * Mockito.when(spy.isValidIdent(mock)).thenReturn(true);
	 * Mockito.when(mock.getText()).thenReturn("a"); spy.visitToken(mock);
	 * assertEquals(1, spy.metrics.getUniqueOperands().size());
	 * 
	 * // fails since a unique op with a exists spy.visitToken(mock);
	 * assertEquals(1, spy.metrics.getUniqueOperands().size());
	 * 
	 * }
	 */

	@Test
	public void isValidIdentTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		// branch test for true false
		Mockito.doReturn(true).when(spy).checkIdent(mock);
		Mockito.doReturn(false).when(spy).checkIdentVar(mock);
		assertTrue(spy.isValidIdent(mock));
		
		// branch test for false true
		Mockito.doReturn(false).when(spy).checkIdent(mock);
		Mockito.doReturn(true).when(spy).checkIdentVar(mock);
		assertTrue(spy.isValidIdent(mock));
		
		// branch test for false false
		Mockito.doReturn(false).when(spy).checkIdent(mock);
		Mockito.doReturn(false).when(spy).checkIdentVar(mock);
		assertFalse(spy.isValidIdent(mock));

	}

	@Test
	public void checkIdentTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST parent = mock(DetailAST.class);

		Mockito.when(mock.getType()).thenReturn(TokenTypes.IDENT);

		// all posible true paths for parent type
		Mockito.when(parent.getType()).thenReturn(TokenTypes.DOT);
		Mockito.when(mock.getParent()).thenReturn(parent);
		assertTrue(spy.checkIdent(mock));
		
		Mockito.when(parent.getType()).thenReturn(TokenTypes.VARIABLE_DEF);
		assertTrue(spy.checkIdent(mock));
		Mockito.when(parent.getType()).thenReturn(TokenTypes.METHOD_DEF);
		assertTrue(spy.checkIdent(mock));

		// failing path for mock
		Mockito.when(mock.getType()).thenReturn(TokenTypes.METHOD_DEF);
		assertFalse(spy.checkIdent(mock));

		// failing path for parent type
		Mockito.when(mock.getType()).thenReturn(TokenTypes.IDENT);
		Mockito.when(parent.getType()).thenReturn(TokenTypes.ABSTRACT);
		assertFalse(spy.checkIdent(mock));

	}

	@Test
	public void checkIdentVarTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST parent = mock(DetailAST.class);

		Mockito.when(parent.getType()).thenReturn(TokenTypes.PLUS);
		Mockito.when(mock.getParent()).thenReturn(parent);
		// Mockito.doReturn(false).when(spy).checkOperator(mock);
		// Mockito.doReturn(false).when(spy).checkExpression(mock);

		Mockito.doReturn(parent).when(mock).getParent();
		Mockito.doReturn(TokenTypes.NUM_DOUBLE).when(mock).getType();
		assertTrue(spy.checkIdentVar(mock));

		Mockito.doReturn(TokenTypes.IDENT).when(mock).getType();
		assertFalse(spy.checkIdentVar(mock));
	}
	
	@Test
	public void checkExpressionTest() {
		
	}

}
