package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

class TestStructuralMetrics {

	StructuralMetricsCheck mockStr = mock(StructuralMetricsCheck.class);
	StructuralMetricsCheck spyStr = spy(new StructuralMetricsCheck());
	DetailAST mockAST = mock(DetailAST.class);

	int[] tokens = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
			TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
			TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
			TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
			TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
			TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.BAND,
			TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BXOR,
			TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
			TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR,
			TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN,
			TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END };

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		assertTrue(true);
	}

	@Test
	void getDefaultTokensTest() {
		// make sure every getTokens is equal to one and another
		assertArrayEquals(tokens, spyStr.getDefaultTokens());
		assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getRequiredTokens());

		// checks missing tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN }).when(mockStr).getAcceptableTokens();
		assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());

		// checks additional tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
				TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END,
				TokenTypes.ARRAY_INIT }).when(mockStr).getAcceptableTokens();
		assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());

	}

	@Test
	void getAcceptableTokensTest() {
		// make sure every getTokens is equal to one and another
		assertArrayEquals(tokens, spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getAcceptableTokens());

		// checks missing tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN }).when(mockStr).getAcceptableTokens();
		assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());

		// checks additional tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
				TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END,
				TokenTypes.ARRAY_INIT }).when(mockStr).getAcceptableTokens();
		assertNotSame(spyStr.getAcceptableTokens(), mockStr.getAcceptableTokens());
	}

	@Test
	void getRequiredTokensTest() {
		// make sure every getTokens is equal to one and another
		assertArrayEquals(tokens, spyStr.getRequiredTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getDefaultTokens());
		
		// checks missing tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN }).when(mockStr).getRequiredTokens();
		assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());

		// checks additional tokens
		Mockito.doReturn(new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
				TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
				TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
				TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
				TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
				TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
				TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END,
				TokenTypes.ARRAY_INIT }).when(mockStr).getAcceptableTokens();
		assertNotSame(spyStr.getRequiredTokens(), mockStr.getRequiredTokens());
	}

	@Test
	void computeBCCountTest() {

		// Mockito.doReturn("Type").when(mockAST).getParent();

		assertTrue(true);

	}

	@Test
	void checkNumTest() {

		int[] numTokens = { TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_LONG,
				TokenTypes.IDENT };

		for (int tok : numTokens) {
			Mockito.doReturn(tok).when(mockAST).getType();
			assertTrue(spyStr.checkNum(mockAST));

		}

		Mockito.doReturn(TokenTypes.BOR_ASSIGN).when(mockAST).getType();
		assertFalse(spyStr.checkNum(mockAST));
		Mockito.doReturn(TokenTypes.ANNOTATION).when(mockAST).getType();
		assertFalse(spyStr.checkNum(mockAST));

	}

	@Test
	void isCommentTest() {
		int[] comTokens = { TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
		DetailAstImpl test = new DetailAstImpl();
		test.setType(TokenTypes.EXPR);
		Mockito.doReturn(test).when(mockAST).getParent();

		for (int tok : comTokens) {
			Mockito.doReturn(tok).when(mockAST).getType();
			assertTrue(spyStr.isComment(mockAST));
		}

		Mockito.doReturn(TokenTypes.STAR).when(mockAST).getType();
		assertFalse(spyStr.isComment(mockAST));
	}
	
	@Test
	void isLoopTest() {
		int[] comTokens = { TokenTypes.LITERAL_WHILE,TokenTypes.LITERAL_FOR,TokenTypes.DO_WHILE };
		
		for( int tok : comTokens) {
			Mockito.doReturn(tok).when(mockAST).getType();
			assertTrue(spyStr.isLoop(mockAST));
		}
		
		Mockito.doReturn(TokenTypes.ASSIGN).when(mockAST).getType();
		assertFalse(spyStr.isLoop(mockAST));
	}
	
	@Test
	void isValidIdentTest() {
		Mockito.doReturn(true).when(spyStr).checkIdent(mockAST);
		Mockito.doReturn(false).when(spyStr).checkIdentVar(mockAST);
	}

}
