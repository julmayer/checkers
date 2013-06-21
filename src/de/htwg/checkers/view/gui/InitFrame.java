package de.htwg.checkers.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class InitFrame implements ActionListener {
	
	private JFrame initFrame;
	private JPanel panel;
	private JButton startButton;
	private JLabel label1;
	private JLabel label2;
	private JTextField gameSizeTextField;
	
	public InitFrame(){
		initFrame = new JFrame("Checkers");
		initFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initFrame.setLocationRelativeTo(null);
		initFrame.setSize(325,125);
	
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));

		
		
		startButton = new JButton("Start");
		label1 = new JLabel("Welcome to checkers!");
		label2 = new JLabel("Please enter size of the Gamefield, min 4.");
		gameSizeTextField = new JTextField("8",15);	
		
		startButton.addActionListener(this); 
		
		panel.add(label1);
		panel.add(label2);
		panel.add(gameSizeTextField);
		panel.add(startButton);
		
		initFrame.getContentPane().add(panel);
				
		initFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int fieldSize =  Integer.parseInt(gameSizeTextField.getText());	
		GameFrame gameFrame = new GameFrame(fieldSize);
	}
	
}
