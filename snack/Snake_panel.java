package snack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake_panel extends JPanel implements KeyListener, ActionListener {//extend the Jpanel
	//load the pictures
	ImageIcon title = new ImageIcon("title.jpg");
	ImageIcon body = new ImageIcon("body.png");
	ImageIcon head_right = new ImageIcon("right.png");
	ImageIcon head_up = new ImageIcon("up.png");
	ImageIcon head_down = new ImageIcon("down.png");
	ImageIcon head_left = new ImageIcon("left.png");
	ImageIcon food = new ImageIcon("food.png");
	//initial size of snake
	int len=3;
	int []snake_x=new int [750];
	int []snake_y=new int [750];
	//initial head direction of snake
	String direction_head ="R";//L:LEFT R:RIGHT U:UP D:DOWN
	//game isn't start at the beginning
	boolean is_started=false;
	//used to repaint the windows every 100ms
	Timer timer = new Timer(100,this);
	//initial food location
	int food_x;
	int food_y;
	//initial score
	int score=0;
	Random rand =new Random();
	
	public Snake_panel() {//Constructor (because of the same name), which is executed when this class is created
		initsnake();
		this.setFocusable(true);
		this.addKeyListener(this);//allow keyboard command
		timer.start();//start game
	}
	public void paintComponent(Graphics g) {//create a paintbrush
		super.paintComponent(g);//g get ready
		this.setBackground(Color.white);//background color
		title.paintIcon(this, g, 25, 11);//paint the title
		g.fillRect(25,75,850,600);//game space
		if(direction_head=="R")//paint the snake head base on its direction
		{
			head_right.paintIcon(this,g,snake_x[0],snake_y[0]);
		}
		else if(direction_head=="L") {
			head_left.paintIcon(this,g,snake_x[0],snake_y[0]);
		}
		else if(direction_head=="U") {
			head_up.paintIcon(this,g,snake_x[0],snake_y[0]);
		}
		else if(direction_head=="D") {
			head_down.paintIcon(this,g,snake_x[0],snake_y[0]);
		}
		//set paintbrush color
		g.setColor(Color.white);
		g.drawString("score:"+score,28,20);//paint score
		if (is_started==false) {
			g.setColor(Color.white);
			g.setFont(new Font("Time new Roma",Font.BOLD,50));//paint stop mentions
			g.drawString("Press space to start",300,300);
		}
		
		for(int i=1;i<len;i++) {
			body.paintIcon(this, g, snake_x[i], snake_y[i]);//paint snake body
		}
		food.paintIcon(this,g,food_x,food_y);
			
	}
	public void initsnake() {//snake and food initializer
		len=3;
		snake_x[0]=100;
		snake_y[0]=100;
		snake_x[1]=75;
		snake_y[1]=100;
		snake_x[2]=50;
		snake_y[2]=100;
		food_x=25+25*rand.nextInt(34);
		food_y=75+25*rand.nextInt(24);
		score=0;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int Keycode=e.getKeyCode();//get keyboard command
		if (Keycode==KeyEvent.VK_SPACE) {
			is_started=!is_started;//stop game
			repaint();
		}
		else if(Keycode==KeyEvent.VK_LEFT) {//change head direction
			direction_head="L";
		}
		else if(Keycode==KeyEvent.VK_RIGHT) {
			direction_head="R";
		}
		else if(Keycode==KeyEvent.VK_UP) {
			direction_head="U";
		}
		else if(Keycode==KeyEvent.VK_DOWN) {
			direction_head="D";
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (is_started) {//game start
			for(int i=len-1;i>0;i--)//snake body move
			{
				snake_x[i]=snake_x[i-1];
				snake_y[i]=snake_y[i-1];
			}
			//head direction decide the movement direction
			if(direction_head=="L") {
				snake_x[0]=snake_x[0]-25;
				if(snake_x[0]<25)
				{
					snake_x[0]=850;
					
				}
				
			}
			else if(direction_head=="R") {
				snake_x[0]=snake_x[0]+25;
				if(snake_x[0]>850)
				{
					snake_x[0]=25;
					
				}
				
			}
			else if(direction_head=="U") {
				snake_y[0]=snake_y[0]-25;
				if(snake_y[0]<75)
				{
					snake_y[0]=650;
					
				}
				
			}
			else if(direction_head=="D") {
				snake_y[0]=snake_y[0]+25;
				if(snake_y[0]>670)
				{
					snake_y[0]=75;
					
				}
				
			}
			//eat food: len++ score++
			if(snake_x[0]==food_x&&snake_y[0]==food_y) {
				len++;
				score++;
				food_x=25+25*rand.nextInt(34);
				food_y=75+25*rand.nextInt(24);
			}
			//snake will die when touch itself
			for(int j=1;j<len;j++) {
				if(snake_x[0]==snake_x[j]&&snake_y[0]==snake_y[j]){
					initsnake();
				}
			}
			
			repaint();//repaint the windows to update the changes
			
		}
		timer.start();
		
	}
}
