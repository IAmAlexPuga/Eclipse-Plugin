package MyPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

public class TestDriver {

	String filePath = "C:\\Users\\pugap\\Desktop\\Eclipse-Plugin\\src\\test\\java\\MyPackage\\";

	@Test
	public void BlackBoxExprTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxExprTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);

		// Initialize Intended Check
		exprCheck check = new exprCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		String msgs[] = { "Expressions: 9" };
		int count = 0;

		// checks the size of log outputs
		assertEquals(1, check.getMessages().size());
		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}

		int result = check.metrics.getExprs();

		// Verify Results
		assertEquals(9, result);
		System.out.println("exprCheck Done!");

	}

	@Test
	public void BlackBoxOperandsTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxOperandsTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);

		// Initialize Intended Check
		StructuralMetricsOperandsCheck check = new StructuralMetricsOperandsCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		String msgs[] = { "Number of operands: 19", "Number of unique operands: 16" };
		int count = 0;
		// checks the size of log outputs
		assertEquals(2, check.getMessages().size());
		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}

		int resultOperands = check.metrics.getOperands();
		int uniqOpSize = check.metrics.getUniqueOperands().size();

		// Verify Results
		assertEquals(19, resultOperands);
		assertEquals(16, uniqOpSize);

		System.out.println("Operands Check Done!");

	}

	@Test
	public void BlackBoxOperatorsTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxOperatorsTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);

		// Initialize Intended Check
		StructuralMetricsOperatorsCheck check = new StructuralMetricsOperatorsCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		String msgs[] = { "Number of operators 3", "Number of unique operators 2" };
		int count = 0;

		// checks the size of log outputs
		assertEquals(2, check.getMessages().size());
		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}

		int resultOps = check.metrics.getOps();
		int uniqOpSize = check.metrics.getUniqueOps().size();

		// Verify Results
		assertEquals(3, resultOps);
		assertEquals(2, uniqOpSize);

		System.out.println("Operators Check Done!");

	}

	@Test
	public void BlackBoxMetricsTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxOperatorsTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		File f2 = new File(filePath + "BlackBoxOperandsTest.java");
		FileText ftOprand = new FileText(f2, "UTF-8");
		FileContents fcOprand = new FileContents(ftOprand);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);

		DetailAST rootOprand = JavaParser.parse(fcOprand);

		// Initialize Intended Check
		StructuralMetricsOperatorsCheck check = new StructuralMetricsOperatorsCheck();
		StructuralMetricsOperandsCheck checkOprand = new StructuralMetricsOperandsCheck();
		StructuralMetricsCheck metrics = new StructuralMetricsCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());
		checkOprand.configure(new DefaultConfiguration("Local"));
		checkOprand.contextualize(new DefaultContext());
		metrics.configure(new DefaultConfiguration("Local"));
		metrics.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);
		checkOprand.beginTree(rootOprand);
		metrics.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);
		helper(checkOprand, rootOprand);

		// Complete tree and display intended logs to user.
		check.finishTree(root);
		checkOprand.finishTree(root);
		metrics.finishTree(root);

		// default values from prev Black box test
		int ops = 3;
		int uniqOps = 2;
		int oprands = 19;
		int uniqOprands = 16;

		int rHLength = ops + oprands;
		double rHVoc = uniqOprands + uniqOps;
		double rHVol = rHLength * Math.log(rHVoc);
		double rHDiff = ((.5 * uniqOps) * oprands) / uniqOprands;
		double rhEff = rHDiff * rHVol;

		String msgs[] = { "Halstead Difficulty: " + String.format("%.2f", rHDiff),
				"Halstead Effort: " + String.format("%.2f", rhEff), "Halstead Length: " + rHLength,
				"Halstead Vocab: " + rHVoc, "Halstead Volume: " + String.format("%.2f", rHVol) };

		int count = 0;

		
		for (LocalizedMessage lm : metrics.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}
		
		// checks the size of log outputs
		assertEquals(5, metrics.getMessages().size());

		int hLength = metrics.metrics.getHLength();
		double hDiff = metrics.metrics.getHDiff();
		double hEff = metrics.metrics.getHEffort();
		double hVol = metrics.metrics.getHVolume();
		double hVoc = metrics.metrics.getHVocab();

		// Verify Results
		assertEquals(rHLength, hLength);
		assertEquals(rHDiff, hDiff);
		assertEquals(rhEff, hEff);
		assertEquals(rHVol, hVol);
		assertEquals(rHVoc, hVoc);

		System.out.println("Halstead Metrics Check Done!");

	}

	@Test
	public void BlackBoxCommentsTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxCommentsTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);
		JavaParser.appendHiddenCommentNodes(root);

		// Initialize Intended Check
		StructuralMetricsCommentsCheck check = new StructuralMetricsCommentsCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		String msgs[] = { "Number of Comments: 2", "Number of Lines Of Comments: 10" };
		int count = 0;

		// checks the size of log outputs
		assertEquals(2, check.getMessages().size());
		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}

		int resultComments = check.getNumComments();
		int resultLComments = check.getNumLinesComments();

		// Verify Results
		assertEquals(2, resultComments);
		// spcf only linecomments not total
		assertEquals(8, resultLComments);

		System.out.println("Comments Check Done!");

	}

	@Test
	public void BlackBoxLoopsTest() throws IOException, CheckstyleException {

		// Using code given for test driver
		File file = new File(filePath + "BlackBoxLoopsTest.java");
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);

		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);

		// Initialize Intended Check
		StructuralMetricsLoopsCheck check = new StructuralMetricsLoopsCheck();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		String msgs[] = { "Number Looping statements: 4" };
		int count = 0;

		// checks the size of log outputs
		assertEquals(1, check.getMessages().size());

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
			assertEquals(msgs[count], lm.getMessage());
			count++;
		}

		int result = check.getLoopCount();

		// Verify Results
		assertEquals(4, result);

		System.out.println("Loop Check Done!");

	}

	public void helper(AbstractCheck b, DetailAST a) {
		while (a != null) {
			a.getType();
			if (a.getParent() != null) {
				b.visitToken(a);
			}

			helper(b, a.getFirstChild());
			a = a.getNextSibling();
		}
	}

}
