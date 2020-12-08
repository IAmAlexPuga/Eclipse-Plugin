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

class TestStructuralMetrics {

	/*
	 * // seperate all into local tests StructuralMetricsCheck mockStr =
	 * mock(StructuralMetricsCheck.class); StructuralMetricsCheck spyStr = spy(new
	 * StructuralMetricsCheck()); DetailAST mockAST = mock(DetailAST.class);
	 * 
	 * int[] tokens = { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD,
	 * TokenTypes.DIV, TokenTypes.STAR, TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT,
	 * TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT, TokenTypes.SLIST,
	 * TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
	 * TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC,
	 * TokenTypes.POST_INC, TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.GE,
	 * TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE,
	 * TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL,
	 * TokenTypes.NOT_EQUAL, TokenTypes.BAND, TokenTypes.BAND_ASSIGN,
	 * TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BXOR,
	 * TokenTypes.BXOR_ASSIGN, TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION,
	 * TokenTypes.COLON, TokenTypes.DOT, TokenTypes.STRING_LITERAL,
	 * TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE,
	 * TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN,
	 * TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END };
	 * 
	 * @BeforeEach void setUp() throws Exception { }
	 * 
	 * 
	 * @Test void getDefaultTokensTest() { // make sure every getTokens is equal to
	 * one and another assertArrayEquals(tokens, spyStr.getDefaultTokens());
	 * assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getAcceptableTokens());
	 * assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getRequiredTokens());
	 * 
	 * // checks missing tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN
	 * }).when(mockStr).getAcceptableTokens();
	 * assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());
	 * 
	 * // checks additional tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN,
	 * TokenTypes.EXPR, TokenTypes.IDENT, TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN,
	 * TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.STAR_ASSIGN,
	 * TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
	 * TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR,
	 * TokenTypes.SR_ASSIGN, TokenTypes.LE, TokenTypes.LT, TokenTypes.SL,
	 * TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
	 * TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR,
	 * TokenTypes.BOR_ASSIGN, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,
	 * TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
	 * TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
	 * TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
	 * TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT,
	 * TokenTypes.BLOCK_COMMENT_END, TokenTypes.ARRAY_INIT
	 * }).when(mockStr).getAcceptableTokens();
	 * assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());
	 * 
	 * }
	 * 
	 * @Test void getAcceptableTokensTest() { // make sure every getTokens is equal
	 * to one and another assertArrayEquals(tokens, spyStr.getAcceptableTokens());
	 * assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
	 * assertArrayEquals(spyStr.getDefaultTokens(), spyStr.getAcceptableTokens());
	 * 
	 * // checks missing tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN
	 * }).when(mockStr).getAcceptableTokens();
	 * assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());
	 * 
	 * // checks additional tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN,
	 * TokenTypes.EXPR, TokenTypes.IDENT, TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN,
	 * TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.STAR_ASSIGN,
	 * TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
	 * TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR,
	 * TokenTypes.SR_ASSIGN, TokenTypes.LE, TokenTypes.LT, TokenTypes.SL,
	 * TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
	 * TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR,
	 * TokenTypes.BOR_ASSIGN, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,
	 * TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
	 * TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
	 * TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
	 * TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT,
	 * TokenTypes.BLOCK_COMMENT_END, TokenTypes.ARRAY_INIT
	 * }).when(mockStr).getAcceptableTokens();
	 * assertNotSame(spyStr.getAcceptableTokens(), mockStr.getAcceptableTokens()); }
	 * 
	 * @Test void getRequiredTokensTest() { // make sure every getTokens is equal to
	 * one and another assertArrayEquals(tokens, spyStr.getRequiredTokens());
	 * assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getAcceptableTokens());
	 * assertArrayEquals(spyStr.getRequiredTokens(), spyStr.getDefaultTokens());
	 * 
	 * // checks missing tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN
	 * }).when(mockStr).getRequiredTokens();
	 * assertNotSame(spyStr.getDefaultTokens(), mockStr.getDefaultTokens());
	 * 
	 * // checks additional tokens Mockito.doReturn(new int[] { TokenTypes.PLUS,
	 * TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
	 * TokenTypes.VARIABLE_DEF, TokenTypes.NUM_INT, TokenTypes.ASSIGN,
	 * TokenTypes.EXPR, TokenTypes.IDENT, TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN,
	 * TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.STAR_ASSIGN,
	 * TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC,
	 * TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR,
	 * TokenTypes.SR_ASSIGN, TokenTypes.LE, TokenTypes.LT, TokenTypes.SL,
	 * TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL,
	 * TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR,
	 * TokenTypes.BOR_ASSIGN, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,
	 * TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
	 * TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE,
	 * TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE, TokenTypes.SINGLE_LINE_COMMENT,
	 * TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT,
	 * TokenTypes.BLOCK_COMMENT_END, TokenTypes.ARRAY_INIT
	 * }).when(mockStr).getAcceptableTokens();
	 * assertNotSame(spyStr.getRequiredTokens(), mockStr.getRequiredTokens()); }
	 * 
	 * @Test void computeBCCountTest() {
	 * 
	 * // Mockito.doReturn("Type").when(mockAST).getParent();
	 * 
	 * assertTrue(true);
	 * 
	 * }
	 * 
	 * @Test void checkNumTest() {
	 * 
	 * int[] numTokens = { TokenTypes.NUM_INT, TokenTypes.NUM_DOUBLE,
	 * TokenTypes.NUM_FLOAT, TokenTypes.NUM_LONG, TokenTypes.IDENT };
	 * 
	 * for (int tok : numTokens) { Mockito.doReturn(tok).when(mockAST).getType();
	 * assertTrue(spyStr.checkNum(mockAST));
	 * 
	 * }
	 * 
	 * Mockito.doReturn(TokenTypes.BOR_ASSIGN).when(mockAST).getType();
	 * assertFalse(spyStr.checkNum(mockAST));
	 * Mockito.doReturn(TokenTypes.ANNOTATION).when(mockAST).getType();
	 * assertFalse(spyStr.checkNum(mockAST));
	 * 
	 * }
	 * 
	 * @Test void isCommentTest() { int[] comTokens = {
	 * TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN };
	 * DetailAstImpl test = new DetailAstImpl(); test.setType(TokenTypes.EXPR);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * 
	 * for (int tok : comTokens) { Mockito.doReturn(tok).when(mockAST).getType();
	 * assertTrue(spyStr.isComment(mockAST)); }
	 * 
	 * Mockito.doReturn(TokenTypes.STAR).when(mockAST).getType();
	 * assertFalse(spyStr.isComment(mockAST)); }
	 * 
	 * @Test void isLoopTest() { int[] comTokens = {
	 * TokenTypes.LITERAL_WHILE,TokenTypes.LITERAL_FOR,TokenTypes.DO_WHILE };
	 * 
	 * for( int tok : comTokens) { Mockito.doReturn(tok).when(mockAST).getType();
	 * assertTrue(spyStr.isLoop(mockAST)); }
	 * 
	 * Mockito.doReturn(TokenTypes.ASSIGN).when(mockAST).getType();
	 * assertFalse(spyStr.isLoop(mockAST)); }
	 * 
	 * @Test void isValidIdentTest() {
	 * Mockito.doReturn(true).when(spyStr).checkIdent(mockAST);
	 * Mockito.doReturn(false).when(spyStr).checkIdentVar(mockAST);
	 * assertTrue(spyStr.isValidIdent(mockAST));
	 * 
	 * Mockito.doReturn(false).when(spyStr).checkIdent(mockAST);
	 * Mockito.doReturn(true).when(spyStr).checkIdentVar(mockAST);
	 * assertTrue(spyStr.isValidIdent(mockAST));
	 * 
	 * Mockito.doReturn(false).when(spyStr).checkIdent(mockAST);
	 * Mockito.doReturn(false).when(spyStr).checkIdentVar(mockAST);
	 * assertFalse(spyStr.isValidIdent(mockAST));
	 * 
	 * }
	 * 
	 * @Test void checkIdentTest() { int[] comTokens = { TokenTypes.DOT
	 * ,TokenTypes.VARIABLE_DEF, TokenTypes.METHOD_DEF}; DetailAstImpl test = new
	 * DetailAstImpl(); Mockito.doReturn(TokenTypes.IDENT).when(mockAST).getType();
	 * 
	 * for(int tok : comTokens) { test.setType(tok);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * assertTrue(spyStr.checkIdent(mockAST)); }
	 * 
	 * test.setType(TokenTypes.ANNOTATION);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * assertFalse(spyStr.checkIdent(mockAST));
	 * 
	 * test.setType(TokenTypes.DOT);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * Mockito.doReturn(TokenTypes.ARRAY_INIT).when(mockAST).getType();
	 * assertFalse(spyStr.checkIdent(mockAST));
	 * 
	 * }
	 * 
	 * @Test void checkIdentVarTest() {
	 * 
	 * DetailAstImpl test = new DetailAstImpl(); test.setType(TokenTypes.PLUS);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * Mockito.doReturn(TokenTypes.NUM_DOUBLE).when(mockAST).getType();
	 * assertTrue(spyStr.checkIdentVar(mockAST));
	 * 
	 * Mockito.doReturn(TokenTypes.IDENT).when(mockAST).getType();
	 * assertFalse(spyStr.checkIdentVar(mockAST));
	 * 
	 * }
	 * 
	 * @Test void checkExpressionTest() {
	 * Mockito.doReturn(TokenTypes.EXPR).when(mockAST).getType();
	 * assertTrue(spyStr.checkExpression(mockAST));
	 * 
	 * Mockito.doReturn(TokenTypes.EXTENDS_CLAUSE).when(mockAST).getType();
	 * assertFalse(spyStr.checkExpression(mockAST));
	 * 
	 * }
	 * 
	 * @Test void checkOperatorTest() { int[] comTokens = {TokenTypes.PLUS ,
	 * TokenTypes.MINUS , TokenTypes.STAR , TokenTypes.DIV , TokenTypes.MOD ,
	 * TokenTypes.PLUS_ASSIGN , TokenTypes.MINUS_ASSIGN , TokenTypes.DIV_ASSIGN ,
	 * TokenTypes.ASSIGN , TokenTypes.STAR_ASSIGN , TokenTypes.MOD_ASSIGN ,
	 * TokenTypes.INC , TokenTypes.POST_INC , TokenTypes.DEC , TokenTypes.POST_DEC ,
	 * TokenTypes.GE , TokenTypes.GT , TokenTypes.SR , TokenTypes.SR_ASSIGN ,
	 * TokenTypes.LE , TokenTypes.LT , TokenTypes.SL , TokenTypes.SL_ASSIGN ,
	 * TokenTypes.EQUAL , TokenTypes.NOT_EQUAL , TokenTypes.BAND ,
	 * TokenTypes.BAND_ASSIGN , TokenTypes.BNOT , TokenTypes.BOR ,
	 * TokenTypes.BOR_ASSIGN , TokenTypes.BXOR , TokenTypes.BXOR_ASSIGN ,
	 * TokenTypes.LOR , TokenTypes.LNOT , TokenTypes.QUESTION , TokenTypes.COLON };
	 * 
	 * for(int tok : comTokens) { Mockito.doReturn(tok).when(mockAST).getType();
	 * assertTrue(spyStr.checkOperator(mockAST)); }
	 * 
	 * Mockito.doReturn(TokenTypes.COMMENT_CONTENT).when(mockAST).getType();
	 * assertFalse(spyStr.checkOperator(mockAST));
	 * 
	 * }
	 * 
	 * @Test void addUniqueOpsTest() {
	 * Mockito.doReturn(TokenTypes.PLUS).when(mockAST).getType();
	 * spyStr.addUniqueOps(mockAST);
	 * assertTrue(spyStr.uniqOps.containsKey(TokenTypes.PLUS));
	 * assertTrue(spyStr.uniqOps.size() == 1); spyStr.addUniqueOps(mockAST);
	 * assertTrue(spyStr.uniqOps.size() == 1);
	 * 
	 * Mockito.doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(mockAST).getType();
	 * assertTrue(spyStr.uniqOps.size() == 1); }
	 * 
	 * @Test void convertUniqueOpTest() {
	 * Mockito.doReturn(TokenTypes.PLUS_ASSIGN).when(mockAST).getType();
	 * assertTrue(spyStr.convertUniqueOp(mockAST) != -1);
	 * Mockito.doReturn(TokenTypes.IDENT).when(mockAST).getType();
	 * assertTrue(spyStr.convertUniqueOp(mockAST) == -1); }
	 * 
	 * @Test void isCommentNodesRequiredTest() {
	 * assertTrue(spyStr.isCommentNodesRequired()); }
	 */
	/*
	 * @Test void beginTreeTest() { spyStr.operands = 3; spyStr.operators = 1;
	 * spyStr.bcls = 4; spyStr.loops = 6; spyStr.uniqOps.put(4, 1);
	 * spyStr.uniqOperands.put("testing", 2); spyStr.expressions = 5;
	 * spyStr.numComments = 2; spyStr.numLinesComments = 15; spyStr.bcle = 10;
	 * spyStr.hDiff = 3; spyStr.hEffort = 0.0; spyStr.hLength = 2; spyStr.hVocab =
	 * 5.0; spyStr.hVolume = 3.0;
	 * 
	 * 
	 * assertFalse(spyStr.uniqOperands.isEmpty());
	 * assertFalse(spyStr.uniqOps.isEmpty()); assertEquals(spyStr.operands, 3);
	 * assertEquals(spyStr.operators, 1); assertEquals(spyStr.bcls, 4);
	 * assertEquals(spyStr.loops, 6); assertEquals(spyStr.expressions, 5);
	 * assertEquals(spyStr.numComments, 2); assertEquals(spyStr.numLinesComments,
	 * 15); assertEquals(spyStr.bcle, 10); assertEquals(spyStr.hDiff, 3);
	 * assertEquals(spyStr.hEffort, .00); assertEquals(spyStr.hLength, 2);
	 * assertEquals(spyStr.hVocab, 5.0); assertEquals(spyStr.hVolume, 3.0);
	 * 
	 * spyStr.beginTree(mockAST); assertTrue(spyStr.uniqOperands.isEmpty());
	 * assertTrue(spyStr.uniqOps.isEmpty()); assertEquals(spyStr.operands, 0);
	 * assertEquals(spyStr.operators, 0); assertEquals(spyStr.bcls, -1);
	 * assertEquals(spyStr.loops, 0); assertEquals(spyStr.expressions, 0);
	 * assertEquals(spyStr.numComments, 0); assertEquals(spyStr.numLinesComments,
	 * 0); assertEquals(spyStr.bcle, -1); assertEquals(spyStr.hDiff, 0);
	 * assertEquals(spyStr.hEffort, 0); assertEquals(spyStr.hLength, 0);
	 * assertEquals(spyStr.hVocab, 0); assertEquals(spyStr.hVolume, 0);
	 * 
	 * 
	 * }
	 * 
	 * @Test void computeBCCCountTest() { spyStr.numLinesComments = 0; spyStr.bcle =
	 * 30; spyStr.bcls = 17;
	 * 
	 * spyStr.computeBCCount(); assertEquals(spyStr.numLinesComments, 13);
	 * assertEquals(spyStr.bcle, -1); assertEquals(spyStr.bcls, -1);
	 * 
	 * }
	 * 
	 * @Test void finishTreeTest() { StructuralMetricsCheck spy = spy(new
	 * StructuralMetricsCheck()); Mockito.doReturn(1).when(mockAST).getLineNo();
	 * spy.operands = 3; spy.operators = 1; spy.bcls = 4; spy.loops = 6;
	 * spy.uniqOps.put(4, 1); spy.uniqOperands.put("testing", 2); spy.expressions =
	 * 5; spy.numComments = 2; spy.numLinesComments = 15; spy.bcle = 10; spy.hDiff =
	 * 0; spy.hEffort = 0; spy.hLength = 0; spy.hVocab = 0; spy.hVolume = 0;
	 * 
	 * assertEquals(spy.hDiff, 0); assertEquals(spy.hEffort, 0);
	 * assertEquals(spy.hLength, 0); assertEquals(spy.hVocab, 0);
	 * assertEquals(spy.hVolume, 0);
	 * 
	 * 
	 * DetailAstImpl test = new DetailAstImpl(); test.setType(TokenTypes.PLUS);
	 * //spyStr.finishTree(mockAST);
	 * Mockito.doReturn(test).when(mockAST).getParent(); NullPointerException e =
	 * new NullPointerException();
	 * Mockito.doNothing().when(spyStr).finishTree(mockAST);
	 * 
	 * spy.hLength = spy.operators + spy.operands; spy.hVocab = spy.uniqOps.size() +
	 * spy.uniqOperands.size(); spy.hVolume = spy.hLength * Math.log(spy.hVocab);
	 * spy.hDiff = ((.5*spy.uniqOps.size())* spy.operands )/spy.uniqOps.size();
	 * spy.hEffort = spy.hDiff*spy.hVolume;
	 * 
	 * int hLength = spy.operators + spy.operands; double hVocab =
	 * spy.uniqOps.size() + spy.uniqOperands.size(); double hVolume = hLength *
	 * Math.log(hVocab); double hDiff = ((.5*spy.uniqOps.size())* spy.operands
	 * )/spy.uniqOps.size(); double hEffort = hDiff*hVolume;
	 * 
	 * assertEquals(spy.hDiff, hDiff); assertEquals(spy.hEffort, hEffort);
	 * assertEquals(spy.hLength, hLength); assertEquals(spy.hVocab, hVocab);
	 * assertEquals(spy.hVolume, hVolume); assertEquals(mockAST.getLineNo(), 1);
	 * 
	 * }
	 * 
	 * @Test void visitTokenTest() { // Checks expression is true branch 1
	 * DetailAstImpl test = new DetailAstImpl(); test.setType(TokenTypes.EXPR);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * Mockito.doReturn(TokenTypes.NUM_INT).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.operands,1); // Checks
	 * expression is true branch 1 DetailAstImpl test2 = new DetailAstImpl();
	 * test2.setType(TokenTypes.PLUS);
	 * Mockito.doReturn(test2).when(mockAST).getParent();
	 * Mockito.doReturn(TokenTypes.NUM_DOUBLE).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.operands,2); // Checks
	 * expression is false branch 1
	 * Mockito.doReturn(TokenTypes.COMMENT_CONTENT).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.operands,2);
	 * assertEquals(spyStr.bcle, -1); assertEquals(spyStr.bcls, -1);
	 * 
	 * //checks for operator test.setType(TokenTypes.PLUS);
	 * Mockito.doReturn(test).when(mockAST).getParent();
	 * Mockito.doReturn(TokenTypes.MINUS).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.operators, 1);
	 * assertEquals(spyStr.uniqOps.size(), 1);
	 * 
	 * // Checks if expression
	 * Mockito.doReturn(TokenTypes.EXPR).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.expressions, 1);
	 * 
	 * // Checks for a loop
	 * Mockito.doReturn(TokenTypes.LITERAL_FOR).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.loops, 1);
	 * 
	 * // Checks for a comment
	 * Mockito.doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(mockAST).getType();
	 * spyStr.visitToken(mockAST); assertEquals(spyStr.numComments, 1);
	 * 
	 * // Checks for a start block comment
	 * Mockito.doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(mockAST).getType();
	 * Mockito.doReturn(5).when(mockAST).getLineNo(); spyStr.visitToken(mockAST);
	 * assertEquals(spyStr.bcls, 5);
	 * 
	 * // Checks for a end block comment
	 * Mockito.doReturn(TokenTypes.BLOCK_COMMENT_END).when(mockAST).getType();
	 * Mockito.doReturn(10).when(mockAST).getLineNo(); spyStr.visitToken(mockAST);
	 * assertEquals(spyStr.bcle, -1);
	 * 
	 * 
	 * }
	 */

}
