package Test;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.common.collect.HashBasedTable;

import MDP.Action;
import MDP.MDP;
import MDP.State;
import MDP.exceptions.MDPException;
import algorithms.ValueIteration;
import sample.RandomMDP;

/**
 * Testing class
 * 
 * @author Enrique Areyan Viqueira
 */
public class Test {

	public static void main(String[] args) throws MDPException{
		
		// Create States.
		State s1 = new State("State 1");
		State s2 = new State("State 2");
		Action a1 = new Action("Action 1");
		
		HashSet<State> states = new HashSet<State>();
		states.add(s1);
		states.add(s2);
		
		//Create Actions.
		HashSet<Action> actions = new HashSet<Action>();
		actions.add(a1);
		
		//Create Transition
		/*
		HashBasedTable<State, Action, Map<State, Double>> transition = HashBasedTable.create();
		for(State sFrom : states){
			for(Action a : actions){
				HashMap<State, Double> t = new HashMap<State, Double>();
				for(State sTo : states){
					//t.put(sTo, Math.random());
					t.put(sTo, 0.45);
				}
				transition.put(sFrom, a, t);			
				}
		}*/
		HashBasedTable<State, Action, Map<State, Double>> transition = HashBasedTable.create();
		
		HashMap<State, Double> t = new HashMap<State, Double>();
		t.put(s1, 0.45);
		t.put(s2, 0.55);
		transition.put(s1, a1, t);

		HashMap<State, Double> t2 = new HashMap<State, Double>();
		t2.put(s1, 0.7);
		t2.put(s2, 0.3);
		transition.put(s2, a1, t2);

		//Create Rewards
		/*
		HashBasedTable<State, Action, Map<State, Double>> rewards = HashBasedTable.create();
		for(State sFrom : states){
			for(Action a : actions){
				HashMap<State, Double> r = new HashMap<State, Double>();
				for(State sTo : states){
					//r.put(sTo, Math.random());
					r.put(sTo, 0.35);
				}
				rewards.put(sFrom, a, r);			
				}
		}*/
		/*HashBasedTable<State, Action, Map<State, Double>> rewards = HashBasedTable.create();
		
		HashMap<State, Double> r = new HashMap<State, Double>();
		r.put(s1, 0.35);
		r.put(s2, 0.55);
		rewards.put(s1, a1, r);	

		HashMap<State, Double> r2 = new HashMap<State, Double>();
		r2.put(s1, 0.77);
		r2.put(s2, 0.44);
		rewards.put(s2, a1, r2);	

		MDP testMDP = new MDP(states, actions, transition, rewards);
		System.out.println(testMDP.getReward(s1, s2, a1));
		System.out.println(testMDP.getTransition(s1, s2, a1));
		
		System.out.println("testMDP = " + testMDP);
		
		ValueIteration vi = new ValueIteration(testMDP, 0);
		
		System.out.println(vi);
		vi.run();
		System.out.println(vi);
		System.out.println("----");
		double[] x = RandomMDP.createDistribution();
		for(double y : x){
			System.out.println(y);
		}*/
		MDP randomMDP = RandomMDP.sample();
		System.out.println("----\n" + randomMDP);
		ValueIteration vi = new ValueIteration(randomMDP, 0.99);
		vi.run();
		System.out.println(vi);

		vi = new ValueIteration(randomMDP, 0.75);
		vi.run();
		System.out.println(vi);

	}
}
