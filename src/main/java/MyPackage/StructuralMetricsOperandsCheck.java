package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;

import jdk.nashorn.internal.parser.TokenType;

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
		return new int[] { TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.MOD, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.VARIABLE_DEF, TokenTypes.TYPECAST, TokenTypes.NUM_INT, TokenTypes.ASSIGN, TokenTypes.EXPR,
				TokenTypes.IDENT, TokenTypes.METHOD_CALL, TokenTypes.SLIST, TokenTypes.PLUS_ASSIGN,
				TokenTypes.MINUS_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.STAR_ASSIGN, TokenTypes.MOD_ASSIGN,
				TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.GE, TokenTypes.GT,
				TokenTypes.SR, TokenTypes.SR_ASSIGN, TokenTypes.LE, TokenTypes.LT, TokenTypes.SL, TokenTypes.SL_ASSIGN,
				TokenTypes.EQUAL, TokenTypes.NOT_EQUAL, TokenTypes.BAND, TokenTypes.BAND_ASSIGN, TokenTypes.BNOT,
				TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.CHAR_LITERAL, TokenTypes.BXOR, TokenTypes.BXOR_ASSIGN,
				TokenTypes.LOR, TokenTypes.LNOT, TokenTypes.QUESTION, TokenTypes.COLON, TokenTypes.DOT,
				TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE };
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

		if (isVariable(aAST) && (checkExpression(aAST.getParent()) || checkOperator(aAST.getParent()))) {
			metrics.addOperands();
			addUniqueOperand(aAST);
		}

	}


	public boolean isValidMethodCall(DetailAST ast) {
		return (ast.getType() == TokenTypes.METHOD_CALL && ast.getParent().getParent().getType() != TokenTypes.SLIST);

	}

	public boolean isVariable(DetailAST ast) {
		return (checkNum(ast) || ast.getType() == TokenTypes.CHAR_LITERAL || ast.getType() == TokenTypes.STRING_LITERAL
				|| ast.getType() == TokenTypes.TYPECAST || isValidMethodCall(ast)); 
	}

	public boolean checkExpression(DetailAST ast) {
		return ast.getType() == TokenTypes.EXPR;
	}
	
	public void addUniqueOperand(DetailAST aAST) {
		if(aAST.getType() == TokenTypes.METHOD_CALL) {
			String key = getName(aAST); 
			metrics.addUniqueOperands(key, 1);
		}else {
			metrics.addUniqueOperands(aAST.getText(), 1);

		}
		
	}

	public boolean checkOperator(DetailAST ast) {
		return ast.getType() == TokenTypes.PLUS || ast.getType() == TokenTypes.MINUS || ast.getType() == TokenTypes.STAR
				|| ast.getType() == TokenTypes.DIV || ast.getType() == TokenTypes.MOD
				|| ast.getType() == TokenTypes.PLUS_ASSIGN || ast.getType() == TokenTypes.MINUS_ASSIGN
				|| ast.getType() == TokenTypes.DIV_ASSIGN || ast.getType() == TokenTypes.ASSIGN
				|| ast.getType() == TokenTypes.STAR_ASSIGN || ast.getType() == TokenTypes.MOD_ASSIGN
				|| ast.getType() == TokenTypes.INC || ast.getType() == TokenTypes.POST_INC
				|| ast.getType() == TokenTypes.DEC || ast.getType() == TokenTypes.POST_DEC
				|| ast.getType() == TokenTypes.GE || ast.getType() == TokenTypes.GT || ast.getType() == TokenTypes.SR
				|| ast.getType() == TokenTypes.SR_ASSIGN || ast.getType() == TokenTypes.LE
				|| ast.getType() == TokenTypes.LT || ast.getType() == TokenTypes.SL
				|| ast.getType() == TokenTypes.SL_ASSIGN || ast.getType() == TokenTypes.EQUAL
				|| ast.getType() == TokenTypes.NOT_EQUAL || ast.getType() == TokenTypes.BAND
				|| ast.getType() == TokenTypes.BAND_ASSIGN || ast.getType() == TokenTypes.BNOT
				|| ast.getType() == TokenTypes.BOR || ast.getType() == TokenTypes.BOR_ASSIGN
				|| ast.getType() == TokenTypes.BXOR || ast.getType() == TokenTypes.BXOR_ASSIGN
				|| ast.getType() == TokenTypes.LOR || ast.getType() == TokenTypes.LNOT
				|| ast.getType() == TokenTypes.QUESTION || ast.getType() == TokenTypes.COLON;
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
	
	public String getName(DetailAST ast) {
		
		String res = "";

		ast = ast.findFirstToken(59);
		while(ast.getFirstChild().getType() != TokenTypes.IDENT) {
			ast = ast.getFirstChild();
			if(ast.getNextSibling() != null) {
				res += ast.getNextSibling().getText() + ".";
			}
			ast = ast.findFirstToken(59);
		}
		
		ast = ast.getFirstChild();
		while(ast != null) {
			res += ast.getText() + ".";
			ast = ast.getNextSibling();
		}
		
		return res;
	}

}
