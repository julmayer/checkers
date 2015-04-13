package de.htwg.checkers.view.plugin.impl;

import java.util.List;
import java.util.Random;
import java.util.Timer;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.plugin.IPlugin;

public class AutoMove implements IPlugin {

    @Override
    public String getMenuEntry() {
        return "Auto move";
    }

    @Override
    public Character getMenuKey() {
        return 'A';
    }

    @Override
    public void execute(IGameController controller, GameFrame frame) {
        List<Move> moves = controller.getPossibleMoves();
        Random r = new Random(System.nanoTime());
        int idx = r.nextInt(moves.size());
        Move pickedMove = moves.get(idx);
        
        controller.input(pickedMove.toString());
    }

}
