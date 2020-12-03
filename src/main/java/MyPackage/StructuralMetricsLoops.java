package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsLoops extends AbstractCheck {

	private int loops = 0;
	
	public int getLoopCount() {
		return this.loops;
	}
	 
	@Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return getAcceptableTokens();
	 }
	 
	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] { TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE,};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		// sepeate into function calls
		// logs data
		log(rootAST.getLineNo(), "Number Looping statements: " + loops);
		System.out.println("asdfddddddddddddddddddddddddddddddddddddddddddddddddd");
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// checks for loops
		if(isLoop(aAST)) {
			loops += 1;
		}
		
	}

	public boolean isLoop(DetailAST ast) {
		return ast.getType() == TokenTypes.LITERAL_WHILE || 
				ast.getType() == TokenTypes.LITERAL_FOR || 
				ast.getType() == TokenTypes.DO_WHILE;
	}

	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		loops = 0;
		
	}
}
