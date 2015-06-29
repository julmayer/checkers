package de.htwg.checkers.view.plugin.impl;

import java.awt.Color;

import javax.swing.JButton;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.IGui;
import de.htwg.checkers.view.plugin.IPlugin;

public class MoveSuggestionPlugin implements IPlugin {
    @Override
    public String getMenuEntry() {
        return "suggest move";
    }

    @Override
    public Character getMenuKey() {
        return 'S';
    }

    @Override
    public void execute(IGameController controller, IGui frame) {
        
        for (Move m : controller.getPossibleMoves()) {
            int x = m.getTo().getX();
            int y = m.getTo().getY();
            JButton button = frame.getButtons()[x][y];
            
            Color color = Color.blue;
            if (m.isKill()) {
                color = Color.red;
            }
            
            button.setBackground(color);
        }
    }
}
