package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsCheck extends AbstractCheck {

	private int operators = 0;
	private int operands = 0;
	private int expressions = 0;
	private Map<Integer, Integer> uniqOps =  new HashMap<Integer, Integer>();
	private Map<String, Integer> uniqOperands =  new HashMap<String, Integer>();
	private String list = "";
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
				TokenTypes.DOT, TokenTypes.STRING_LITERAL };
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		
		// logs data for MS1
		log(rootAST.getLineNo(), "Number of operators " + operators);
		log(rootAST.getLineNo(), "Number of operands: " + operands);
		log(rootAST.getLineNo(), "Halstead Length: " + (operators + operands));
		log(rootAST.getLineNo(), "Halstead Length: " + (uniqOps.size() + uniqOperands.size()));
		log(rootAST.getLineNo(), "Expressions: " + expressions);
		log(rootAST.getLineNo(), "Identities : " + list);

	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// traversers through the tree, not sure why this over counts but
		// have a different solution. Might look into this
//		if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.VARIABLE_DEF)
//		{
//			operands -= 1;
//			operators += traverse(aAST.getFirstChild());
//		}else if(aAST.getParent().getType() == TokenTypes.SLIST && aAST.getType() == TokenTypes.EXPR)
//		{
//			operands -= 1;
//			operators += traverse(aAST.getFirstChild().getFirstChild());
//		}
		
		
		// this also works to get operands
				
		// checks if its a number 
		if(checkNum(aAST) && (aAST.getParent().getType() ==TokenTypes.EXPR || checkOperator(aAST.getParent()))) {
			operands += 1; 
		} 
				
		// checks if its an operator 
		if(checkOperator(aAST)) { 
			operators+=1; 
		}
		
		// checks if unique operator to add to uniqOps
		addUniqueOps(aAST);
		
		// checks if token is an expression
		if(checkExpression(aAST)) {
			expressions += 1;
		}
		
		if(isValidIdent(aAST)) {
			if(!uniqOperands.containsKey(aAST.getText()))
			{
				uniqOperands.put(aAST.getText(), 1);
			}
		}	 
		
	}
	
	private boolean isValidIdent(DetailAST ast) {
		if( checkIdent(ast) || checkIdentVar(ast) ) {
		//if( checkIdentVar(ast) ) {
			return true;
		}
		return false;
	}
	
	private boolean checkIdent(DetailAST ast) {
		
		// idk if class def should be included or not
		// assuming imports ok
		return ast.getType() == TokenTypes.IDENT && ast.getType() != TokenTypes.CLASS_DEF && (
				ast.getParent().getType() == TokenTypes.DOT || 
				ast.getParent().getType() == TokenTypes.VARIABLE_DEF ||
				ast.getParent().getType() == TokenTypes.METHOD_DEF);
	}
	
	private boolean checkIdentVar(DetailAST ast) {
		return !checkOperator(ast) && ast.getType() != TokenTypes.IDENT && !checkExpression(ast) && ( checkOperator(ast.getParent()) || checkExpression(ast.getParent()) );
	}
	
	private boolean checkExpression(DetailAST ast) {
		return ast.getType() == TokenTypes.EXPR;
	}

	private boolean checkOperator(DetailAST ast) {
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

	private boolean checkNum(DetailAST ast) {
		return ast.getType() == TokenTypes.NUM_INT || ast.getType() == TokenTypes.NUM_DOUBLE
				|| ast.getType() == TokenTypes.NUM_FLOAT || ast.getType() == TokenTypes.NUM_LONG
				|| ast.getType() == TokenTypes.IDENT;
	}
	
	private void addUniqueOps(DetailAST ast) {
		int key = convertUniqueOp(ast);
		// makes sure key is non negative and uniqOps does not contain key
		if(key != -1 && !uniqOps.containsKey(key)) {
			uniqOps.put(key, 1);
		}
	}
	
	private int convertUniqueOp(DetailAST ast) {
		// can use check operator then just return ast.getType()
		// checks what op the ast type is
		switch(ast.getType()) {
		case TokenTypes.PLUS:
			return TokenTypes.PLUS;
		case TokenTypes.PLUS_ASSIGN:
			return TokenTypes.PLUS_ASSIGN;
		case TokenTypes.MINUS:
			return TokenTypes.MINUS;
		case TokenTypes.MINUS_ASSIGN:
			return TokenTypes.MINUS_ASSIGN;
		case TokenTypes.MOD:
			return TokenTypes.MOD;
		case TokenTypes.MOD_ASSIGN:
			return TokenTypes.MOD_ASSIGN;
		case TokenTypes.STAR:
			return TokenTypes.STAR;
		case TokenTypes.STAR_ASSIGN:
			return TokenTypes.STAR_ASSIGN;
		case TokenTypes.DIV:
			return TokenTypes.DIV;
		case TokenTypes.DIV_ASSIGN:
			return TokenTypes.DIV_ASSIGN;
		case TokenTypes.INC:
			return TokenTypes.INC;
		case TokenTypes.POST_INC:
			return TokenTypes.POST_INC;
		case TokenTypes.DEC:
			return TokenTypes.DEC;
		case TokenTypes.POST_DEC:
			return TokenTypes.POST_DEC;
		case TokenTypes.GE:
			return TokenTypes.GE;
		case TokenTypes.GT:
			return TokenTypes.GT;
		case TokenTypes.SR:
			return TokenTypes.SR;
		case TokenTypes.SR_ASSIGN:
			return TokenTypes.SR_ASSIGN;
		case TokenTypes.LE:
			return TokenTypes.LE;
		case TokenTypes.LT:
			return TokenTypes.LT;
		case TokenTypes.SL:
			return TokenTypes.SL;
		case TokenTypes.SL_ASSIGN:
			return TokenTypes.SL_ASSIGN;
		case TokenTypes.EQUAL:
			return TokenTypes.EQUAL;
		case TokenTypes.NOT_EQUAL:
			return TokenTypes.NOT_EQUAL;
		case TokenTypes.BAND:
			return TokenTypes.BAND;
		case TokenTypes.BAND_ASSIGN:
			return TokenTypes.BAND_ASSIGN;
		case TokenTypes.BNOT:
			return TokenTypes.BNOT;
		case TokenTypes.BOR:
			return TokenTypes.BOR;
		case TokenTypes.BOR_ASSIGN:
			return TokenTypes.BOR_ASSIGN;
		case TokenTypes.BXOR:
			return TokenTypes.BXOR;
		case TokenTypes.BXOR_ASSIGN:
			return TokenTypes.BXOR_ASSIGN;
		case TokenTypes.LOR:
			return TokenTypes.LOR;
		case TokenTypes.LNOT:
			return TokenTypes.LNOT;
		case TokenTypes.QUESTION:
			return TokenTypes.QUESTION;
		case TokenTypes.COLON:
			return TokenTypes.COLON;
		case TokenTypes.ASSIGN:
			return TokenTypes.ASSIGN;
		default:
			break;
		}
		
		return -1;
		
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
		uniqOps = new HashMap<Integer, Integer>();
		uniqOperands =  new HashMap<String, Integer>();
		expressions = 0;
		
	}
	

}
