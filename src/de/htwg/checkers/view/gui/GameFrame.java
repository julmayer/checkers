package de.htwg.checkers.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;

public class GameFrame implements ActionListener, Observer{
	
	private IGameController gameController;

	private JButton[][] buttons;
	private JLabel moveCountLabel;
	private JLabel errorLabel;
	private JLabel turnLabel;
	
	private int fieldSize;
	private int clickCount = 0;
	private String input = "";
	StringBuilder sB = new StringBuilder();
	StringBuilder stringOutput = new StringBuilder();
	
	
	public GameFrame(IGameController gameController){
		
		
		JFrame gameFrame;
		JPanel panel;
		JPanel statusPanel;
		JPanel gamePanel;	
		
		this.gameController = gameController;
		this.gameController.addObserver(this);
		
		fieldSize = gameController.getFieldSize();
		
		gameFrame = new JFrame("Checkers the game");
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		int gameFrameExtenderInt = 50;
		gameFrame.setSize(fieldSize*gameFrameExtenderInt,fieldSize*gameFrameExtenderInt);
		
		moveCountLabel = new JLabel("Move Count");
		errorLabel = new JLabel("Error");
		turnLabel = new JLabel("Whos turn");
		
		buttons = new JButton[fieldSize][fieldSize];
		
		panel = new JPanel();
		statusPanel = new JPanel();
		gamePanel = new JPanel();
		
		panel.setLayout(new GridLayout(fieldSize,fieldSize));
		statusPanel.setLayout(new FlowLayout());
		gamePanel.setLayout(new BorderLayout());
		
		for (int i = 0; i < fieldSize; i++){
			for (int j = 0; j < fieldSize; j++){
				buttons[i][j] = new JButton();
				buttons[i][j].setName(i + " " + j + " ");
				
				buttons[i][j].addActionListener(this); 
				if(i % 2 == j % 2){
					if (fieldSize % 2 == 0){
						buttons[i][j].setBackground(Color.gray);
					} else {
						buttons[i][j].setBackground(Color.white);
					}
				} else {
					if (fieldSize % 2 == 0){
						buttons[i][j].setBackground(Color.white);
					} else {
						buttons[i][j].setBackground(Color.gray);
					}
				}
				panel.add(buttons[i][j]);
			}
		}		
		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		statusPanel.add(turnLabel);
		statusPanel.add(moveCountLabel);
		statusPanel.add(errorLabel);
		
		gamePanel.add(panel,BorderLayout.CENTER);
		gamePanel.add(statusPanel,BorderLayout.SOUTH);

		
		gameFrame.getContentPane().add(gamePanel);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		paint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clickCount++;
		sB.append(((JButton) e.getSource()).getName());
		if (clickCount == 2){
			gameController.input(input);
			clickCount = 0;
			sB.delete(0, sB.length());
		}
	}

	@Override
	public void update() {
		paint();
		if (gameController.checkIfWin(stringOutput)){
			errorLabel.setText(stringOutput.toString());
			new WinPopUp(stringOutput);
		}
	}
	
	private void paint(){
		int i;
		int j;
		for (i = 0; i < fieldSize; i++){
			for(j = 0; j < fieldSize; j++){
				setFigureOnButton(i, j);
			}
		}
	}
	
	private void setFigureOnButton(int i, int j){
		if (gameController.getFigureOnField(i, j).isBlack()){
			buttons[i][j].setIcon(new ImageIcon("/checkers/black_fig_skal.jpg"));
		} else if (gameController.getFigureOnField(i, j).isBlack() && gameController.getFigureOnField(i, j).isCrowned()){
			buttons[i][j].setIcon(new ImageIcon("checkers/black_checker_fig_skal.jpg"));
		} else if (!gameController.getFigureOnField(i, j).isBlack()){
			buttons[i][j].setIcon(new ImageIcon("checkers/white_fig_skal.jpg"));
		} else if ((!gameController.getFigureOnField(i, j).isBlack() && gameController.getFigureOnField(i, j).isCrowned())) {
			buttons[i][j].setIcon(new ImageIcon("checkers/white_checker_fig_skal.jpg"));
		} else {
			buttons[i][j].setIcon(null);
		}
	}
}
