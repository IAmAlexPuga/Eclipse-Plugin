package MyPackage;
import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsCommentsCheck extends AbstractCheck {

	private int numComments = 0;
	private int numLinesComments = 0;
	private int bcls = -1;
	private int bcle = -1;
	
	public int get_bcle() {
		return this.bcle;
	}
	
	public void set_bcle(int line) {
		this.bcle = line;
	}
	
	public void reset_bcle() {
		this.bcle = -1;
	}
	
	public int get_bcls() {
		return this.bcls;
	}
	
	public void set_bcls(int line) {
		this.bcls = line;
	}
	
	public void reset_bcls() {
		this.bcls = -1;
	}
	
	public int getNumComments() {
		return this.numComments;
	}
	
	public void addNumComments() {
		this.numComments += 1;
	}
	
	public void resetNumComments() {
		this.numComments = 0;
	}
	
	public int getNumLinesComments() {
		return this.numLinesComments;
	}
	
	public void addNumLinesComments(int amount) {
		this.numLinesComments += amount;
	}
	
	public void resetNumLinesComments() {
		this.numLinesComments = 0;
	}
	 
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
		log(rootAST.getLineNo(), "Number of Comments: " + this.getNumComments());
		log(rootAST.getLineNo(), "Number of Lines Of Comments: " + (this.getNumLinesComments() + this.getNumComments()));
	}

	@Override
	public void visitToken(DetailAST aAST) {
		
		// checks for comments
		if(isComment(aAST)) {
			this.addNumComments();
		}
		
		// checks for beg of block comment
		if(aAST.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
			this.set_bcls(aAST.getLineNo());
		}
		
		// checks for end of block comment
		if(aAST.getType() == TokenTypes.BLOCK_COMMENT_END) {
			this.set_bcle(aAST.getLineNo());
		}
		
		// computes the size of block comment
		if(this.get_bcle() != -1 && this.get_bcls() != -1) {
			computeBCCount();
		}
		
	}
	
	
	public void computeBCCount() {
		this.addNumLinesComments(bcle - bcls);
		this.reset_bcle();
		this.reset_bcls();
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
		this.reset_bcle();
		this.reset_bcls();
		this.resetNumComments();
		this.resetNumLinesComments();
	}

}
