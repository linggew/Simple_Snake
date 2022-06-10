import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Qtable {
	static int episodes = 3000000;
	static double learning_rate = 0.3;
	static double gamma = 0.9;
	static double epsilon_Greedy = 0.3;
	static int []score_plot=new int [301];
	static int point=0;
	static int history_highest_score=0;
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
	
	private static void exportData(int[] rewardPlot, String filename){
	    File file = new File(filename+".txt");
	    file.delete();
	    try {
	        file.createNewFile();
	        PrintWriter out = new PrintWriter(filename+".txt");
	        StringBuilder string = new StringBuilder();
	        for(int i=0;i<rewardPlot.length;i++){
	            string.append(rewardPlot[i]);
	            string.append("\n");
	            out.write(string.toString());
	            string.setLength(0);
	        }
	        out.close();
	    }
	    catch(Exception e) {
	        System.out.println("File output failure");
	        return;
	    }
	}
	
	public static void main(String[] args) {
		int[][] world= env();
		//ArrayList<Double> zero_list = new ArrayList<Double>() {{add((double) 0);add((double) 0);add((double) 0);add((double) 0);add((double) 0);}};
		Robot_snake robot = new Robot_snake(world);
		for(int time=0; time<episodes+1;time++) {
			robot.live=true;
			while(robot.live) {
				ArrayList<Integer> current_state=robot.state(world);
				boolean contains = Qtable.containsKey(current_state);
				if(!contains) {
					ArrayList<Double> zero_list = new ArrayList<Double>() {{add((double) 0);add((double) 0);add((double) 0);add((double) 0);add((double) 0);}};
					Qtable.put(current_state,zero_list);
				}
				int act=robot.choose_action(current_state, Qtable,epsilon_Greedy);
				int awards=robot.award(act,world);
				ArrayList<Integer> next_state=robot.state(world);
				contains = Qtable.containsKey(next_state);
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
			if(time%10000==0) {
				if(epsilon_Greedy>0) epsilon_Greedy=epsilon_Greedy-0.001;
				score_plot[point]=robot.history_score;
				point=point+1;
				if(robot.history_score>history_highest_score) history_highest_score=robot.history_score;
				robot.history_score=0;
			}
		}
		exportData(score_plot,"Plot1");
//		for (ArrayList<Integer> i : Qtable.keySet()) 
//      System.out.println("key: " + i + " value: " + Qtable.get(i));

		System.out.println("\nthe highest score is"+history_highest_score);
//	
	}
//	
}
