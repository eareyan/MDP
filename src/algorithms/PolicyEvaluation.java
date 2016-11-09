package algorithms;

import java.util.HashMap;
import java.util.Map;

import MDP.Action;
import MDP.MDP;
import MDP.State;

public class PolicyEvaluation {
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
	 * This map stores the policy to be evaluated.
	 */
	private final Map<State, Action> policy;
	
	/**
	 * Tolerance parameter.
	 */
	private static double tolerance = 0.0001;
	
	/**
	 * Max iteration parameter.
	 */
	private static int maxIter = 10000;
	
	public PolicyEvaluation(MDP mdp, double gamma, Map<State, Action> policy) {
		this.mdp = mdp;
		this.gamma = gamma;
		this.policy = policy;
	}
	
	/**
	 * Implements Policy Evaluation in this MDP. Implements singleton.
	 */
	public void run() {
		if(this.V == null) {
			this.V = new HashMap<State, Double>();
			// Initialize the value function to 0.0
			for (State s : this.mdp.getStates()) {
				this.V.put(s, 0.0);
			}
			int i = 0;
			boolean convergence = false;
			// Convergence criteria are: (1) number of iterations and (2) difference of absolute values.
			while (i < maxIter && !convergence) {
				convergence = true;
				// For each state of the MDP.
				for (State state : this.mdp.getStates()) {
					// Compute the sum of expected future rewards assuming we take the action from the policy.
					double sum = 0.0;
					for(State sprime : this.mdp.getStates()) {
						sum += this.mdp.getTransition(state, sprime, this.policy.get(state)) * (this.mdp.getReward(state, sprime, this.policy.get(state)) + this.gamma * this.V.get(sprime));
					}
					// Current Value
					double currentV = this.V.get(state);
					// Update the V value.
					this.V.put(state, sum);
					// If the difference between V values is greater than tolerance, then we have not converged.
					if(convergence && (Math.abs(currentV - this.V.get(state)) > PolicyEvaluation.tolerance)){
						convergence = false;
					}
				}
				i++;
			}
			System.out.println("Number of iters = " + i);
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
