package sample;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import MDP.Action;
import MDP.MDP;
import MDP.State;

/**
 * Static functions to sample from an MDP object.
 * 
 * @author Enrique Areyan Viqueira
 */
public class SampleMDP {

	/**
	 * Given an MDP object and a length, produces a trajectory sampled
	 * from the MDP of the desired length.
	 * 
	 * @param mdp
	 * @param length
	 */
	public static void sampleTrajectory(MDP mdp, int length) {
		// Pick an initial state at random.
		State state = (State) SampleMDP.randomElement(mdp.getStates());
		for(int i = 0; i <length; i++) {
			State currentState = state;
			// Pick an action at random.
			Action action = (Action) SampleMDP.randomElement(mdp.getActions());
			// Pick the next state according to the transition distribution.
			state = SampleMDP.sampleState(mdp.getTransitionTable().get(currentState, action));
			System.out.print(currentState + ", " + action + ", " + mdp.getReward(currentState, state, action) + ", ");
		}
		System.out.print(state);
	}
	
	/**
	 * Given a map from states to probability distributions, pick a state
	 * 
	 * @param probDistribution
	 * @return a state chosen at random according to the given probability distribution.
	 */
	private static State sampleState(Map<State, Double> probDistribution) {
		//System.out.println(probDistribution);
		double startProb = 0.0;
		double uniform = Math.random();
		//System.out.println(uniform);
		for (Entry<State, Double> x : probDistribution.entrySet()) {
			if (uniform >= startProb && uniform <= startProb + x.getValue()) {
				//System.out.println("Pick " + x.getKey());
				return x.getKey();
			}
			startProb += x.getValue();
		}
		return null;
	}
	
	/**
	 * Returns a random element from the given set.
	 * @param <T>
	 * 
	 * @return a random element from the given set.
	 */
	private static <T> Object randomElement(Set<T> set) {
		int numberElem = set.size();
		int item = new Random().nextInt(numberElem); 
		int i = 0;
		Object choosenElem = null;
		for(Object elements : set) {
		    if (i == item) {
		    	choosenElem = elements;
		        break;
		    }
		    i++;
		}
		return choosenElem;
	}

}
