package snack;

import javax.swing.JFrame;

public class Msnake { 

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(10,10,900,720); //set the size of window
		frame.setResizable(false);//could not resize the window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//could close the window
		frame.add(new Snake_panel());//set panel
		frame.setVisible(true);//enable visible
	}

}
