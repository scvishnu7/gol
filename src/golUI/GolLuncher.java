package golUI;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GolLuncher extends JFrame{
public static OptSel op;

public static void main(String[] args) {
	op = new OptSel(65,62,10,100);
}
}
class OptSel extends JFrame {
	int x,y,size;
public	OptSel(int x,int y,int size,int delay){
	GolUI gl1 = new GolUI(x,y,size);
	setTitle("Conway's Game Of Life <<"+x+","+y+">>");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	add(gl1);
	setSize(x*size+100,y*size+100);
	//setMaximumSize(new Dimension(x*size,y*size));
	//setMinimumSize(new Dimension(x*size,y*size));
	gl1.setDelay(delay);
	setVisible(true);
}
}