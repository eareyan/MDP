package sample;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.HashBasedTable;

import MDP.Action;
import MDP.MDP;
import MDP.State;
import MDP.exceptions.MDPException;

/**
 * This class implements functionality to sample MDP from distribution RandomMDP
 * as defined in Jiang et al.
 * 
 * @author Enrique Areyan Viqueira
 */
public class RandomMDP {

	public static MDP sample() throws MDPException {
		// Create 10 states.
		HashSet<State> states = new HashSet<State>();
		for (int i = 0; i < 10; i++) {
			states.add(new State("State # " + i));
		}
		// Create 10 actions.
		HashSet<Action> actions = new HashSet<Action>();
		for (int j = 0; j < 2; j++) {
			actions.add(new Action("Action # " + j));
		}
		// Create the transition distribution.
		HashBasedTable<State, Action, Map<State, Double>> transitions = HashBasedTable.create();
		for (State s : states) {
			for (Action a : actions) {
				HashMap<State, Double> t = new HashMap<State, Double>();
				double[] dist = RandomMDP.createDistribution();
				int k = 0;
				for (State sTo : states) {
					t.put(sTo, dist[k]);
					k++;
				}
				transitions.put(s, a, t);
			}
		}
		Random randomGenerator = new Random();
		// Create the rewards.
		HashBasedTable<State, Action, Map<State, Double>> rewards = HashBasedTable.create();
		for (State sFrom : states) {
			for (Action a : actions) {
				HashMap<State, Double> r = new HashMap<State, Double>();
				for (State sTo : states) {
					r.put(sTo, randomGenerator.nextDouble());
				}
				rewards.put(sFrom, a, r);
			}
		}
		return new MDP(states, actions, transitions, rewards);
	}

	/**
	 * Creates a vector of 10 numbers where only 5 are positive
	 * and these 5 add to 1. The position of the 5 numbers is random.
	 * 
	 * @return
	 */
	public static double[] createDistribution() {
		Random randomGenerator = new Random();
		double[] distribution = new double[10];
		double sum = 0.0;
		// Create 5 random numbers between 0 and 1.
		for (int i = 0; i < 5; i++) {
			distribution[i] = randomGenerator.nextDouble();
			sum += distribution[i];
		}
		// Normalize these 5 numbers.
		for (int i = 0; i < 5; i++) {
			distribution[i] /= sum;
		}
		// Create a list of 10 indices, from 0 to 9/
		ArrayList<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			indices.add(i);
		}
		// Randomize the indices.
		Collections.shuffle(indices);
		
		//Create the final distribution.
		double[] finalDistribution = new double[10];
		int k = 0;
		for(Integer i : indices){
			finalDistribution[k] = distribution[i]; 
			k++;
		}
		return finalDistribution;
	}
}
