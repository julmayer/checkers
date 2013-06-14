package de.htwg.checkers.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class GameFrame implements ActionListener{

	private JFrame gameFrame;
	private JPanel panel;
	private JPanel statusPanel;
	private JPanel gamePanel;
	private JTextField statusText;
	private JButton[][] buttons;
	
	public GameFrame(int fieldSize){
		
		gameFrame = new JFrame("Checkers the game");
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setSize(fieldSize*50,fieldSize*50);
		
		statusText = new JTextField("Status");
		statusText.setEditable(false);
		
		buttons = new JButton[fieldSize][fieldSize];
		
		panel = new JPanel();
		statusPanel = new JPanel();
		gamePanel = new JPanel();
		
		panel.setLayout(new GridLayout(fieldSize,fieldSize));
		statusPanel.setLayout(new GridLayout());
		gamePanel.setLayout(new BorderLayout());
		
		for (int i = 0; i < fieldSize; i++){
			for (int j = 0; j < fieldSize; j++){
				buttons[i][j] = new JButton();
				
				buttons[i][j].addActionListener(this); 
				if(i % 2 == j % 2){
					buttons[i][j].setBackground(Color.gray);
				} else {
					buttons[i][j].setBackground(Color.white);
				}
				buttons[i][j].setIcon(new ImageIcon("/home/ibex/workspace/checkers/black_checker_fig_skal.jpg"));
				panel.add(buttons[i][j]);
			}
		}		
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		statusPanel.add(statusText);
		
		gamePanel.add(panel,BorderLayout.CENTER);
		gamePanel.add(statusPanel,BorderLayout.SOUTH);

		
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
