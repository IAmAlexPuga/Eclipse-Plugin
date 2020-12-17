package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TestBolongs {
	int[] toks = {TokenTypes.CLASS_DEF};
	
	@Test
	public void getDefaulTokensTest() {
		MissingCtorCheck spy = spy(new MissingCtorCheck());
		assertArrayEquals(toks, spy.getDefaultTokens());
	}
	
	@Test
	public void getAcceptableTokensTest() {
		MissingCtorCheck spy = spy(new MissingCtorCheck());
		assertArrayEquals(toks, spy.getAcceptableTokens());
	}
	
	@Test
	public void getRequiredTokensTest() {
		MissingCtorCheck spy = spy(new MissingCtorCheck());
		assertArrayEquals(toks, spy.getRequiredTokens());
	}
	
	@Test
	public void visitTokenTest() {
		MissingCtorCheck spy = spy(new MissingCtorCheck());
		DetailAST mock = mock(DetailAST.class);
		DetailAST mockR = mock(DetailAST.class);
		
		
		Mockito.doReturn(TokenTypes.OBJBLOCK).when(mockR).getType();
		Mockito.doReturn(null).when(mockR).findFirstToken(TokenTypes.CTOR_DEF);
		
		Mockito.doReturn(mockR).when(mock).findFirstToken(TokenTypes.MODIFIERS);
		
		spy.visitToken(mock);
		
		assertEquals("missing.ctor", spy.getMessages().first().getMessage());
		
	}

}
