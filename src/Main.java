import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Main implements WindowListener, ActionListener{	
	
	public static JButton restart = new JButton("Restart");
	public static JFrame myFrame;
	
		
	
	public static void main(String[] args) {
		myFrame = new JFrame("Color Grid");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);

		MyPanel myPanel = new MyPanel();
		myFrame.add(myPanel);

		MyMouseAdapter myMouseAdapter = new MyMouseAdapter();
		myFrame.addMouseListener(myMouseAdapter);

		restart.setBackground(Color.WHITE);
		restart.setOpaque(true);		
		restart.setBounds(180, 200, 40, 20);
		
		

		restart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				myFrame.dispose();
				main(new String[]{});
			}
		});

		myFrame.setVisible(true);

}
}
