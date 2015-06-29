package de.htwg.checkers.view.plugin;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.view.gui.IGui;

public interface IPlugin {
    String getMenuEntry();
    
    Character getMenuKey();
    
    void execute(IGameController controller, IGui frame);
}
