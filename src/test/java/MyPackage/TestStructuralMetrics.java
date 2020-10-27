package MyPackage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestStructuralMetrics {

	StructuralMetricsCheck mockStr = mock(StructuralMetricsCheck.class);
	StructuralMetricsCheck spyStr= spy(new StructuralMetricsCheck());
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		assertTrue(true);
	}

}
