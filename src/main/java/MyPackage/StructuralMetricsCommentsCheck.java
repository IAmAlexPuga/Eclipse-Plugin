package MyPackage;
import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsCommentsCheck extends AbstractCheck {

	public int numComments = 0;
	public int numLinesComments = 0;
	public int bcls = -1;
	public int bcle = -1;
	 
	@Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return getAcceptableTokens();
	 }
	 

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		// TokenTypes.COMMENT_CONTENT,
		return new int[] {TokenTypes.SINGLE_LINE_COMMENT, TokenTypes.BLOCK_COMMENT_BEGIN, TokenTypes.BLOCK_COMMENT_END};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		log(rootAST.getLineNo(), "Number of Comments: " + numComments);
		log(rootAST.getLineNo(), "Number of Lines Of Comments: " + (numLinesComments + numComments));
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
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

	@Override
	public boolean isCommentNodesRequired() {
		return true;
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		numComments = 0;
		numLinesComments = 0;
		bcls = -1;
		bcle = -1;
	}

}
