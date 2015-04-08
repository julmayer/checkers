package de.htwg.checkers.view.plugin.impl;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.plugin.IPlugin;

public class MoveSuggestionPlugin implements IPlugin {
	
	Map<ButtonCoordiante, Color> oldColor = new HashMap<ButtonCoordiante, Color>();

    @Override
    public String getMenuEntry() {
        return "suggest move";
    }

    @Override
    public Character getMenuKey() {
        return 'S';
    }

    @Override
    public void execute(IGameController controller, GameFrame frame) {
        showMoves(frame.getButtons(), controller.getPossibleMoves());
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        hideMoves(frame.getButtons());
    }
    
    private void showMoves(JButton[][] buttons, List<Move> moves) {
        for (Move m : moves) {
            int x = m.getTo().getX();
            int y = m.getTo().getY();
            JButton button = buttons[x][y];
            
            Color color = Color.blue;
            if (m.isKill()) {
                color = Color.red;
            }
            
            oldColor.put(new ButtonCoordiante(x, y), button.getBackground());
            button.setBackground(color);
        }
    }
    
    private void hideMoves(JButton[][] buttons) {
    	for (Entry<ButtonCoordiante, Color> e : oldColor.entrySet()) {
    		ButtonCoordiante bc = e.getKey();
    		buttons[bc.x][bc.y].setBackground(e.getValue());
    	}
    	
    	oldColor.clear();
    }
    
    class ButtonCoordiante {
    	private int x;
    	private int y;
    	
    	public ButtonCoordiante(int x, int y) {
    		this.x = x;
    		this.y = y;
    	}
    }
}
