package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestStructuralMetricsOperatorsCheck {
	int tokens[] = {TokenTypes.PLUS , TokenTypes.MINUS , TokenTypes.STAR
			, TokenTypes.DIV , TokenTypes.MOD , TokenTypes.PLUS_ASSIGN 
			, TokenTypes.MINUS_ASSIGN , TokenTypes.DIV_ASSIGN , TokenTypes.ASSIGN
			, TokenTypes.STAR_ASSIGN , TokenTypes.MOD_ASSIGN , TokenTypes.INC
			,TokenTypes.POST_INC , TokenTypes.DEC , TokenTypes.POST_DEC , TokenTypes.GE
			, TokenTypes.GT , TokenTypes.SR , TokenTypes.SR_ASSIGN , TokenTypes.LE , TokenTypes.LT
			,TokenTypes.SL , TokenTypes.SL_ASSIGN , TokenTypes.EQUAL , TokenTypes.NOT_EQUAL
			,TokenTypes.BAND , TokenTypes.BAND_ASSIGN , TokenTypes.BNOT , TokenTypes.BOR , TokenTypes.BOR_ASSIGN
			, TokenTypes.BXOR , TokenTypes.BXOR_ASSIGN , TokenTypes.LOR , TokenTypes.LNOT , TokenTypes.QUESTION
			,TokenTypes.COLON};
	
	@Test
	public void getDefaultTokenTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		assertArrayEquals(tokens, spy.getDefaultTokens());

		
	}
	
	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());

		assertArrayEquals(tokens, spy.getAcceptableTokens());
	}

	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());

		assertArrayEquals(tokens, spy.getRequiredTokens());
	}
	
	@Test
	public void finishTreeTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		
		spy.metrics.resetUniqueOps();
		spy.metrics.resetOps();
		
		// Set some default data
		spy.metrics.addOps();
		spy.metrics.addUniqueOps(2, 1);

		Mockito.when(mock.getLineNo()).thenReturn(1);
		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);
		assertEquals(1, spy.metrics.getOps());
		assertEquals(1, spy.metrics.getUniqueOps().size());
	}
	
	@Test
	public void visitTokenTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		spy.metrics.resetOps();
		
		Mockito.doReturn(true).when(spy).checkOperator(mock);
		Mockito.doReturn(-1).when(spy).convertUniqueOp(mock);
		spy.visitToken(mock);
		assertEquals(1,spy.metrics.getOps());
		
		Mockito.doReturn(false).when(spy).checkOperator(mock);
		spy.visitToken(mock);
		assertEquals(1,spy.metrics.getOps());
		
		Mockito.when(mock.getType()).thenReturn(TokenTypes.PLUS);
		assertEquals(1, spy.metrics.getUniqueOps().size());
	}
	
	@Test
	public void checkOperatorTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
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
	public void addUniqueOpsTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.metrics.resetUniqueOps();
		
		// initial add passing
		Mockito.doReturn(TokenTypes.MINUS).when(spy).convertUniqueOp(mock);
		spy.addUniqueOps(mock);
		assertEquals(spy.metrics.getUniqueOps().size() , 1);
		
		//fail to add same object
		spy.addUniqueOps(mock);
		assertEquals(spy.metrics.getUniqueOps().size() , 1);
		
		// key -1
		Mockito.doReturn(-1).when(spy).convertUniqueOp(mock);
		spy.addUniqueOps(mock);
		assertEquals(spy.metrics.getUniqueOps().size() , 1);
		
	}
	
	@Test
	public void convertUniqueOpTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		Mockito.doReturn(TokenTypes.DIV).when(mock).getType();
		Mockito.doReturn(true).when(spy).checkOperator(mock);
		assertEquals(TokenTypes.DIV, spy.convertUniqueOp(mock));
		
		Mockito.doReturn(false).when(spy).checkOperator(mock);
		assertEquals(-1, spy.convertUniqueOp(mock));
	}
	
	@Test
	public void beginTreeTest() {
		StructuralMetricsOperatorsCheck spy = spy(new StructuralMetricsOperatorsCheck());
		DetailAST mock = mock(DetailAST.class);
		
		spy.metrics.addOps();
		spy.metrics.addUniqueOps(1, 1);
		spy.beginTree(mock);
		assertEquals(0,spy.metrics.getUniqueOps().size());
		assertEquals(0,spy.metrics.getOps());
		
		
	}
	

}
