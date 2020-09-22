package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;

public class StructuralMetricsCheck extends AbstractCheck {

	private int operators = 0;
	private int operands = 0;
	int test = 0;
	int test2 = 0;
	int test3 = 0;
	int c = 0;

	
	 @Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return new int[] 
				 {TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV,TokenTypes.STAR , TokenTypes.VARIABLE_DEF, 
					TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
					TokenTypes.SLIST };
	 }
	 

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] {TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, 
				TokenTypes.DIV,TokenTypes.STAR , TokenTypes.VARIABLE_DEF, 
				TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST };
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
		log(rootAST.getLineNo(), "isoperators: " + test);
		log(rootAST.getLineNo(), "isoparands: " + test2);
		log(rootAST.getLineNo(), "isoparands3: " + test3);
		//log(rootAST.getLineNo(), "c: " + c);
	}

	@Override
	public void visitToken(DetailAST aAST) {
		//|| aAST.getType() == TokenTypes.ASSIGN
		// checks to see if token is expression and its parent is SLIST
		/*
		 * if((aAST.getType() == TokenTypes.EXPR) && aAST.getParent().getType() ==
		 * TokenTypes.SLIST ) { test += traverse(aAST); }
		 * 
		 * // checks to see if token is viraible def and parent is SLIST // needs to sub
		 * 1 to operands since counts extra int if( aAST.getType() ==
		 * TokenTypes.VARIABLE_DEF && aAST.getParent().getType() == TokenTypes.SLIST) {
		 * operands -= 1; test += traverse(aAST); }
		 */
		
		// checks if its a number
		if(checkNum(aAST) && (aAST.getParent().getType() == TokenTypes.EXPR || checkOperator(aAST.getParent())))
		{
			test2 += 1;
		}
		
		// if parent is operator || parent is expression
		
		
		// Parent is expression, assign
				// if exp and parent slist and child assign - 1
				// if variable def & parent slist - 1
		if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.VARIABLE_DEF)
		{
			operands -= 1;
			traverse(aAST);
			test3 += traverseOperands(aAST);
		}else if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.EXPR)
		{
			operands -= 1;
			test3 += traverseOperands(aAST);
			traverse(aAST);
		}
		
		
		// checks if its an operator
		
		if(checkOperator(aAST))
		{
			operators+=1;
		}
		
	}

	private boolean checkExToAssign(DetailAST ast) {
		if (ast.getType() == TokenTypes.EXPR && ast.getFirstChild() != null
				&& ast.getFirstChild().getType() == TokenTypes.ASSIGN) {
			
			return true;
		}
		return false;
	}

	private boolean checkOperator(DetailAST ast) {
		return ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.MINUS || ast.getType() == TokenTypes.STAR
				|| ast.getType() == TokenTypes.DIV || ast.getType() == TokenTypes.MOD;
	}

	private boolean checkNum(DetailAST ast) {
		return ast.getType() == TokenTypes.NUM_INT || ast.getType() == TokenTypes.NUM_DOUBLE
				|| ast.getType() == TokenTypes.NUM_FLOAT || ast.getType() == TokenTypes.NUM_LONG
				|| ast.getType() == TokenTypes.IDENT;
	}

	private int traverse(DetailAST ast) {
		if (ast == null) {
			return 0;
		}

		if (checkOperator(ast)) {
			return 1 + traverse(ast.getNextSibling()) + traverse(ast.getFirstChild());

		} else if (checkNum(ast)) {
			operands += 1;
			return traverse(ast.getNextSibling()) + traverse(ast.getFirstChild());

		}

		return traverse(ast.getNextSibling()) + traverse(ast.getFirstChild());
	}

	private int getOperators(DetailAST objBlock) {

		int total = 0;
		total += objBlock.getChildCount(TokenTypes.STAR);
		total += objBlock.getChildCount(TokenTypes.PLUS);
		total += objBlock.getChildCount(TokenTypes.MINUS);
		total += objBlock.getChildCount(TokenTypes.MOD);
		total += objBlock.getChildCount(TokenTypes.DIV);

		return total;
	}

	private boolean checkExpr(DetailAST ast) {
		if (ast != null) {
			return ast.equals(TokenTypes.EXPR);
		}
		return false;
	}

	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		operators = 0;
		operands = 0;
		c = 0;
		test = 0;
		test2 = 0;
		test3 = 0;
	}
	
	private int traverseOperands(DetailAST ast) {
		if (ast == null) {
			return 0;
		}

		if (checkOperator(ast)) {
			return traverseOperands(ast.getNextSibling()) + traverseOperands(ast.getFirstChild());

		} else if (checkNum(ast)) {
			
			return 1 + traverseOperands(ast.getNextSibling()) + traverseOperands(ast.getFirstChild());

		}

		return traverseOperands(ast.getNextSibling()) + traverseOperands(ast.getFirstChild());
	}
	
	
	private int getItems(DetailAST ast)
	{
		if(ast == null)
		{
			return 0;
		}
		return 1 + getItems(ast.getFirstChild()) + getItems(ast.getNextSibling());
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
