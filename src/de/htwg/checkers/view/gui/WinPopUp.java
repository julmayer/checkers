package de.htwg.checkers.view.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.htwg.checkers.controller.GameController;

/**
 * PopUp frame when game is over
 * @author Julian Mayer, Marcel Loevenich
 */
public class WinPopUp  extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5166328560140505158L;

	/**
     *contructor for the frame
     * @param sb
     */
    public WinPopUp(String message, ActionListener ButtonListener) {
		final int dialogSizeX = 300;
		final int dialogSizeY = 100;
		
		JButton quit = new JButton("Quit");
		quit.setName(GameController.QUIT + "");
		JButton restart = new JButton("Restart");
		restart.setName(GameController.RESTART+ "");
		JButton newGame = new JButton("New game");
		newGame.setName(GameController.NEW_GAME+ "");
		
		JLabel messageLabel = new JLabel();
		
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		
		this.setTitle("Game over!");
		this.setSize(dialogSizeX,dialogSizeY);
		
		messageLabel.setText(message);
		
		quit.addActionListener(ButtonListener); 
		restart.addActionListener(ButtonListener);
		newGame.addActionListener(ButtonListener);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newGame);
		buttonPanel.add(restart);
		buttonPanel.add(quit);
				
		
		this.add(messageLabel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.PAGE_END);
		
		this.setResizable(false);
		this.setVisible(true);
	}
}
