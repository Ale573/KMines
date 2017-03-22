import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyMouseAdapter extends MouseAdapter {
	
	private Random generator = new Random();	

	public int minesNear(MyPanel myPanel){
		int mines=0;
		if(myPanel.blockMines[myPanel.mouseDownGridX - 1][myPanel.mouseDownGridY - 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX - 1][myPanel.mouseDownGridY].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX - 1][myPanel.mouseDownGridY + 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX][myPanel.mouseDownGridY - 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX][myPanel.mouseDownGridY + 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX + 1][myPanel.mouseDownGridY - 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX + 1][myPanel.mouseDownGridY].equals(Color.BLACK)){
			mines += 1;
		}
		if(myPanel.blockMines[myPanel.mouseDownGridX + 1][myPanel.mouseDownGridY + 1].equals(Color.BLACK)){
			mines += 1;
		}
		System.out.println(mines);
		return mines;
	}
	
	public void mousePressed(MouseEvent e) {

		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();	
			break;
		case 3:		//Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame) c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		Color newColor = null;
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						//On the grid other than on the left column and on the top row:
						if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.GRAY)){
							if(myPanel.blockMines[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.BLACK)){
								
								newColor = Color.BLACK; 
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							}
							else {
								int mines = minesNear(myPanel);
								newColor = Color.WHITE;
										JLabel label1= new JLabel();
										label1.setBounds(51+30*gridX, 41+30*gridY, 29, 29);
										switch (mines){
										case 0:
											//NOTHING
											break;
										case 1:
											label1.setText("  "+mines);
											label1.setForeground(Color.BLUE);
											break;
										case 2:
											label1.setText("  "+mines);
											label1.setForeground(Color.GREEN);
											break;
										case 3:
											label1.setText("  "+mines);
											label1.setForeground(Color.ORANGE);
											break;
										default:
											label1.setText("  "+mines);
											label1.setForeground(Color.RED);

										}
										myPanel.add(label1);
										myPanel.repaint();
										myFrame.add(myPanel);
										myFrame.repaint();
										System.out.println(mines);
										myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			myFrame = (JFrame)c;
			myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			myInsets = myFrame.getInsets();
			x1 = myInsets.left;
			y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			x = e.getX();
			y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			gridX = myPanel.getGridX(x, y);
			gridY = myPanel.getGridY(x, y);
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed
						//On the grid other than on the left column and on the top row:
						if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.GRAY)){

							newColor = Color.RED;
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();
						}
						else if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY].equals(Color.RED)){
							newColor = Color.GRAY;
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = newColor;
							myPanel.repaint();
						}
					}
				}
			}
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}