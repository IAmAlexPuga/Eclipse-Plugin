package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;

public class StructuralMetricsCheck extends AbstractCheck {

	private int operators = 0;
	private int operands = 0;
	
	private int test2 = 0;
	private int test3 = 0;

	
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
		
		log(rootAST.getLineNo(), "Number of operators2 " + test3);
		log(rootAST.getLineNo(), "Number of operands2: " + test2);
		
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.VARIABLE_DEF)
		{
			operands -= 1;
			operators += traverse(aAST.getFirstChild());
		}else if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.EXPR)
		{
			operands -= 1;
			operators += traverse(aAST.getFirstChild().getFirstChild());
		}
		
		
		// this also works to get operands
				
				 // checks if its a number 
		if(checkNum(aAST) && (aAST.getParent().getType() ==TokenTypes.EXPR || checkOperator(aAST.getParent()))) {
			test2 += 1; 
		}
				 
		// this also works to get operators
				
				 // checks if its an operator 
		if(checkOperator(aAST)) { 
			test3+=1; 
		}
				 
		
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

	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		operators = 0;
		operands = 0;
		test2 = 0;
		test3 = 0;
		
	}
	

}
