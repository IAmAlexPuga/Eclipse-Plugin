package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;

public class StructuralMetricsCheck extends AbstractCheck{

	
	private int operators = 0;
	private int operands = 0;
	int test = 0;
	int test2 = 0;
	int test3 = 0;
	int c = 0;
	@Override
	public int[] getDefaultTokens() {
		//  TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR , TokenTypes.VARIABLE_DEF
		return new int[]{ TokenTypes.ASSIGN, TokenTypes.EXPR}; 
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
        // logs data for MS1
		log(rootAST.getLineNo(), "Number of operators " + operators);
		log(rootAST.getLineNo(), "Number of operands: " + operands);
		log(rootAST.getLineNo(), "Halstead Length: " + (operators + operands));
		log(rootAST.getLineNo(), "isTest: " + test);
		log(rootAST.getLineNo(), "isTest2: " + test2);
		log(rootAST.getLineNo(), "isTest3: " + test3);
		log(rootAST.getLineNo(), "c: " + c);
    }
	
	@Override 
	public void visitToken(DetailAST aAST) {
		DetailAST objBlock = aAST.findFirstToken(TokenTypes.OBJBLOCK);
		DetailAST t = aAST.findFirstToken(TokenTypes.EXPR);
		
		if(t != null && checkExpr(t))
		{
			test2 = 1;
		}
		// checkExpr(aAST) || aAST.getType() == TokenTypes.EXPR ||
		
		if(  aAST.getType() == TokenTypes.ASSIGN && aAST.hasChildren())
		{
			
			c += getOperators(aAST);
			
			
			test = 2;
			objBlock = aAST.getFirstChild();
			
			if(objBlock.getType() == TokenTypes.EXPR)
			{
				operators += getOperators(objBlock);
				test3 += traverse(objBlock);
			}
			
		}
		
		
	}
	
	private boolean checkOperator(DetailAST ast)
	{
		return ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.MINUS || ast.getType() == TokenTypes.STAR
				|| ast.getType() == TokenTypes.DIV || ast.getType() == TokenTypes.MOD;
	}
	
	
	private boolean checkNum(DetailAST ast)
	{
		return ast.getType() == TokenTypes.NUM_INT || ast.getType() == TokenTypes.NUM_DOUBLE 
				|| ast.getType() == TokenTypes.NUM_FLOAT || ast.getType() == TokenTypes.NUM_LONG 
				|| ast.getType() == TokenTypes.IDENT;
	}
	
	private int traverse(DetailAST ast)
	{
		if(ast == null)
		{
			return 0;
		}
		
		if(checkOperator(ast))
		{
			return 1 + traverse(ast.getNextSibling()) + traverse(ast.getFirstChild());
			
		}else if(checkNum(ast)) {
			operands += 1;
			return traverse(ast.getNextSibling())  + traverse(ast.getFirstChild());
			
		}
		
		return traverse(ast.getNextSibling()) + traverse(ast.getFirstChild());
	}
	
	
	
	private int getOperators(DetailAST objBlock)
	{
		
		int total = 0;
		total += objBlock.getChildCount(TokenTypes.STAR);
		total += objBlock.getChildCount(TokenTypes.PLUS);
		total += objBlock.getChildCount(TokenTypes.MINUS);
		total += objBlock.getChildCount(TokenTypes.MOD);
		total += objBlock.getChildCount(TokenTypes.DIV);
		
		return total;
	}
	
	private boolean checkExpr(DetailAST ast)
	{
		if(ast != null)
		{
			return ast.equals(TokenTypes.EXPR);
		}
		return false;
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		operators = 0;
		operands = 0;
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
