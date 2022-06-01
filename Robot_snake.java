package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Robot_snake {
	int snake_len=3;
	int []snake_x=new int [100];
	int []snake_y=new int [100];
	int WALL =2;
	int FOOD =1;
	int EMPTY =0;
	int food_x;
	int food_y;
	int score;
	Random r = new Random();
	
//snake and food initializer 	
	public void initsnake(int[][] env) {//tested
		snake_len=3;
		snake_x[0]=3;
		snake_y[0]=1;
		snake_x[1]=2;
		snake_y[1]=1;
		snake_x[2]=1;
		snake_y[2]=1;
		env[snake_x[1]][snake_y[1]]=WALL;//body part will be regards as wall
		env[snake_x[2]][snake_y[2]]=WALL;
		score=0;
	}
	public void initfood(int[][] env) { //tested
		while (true) {
		food_x=r.nextInt(1,11);//random generate food on maze
		food_y=r.nextInt(1,11);
		if(env[food_x][food_y]!=2) { // if food location not appear on snake body,break 
			env[food_x][food_y]=FOOD;
			break;}}}
	
	public void game_restart(int[][] env) {// restart game 
		for(int i=0;i<snake_len;i++) {//remove the previous body in the maze
			env[snake_x[i]][snake_y[i]]=0;
		}
		env[food_x][food_y]=0;//remove the previous food in the maze
		System.out.println("snake died, and score is:"+score);
		initsnake(env);
		initfood(env);
		
	}
	//start game
	public Robot_snake(int[][] env) {//tested 
		initsnake(env);
		initfood(env);
	}
	//update body location when body move
	public void body_move(int[][] env) {//tested
		if((snake_x[snake_len-1]==0)&(snake_x[snake_len-1]==0)) System.out.println("body just get longger");
		else env[snake_x[snake_len-1]][snake_y[snake_len-1]]=0;//we need to remove the previous tail in the maze
		for(int i=snake_len-1;i>0;i--)//snake body move
		{
			snake_x[i]=snake_x[i-1];//node 3= node2, node2=node=node1...
			snake_y[i]=snake_y[i-1];
			env[snake_x[i]][snake_y[i]]=WALL;//set node ==wall in the maze
			
		}
		//env[snake_x[0]][snake_y[0]]=WALL;
		
	}
	
	public int sensor(int sense,int[][] env) {//tested
        if (sense==0) return env[snake_x[0]+1][snake_y[0]];//East
        else if (sense==1) return env[snake_x[0]-1][snake_y[0]];//West
        else if (sense==2) return env[snake_x[0]][snake_y[0]-1];//South
        else if (sense==3) return env[snake_x[0]][snake_y[0]+1];//North
        else if (sense==4) return env[snake_x[0]][snake_y[0]];//current
        else return -1;
	}
	public int action(int act,int[][] env) {//tested
        if(act == 0) //move to East
        	if(sensor(0,env)==WALL) 
        		{game_restart(env);return 0;}
            else 
            {snake_x[0]=snake_x[0]+1;body_move(env);return 1;}
        else if(act == 1)//move to West
        	if(sensor(1,env)==WALL) 
        		{game_restart(env);return 0;}
            else 
            {snake_x[0]=snake_x[0]-1;body_move(env);return 1;}
        else if(act == 2)//move to South
        	if(sensor(2,env)==WALL) 
        		{game_restart(env);return 0;}
            else 
            {snake_y[0]=snake_y[0]-1;body_move(env);return 1;}
        else if(act == 3)//move to North
        	if(sensor(3,env)==WALL) 
        		{game_restart(env);return 0;}
            else 
            {snake_y[0]=snake_y[0]+1;body_move(env);return 1;}
        else if(act==4)//eat current food
        	if(sensor(4,env)!=FOOD) 
        		return 0;
        	else 
        		{snake_len++;score++;env[food_x][food_y]=0;initfood(env);System.out.print("*********************************************************"); return 1;}
        else
        	return -1;
            
	}
	
	public int award(int act, int[][] env) {//no need test
        if(act==0)
            if (action(act,env)==0) return -100;//died
            else return 5;//move Easy successfully
        if(act==1)
            if (action(act,env)==0) return -100;
            else return 5;//move West successfully
        if(act==2)
            if (action(act,env)==0) return -100;
            else return 5;//move South successfully
        if(act==3)
            if (action(act,env)==0) return -100;
            else return 5;//move North successfully
        if(act==4)
        	if (action(act,env)==0) return -5;//eat at empty grid
            else return 100;//eat successfully
        return 0;
       
	}
	//to adjust if a arraylist all 0, if so return true
	public boolean helper_array_all_0(ArrayList<Double> list) {
		int count=0;
		for(int i=0;i<list.size();i++)
			
			if(list.get(i)==0) count++;
		if(count==list.size()) return true;
		else return false;
	}
	//choose the next act according to max value in Qtable. if all 0, random one. 
	public int choose_action(ArrayList<Integer> state,HashMap <ArrayList<Integer>,ArrayList<Double>> Qtable ,double epsilon) {
		int temp = r.nextInt(100);
		int choice;
		if (temp<epsilon*100 || helper_array_all_0(Qtable.get(state))) 
			{choice = r.nextInt(5);
			if(choice==-1) System.out.print("xiiiiiiiii");}
		else {
			double max=Collections.max(Qtable.get(state));
			choice=Qtable.get(state).indexOf(max);
			if(choice==-1) System.out.print("eneneneneneenen");
		}
		return choice;
	}
	//return a state format
	public ArrayList<Integer> state(int[][] env) {
		ArrayList<Integer> state= new ArrayList();
		state.add(sensor(0,env));
		state.add(sensor(1,env));
		state.add(sensor(2,env));
		state.add(sensor(3,env));
		state.add(sensor(4,env));
		return state;
	}
	
		
}