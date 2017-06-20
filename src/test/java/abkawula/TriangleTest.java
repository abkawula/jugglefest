package abkawula;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TriangleTest {

	
	// 5
	// 9 6
	// 4 6 8
	// 0 7 1 5
	List<List<Integer>> input;
	
	@Before
	public void setup() {
		input = new ArrayList<>();
		List<Integer> row0 = new ArrayList<>();
		List<Integer> row1 = new ArrayList<>();
		List<Integer> row2 = new ArrayList<>();
		List<Integer> row3 = new ArrayList<>();
		input.add(row0);
		input.add(row1);
		input.add(row2);
		input.add(row3);
		row0.add(5);
		row1.add(9);
		row1.add(6);
		row2.add(4);
		row2.add(6);
		row2.add(8);
		row3.add(0);
		row3.add(7);
		row3.add(1);
		row3.add(5);
	}
	
	@Test
	public void test() {
		Triangle t = new Triangle(input);
		assertEquals(27, t.getMaxSum());
	}

}
