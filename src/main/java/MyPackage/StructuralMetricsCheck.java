package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsCheck extends AbstractCheck {

	public int operators = 0;
	public int operands = 0;
	public int expressions = 0;
	public int loops = 0;
	public int numComments = 0;
	public int numLinesComments = 0;
	public int bcls = -1;
	public int bcle = -1;
	public Map<Integer, Integer> uniqOps =  new HashMap<Integer, Integer>();
	public Map<String, Integer> uniqOperands =  new HashMap<String, Integer>();
	int hLength = 0;
	double hVocab = 0;
	double hVolume = 0;
	double hDiff = 0;
	double hEffort = 0;
	 
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
				TokenTypes.DOT, TokenTypes.STRING_LITERAL, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_FOR, TokenTypes.DO_WHILE,
				TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.COMMENT_CONTENT, TokenTypes.BLOCK_COMMENT_END};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		hLength = operators + operands;
		hVocab = uniqOps.size() + uniqOperands.size();
		hVolume = hLength * Math.log(hVocab);
		hDiff = ((.5*uniqOps.size())* operands )/uniqOps.size();
		hEffort = hDiff*hVolume;
		
		// sepeate into function calls
		// logs data for MS1
		log(rootAST.getLineNo(), "Number of operators " + operators);
		log(rootAST.getLineNo(), "Number of operands: " + operands);
		log(rootAST.getLineNo(), "Halstead Length: " + hLength );
		log(rootAST.getLineNo(), "Halstead Vocab: " + hVocab);
		log(rootAST.getLineNo(), "Halstead Volume: " + 	String. format("%.2f", hVolume));
		log(rootAST.getLineNo(), "Halstead Difficulty: " + String. format("%.2f", hDiff));
		log(rootAST.getLineNo(), "Halstead Effort: " + String. format("%.2f", hEffort));
		log(rootAST.getLineNo(), "Expressions: " + expressions);
		log(rootAST.getLineNo(), "Number Looping statements: " + loops);
		log(rootAST.getLineNo(), "Number of Comments: " + numComments);
		log(rootAST.getLineNo(), "Number of Lines Of Comments: " + (numLinesComments + numComments));
		

	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// this also works to get operands
		// add checks if its others not than number like char, strings, ect...		
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
		
		// checks for unique operands 
		if(isValidIdent(aAST)) {
			if(!uniqOperands.containsKey(aAST.getText()))
			{
				uniqOperands.put(aAST.getText(), 1);
			}
		}
		
		// checks for loops
		if(isLoop(aAST)) {
			loops += 1;
		}
		
		// checks for comments
		if(isComment(aAST)) {
			numComments += 1;
		}
		
		// checks for beg of block comment
		if(aAST.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
			bcls = aAST.getLineNo();
		}
		
		// checks for end of block comment
		if(aAST.getType() == TokenTypes.BLOCK_COMMENT_END) {
			bcle = aAST.getLineNo();
		}
		
		// computes the size of block comment
		if(bcle != -1 && bcls != -1) {
			computeBCCount();
		}
		
	}
	
	
	public void computeBCCount() {
		numLinesComments += (bcle - bcls);
		bcle = -1;
		bcls = -1;
	}
	
	public boolean isComment(DetailAST ast) {
		return ast.getParent().getType() != TokenTypes.BLOCK_COMMENT_BEGIN && 
				(ast.getType() == TokenTypes.SINGLE_LINE_COMMENT ||
				ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN);
	}
	public boolean isLoop(DetailAST ast) {
		return ast.getType() == TokenTypes.LITERAL_WHILE || 
				ast.getType() == TokenTypes.LITERAL_FOR || 
				ast.getType() == TokenTypes.DO_WHILE;
	}
	
	public boolean isValidIdent(DetailAST ast) {
		if( checkIdent(ast) || checkIdentVar(ast) ) {
			return true;
		}
		return false;
	}
	
	public boolean checkIdent(DetailAST ast) {
		
		// idk if class def should be included or not
		// assuming imports ok
		return ast.getType() == TokenTypes.IDENT && ast.getType() != TokenTypes.CLASS_DEF && (
				ast.getParent().getType() == TokenTypes.DOT || 
				ast.getParent().getType() == TokenTypes.VARIABLE_DEF ||
				ast.getParent().getType() == TokenTypes.METHOD_DEF);
	}
	
	public boolean checkIdentVar(DetailAST ast) {
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
	
	public void addUniqueOps(DetailAST ast) {
		int key = convertUniqueOp(ast);
		// makes sure key is non negative and uniqOps does not contain key
		if(key != -1 && !uniqOps.containsKey(key)) {
			uniqOps.put(key, 1);
		}
	} 
	
	public int convertUniqueOp(DetailAST ast) {
		// can use check operator then just return ast.getType()
		if(checkOperator(ast)) {
			return ast.getType();
		}
		
		return -1;
		
	}

	@Override
	public boolean isCommentNodesRequired() {
		return true;
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		operators = 0;
		operands = 0;
		loops = 0;
		uniqOps = new HashMap<Integer, Integer>();
		uniqOperands =  new HashMap<String, Integer>();
		expressions = 0;
		numComments = 0;
		numLinesComments = 0;
		bcls = -1;
		bcle = -1;
		hLength = 0;
		hVocab = 0;
		hVolume = 0;
		hDiff = 0;
		hEffort = 0;
	}
	

}
