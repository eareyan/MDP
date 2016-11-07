package MDP;

/**
 * Represents an action in an MDP.
 * 
 * @author Enrique Areyan Viqueira
 */
public class Action {

	/**
	 * Internal representation Id. Could be whatever the implementing client wants.
	 */
	private final String id;

	/**
	 * Constructor.
	 * 
	 * @param id - a string identifying this action.
	 */
	public Action(String id) {
		this.id = id;
	}

	/**
	 * Getter.
	 * 
	 * @return this action identification string.
	 */
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.getId();
	}

}
