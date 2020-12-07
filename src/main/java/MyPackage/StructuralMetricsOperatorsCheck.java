package MyPackage;
import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsOperatorsCheck extends AbstractCheck {
	
	
	
	public static MetricsSingleton metrics = MetricsSingleton.getInstance();
	
	
	@Override 
	 public int[] getDefaultTokens() { 
		 return getAcceptableTokens();
	 }
	 

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] {TokenTypes.PLUS , TokenTypes.MINUS , TokenTypes.STAR
				, TokenTypes.DIV , TokenTypes.MOD , TokenTypes.PLUS_ASSIGN 
				, TokenTypes.MINUS_ASSIGN , TokenTypes.DIV_ASSIGN , TokenTypes.ASSIGN
				, TokenTypes.STAR_ASSIGN , TokenTypes.MOD_ASSIGN , TokenTypes.INC
				,TokenTypes.POST_INC , TokenTypes.DEC , TokenTypes.POST_DEC , TokenTypes.GE
				, TokenTypes.GT , TokenTypes.SR , TokenTypes.SR_ASSIGN , TokenTypes.LE , TokenTypes.LT
				,TokenTypes.SL , TokenTypes.SL_ASSIGN , TokenTypes.EQUAL , TokenTypes.NOT_EQUAL
				,TokenTypes.BAND , TokenTypes.BAND_ASSIGN , TokenTypes.BNOT , TokenTypes.BOR , TokenTypes.BOR_ASSIGN
				, TokenTypes.BXOR , TokenTypes.BXOR_ASSIGN , TokenTypes.LOR , TokenTypes.LNOT , TokenTypes.QUESTION
				,TokenTypes.COLON};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		log(rootAST.getLineNo(), "Number of operators " + metrics.getOps());
		log(rootAST.getLineNo(), "Number of unique operators " + metrics.getUniqueOps().size());

	}

	@Override
	public void visitToken(DetailAST aAST) { 
				
		// checks if its an operator 
		if(checkOperator(aAST)) { 
			metrics.addOps();
		}
		
		// checks if unique operator to add to uniqOps
		addUniqueOps(aAST);
		
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

	
	public void addUniqueOps(DetailAST ast) {
		int key = convertUniqueOp(ast);
		
		// makes sure key is non negative and uniqOps does not contain key
		if(key != -1 && !metrics.getUniqueOps().containsKey(key)) {
			metrics.addUniqueOps(key, 1);
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
	public void beginTree(DetailAST rootAST) {
		// init the variables
		metrics.resetOps();
		metrics.resetUniqueOps();

	}

}
