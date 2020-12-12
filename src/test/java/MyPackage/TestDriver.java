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

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
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

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
		}

		int resultOperands = check.metrics.getOperands();
		int uniqOpSize = check.metrics.getUniqueOperands().size();

		// Verify Results
		assertEquals(16, resultOperands);
		assertEquals(15, uniqOpSize);
		
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

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
		}

		int resultOps = check.metrics.getOps();
		int uniqOpSize = check.metrics.getUniqueOps().size();

		// Verify Results
		assertEquals(3, resultOps);
		assertEquals(1, uniqOpSize);
		
		System.out.println("Operators Check Done!");

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

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
		}

		int resultComments = check.getNumComments();
		int resultLComments = check.getNumLinesComments();

		// Verify Results
		assertEquals(2, resultComments);
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

		for (LocalizedMessage lm : check.getMessages()) {
			System.out.println(lm.getMessage());
		}

		int result = check.getLoopCount();
		

		// Verify Results
		assertEquals(4, result);

		
		System.out.println("Loop Check Done!");

	}
	
	
	public void helper(AbstractCheck b, DetailAST a) {
		while (a != null) {
			a.getType();
			b.visitToken(a);
			helper(b, a.getFirstChild());
			a = a.getNextSibling();
		}
	}

}
