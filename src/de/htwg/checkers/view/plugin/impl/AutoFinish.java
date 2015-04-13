package de.htwg.checkers.view.plugin.impl;

import java.util.List;
import java.util.Random;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.plugin.IPlugin;

public class AutoFinish implements IPlugin {

    @Override
    public String getMenuEntry() {
        return "Auto Finish";
    }

    @Override
    public Character getMenuKey() {
        return 'F';
    }

    @Override
    public void execute(IGameController controller, GameFrame frame) {
        new Thread(new Finisher(controller)).start();
    }

    private class Finisher implements Runnable {
        private IGameController controller;
        
        private Finisher(IGameController controller) {
            this.controller = controller;
        }
        @Override
        public void run() {
            List<Move> moves;
            while ((moves = this.controller.getPossibleMoves()).size() > 0) {
                Random random = new Random(System.nanoTime());
                Move move = moves.get(random.nextInt(moves.size()));
                this.controller.input(move.toString());
            }
            
        }
        
    }
}
