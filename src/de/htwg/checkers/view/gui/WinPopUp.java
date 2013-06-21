package de.htwg.checkers.view.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class WinPopUp implements ActionListener {

	
	public WinPopUp(StringBuilder sb) {
		int dialogSizeX = 200;
		int dialogSizeY = 100;
		
		JDialog dialog = new JDialog();
		JButton close = new JButton();
		JLabel label = new JLabel();
		
		dialog.setLayout(new BorderLayout());
		dialog.setLocationRelativeTo(null);
		
		dialog.setTitle("Game over!");
		dialog.setSize(dialogSizeX,dialogSizeY);
		
		label.setText(sb.toString());
		
		close.setText("Close");
		close.addActionListener(this); 
		
		dialog.add(label, BorderLayout.CENTER);
		dialog.add(close, BorderLayout.PAGE_END);
		
		dialog.setResizable(false);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}
}