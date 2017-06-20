package abkawula;

public class Evaluate {

	public static final int dotProduct(Juggler j, Circuit c) {
		return j.getEndurance() * c.getEndurance() 
				+ j.getHandEyeCoordination() * c.getHandEyeCoordination()
				+ j.getPizzazz() * c.getPizzazz();
	}
}
