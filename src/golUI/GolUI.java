package golUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GolUI extends JPanel implements ActionListener {
public static int gridSize=20;//inpixel
static int rowNo;
static int colNo,curgen;
private JButton[][] buttons;
private static JPanel gamePane;
private JPanel statusPane;
private JComboBox cbSize;
public static JSlider sldDelay;
JLabel curGen;
private JButton go;
private JButton btnIntro;
private JButton btnHelp;
private JButton btnClear;
public static int delay;
public void setDelay(int delay){
	this.delay = delay;
}
public static void reDraw(){
	gamePane.setLayout(new GridLayout(rowNo,colNo));
	gamePane.setSize(rowNo*gridSize,colNo*gridSize);
}
private RunAni runAni;

	public GolUI(int x,int y,int size){
		runAni=new RunAni();
		rowNo=x;colNo=y;gridSize=size;
		buttons = new JButton[x][y];
		btnIntro = new JButton("Intro ...");
		btnHelp = new JButton("Help");
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new GoListener());
		btnClear.setVisible(false);
		btnHelp.addActionListener(new ClickListener());
		btnIntro.addActionListener(new ClickListener());
		statusPane = new JPanel();
		statusPane.setLayout(new FlowLayout());
		curgen = 0;
		curGen = new JLabel("Gen : ");
		sldDelay = new JSlider(10,1000,100);
		sldDelay.addChangeListener(new ClickListener());
		
		cbSize = new JComboBox();
		cbSize.addItem("10*10");
		cbSize.addItem("20*20");
		cbSize.addItem("40*40");
		cbSize.addItemListener(new ClickListener());
		
		statusPane.add(btnHelp);
		statusPane.add(btnIntro);
		statusPane.add(new JLabel("GenDelay:"));
		statusPane.add(sldDelay);
		statusPane.add(curGen);
		statusPane.add(btnClear);
		
		
		gamePane = new JPanel();
		gamePane.setLayout(new GridLayout(x,y));
		gamePane.setSize(x*gridSize,y*gridSize);
	//	gamePane.addMouseMotionListener(new MouseClicker());
		for(int i=0;i<x;i++){
			for(int j=0;j<y;j++){
				buttons[i][j] = new JButton("");
				//buttons[i][j].setBorder(null);
				gamePane.add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
				buttons[i][j].setBackground(Color.gray);
				buttons[i][j].setActionCommand("0");
				//buttons[i][j].addMouseMotionListener(new MouseClicker());
				
			}
		}
		
		int xx[]={6,7,6,7,  7,6,5,  4,8,  3, 9 , 3, 4, 6, 8, 9,  6,  5, 7, 5, 7,   5, 6,  4,  3, 4, 5,  2, 6,  1, 3, 4, 5, 7,  2, 3, 4, 5, 6,   4, 5, 4, 5};
		int yy[]={0,0,1,1,  9,9,9, 10,10, 11,11,12,12,12,12,12, 15, 16,16, 17,17, 18,18, 19, 20,20,20, 21,21, 22,22,22,22,22, 23,23,23,23,23,  34,34,35,35};
		for(int i=0;i<xx.length;i++)
				birth(xx[i],yy[i]);
		go = new JButton("GO");
		go.addActionListener(new GoListener());

		setSize(x*gridSize,y*gridSize);
		setLayout(new BorderLayout());
		add(gamePane,BorderLayout.CENTER);
		add(go,BorderLayout.SOUTH);
		add(statusPane,BorderLayout.NORTH);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton clicked = (JButton)arg0.getSource();
		if(clicked.getActionCommand().toString().compareTo("1")==0)
		{
		clicked.setBackground(Color.gray);
		clicked.setActionCommand("0");}
		else{
			clicked.setBackground(Color.cyan);
			clicked.setActionCommand("1");
		}
		
	}
	public void takeMove(){
		int[][] neigh=new int[rowNo][colNo];
		System.out.println("Running");
		curgen++;
		for(int i=0;i<rowNo;i++){
			for(int j=0;j<colNo;j++){
				//buttons[i][j].setBackground(new Color(((int)(Math.random()*1000))%255,((int)(Math.random()*1000))%255,((int)(Math.random()*1000))%255));
				neigh[i][j]=neighSum(i,j);
			}
		}
		curGen.setText("Gen : "+curgen);
		for(int i=0;i<rowNo;i++){
			for(int j=0;j<colNo;j++){
				//buttons[i][j].setBackground(new Color(((int)(Math.random()*1000))%255,((int)(Math.random()*1000))%255,((int)(Math.random()*1000))%255));
				if(neigh[i][j]==0){
					killme(i,j);
				}
				else if(neigh[i][j]==1){
					killme(i,j);
				}
				else if(neigh[i][j]==2){
					//as it is
				}
				else if(neigh[i][j]==3){
					birth(i,j);
				}
				else 
					killme(i,j);
			}
		}
		
	}
	public void killme(int i,int j){
		buttons[i][j].setBackground(Color.gray);
		buttons[i][j].setActionCommand("0");
	}
	public void birth(int i,int j){
		buttons[i][j].setBackground(Color.cyan);
		buttons[i][j].setActionCommand("1");
	}
	public int neighSum(int i,int j){
		int sum=0;
		for(int k=i-1;k<=i+1;k++)
			for(int l=j-1;l<=j+1;l++){
				if(k<0 || k>=rowNo)
					continue;
				if(l<0 || l>=colNo)
					continue;
				if(k==i && l==j)
					continue;
				sum += Integer.parseInt(buttons[k][l].getActionCommand().toString());
			}
		return sum;
	}
	//// Writing inner class for accessing the members of the class easily.
	public class GoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JButton btnClicked = (JButton)arg0.getSource();
			if(btnClicked.getText().compareTo("Clear")==0){
				System.out.println("clear clicked");
				curgen=0;
				curGen.setText("Gen: 0");
				runAni.stopAni();
				for(int i=0;i<rowNo;i++){
					for(int j=0;j<colNo;j++){
						buttons[i][j].setBackground(Color.gray);
						buttons[i][j].setActionCommand("0");
					}
				}
				go.setText("Resume");
				return;
			}
			if(btnClicked.getText().compareTo("GO")==0){
				btnClear.setVisible(true);
				//btnClear.disable()
				runAni.startAni();
				btnClicked.setText("Pause");
			}
			else if(btnClicked.getText().compareTo("Pause")==0){
				System.out.println("Paused.");
				runAni.stopAni();
				btnClicked.setText("Resume");
			}
			else if(btnClicked.getText().compareTo("Resume")==0){
				System.out.println("Resumed.");
				runAni.resumeAni();
				btnClicked.setText("Pause");
			}
		}
	}
	public class RunAni extends Thread{
		public boolean isRunning;
		public void run(){

			for(;;){
				while(!isRunning)
					try {
						sleep(delay);
					} catch (InterruptedException e) {
						System.out.println("Program interruped.");
						e.printStackTrace();
					}
				takeMove();
				try {
					sleep(delay);
				} catch (InterruptedException e) {
					System.out.println("Sleep Distrubed.");
					e.printStackTrace();
				}
					
				
			}
		}
		
		public void startAni(){
			isRunning=true;
			this.start();
		}
		public void stopAni(){
			isRunning = false;
		}
		public void resumeAni(){
			isRunning = true;
			//notify();
		}
	}
}
	