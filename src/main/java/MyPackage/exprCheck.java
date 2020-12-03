package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class exprCheck extends AbstractCheck{
	public int expressions = 0;
	 
	@Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return getAcceptableTokens();
	 }
	 
	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] {TokenTypes.EXPR};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		log(rootAST.getLineNo(), "Expressions: " + expressions);
	}

	@Override
	public void visitToken(DetailAST aAST) {
		// checks if token is an expression
		if(checkExpression(aAST)) {
			expressions += 1;
		}
		
		
	}
	
	public boolean checkExpression(DetailAST ast) {
		return ast.getType() == TokenTypes.EXPR;
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		expressions = 0;
	}
	
}
