package de.htwg.checkers.view.plugin;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.GameFrame;

public interface IPlugin {
    public String getMenuEntry();
    
    public Character getMenuKey();
    
    public void execute(IGameController controller, GameFrame frame);
}
