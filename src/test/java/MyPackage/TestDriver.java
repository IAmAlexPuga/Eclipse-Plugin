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
		check.metrics.resetExpressions();

		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());

		// Initialize Local Variables in Check
		check.beginTree(root);

		// Visit Each Token in Tree
		helper(check, root);

		// Complete tree and display intended logs to user.
		check.finishTree(root);

		// for(LocalizedMessage lm : check.getMessages()) {
		// System.out.println(lm.getMessage());
		// }

		int result = check.metrics.getExprs();

		// Verify Results
		assertEquals(4,result);
		System.out.println("exprCheck Done!");

	}

	public void helper(AbstractCheck b, DetailAST a) {
		while (a != null) {
			b.visitToken(a);
			helper(b, a.getFirstChild());
			a = a.getNextSibling();
		}
	}

}
