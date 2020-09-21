package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;

public class StructuralMetricsCheck extends AbstractCheck{

	
	private int ops = 0;
	@Override
	public int[] getDefaultTokens() {
		return new int[]{TokenTypes.VARIABLE_DEF, TokenTypes.STAR, TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV}; 
	}

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void finishTree(DetailAST rootAST) {
        // No code by default, should be overridden only by demand at subclasses
		log(rootAST.getLineNo(), ops + "");
    }


	/*
	 * @Override public int[] getDefaultTokens() { return new int[]
	 * {TokenTypes.VARIABLE_DEF}; }
	 * 
	 * 
	 * @Override public int[] getAcceptableTokens() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * @Override public int[] getRequiredTokens() { // TODO Auto-generated method
	 * stub return null; }
	 * 
	 * @Override public void visitToken(DetailAST aAST) { String variableName =
	 * findVariableName(aAST); if (itsAFieldVariable(aAST)) { reportStyleError(aAST,
	 * variableName); } }
	 * 
	 * private String findVariableName(DetailAST aAST) { DetailAST identifier =
	 * aAST.findFirstToken(TokenTypes.IDENT); return identifier.toString(); }
	 * 
	 * private boolean itsAFieldVariable(DetailAST aAST) { return
	 * aAST.getParent().getType() == TokenTypes.OBJBLOCK; }
	 * 
	 * private void reportStyleError(DetailAST aAST, String variableName) {
	 * log(aAST.getLineNo(), variableName); }
	 */

}

