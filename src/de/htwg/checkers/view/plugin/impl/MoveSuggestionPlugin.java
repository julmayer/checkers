package de.htwg.checkers.view.plugin.impl;

import java.awt.Color;

import javax.swing.JButton;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.GameFrame;
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
    public void execute(IGameController controller, GameFrame frame) {
        JButton[][] buttons = frame.getButtons();
        for (Move m : controller.getPossibleMoves()) {
            int x = m.getTo().getX();
            int y = m.getTo().getY();
            
            Color color = Color.blue;
            if (m.isKill()) {
                color = Color.red;
            }
            
            buttons[x][y].setBackground(color);
        }
    }

}
