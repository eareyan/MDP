package algorithms;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.MDP;
import MDP.State;

/**
 * This class implements Value Iteration in an MDP and a Gamma.
 * 
 * @author Enrique Areyan Viqueira
 */
public class ValueIteration {

	/**
	 * Value iteration runs in an MDP.
	 */
	private final MDP mdp;
	
	/**
	 * There is a value of gamma.
	 */
	private final double gamma;

	/**
	 * This map stores the V function.
	 */
	private Map<State, Double> V;
	
	/**
	 * This map stores the deterministic greedy policy.
	 */
	private Map<State, Action> policy;
	
	/**
	 * Tolerance parameter.
	 */
	private static double tolerance = 0.0001;
	
	/**
	 * Max iteration parameter.
	 */
	private static int maxIter = 10000;

	/**
	 * Constructor. 
	 * 
	 * @param mdp
	 * @param gamma
	 */
	public ValueIteration(MDP mdp, double gamma) {
		this.mdp = mdp;
		this.gamma = gamma;
		this.V = new HashMap<State, Double>();
		// Initialize the value function to 0.0
		for (State s : this.mdp.getStates()) {
			this.V.put(s, 0.0);
		}
	}

	/**
	 * Implements VI in this MDP.
	 */
	public void run() {
		int i = 0;
		boolean convergence = false;
		// Convergence criteria are: (1) number of iterations and (2) difference of absolute values.
		while (i < maxIter && !convergence) {
			convergence = true;
			// For each state of the MDP.
			for (State state : this.mdp.getStates()) {
				// Compute the action (and its value) that is maximal from current state.
				ActionValueTuple actionValueTuple = this.getArgMaxActionFromState(state);
				// Current Value
				double currentV = this.V.get(state);
				// Update the V value.
				this.V.put(state, actionValueTuple.getValue());
				// If the difference between V values is greater than tolerance, then we have not converged.
				if(convergence && (Math.abs(currentV - this.V.get(state)) > ValueIteration.tolerance)){
					convergence = false;
				}
			}
			i++;
		}
		System.out.println("Number of iters = " + i);
	}
	
	/**
	 * Computes the action that has maximal expected value from the current state.
	 * 
	 * @param state
	 * @return the action that has maximal expected value from the current state.
	 */
	private ActionValueTuple getArgMaxActionFromState(State state){
		double maxSum = Double.NEGATIVE_INFINITY;
		ActionValueTuple tuple = null;
		// Compute the value of the action with the highest expected reward.
		for (Action action : this.mdp.getActions()) {
			double sum = 0.0;
			for(State sprime : this.mdp.getStates()) {
				sum += this.mdp.getTransition(state, sprime, action) * (this.mdp.getReward(state, sprime, action) + this.gamma * this.V.get(sprime));
			}
			if(sum > maxSum) {
				maxSum = sum;
				tuple = new ActionValueTuple(action, maxSum);
			}
		}
		return tuple;
	}
	
	/**
	 * Returns the deterministic greedy policy with respect to the value
	 * function already computed.
	 * 
	 * @return a policy, i.e., a map from states to actions.
	 */
	public Map<State, Action> getPolicy() {
		if(this.policy == null) {
			this.policy = new HashMap<State, Action>();
			for (State state : this.mdp.getStates()) {
				this.policy.put(state, this.getArgMaxActionFromState(state).getAction());
			}
		}
		return this.policy;
	}
	
	/**
	 * A helper class. Represents a tuple (action, value).
	 * 
	 * @author Enrique Areyan Viqueira
	 */
	private class ActionValueTuple{
		
		private final Action action;
		
		private final double value;
		
		public ActionValueTuple(Action action, double value) {
			this.action = action;
			this.value = value;
		}
		
		public Action getAction() {
			return this.action;
		}
		
		public double getValue() {
			return this.value;
		}
	}

	@Override
	public String toString() {
		String ret = "\n V function:";
		for (State s : this.mdp.getStates()) {
			ret += "\n\t V(" + s.getId() + ") = " + this.V.get(s);
		}
		return ret;
	}
}
