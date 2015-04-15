package de.htwg.checkers.view.plugin.impl;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.GameFrame;
import de.htwg.checkers.view.plugin.IPlugin;

public class AutoFinish implements IPlugin {
    Thread thread;
    boolean stop = false;
    
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
        if (this.thread == null) {
            this.stop = false;
            this.thread = new Thread(new Finisher(controller));
            this.thread.start();
        } else {
            this.stop = true;
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.thread = null;
        }
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
                if (stop) {
                    break;
                }
                Random random = new Random(System.nanoTime());
                Move move = moves.get(random.nextInt(moves.size()));
                this.controller.input(move.toString());
            }
            Logger.getGlobal().info("finished");
        }
        
    }
}
