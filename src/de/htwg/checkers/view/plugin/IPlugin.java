package de.htwg.checkers.view.plugin;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.GameFrame;

public interface IPlugin {
    String getMenuEntry();
    
    Character getMenuKey();
    
    void execute(IGameController controller, GameFrame frame);
}
