package MyPackage;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.regex.Pattern;
import java.util.*;

public class StructuralMetricsCheck extends AbstractCheck {

	
	public static MetricsSingleton metrics = MetricsSingleton.getInstance();
	 
	@Override 
	 public int[] getDefaultTokens() { // TokenTypes.PLUS,
		 return getAcceptableTokens();
	 }
	 

	@Override
	public int[] getAcceptableTokens() {
		// TODO Auto-generated method stub
		return new int[] {};
	}

	@Override
	public int[] getRequiredTokens() {
		// TODO Auto-generated method stub
		return getAcceptableTokens();
	}

	@Override
	public void finishTree(DetailAST rootAST) {
		metrics.computeHLength();
		metrics.computeHVocab();
		metrics.computeHVolume();
		metrics.computeHDiff();
		metrics.computeHEffort();
		
		// seperate into function calls
		log(rootAST.getLineNo(), "Halstead Length: " + metrics.getHLength() );
		log(rootAST.getLineNo(), "Halstead Vocab: " + metrics.getHVocab());
		log(rootAST.getLineNo(), "Halstead Volume: " + 	String. format("%.2f", metrics.getHVolume()));
		log(rootAST.getLineNo(), "Halstead Difficulty: " + String. format("%.2f", metrics.getHDiff()));
		log(rootAST.getLineNo(), "Halstead Effort: " + String. format("%.2f", metrics.getHEffort()));
	}

	@Override
	public void visitToken(DetailAST aAST) {
		// class used to report the Halstead Metrics. No need to parse data
	}
	
	@Override
	public void beginTree(DetailAST rootAST) {
		// init the variables
		metrics.resetHLength();
		metrics.resetHVocab();
		metrics.resetHVolume();
		metrics.resetHEffort();
		metrics.resetHDiff();
	}
	

}
