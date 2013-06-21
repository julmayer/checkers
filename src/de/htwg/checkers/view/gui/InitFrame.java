package de.htwg.checkers.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import de.htwg.checkers.util.observer.Observable;


public class InitFrame extends Observable implements ActionListener {
	
	private JFrame initFrame;
	private JPanel panel;
	private JButton startButton;
	private JRadioButton radioButton8x8;
	private JRadioButton radioButton10x10;
	private JRadioButton radioButton12x12;
	private JLabel label1;
	private JLabel label2;
	private int size;
	//private JTextField gameSizeTextField;
	
	public InitFrame(){

		initFrame = new JFrame("Checkers");
		initFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initFrame.setLocationRelativeTo(null);
		initFrame.setSize(325,125);
	
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		startButton = new JButton("Start");
		label1 = new JLabel("Welcome to checkers!");
		label2 = new JLabel("Please choose the size of the gamefield:");
		radioButton8x8 = new JRadioButton("8x8");
		radioButton10x10 = new JRadioButton("10x10");
		radioButton12x12 = new JRadioButton("12x12");
		//gameSizeTextField = new JTextField("8",15);	
		
		startButton.addActionListener(this); 
		
		panel.add(label1);
		panel.add(label2);
		//panel.add(gameSizeTextField);
		panel.add(radioButton8x8);
		panel.add(radioButton10x10);
		panel.add(radioButton12x12);
		panel.add(startButton);
		
		ButtonGroup group = new ButtonGroup();
	    group.add(radioButton8x8);
	    group.add(radioButton10x10);
	    group.add(radioButton12x12);
		
		initFrame.getContentPane().add(panel);
				
		initFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//int fieldSize =  Integer.parseInt(gameSizeTextField.getText());	
		//GameFrame gameFrame = new GameFrame(fieldSize);
		
		notify();
		
	}
	
	public int getSize() {
		return size;
	}
	
	public void exit(){
		initFrame.dispose();
	}
}
