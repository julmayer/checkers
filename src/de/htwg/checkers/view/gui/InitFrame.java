package de.htwg.checkers.view.gui;

import java.awt.Color;
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
public class InitFrame {
	
	private JRadioButton radioButton4x4;
	private JRadioButton radioButton8x8;
	private JRadioButton radioButton10x10;
	private JRadioButton radioButton12x12;
	private JRadioButton singelplayer;
	private JRadioButton easy;
	private JRadioButton medium;
	private JFrame initFrame;
	
	/**
     *constructor for the frame
     */
    public InitFrame(ActionListener startListener, ActionListener loadListener) {

		JPanel panel;
		JPanel playerPanel;
		JPanel sizePanel;
		JPanel labelPanel;
		JPanel buttonPanel;
		JPanel difficultyPanel;
		JButton startButton;
		JButton loadButton;
		JLabel label1;
		JLabel label2;
		JRadioButton multiplayer;
		
		initFrame = new JFrame("Checkers");
		initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		buttonPanel = new JPanel();
		difficultyPanel = new JPanel();
		
		
		startButton = new JButton("Start");
		loadButton = new JButton("Load");
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
		easy = new JRadioButton("easy");
		easy.setSelected(true);
		medium = new JRadioButton("medium");
		
		startButton.addActionListener(startListener); 
		loadButton.addActionListener(loadListener);
		
		playerPanel.add(singelplayer);
		playerPanel.add(multiplayer);
		sizePanel.add(radioButton4x4);
		sizePanel.add(radioButton8x8);
		sizePanel.add(radioButton10x10);
		sizePanel.add(radioButton12x12);
		buttonPanel.add(startButton);
		buttonPanel.add(loadButton);
		labelPanel.add(label1);
		labelPanel.add(label2);
		difficultyPanel.add(easy);
		difficultyPanel.add(medium);
		
		panel.add(labelPanel);
		panel.add(playerPanel);
		panel.add(difficultyPanel);
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
	    
	    ButtonGroup difficultyGroup = new ButtonGroup();
	    difficultyGroup.add(easy);
	    difficultyGroup.add(medium);
		
		initFrame.getContentPane().add(panel);
		initFrame.pack();
		initFrame.setVisible(true);
	}
	
	/**
     * 
     * @return fieldsize
     */
    public int getSize() {
		int size;
		final int sizeFour = 4;
		final int sizeEight = 8;
		final int sizeTen = 10;
		final int sizeTwelve = 12;

		if (radioButton8x8.isSelected()) {
			size = sizeEight;
		} else if (radioButton10x10.isSelected()) {
			size = sizeTen;
		} else if (radioButton4x4.isSelected()) {
			size = sizeFour;
		} else {
			size = sizeTwelve;
		}
    	
    	return size;
	}
    
    /**
     * Shows if singleplayer or multiplayer is selected.
     * @return true if singelplayer is selected.
     */
    public boolean isOnePlayer() {
    	return singelplayer.isSelected();
    }
	
    /**
     * Retruns selected difficulty.
     * @return difficulty. Lower number equals easier gameplay.
     */
    public int getDifficulty() {
    	int difficulty;
		
    	if (easy.isSelected()) {
			difficulty = 1;
		} else {
			difficulty = 2;
		}
		
    	return difficulty;
    }
    
	/**
     * method to close the InitFrame
     */
    public void exit(){
		initFrame.dispose();
		initFrame.setVisible(false);
	}
}
