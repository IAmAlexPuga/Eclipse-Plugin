package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


public class TestStructuralMetricsOperandsCheck {

	int[] tokens = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
			TokenTypes.VARIABLE_DEF, TokenTypes.TYPECAST, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR,
			TokenTypes.IDENT, TokenTypes.METHOD_CALL, TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN,
			TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN,
			TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT,
			TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE, TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN,
			TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT,
			TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.CHAR_LITERAL, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,
			TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON, TokenTypes.DOT,
			TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE };
	
	@Test
	public void getDefaultTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		
		assertArrayEquals(tokens, spy.getDefaultTokens());
	}

	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());

		assertArrayEquals(tokens, spy.getAcceptableTokens());
	}

	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());

		assertArrayEquals(tokens, spy.getRequiredTokens());
	}

	@Test
	public void finishTreeTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		
		spy.metrics.resetOperands();
		spy.metrics.resetUniqueOperands();
		
		// Set some default data
		spy.metrics.addOperands();
		spy.metrics.addUniqueOperands("v", 5);

		Mockito.when(mock.getLineNo()).thenReturn(1);

		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);

		assertEquals(1, spy.metrics.getOperands());
		assertEquals(1, spy.metrics.getUniqueOperands().size());

	}

	@Test
	public void visitTokenTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST parent = mock(DetailAST.class);
		
		spy.metrics.resetOperands();

		Mockito.when(parent.getType()).thenReturn(TokenTypes.EXPR);
		Mockito.when(mock.getParent()).thenReturn(parent);

		// adds operand path1 Mockito.when(spy.checkNum(mock)).thenReturn(true);
		//Mockito.doReturn(true).when(spy).checkNum(mock);
		Mockito.doReturn(false).when(spy).checkOperator(mock);
		Mockito.doReturn(true).when(spy).checkExpression(mock);
		Mockito.doReturn(true).when(spy).isVariable(mock);
		spy.visitToken(mock);
		assertEquals(1, spy.metrics.getOperands());

		// adds operand path2

		Mockito.doReturn(true).when(spy).checkOperator(mock);
		Mockito.doReturn(false).when(spy).checkExpression(mock);
		Mockito.doReturn(true).when(spy).checkOperator(mock);
		spy.visitToken(mock);
		assertEquals(2, spy.metrics.getOperands());
		
		Mockito.doReturn(true).when(spy).checkOperator(mock);
		Mockito.doReturn(true).when(spy).checkExpression(mock);
		Mockito.doReturn(false).when(spy).isVariable(mock);
		spy.visitToken(mock);
		assertEquals(2, spy.metrics.getOperands());
		
		Mockito.doReturn(false).when(spy).checkExpression(mock);
		Mockito.doReturn(false).when(spy).checkOperator(mock);
		Mockito.doReturn(false).when(spy).isVariable(mock);
		spy.visitToken(mock);
		assertEquals(2, spy.metrics.getOperands());

	}
	
	@Test
	public void isValidMethodCallTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST parent = mock(DetailAST.class);
		DetailAST gP = mock(DetailAST.class);
		
		Mockito.doReturn(TokenTypes.METHOD_CALL).when(mock).getType();
		Mockito.doReturn(TokenTypes.SLIST).when(gP).getType();
		Mockito.doReturn(gP).when(parent).getParent();
		Mockito.doReturn(parent).when(mock).getParent();
		
		assertFalse(spy.isValidMethodCall(mock));
		
		Mockito.doReturn(TokenTypes.ANNOTATION).when(gP).getType();
		assertTrue(spy.isValidMethodCall(mock));
		
		Mockito.doReturn(TokenTypes.AT).when(mock).getType();
		assertFalse(spy.isValidMethodCall(mock));
		
		
	}
	
	@Test
	public void isVariableTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		Mockito.doReturn(TokenTypes.ANNOTATION).when(mock).getType();
		
		Mockito.doReturn(false).when(spy).isValidMethodCall(mock);
		Mockito.doReturn(true).when(spy).checkNum(mock);
		assertTrue(spy.isVariable(mock));
		
		Mockito.doReturn(true).when(spy).isValidMethodCall(mock);
		Mockito.doReturn(false).when(spy).checkNum(mock);
		assertTrue(spy.isVariable(mock));
		
		Mockito.doReturn(false).when(spy).isValidMethodCall(mock);
		Mockito.doReturn(false).when(spy).checkNum(mock);
		assertFalse(spy.isVariable(mock));
		
		Mockito.doReturn(TokenTypes.CHAR_LITERAL).when(mock).getType();
		assertTrue(spy.isVariable(mock));
		
		Mockito.doReturn(TokenTypes.TYPECAST).when(mock).getType();
		assertTrue(spy.isVariable(mock));
		
		Mockito.doReturn(TokenTypes.STRING_LITERAL).when(mock).getType();
		assertTrue(spy.isVariable(mock));
		
		
	}
	
	@Test
	public void checkExpressionTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);

		Mockito.doReturn(TokenTypes.EXPR).when(mock).getType();
		boolean ans = spy.checkExpression(mock);
		assertTrue(ans);
		
		Mockito.doReturn(TokenTypes.ANNOTATION_ARRAY_INIT).when(mock).getType();
		ans = spy.checkExpression(mock);
		assertFalse(ans);
	}

	@Test
	public void checkOperatorTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);

		int[] tok = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.STAR, TokenTypes.DIV, TokenTypes.MOD,
				TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON };
		boolean isTrue = false;
		for (int token : tok) {
			Mockito.when(mock.getType()).thenReturn(token);
			isTrue = spy.checkOperator(mock);
			assertTrue(isTrue);
		}

	}
	
	@Test
	public void addUniqueOperandTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		spy.metrics.resetUniqueOperands();
		
		Mockito.doReturn(TokenTypes.ANNOTATION).when(mock).getType();
		Mockito.doReturn("a").when(mock).getText();
		spy.addUniqueOperand(mock);
		assertEquals(1, spy.metrics.getUniqueOperands().size());
		
		spy.addUniqueOperand(mock);
		assertEquals(1, spy.metrics.getUniqueOperands().size());
		
		Mockito.doReturn(TokenTypes.METHOD_CALL).when(mock).getType();
		Mockito.doReturn("b").when(spy).getName(mock);
		spy.addUniqueOperand(mock);
		assertEquals(2, spy.metrics.getUniqueOperands().size());
		
	}

	@Test
	public void checkNumTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);

		int[] tok = { TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_LONG,
				TokenTypes.IDENT };

		boolean isTrue = false;
		for (int token : tok) {
			Mockito.when(mock.getType()).thenReturn(token);
			isTrue = spy.checkNum(mock);
			assertTrue(isTrue);
		}
		
		// Something not in num arr
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BXOR);
		isTrue = spy.checkNum(mock);
		assertFalse(isTrue);
		
	}

	@Test
	public void beginTreeTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		spy.metrics.addOperands();
		spy.metrics.addOperands();
		spy.metrics.addUniqueOperands("q", 1);

		spy.beginTree(mock);

		assertEquals(0, spy.metrics.getOperands());
		assertEquals(0, spy.metrics.getUniqueOperands().size());
	}
	
	@Test
	public void getNameTest() {
		StructuralMetricsOperandsCheck spy = spy(new StructuralMetricsOperandsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		/*
		 * DetailAST mock = mock(DetailAST.class); DetailAST child =
		 * mock(DetailAST.class); DetailAST child2 = mock(DetailAST.class);
		 * 
		 * //Mockito.doReturn("test2").when(child).getText();
		 * Mockito.doReturn("test").when(child).getText();
		 * Mockito.doReturn(TokenTypes.IDENT).when(child).getType();
		 * //Mockito.doReturn(TokenTypes.IDENT).when(child2).getType();
		 * 
		 * 
		 * 
		 * //Mockito.doReturn(child).when(mock).getFirstChild();
		 * //Mockito.doReturn(child2).when(child).getFirstChild();
		 * //Mockito.doReturn(child2).when(mock).getNextSibling();
		 * 
		 * //Mockito.doReturn(child2).when(mock).getFirstChild();
		 * Mockito.doReturn(mock).when(mock).findFirstToken(59);
		 * Mockito.doReturn(child).when(mock).getFirstChild();
		 * Mockito.doReturn(TokenTypes.IDENT).when(mock).getType();
		 * assertEquals("test.test2.", spy.getName(mock));
		 */
		Mockito.doReturn("test.t").when(spy).getName(mock);
		assertEquals("test.t", spy.getName(mock));
	}

}
