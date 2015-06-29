package de.htwg.checkers.view.plugin.impl;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.view.gui.IGui;
import de.htwg.checkers.view.plugin.IPlugin;

public class AutoFinish implements IPlugin {
    private Thread thread;
    private boolean stop = false;
    
    @Override
    public String getMenuEntry() {
        return "Auto Finish";
    }

    @Override
    public Character getMenuKey() {
        return 'F';
    }

    @Override
    public void execute(IGameController controller, IGui frame) {
        if (this.thread == null) {
            this.stop = false;
            this.thread = new Thread(new Finisher(controller));
            this.thread.start();
        } else {
            this.stop = true;
            try {
                this.thread.join();
            } catch (InterruptedException e) {
                Logger.getGlobal().throwing(AutoFinish.class.getName(), "execute", e);
            }
            this.thread = null;
        }
    }

    private final class Finisher implements Runnable {
        private IGameController controller;
        
        private Finisher(IGameController controller) {
            this.controller = controller;
        }
        @Override
        public void run() {
            List<Move> moves;
            while (this.controller.getPossibleMoves().size() > 0) {
                if (stop) {
                    break;
                }
                moves = this.controller.getPossibleMoves();
                Random random = new Random(System.nanoTime());
                Move move = moves.get(random.nextInt(moves.size()));
                this.controller.input(move.toString());
            }
        }
        
    }
}
