import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyMouseAdapter extends MouseAdapter {

	public void paintNumbers(MyPanel myPanel, JFrame myFrame, int x, int y, int mines){
		JLabel label1= new JLabel();
		//sets label to square touched

		label1.setBounds(51+30*x, 41+30*y, 29, 29);

		switch (mines){
		case 0:
			break;
		case 1:
			label1.setForeground(Color.BLUE);
			break;
		case 2:
			label1.setForeground(Color.GREEN);
			break;
		case 3:
			label1.setForeground(Color.ORANGE);
			break;
		default:
			label1.setForeground(Color.RED);
		}

		label1.setText("  "+mines);
		myPanel.add(label1);
		myPanel.repaint();
		myFrame.add(myPanel);
		myFrame.repaint();
		System.out.println(mines);
	}

	public void uncoverSquares(MyPanel myPanel, JFrame myFrame, int x, int y) {
		if(x >= 0 && x <= 8 && y >= 0 && y <= 8){

			if(!(myPanel.colorArray[x][y].equals(Color.RED)) && !(myPanel.blockMines[x][y].equals(Color.BLACK)) && !(myPanel.colorArray[x][y].equals(Color.WHITE))) {
				if(minesNear(myPanel, x, y) == 0){
					myPanel.colorArray[x][y] = Color.WHITE;
					uncoverSquares(myPanel, myFrame, x - 1, y);
					uncoverSquares(myPanel, myFrame, x + 1, y);
					uncoverSquares(myPanel, myFrame, x, y - 1);
					uncoverSquares(myPanel, myFrame, x, y + 1);
					uncoverSquares(myPanel, myFrame, x - 1, y + 1);
					uncoverSquares(myPanel, myFrame, x + 1, y + 1);
					uncoverSquares(myPanel, myFrame, x - 1, y - 1);
					uncoverSquares(myPanel, myFrame, x + 1, y - 1);
				}
				
				else {
					myPanel.colorArray[x][y] = Color.WHITE;
					paintNumbers(myPanel, myFrame, x, y, minesNear(myPanel, x, y));
				}
			}
		}
	}

	public int minesNear(MyPanel myPanel, int x, int y){
		int mines=0;			
		if(x!=0 && myPanel.blockMines[x - 1][y].equals(Color.BLACK)){
			mines += 1;
		}
		if(x!=8 && myPanel.blockMines[x + 1][y].equals(Color.BLACK)){
			mines += 1;
		}
		if(y!=0 && myPanel.blockMines[x][y - 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(y!=8 && myPanel.blockMines[x][y + 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(x!=0 && y!=8 && myPanel.blockMines[x - 1][y + 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(x!=8 && y!=8 && myPanel.blockMines[x + 1][y + 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(x!=0 && y!=0 && myPanel.blockMines[x - 1][y - 1].equals(Color.BLACK)){
			mines += 1;
		}
		if(x!=8 && y!=0 && myPanel.blockMines[x + 1][y - 1].equals(Color.BLACK)){
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
								uncoverSquares(myPanel, myFrame, myPanel.mouseDownGridX, myPanel.mouseDownGridY);
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