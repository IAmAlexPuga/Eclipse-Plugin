package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;


public class StructuralMetricsOperandsCheck extends AbstractCheck {
	
	
	public static MetricsSingleton metrics = MetricsSingleton.getInstance();
	 
	@Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return getAcceptableTokens();
	 }
	 

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] {TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, 
				TokenTypes.DIV,TokenTypes.STAR , TokenTypes.VARIABLE_DEF, 
				TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR, TokenTypes.IDENT,
				TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN,
				TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.INC, TokenTypes.POST_INC,
				TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT, TokenTypes.SR, TokenTypes.SR_ASSIGN,
				TokenTypes.LE, TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN, TokenTypes.EQUAL, TokenTypes.NOT_EQUAL
				, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT, TokenTypes.BOR, TokenTypes.BOR_ASSIGN,
				TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON,
				TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		
		// sepeate into function calls
		log(rootAST.getLineNo(), "Number of operands: " + metrics.getOperands());
		log(rootAST.getLineNo(), "Number of unique operands: " + metrics.getUniqueOperands().size());
	
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// this also works to get operands
		// add checks if its others not than number like char, strings, ect...		
		// checks if its a number 
		if(checkNum(aAST) && (aAST.getParent().getType() ==TokenTypes.EXPR || checkOperator(aAST.getParent()))) {
			metrics.addOperands();
		} 
				
		
		// checks for unique operands 
		if(isValidIdent(aAST)) {
			if(!metrics.getUniqueOperands().containsKey(aAST.getText()))
			{
				metrics.addUniqueOperands(aAST.getText(), 1);
			}
		}
		
		
	}
	
	
	public boolean isValidIdent(DetailAST ast) {
		if( this.checkIdent(ast) || this.checkIdentVar(ast) ) {
			return true;
		}
		return false;
	}
	
	public boolean checkIdent(DetailAST ast) {
		if(ast.getParent() == null) {
			return false;
		}
		// idk if class def should be included or not
		// assuming imports ok
		return ast.getType() == TokenTypes.IDENT && (
				ast.getParent().getType() == TokenTypes.DOT || 
				ast.getParent().getType() == TokenTypes.VARIABLE_DEF ||
				ast.getParent().getType() == TokenTypes.METHOD_DEF);
	}
	
	public boolean checkIdentVar(DetailAST ast) {
		if(ast.getParent() == null) {
			return false;
		}
		
		return !checkOperator(ast) && ast.getType() != TokenTypes.IDENT && !checkExpression(ast) &&
				( checkOperator(ast.getParent()) || checkExpression(ast.getParent()) );
	}
	
	public boolean checkExpression(DetailAST ast) {
		return ast.getType() == TokenTypes.EXPR;
	}

	public boolean checkOperator(DetailAST ast) {
		return ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.MINUS || ast.getType() == TokenTypes.STAR
				|| ast.getType() == TokenTypes.DIV || ast.getType() == TokenTypes.MOD || ast.getType() == TokenTypes.PLUS_ASSIGN 
				|| ast.getType() == TokenTypes.MINUS_ASSIGN || ast.getType() == TokenTypes.DIV_ASSIGN || ast.getType() == TokenTypes.ASSIGN
				|| ast.getType() == TokenTypes.STAR_ASSIGN || ast.getType() == TokenTypes.MOD_ASSIGN || ast.getType() == TokenTypes.INC
				|| ast.getType() ==TokenTypes.POST_INC || ast.getType() == TokenTypes.DEC || ast.getType() == TokenTypes.POST_DEC || ast.getType() == TokenTypes.GE
				|| ast.getType() == TokenTypes.GT || ast.getType() == TokenTypes.SR || ast.getType() == TokenTypes.SR_ASSIGN || ast.getType() == TokenTypes.LE || ast.getType() == TokenTypes.LT
				|| ast.getType() ==TokenTypes.SL || ast.getType() == TokenTypes.SL_ASSIGN || ast.getType() == TokenTypes.EQUAL || ast.getType() == TokenTypes.NOT_EQUAL
				|| ast.getType() ==TokenTypes.BAND || ast.getType() == TokenTypes.BAND_ASSIGN || ast.getType() == TokenTypes.BNOT || ast.getType() == TokenTypes.BOR || ast.getType() == TokenTypes.BOR_ASSIGN
				|| ast.getType() == TokenTypes.BXOR || ast.getType() == TokenTypes.BXOR_ASSIGN || ast.getType() == TokenTypes.LOR || ast.getType() == TokenTypes.LNOT || ast.getType() == TokenTypes.QUESTION
				|| ast.getType() ==TokenTypes.COLON;
	}

	public boolean checkNum(DetailAST ast) {
		return ast.getType() == TokenTypes.NUM_INT || ast.getType() == TokenTypes.NUM_DOUBLE
				|| ast.getType() == TokenTypes.NUM_FLOAT || ast.getType() == TokenTypes.NUM_LONG
				|| ast.getType() == TokenTypes.IDENT;
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		metrics.resetOperands();
		metrics.resetUniqueOperands();
		
	}
	
	
	

}
