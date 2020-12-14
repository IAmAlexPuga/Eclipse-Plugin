package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestMetricsSingleton {

	@BeforeEach
	public void init() {
		MetricsSingleton single = MetricsSingleton.getInstance();
		single.resetExpressions();
		single.resetHDiff();
		single.resetHEffort();
		single.resetHLength();
		single.resetHVocab();
		single.resetHVolume();
		single.resetOperands();
		single.resetOps();
		single.resetUniqueOperands();
		single.resetUniqueOps();
	}

	@Test
	public void getInstanceTest() {
		MetricsSingleton single = spy(MetricsSingleton.class);
		
		single = MetricsSingleton.getInstance();
		assertNotNull(single);
		
		single.addExprs();
		single = MetricsSingleton.getInstance();
		assertEquals(1, single.getExprs());
		
	}

	@Test
	public void computeHLengthTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());

		Mockito.doReturn(8).when(single).getOperands();
		Mockito.doReturn(8).when(single).getOps();
		single.computeHLength();

		double ans = 16;

		assertEquals(ans, single.getHLength());
	}

	@Test
	public void computeHVocabTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());

		single.addUniqueOperands("av", 1);
		single.addUniqueOps(1, 3);
		single.addUniqueOperands("ab", 1);
		single.addUniqueOps(2, 1);
		single.computeHVocab();

		assertEquals(4, single.getHVocab());
	}

	@Test
	public void computeHVolumeTest() {

		MetricsSingleton single = spy(MetricsSingleton.getInstance());

		Mockito.doReturn(4).when(single).getHLength();
		Mockito.doReturn(5.0).when(single).getHVocab();

		double ans = 4 * Math.log(5);
		
		single.computeHVolume();

		assertEquals(ans, single.getHVolume());
	}

	@Test
	public void computeHDiffTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addUniqueOps(1, 1);
		single.addUniqueOps(2, 1);
		single.addUniqueOps(3, 1);
		single.addUniqueOps(4, 1);
		single.addUniqueOperands("a", 1);
		single.addUniqueOperands("b", 1);
		Mockito.doReturn(5).when(single).getOps();
		Mockito.doReturn(5).when(single).getOperands();

		double ans = ((.5 * 4) * 5) / 2;
		single.computeHDiff();
		
		assertEquals(ans, single.getHDiff());
	}
	

	@Test
	public void computeHEffortTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		
		Mockito.doReturn(5.0).when(single).getHDiff();
		Mockito.doReturn(5.0).when(single).getHVolume();
		
		single.computeHEffort();
		
		assertEquals(25.0,single.getHEffort());
		
	}
	
	@Test
	public void resetOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOps();
		single.resetOps();
		assertEquals(0,single.getOps());
		
	}
	
	@Test
	public void resetOperandsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOperands();
		single.resetOperands();
		assertEquals(0,single.getOperands());
		
	}
	
	@Test
	public void resetExpressionsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addExprs();
		single.resetExpressions();
		assertEquals(0,single.getExprs());
		
	}
	
	@Test
	public void resetUniqueOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addUniqueOps(1,1);
		single.resetUniqueOps();
		assertEquals(0,single.getUniqueOps().size());
		
	}
	
	@Test
	public void resetHLengthTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHLength(23);
		single.resetHLength();
		assertEquals(0,single.getHLength());
		
	}
		
	@Test
	public void resetHVocabTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVocab(3);
		single.resetHVocab();
		assertEquals(0,single.getHVocab());
		
	}
	
	@Test
	public void resetHVolumeTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVolume(3);
		single.resetHVolume();
		assertEquals(0,single.getHVolume());
		
	}
	
	@Test
	public void resetHDiffTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHDiff(3);
		single.resetHDiff();
		assertEquals(0,single.getHDiff());
		
	}
	
	@Test
	public void resetHEffortTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHEffort(3);
		single.resetHEffort();
		assertEquals(0,single.getHEffort());
		
	}
	
	@Test
	public void addOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOps();
		assertEquals(1,single.getOps());
		
	}
	
	@Test
	public void addOperandsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOperands();
		assertEquals(1,single.getOperands());
		
	}

	@Test
	public void addUniqueOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addUniqueOps(1,3);
		assertEquals(1,single.getUniqueOps().size());
		
	}
	
	@Test
	public void addUniqueOperandsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addUniqueOperands("a",3);
		assertEquals(1,single.getUniqueOperands().size());
		
	}
	
	@Test
	public void setHLengthTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHLength(2);
		assertEquals(2,single.getHLength());
		
	}
	
	@Test
	public void setHVocabTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVocab(2);
		assertEquals(2,single.getHVocab());
		
	}
	
	@Test
	public void setHDiffTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHDiff(2);
		assertEquals(2,single.getHDiff());
		
	}
	
	@Test
	public void setHVolumeTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVolume(2);
		assertEquals(2,single.getHVolume());
		
	}
	
	@Test
	public void setHEffortTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHEffort(2);
		assertEquals(2,single.getHEffort());
		
	}
	
	@Test
	public void getOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOps();
		assertEquals(1,single.getOps());
		
	}
	
	@Test
	public void getOperandsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addOperands();
		assertEquals(1,single.getOperands());
		
	}
	
	@Test
	public void getExprsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.addExprs();
		assertEquals(1,single.getExprs());
		
	}

	@Test
	public void getUniqueOpsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		HashMap<Integer, Integer> ans = new HashMap<Integer, Integer>();
		ans.put(3, 1);
		
		single.addUniqueOps(3,1);
		assertEquals(ans,single.getUniqueOps());
		
	}
	
	@Test
	public void getUniqueOperandsTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		Map<String, Integer> ans = new HashMap<String, Integer>();
		ans.put("test", 1);
		
		single.addUniqueOperands("test",1);
		assertEquals(ans,single.getUniqueOperands());
	
	}
	
	@Test
	public void getHLengthTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHLength(2);
		assertEquals(2,single.getHLength());
	}
	
	@Test
	public void getHVocabTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVocab(2);
		assertEquals(2,single.getHVocab());
	}
	
	@Test
	public void getHVolumeTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHVolume(2);
		assertEquals(2,single.getHVolume());
	}
	
	@Test
	public void getHDiffTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHDiff(2);
		assertEquals(2,single.getHDiff());
	}
	
	@Test
	public void getHEffortTest() {
		MetricsSingleton single = spy(MetricsSingleton.getInstance());
		single.setHEffort(2);
		assertEquals(2,single.getHEffort());
	}

}
