package abkawula;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Jugglefest {
	private static final int STABILIZATION_MINIMUM = 1000;

	List<Circuit> circuits;
	List<Juggler> jugglers;

	public Jugglefest(List<Circuit> circuits, List<Juggler> jugglers) {
		this.circuits = circuits;
		this.jugglers = jugglers;

		System.out.println("Parsing completed successfully.  Starting assignments");
		putJugglersInCircuits(circuits, jugglers);

	}

	public int getSolution() {
		Circuit c1970 = circuits.stream().filter(c -> "C1970".equals(c.getId())).findFirst().get();
		if (c1970.isFull()) {
			int solution = c1970.getJugglers().stream().map(juggler -> juggler.getId()).map(s -> s.substring(1))
					.map(s -> Integer.parseInt(s)).mapToInt(Integer::intValue).sum();
			return solution;
		}

		throw new RuntimeException("No Solution found");
	}

	public void writeToFile(PrintStream out) {
		for (Circuit c : circuits) {
			out.println(c);
		}
	}

	private void putJugglersInCircuits(List<Circuit> circuits2, List<Juggler> jugglers2) {
		LinkedList<Juggler> jugglerQueue = new LinkedList<>(jugglers);
		Juggler j = jugglerQueue.poll();
		while (j != null) {
			Juggler kicked = j.findACircuit(circuits);
			// System.out.println("Kicked: " + kicked);
			if (kicked != null) {
				jugglerQueue.add(kicked);
			}
			if (hasAlgorhythmStabilized(jugglerQueue)) {
				List<Circuit> nonEmptyCircuits = getNonEmptyCircuits(circuits);
				// I think that I've already got my answer
				stuffRemainingJugglersIntoNonEmptyCircuit(jugglerQueue, nonEmptyCircuits);
			}
			j = jugglerQueue.poll();

		}
	}

	private List<Circuit> getNonEmptyCircuits(List<Circuit> circuits) {
		return circuits.stream().filter(f -> !f.isFull()).collect(Collectors.toList());
	}

	private void stuffRemainingJugglersIntoNonEmptyCircuit(LinkedList<Juggler> jugglerQueue,
			List<Circuit> nonEmptyCircuits) {

		Juggler j = jugglerQueue.poll();
		if (j != null) {
			Circuit bestFit = nonEmptyCircuits.stream().max((c1, c2) -> j.dot(c1) - j.dot(c2)).get();
			bestFit.getJugglers().add(j);
		} else {
			return;
		}
		stuffRemainingJugglersIntoNonEmptyCircuit(jugglerQueue, getNonEmptyCircuits(nonEmptyCircuits));

	}

	// At some point, we will wind up with a queue of users who cannot join any
	// of their preferred circuits.
	// We'll know we've hit this point because the number of times they get
	// kicked will go up very quickly.
	// Once the min number of times kicked in the queue is over a certain
	// threshold, we'll declare that this
	// algorithm has stabilized.
	private boolean hasAlgorhythmStabilized(LinkedList<Juggler> jugglerQueue) {
		int min = jugglerQueue.stream().map(j -> j.getNumberOfTimesKicked()).min(Integer::compare).get();
		return min > STABILIZATION_MINIMUM;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			File f = new File("jugglefest.txt");
			in = new BufferedReader(new FileReader(f));

			String line = in.readLine();
			List<Circuit> circuits = new ArrayList<>();
			List<Juggler> jugglers = new ArrayList<>();
			Map<String, Circuit> circuitMap = null;
			while (line != null) {
				if (line.length() == 0) {

				} else if (line.startsWith("C")) {
					circuits.add(new Circuit(line));
				} else if (line.startsWith("J")) {
					if (circuitMap == null) {
						circuitMap = circuits.stream().collect(Collectors.toMap(Circuit::getId, Function.identity()));
					}
					jugglers.add(new Juggler(line, circuitMap));
				}
				line = in.readLine();

			}

			Circuit.setMaxNumberOfJugglers(jugglers.size() / circuits.size());

			Jugglefest fest = new Jugglefest(circuits, jugglers);
			fest.writeToFile(new PrintStream(new FileOutputStream(new File("out.txt"))));
			System.out.println("Solution: " + fest.getSolution());

		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException io) {
				}

			if (out != null) {
				out.flush();
				out.close();
			}

		}

	}

}
