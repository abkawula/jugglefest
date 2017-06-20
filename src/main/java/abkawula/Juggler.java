package abkawula;

import static abkawula.Evaluate.dotProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Juggler {

	String id;
	int handEyeCoordination;
	int endurance;
	int pizzazz;
	List<Circuit> preferedCircuits;
	int numberOfTimesKicked = 0;

	// J J0 H:3 E:9 P:2 C2,C0,C1
	private static final Pattern p = Pattern.compile("J (J\\d+) H:(\\d+) E:(\\d+) P:(\\d+) (.*)");

	public Juggler(String line, Map<String, Circuit> allCircuits) {
		Matcher m = p.matcher(line);
		if (m.matches()) {
			id = m.group(1);
			handEyeCoordination = Integer.parseInt(m.group(2));
			endurance = Integer.parseInt(m.group(3));
			pizzazz = Integer.parseInt(m.group(4));
			preferedCircuits = parsePreferedCircuits(m.group(5).split(","), allCircuits);
		} else {
			throw new RuntimeException("Juggler pattern failed for:\n" + line);
		}
	}
	
	public Juggler findACircuit(List<Circuit> circuits) {
		Juggler j = null;
		for (Circuit c : preferedCircuits) {
			j = c.join(this);
			if (j != this) {
				return j;
			}
		}
			
		return j;
	}
	
	public int dot(Circuit c) {
		return dotProduct(this, c);
	}

	private List<Circuit> parsePreferedCircuits(String[] split, Map<String, Circuit> allCircuits) {
		List<Circuit> circuits = new ArrayList<>();
		for (String id : split) {
			if (allCircuits.containsKey(id)) {
				circuits.add(allCircuits.get(id));
			} else {
				throw new RuntimeException("Juggler pattern failed to parse for circuit id = " + id);
			}
		}

		return circuits;
	}

	public String getId() {
		return id;
	}

	public int getHandEyeCoordination() {
		return handEyeCoordination;
	}

	public int getEndurance() {
		return endurance;
	}

	public int getPizzazz() {
		return pizzazz;
	}

	public int getNumberOfTimesKicked() {
		return numberOfTimesKicked;
	}

	public void incrementNumberOfTimesKicked() {
		this.numberOfTimesKicked++;
	}

	@Override
	public String toString() {
		return "Juggler [id=" + id + ", handEyeCoordination=" + handEyeCoordination + ", endurance=" + endurance
				+ ", pizzazz=" + pizzazz  + ", numberOfTimesKicked="
				+ numberOfTimesKicked + "]";
	}
	
	

}
