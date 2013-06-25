package de.htwg.checkers.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 *
 * @author jmayer
 */
public class InitFrame  implements ActionListener {
	
	private JRadioButton radioButton4x4;
	private JRadioButton radioButton8x8;
	private JRadioButton radioButton10x10;
	private JRadioButton radioButton12x12;
	private int size;
	private JFrame initFrame;
	
	/**
     *
     */
    public InitFrame(){

		JPanel panel;
		JButton startButton;
		JLabel label1;
		JLabel label2;
		
		initFrame = new JFrame("Checkers");
		initFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initFrame.setLocationRelativeTo(null);
		final int frameSizeX = 325;
		final int frameSizeY = 125;
		initFrame.setSize(frameSizeX,frameSizeY);
	
		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		
		startButton = new JButton("Start");
		label1 = new JLabel("Welcome to checkers!");
		label2 = new JLabel("Please choose the size of the gamefield:");
		radioButton4x4 = new JRadioButton("4x4");
		radioButton8x8 = new JRadioButton("8x8");
		radioButton10x10 = new JRadioButton("10x10");
		radioButton12x12 = new JRadioButton("12x12");
		radioButton8x8.setSelected(true);
		
		startButton.addActionListener(this); 
		
		panel.add(label1);
		panel.add(label2);
		panel.add(radioButton4x4);
		panel.add(radioButton8x8);
		panel.add(radioButton10x10);
		panel.add(radioButton12x12);
		panel.add(startButton);
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioButton4x4);
	    group.add(radioButton8x8);
	    group.add(radioButton10x10);
	    group.add(radioButton12x12);
		
		initFrame.getContentPane().add(panel);
				
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
		synchronized (this) {
			this.notify();
		}
	}
	
	/**
     *
     * @return
     */
    public int getSize() {
		return size;
	}
	
	/**
     *
     */
    public void exit(){
		initFrame.dispose();
		initFrame.setVisible(false);
	}
}
