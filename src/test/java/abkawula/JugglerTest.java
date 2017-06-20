package abkawula;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class JugglerTest {

	@Test
	public void constructorTest() {
		String line = "J J0 H:7 E:6 P:0 C453,C1706,C318,C271,C1958,C1051,C241,C1736,C304,C518";
		
		Map<String, Circuit> m = ImmutableMap.<String, Circuit>builder()
				.put("C453", new Circuit(""))
				.put("C1706", new Circuit(""))
				.put("C318", new Circuit(""))
				.put("C271",new Circuit(""))
				.put("C1958", new Circuit(""))
				.put("C1051", new Circuit(""))
				.put("C241", new Circuit(""))
				.put("C1736", new Circuit(""))
				.put("C304", new Circuit(""))
				.put("C518", new Circuit("")).build();
		
		Juggler j = new Juggler(line, m);
	}

}
