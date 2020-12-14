package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestStructuralMetricsCommentsCheck {
	
	@Test
	public void get_bcleTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.set_bcle(5);
		
		assertEquals(5,spy.get_bcle());
	}
	
	@Test
	public void get_bclsTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.set_bcls(5);
		
		assertEquals(5,spy.get_bcls());
	}
	
	@Test
	public void getNumLinesComments() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.addNumLinesComments(4);
		
		assertEquals(4,spy.getNumLinesComments());
	}
	
	@Test
	public void getNumComments() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.addNumComments();
		spy.addNumComments();
		
		assertEquals(2,spy.getNumComments());
	}
	
	@Test
	public void set_bcleTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		spy.set_bcle(3);
		assertNotEquals(-1,spy.get_bcle());
	}
	
	@Test
	public void set_bclsTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		spy.set_bcls(3);
		assertNotEquals(-1,spy.get_bcls());
	}
	
	@Test
	public void reset_bclsTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.set_bcls(3);
		assertNotEquals(-1,spy.get_bcls());
		spy.reset_bcls();
		assertEquals(-1,spy.get_bcls());
	}
	
	@Test
	public void reset_NumCommentsTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.addNumComments();
		assertEquals(1,spy.getNumComments());
		spy.resetNumComments();
		assertEquals(0,spy.getNumComments());
	}
	
	@Test
	public void reset_bcleTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.set_bcle(3);
		assertNotEquals(-1,spy.get_bcle());
		spy.reset_bcle();
		assertEquals(-1,spy.get_bcle());
	}
	
	@Test
	public void reset_NumLinesCommentsTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.addNumLinesComments(4);
		spy.resetNumLinesComments();
		assertEquals(0,spy.getNumLinesComments());
	}
	
	@Test
	public void isCommentTest() {
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		DetailAST parent = mock(DetailAST.class);

		Mockito.doReturn(TokenTypes.LITERAL_CHAR).when(parent).getType();
		Mockito.doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(mock).getType();
		Mockito.doReturn(parent).when(mock).getParent();
		assertTrue(spy.isComment(mock));
		
		Mockito.doReturn(TokenTypes.SINGLE_LINE_COMMENT).when(mock).getType();
		assertTrue(spy.isComment(mock));
		
		Mockito.doReturn(TokenTypes.LAND).when(mock).getType();
		assertFalse(spy.isComment(mock));
		
		Mockito.doReturn(TokenTypes.BLOCK_COMMENT_BEGIN).when(parent).getType();
		Mockito.doReturn(TokenTypes.BLOCK_COMMENT_END).when(mock).getType();
		Mockito.doReturn(parent).when(mock).getParent();
		assertFalse(spy.isComment(mock));
		

	}
	
	@Test
	public void isCommentNodesRequired() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		assertTrue(spy.isCommentNodesRequired());
	}
	@Test
	public void getDefaultTokensTest() {
		StructuralMetricsCommentsCheck mock = mock(StructuralMetricsCommentsCheck.class);
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		int[] tok = {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END};
		
		assertArrayEquals(tok, spy.getDefaultTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END, TokenTypes.LITERAL_INT }).when(mock).getDefaultTokens();
		assertNotSame(spy.getDefaultTokens(),mock.getDefaultTokens());
	}
	
	@Test
	public void getAcceptableTokensTest() {
		StructuralMetricsCommentsCheck mock = mock(StructuralMetricsCommentsCheck.class);
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		int[] tok = {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END};
		
		assertArrayEquals(tok, spy.getAcceptableTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END, TokenTypes.LITERAL_INT }).when(mock).getAcceptableTokens();
		assertNotSame(spy.getAcceptableTokens(),mock.getAcceptableTokens());
	}
	
	@Test
	public void getRequiredTokensTest() {
		StructuralMetricsCommentsCheck mock = mock(StructuralMetricsCommentsCheck.class);
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		int[] tok = {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END};
		
		assertArrayEquals(tok, spy.getRequiredTokens());
		Mockito.doReturn(new int[] {TokenTypes.EXPR, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END, TokenTypes.LITERAL_INT }).when(mock).getRequiredTokens();
		assertNotSame(spy.getRequiredTokens(),mock.getRequiredTokens());
	}
	
	@Test
	public void visitTokenTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST parent = mock(DetailAST.class);
		
		Mockito.when(parent.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
		
		// Checks if comment is increased for valid data
		Mockito.when(mock.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
		Mockito.when(mock.getParent()).thenReturn(parent);
		spy.visitToken(mock);
		assertEquals(1, spy.getNumComments());
		
		// Checks if comment is increased for valid data
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
		Mockito.when(mock.getLineNo()).thenReturn(5);
		spy.visitToken(mock);
		assertEquals(2, spy.getNumComments());
		assertEquals(5, spy.get_bcls());
		
		spy.reset_bcls();
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_END);
		Mockito.when(mock.getLineNo()).thenReturn(5);
		spy.visitToken(mock);
		assertEquals(2, spy.getNumComments());
		assertEquals(5, spy.get_bcle());
		
		// Grabs the end block
		Mockito.doReturn(4).when(spy).get_bcls();
		Mockito.when(mock.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_END);
		Mockito.when(mock.getLineNo()).thenReturn(9);
		spy.visitToken(mock);
		
		// bcle and bcls gets reset
		assertEquals(-1,spy.get_bcle());
		// reset the bcls manually since it will return 4
		Mockito.doReturn(-1).when(spy).get_bcls();
		assertEquals(-1, spy.get_bcls());
	}
	
	@Test
	public void beginTreeTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		DetailAST mock = mock(DetailAST.class);

		spy.set_bcle(2);
		spy.set_bcls(4);
		spy.addNumComments();
		spy.addNumLinesComments(4);
		
		spy.beginTree(mock);
		
		assertEquals(-1, spy.get_bcle());
		assertEquals(-1, spy.get_bcls());
		assertEquals(0, spy.getNumComments());
		assertEquals(0, spy.getNumLinesComments());
		
	}
	
	@Test
	public void finishTreeTest() {
		
		DetailAST mock = mock(DetailAST.class);
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		
		// Set some default data
		spy.addNumComments();
		spy.addNumLinesComments(30);
		
		Mockito.when(mock.getLineNo()).thenReturn(1);
		
		Mockito.doNothing().when(spy).log(Mockito.anyInt(), Mockito.anyString());
		spy.finishTree(mock);
		
		assertEquals(1, spy.getNumComments());
		assertEquals(31, spy.getNumComments() + spy.getNumLinesComments());
		
		String msgs[] = {"Number of Comments: 1", "Number of Lines Of Comments: 31"};
		int count = 0;
		for (LocalizedMessage lm : spy.getMessages()) {
			assertEquals(msgs[count], lm);
			count++;
		}
	}
	
	@Test
	public void computeBCCountTest() {
		StructuralMetricsCommentsCheck spy = spy(new StructuralMetricsCommentsCheck());
		spy.set_bcle(6);
		spy.set_bcls(3);
		
		spy.computeBCCount();
		
		assertEquals(3, spy.getNumLinesComments());
		assertEquals(-1, spy.get_bcle());
		assertEquals(-1, spy.get_bcls());
	}
	
	

}
