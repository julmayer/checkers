package de.htwg.checkers.view.gui;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 * first frame, to choose the fieldsize, after the game has started
 * @author Julian Mayer, Marcel Loevenich
 */
public class InitFrame  implements ActionListener {
	
	private JRadioButton radioButton4x4;
	private JRadioButton radioButton8x8;
	private JRadioButton radioButton10x10;
	private JRadioButton radioButton12x12;
	private JRadioButton singelplayer;
	private JRadioButton multiplayer;
	private int size;
	private JFrame initFrame;
	private boolean onePlayer;
	
	/**
     *constructor for the frame
     */
    public InitFrame(){

		JPanel panel;
		JPanel playerPanel;
		JPanel sizePanel;
		JPanel labelPanel;
		JPanel buttonPanel;
		JButton startButton;
		JLabel label1;
		JLabel label2;
		
		initFrame = new JFrame("Checkers");
		initFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initFrame.setLocationRelativeTo(null);
		final int frameSizeX = 325;
		final int frameSizeY = 150;
		initFrame.setSize(frameSizeX,frameSizeY);
	
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		playerPanel = new JPanel();
		sizePanel = new JPanel();
		labelPanel = new JPanel();
		//labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
		buttonPanel = new JPanel();
		
		startButton = new JButton("Start");
		label1 = new JLabel("Welcome to checkers!");
		label2 = new JLabel("Please choose the size of the gamefield:");
		radioButton4x4 = new JRadioButton("4x4");
		radioButton8x8 = new JRadioButton("8x8");
		radioButton10x10 = new JRadioButton("10x10");
		radioButton12x12 = new JRadioButton("12x12");
		radioButton8x8.setSelected(true);
		singelplayer = new JRadioButton("Singelplayer");
		singelplayer.setSelected(true);
		multiplayer = new JRadioButton("Multiplayer");
		
		startButton.addActionListener(this); 
		
		playerPanel.add(singelplayer);
		playerPanel.add(multiplayer);
		sizePanel.add(radioButton4x4);
		sizePanel.add(radioButton8x8);
		sizePanel.add(radioButton10x10);
		sizePanel.add(radioButton12x12);
		buttonPanel.add(startButton);
		labelPanel.add(label1);
		labelPanel.add(label2);
		
		panel.add(labelPanel);
		panel.add(playerPanel);
		panel.add(sizePanel);
		panel.add(buttonPanel);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton4x4);
	    group.add(radioButton8x8);
	    group.add(radioButton10x10);
	    group.add(radioButton12x12);
	    
	    ButtonGroup player = new ButtonGroup();
	    player.add(singelplayer);
	    player.add(multiplayer);
		
		initFrame.getContentPane().add(panel);
		initFrame.pack();
		initFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		final int sizeFour = 4;
		final int sizeEight = 8;
		final int sizeTen = 10;
		final int sizeTwelve = 12;

		if (radioButton8x8.isSelected()){
			size = sizeEight;
		} else if (radioButton10x10.isSelected()){
			size = sizeTen;
		} else if (radioButton4x4.isSelected()) {
			size = sizeFour;
		} else if (radioButton12x12.isSelected()){
			size = sizeTwelve;
		}
		
		onePlayer = singelplayer.isSelected();
		
		synchronized (this) {
			this.notify();
		}
	}
	
	/**
     * 
     * @return fieldsize
     */
    public int getSize() {
		return size;
	}
    
    public boolean isOnePlayer() {
    	return onePlayer;
    }
	
	/**
     * method to close the InitFrame
     */
    public void exit(){
		initFrame.dispose();
		initFrame.setVisible(false);
	}
}
