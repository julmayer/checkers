package de.htwg.checkers.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;

/**
 * main frame with the game
 * @author Julian Mayer, Marcel Loevenich
 */
public class GameFrame implements ActionListener, Observer{
	
	private IGameController gameController;

	private JButton[][] buttons;
	private JLabel moveCountLabel;
	private JLabel errorLabel;
	private JLabel turnLabel;
	
	private int fieldSize;
	private int clickCount = 0;
	private StringBuilder sB = new StringBuilder();
	private StringBuilder stringOutput = new StringBuilder();
	
	/**
     * constructor for the gameframe
     * @param gameController
     */
    @Inject
	public GameFrame(IGameController gameController){
		
		JPanel panel;
		JPanel statusPanel;
		JPanel gamePanel;	
		JFrame gameFrame;
		
		this.gameController = gameController;
		this.gameController.addObserver(this);
		
		fieldSize = gameController.getFieldSize();
		
		gameFrame = new JFrame("Checkers the game");
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		final int gameFrameExtenderInt = 50;
		gameFrame.setSize(fieldSize*gameFrameExtenderInt,fieldSize*gameFrameExtenderInt);
		
		moveCountLabel = new JLabel("Overall number of moves: 0");
		errorLabel = new JLabel("");
		turnLabel = new JLabel("Active color is black!");
		
		buttons = new JButton[fieldSize][fieldSize];
		
		panel = new JPanel();
		statusPanel = new JPanel();
		gamePanel = new JPanel();
		
		panel.setLayout(new GridLayout(fieldSize,fieldSize));
		final int three = 3;
		statusPanel.setLayout(new GridLayout(three,1));
		gamePanel.setLayout(new BorderLayout());
		
		for (int j = fieldSize-1; j > -1; j--){
			for (int i = 0; i < fieldSize; i++){
				buttons[i][j] = new JButton();
				buttons[i][j].setName(i + " " + j + " ");
				
				buttons[i][j].addActionListener(this); 
				if(i % 2 == j % 2){
					if (fieldSize % 2 == 0){
						buttons[i][j].setBackground(Color.white);
					} else {
						buttons[i][j].setBackground(Color.gray);
					}
				} else {
					if (fieldSize % 2 == 0){
						buttons[i][j].setBackground(Color.gray);
					} else {
						buttons[i][j].setBackground(Color.white);
					}
				}
				panel.add(buttons[i][j]);
			}
		}		
		
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		statusPanel.add(turnLabel);
		statusPanel.add(moveCountLabel);
		statusPanel.add(errorLabel);
		
		gamePanel.add(panel,BorderLayout.CENTER);
		gamePanel.add(statusPanel,BorderLayout.SOUTH);

		
		gameFrame.setContentPane(gamePanel);
		gameFrame.setResizable(true);
		gameFrame.setVisible(true);
		paint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clickCount++;
		sB.append(((JButton) e.getSource()).getName());
		if (clickCount == 2){
			gameController.input(sB.toString());
			clickCount = 0;
			sB.delete(0, sB.length());
		}
	}

	/**
     * methode to update the gui
     */
    @Override
	public void update() {
		String error = gameController.getError();
		if (error == null) {
			errorLabel.setText("");
			paint();
						
			int moveCount = gameController.getMoveCount();
			String s = ("Overall number of moves: " + moveCount);
			moveCountLabel.setText(s);
			
			if (gameController.isBlackTurn()){
				turnLabel.setText("Active color is black!");
			} else {
				turnLabel.setText("Active color is white!");
			}
			
			if (gameController.checkIfWin(stringOutput)){
				errorLabel.setText(stringOutput.toString());
				new WinPopUp(stringOutput);
			}
		} else {
				errorLabel.setText(error);
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
	
	private void setFigureOnButton(int i, int j) {
		if (gameController.getFigureOnField(i, j) == null) {
			buttons[i][j].setIcon(null);
		} else if (gameController.getFigureOnField(i, j).isBlack() && gameController.getFigureOnField(i, j).isCrowned()){
			buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/images/black_checker_fig_skal.jpg")));
		} else if ((!gameController.getFigureOnField(i, j).isBlack() && gameController.getFigureOnField(i, j).isCrowned())) {
			buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/images/white_checker_fig_skal.jpg")));
		} else if (gameController.getFigureOnField(i, j).isBlack() ){
			buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/images/black_fig_skal.jpg")));
		} else if (!gameController.getFigureOnField(i, j).isBlack()){
			buttons[i][j].setIcon(new ImageIcon(getClass().getResource("/images/white_fig_skal.jpg")));
		}
	}
}
