package golUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ClickListener implements ActionListener,ChangeListener,ItemListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getActionCommand().compareTo("Intro ...")==0){
		JOptionPane.showMessageDialog(null, "At each step in time, the following transitions occur:\n"+
    "1> Any live cell with fewer than two live neighbours dies, as if caused by under-population.\n"+
    "2> Any live cell with two or three live neighbours lives on to the next generation.\n"+
    "3> Any live cell with more than three live neighbours dies, as if by overcrowding.\n"+
    "4> Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction."+
    "\n\nFor more info  http://en.wikipedia.org/wiki/Conway's_Game_of_Life .");
	}
	else if(arg0.getActionCommand().compareTo("Help")==0){
		JOptionPane.showMessageDialog(null, "How to play ? \n"+
			    "1> Move mouse over cells to make them live.\n"+
			    "2> Move along with clicking left mouse button to let the cells as they are.\n"+
			    "3> Press Left mouse button to kill already live cell.\n");
	}
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		//Change listener of the slider
		JSlider sldClicked = (JSlider)e.getSource();
		if(sldClicked==GolUI.sldDelay){
			GolUI.delay = GolUI.sldDelay.getValue();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		//Item chnge of the combobox.
		if(e.getItem().toString().compareTo("10*10")==0){
			GolUI.rowNo = 10;
			GolUI.colNo = 10;
			GolLuncher.op.dispose();

			GolLuncher.op = new OptSel(10,10,30,100);
		}else if(e.getItem().toString().compareTo("20*20")==0){
			GolUI.rowNo = 20;
			GolUI.colNo = 20;
			GolLuncher.op.dispose();

			GolLuncher.op = new OptSel(20,20,20,100);
		}else if(e.getItem().toString().compareTo("40*40")==0){
			GolUI.rowNo = 40;
			GolUI.colNo = 40;
			GolLuncher.op.dispose();
			GolLuncher.op = new OptSel(40,40,10,100);
		}
	}

}
