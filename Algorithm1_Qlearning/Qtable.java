package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Qtable {
	static int episodes = 500000;
	static double learning_rate = 0.2;
	static double gamma = 0.9;
	static double epsilon_Greedy = 0.2;
	static HashMap<ArrayList<Integer>,ArrayList<Double>> Qtable=new HashMap <ArrayList<Integer>,ArrayList<Double>>();
	
	public static int[][] env() {
		int env[][]=new int[12][12];
		for (int i=0;i<12;i++) {env[0][i]=2; env[11][i]=2;}
		for (int j=1;j<11;j++)
			for(int k=0;k<12;k++) {
				if (k==0||k==11)
					env[j][k]=2;
			}
		return env;
	}

	public static void main(String[] args) {
		int[][] world= env();
		//ArrayList<Double> zero_list = new ArrayList<Double>() {{add((double) 0);add((double) 0);add((double) 0);add((double) 0);add((double) 0);}};
		Robot_snake robot = new Robot_snake(world);
		for(int time=0; time<episodes;time++) {
				ArrayList<Integer> current_state=robot.state(world);
				boolean contains = Qtable.containsKey(current_state);// if the Qtable contain the current state, if not, add the pair(current_state)- value[0,0,0,0,0]
				if(!contains) {
					ArrayList<Double> zero_list = new ArrayList<Double>() {{add((double) 0);add((double) 0);add((double) 0);add((double) 0);add((double) 0);}};
					Qtable.put(current_state,zero_list);
				}
				int act=robot.choose_action(current_state, Qtable,epsilon_Greedy);//choose the max value of current state in Qtable as act
				int awards=robot.award(act,world);//get awards for current act
				ArrayList<Integer> next_state=robot.state(world);
				contains = Qtable.containsKey(next_state);//if the Qtable contain the next state, if not add to the qtable
				if(!contains) {
					ArrayList<Double> zero_list = new ArrayList<Double>() {{add((double) 0);add((double) 0);add((double) 0);add((double) 0);add((double) 0);}};
					Qtable.put(next_state,zero_list);
				}
					
//***********************Qtable update*****************************************************************
				ArrayList<Double> Qtable_current_state_actions=Qtable.get(current_state);
				double Qtable_current_action=Qtable_current_state_actions.get(act);
				ArrayList<Double> Qtable_next_state_actions=Qtable.get(next_state);
				double Qtable_next_action=Collections.max(Qtable_next_state_actions);
				double Qtable_current_action_updated=Qtable_current_action+learning_rate*(awards+gamma*Qtable_next_action-Qtable_current_action);
				Qtable_current_state_actions.set(act,Qtable_current_action_updated);
				Qtable.put(current_state,Qtable_current_state_actions);
//*******************************************************************************************************	
			}
		for (ArrayList<Integer> i : Qtable.keySet()) //print qtable
      			System.out.println("key: " + i + " value: " + Qtable.get(i));
		for (int i=0;i<12;i++) {	//print maze
			System.out.print("\n");
			for(int j=0;j<12;j++)
				System.out.print(world[i][j]);}
		System.out.println("\nthe highest score is"+robot.history_score);// print highest history score
	
	}
}
