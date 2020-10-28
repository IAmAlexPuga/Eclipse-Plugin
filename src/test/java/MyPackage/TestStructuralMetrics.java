package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

class TestStructuralMetrics {

	StructuralMetricsCheck mockStr = mock(StructuralMetricsCheck.class);
	StructuralMetricsCheck spyStr= spy(new StructuralMetricsCheck());
	int[] tokens = {TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, 
			TokenTypes.DIV,TokenTypes.STAR , TokenTypes.VARIABLE_DEF, 
			TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
			TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
			TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC,
			TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN,
			TokenTypes.LE, TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL
			, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
			TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
			TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE,
			TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END};
	;
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
		assertArrayEquals(spyStr.getAcceptableTokens(), spyStr.getDefaultTokens());
		assertArrayEquals(spyStr.getAcceptableTokens(), spyStr.getRequiredTokens());
	}
	
	@Test
	void getAcceptableTokensTest() {
		// make sure every getTokens is equal to one and another
		assertArrayEquals(tokens, spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getAcceptableTokens());
	}
	
	@Test
	void getRequiredTokensTest() {
		// make sure every getTokens is equal to one and another
		assertArrayEquals(tokens, spyStr.getRequiredTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
		assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getDefaultTokens());
	}

}
