package MDP;

/**
 * Represents a state in an MDP.
 * 
 * @author Enrique Areyan Viqueira
 */
public class State {

	/**
	 * Internal representation Id. Could be whatever the implementing client wants.
	 */
	private final String id;

	/**
	 * Constructor.
	 * 
	 * @param id - a string identifying this state.
	 */
	public State(String id) {
		this.id = id;
	}

	/**
	 * Getter.
	 * 
	 * @return this state identification string.
	 */
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.getId();
	}
}
