package abkawula;

import static abkawula.Evaluate.dotProduct;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Circuit {

	private static int maxNumberOfJugglers;
	
	private String id;
	private int handEyeCoordination;
	private int endurance;
	private int pizzazz;
	private List<Juggler> jugglers = new ArrayList<>();

	// C C0 H:7 E:7 P:10
	private static final Pattern p = Pattern.compile("C (C\\d+) H:(\\d+) E:(\\d+) P:(\\d+)");

	public Circuit(String line) {
		Matcher m = p.matcher(line);
		if (m.matches()) {
			id = m.group(1);
			handEyeCoordination = Integer.parseInt(m.group(2));
			endurance = Integer.parseInt(m.group(3));
			pizzazz = Integer.parseInt(m.group(4));
		} else {
			throw new RuntimeException("Circuit pattern failed for:\n" + line);
		}
	}
	
	public Juggler join(Juggler j) {
		jugglers.add(j);
		
		if (jugglers.size() > maxNumberOfJugglers) {
			// If this circuit is full, kick the lowest score
			Juggler kicked =  jugglers.stream().min((j1, j2) -> dot(j1) - dot(j2)).get();
			jugglers.remove(kicked);
			kicked.incrementNumberOfTimesKicked();
			return kicked;
		}
		
		return null;
		
		
		
	}
	
	public boolean isFull() {
		return jugglers.size() >= maxNumberOfJugglers;
	}

	public int dot(Juggler j) {
		return dotProduct(j, this);
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

	public List<Juggler> getJugglers() {
		return jugglers;
	}

	public void setJugglers(List<Juggler> jugglers) {
		this.jugglers = jugglers;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHandEyeCoordination(int handEyeCoordination) {
		this.handEyeCoordination = handEyeCoordination;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public void setPizzazz(int pizzazz) {
		this.pizzazz = pizzazz;
	}

	public static void setMaxNumberOfJugglers(int maxNumberOfJugglers) {
		Circuit.maxNumberOfJugglers = maxNumberOfJugglers;
	}

	@Override
	public String toString() {
		String s = id;
		for (Juggler j : jugglers) {
			s += " " + j;
		}
		return s;
	}

	
}
