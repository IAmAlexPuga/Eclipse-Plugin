package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsLoopsCheck extends AbstractCheck {

	private int loops = 0;
	
	public int getLoopCount() {
		return this.loops;
	}
	
	public void addLoopCount() {
		this.loops += 1;
	}
	
	public void resetLoopCount() {
		this.loops = 0;
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
		// logs data
		log(rootAST.getLineNo(), "Number Looping statements: " + this.getLoopCount());
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// checks for loops
		if(isLoop(aAST)) {
			this.addLoopCount();
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
		this.resetLoopCount();
		
	}
}
