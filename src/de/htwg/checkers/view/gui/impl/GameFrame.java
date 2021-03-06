package de.htwg.checkers.view.gui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.controller.IPersistenceController;
import de.htwg.checkers.persistence.PersistContainer;
import de.htwg.checkers.util.observer.Observer;
import de.htwg.checkers.view.gui.IGui;
import de.htwg.checkers.view.gui.InitFrame;
import de.htwg.checkers.view.gui.WinPopUp;
import de.htwg.checkers.view.plugin.IPlugin;

/**
 * main frame with the game
 * @author Julian Mayer, Marcel Loevenich
 */
public class GameFrame extends JFrame implements ActionListener, Observer, IGui {
	
	private class initFrameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startClicked();
		}
	}
	
	private class loadListener implements ActionListener {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
	        List<String> selection = persistenceController.getStoredGames();
	        Object[] array = selection.toArray();
	        String gameName = (String) JOptionPane.showInputDialog(GameFrame.this,
	                "Select savegame to be loaded", "Load game",
	                JOptionPane.QUESTION_MESSAGE, null, array, null);

	        if (gameName != null && !gameName.isEmpty()) {
	            // hide gameframe to repaint after update
	            GameFrame.this.setVisible(false);
	            gameController.gameInit(persistenceController.getByName(gameName));

	            if (initFrame != null) {
	                initFrame.exit();
	                initFrame = null;
	            }
	        }
	    }
	}

	private class winPopUpListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// get info
			JButton clickedButton = (JButton) arg0.getSource();
			String control = new String(clickedButton.getName());
			
			controlEntered(control);
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6475445915710130057L;

	private IGameController gameController;
	private IPersistenceController persistenceController;

	private JButton[][] buttons;
	private JPanel statusPanel;
	private JPanel buttonPanel;
	private JLabel moveCountLabel;
	private JLabel errorLabel;
	private JLabel turnLabel;

	
	private int fieldSize;
	private int clickCount = 0;
	private StringBuilder inputStringBuilder = new StringBuilder();

	private InitFrame initFrame;
	private WinPopUp winPopUp;

	/**
     * constructor for the gameframe
     * @param gameController
     */
    @Inject
	public GameFrame(final IGameController gameController, Set<IPlugin> plugins, 
	        final IPersistenceController persistenceController) {
		this.gameController = gameController;
		this.gameController.addObserver(this);
		this.persistenceController = persistenceController;
		
		
		this.setTitle("Checkers the game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);

		moveCountLabel = new JLabel("");
		errorLabel = new JLabel("");
		turnLabel = new JLabel("");
		buildStatusPanel();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu optionMenu = new JMenu("Options");
		optionMenu.setMnemonic('O');
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = (String)JOptionPane.showInputDialog(GameFrame.this, 
                        "Inser name for savegame", "new Game");
                if (name != null && !name.isEmpty()) {
                    PersistContainer container = new PersistContainer(name, 
                            gameController.getField(), gameController.getGameState());
                    persistenceController.save(container);
                }
            }
        });
		
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new loadListener());
		JMenuItem restart = new JMenuItem("Restart");
		restart.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.input("R");
            }
        });
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                gameController.input("N");
            }
        });
        optionMenu.add(save);
        optionMenu.add(load);
        optionMenu.add(restart);
        optionMenu.add(newGame);
		JMenu pluginMenu = new JMenu("Plugins");
		pluginMenu.setMnemonic('P');
		
		menuBar.add(optionMenu);
		menuBar.add(pluginMenu);
		
		for (final IPlugin p : plugins) {
			JMenuItem m = new JMenuItem(p.getMenuEntry(), p.getMenuKey());
			m.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					p.execute(gameController, GameFrame.this);
					
				}
			});
			pluginMenu.add(m);
		}
		this.setJMenuBar(menuBar);
		update();
	}
    
    private void initGameFrame() {
		// set size
		fieldSize = gameController.getFieldSize();
    	final int gameFrameExtenderInt = 50;
		this.setSize(fieldSize * gameFrameExtenderInt, fieldSize * gameFrameExtenderInt);
		
		// generate subPanels
		buttons = new JButton[fieldSize][fieldSize];
		buildButtonPanel();
		
		initLabels();
		
		// build main Panel
		JPanel gamePanel = new JPanel(new BorderLayout());
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		gamePanel.add(buttonPanel,BorderLayout.CENTER);
		gamePanel.add(statusPanel,BorderLayout.SOUTH);

		this.setContentPane(gamePanel);
		this.setVisible(true);
		paint();
    }
    
   private void buildButtonPanel() {
    	buttonPanel = new JPanel(new GridLayout(fieldSize,fieldSize));
    	
		for (int j = fieldSize - 1; j > -1; j--) {
			for (int i = 0; i < fieldSize; i++) {
				JButton button = new JButton();
				button.setName(i + " " + j + " ");
				button.addActionListener(this);
				button.setBackground(getButtonBackground(i, j));
				buttonPanel.add(button);
				buttons[i][j] = button;
			}
		}
    }
   
   private Color getButtonBackground(int x, int y) {
       Color color;
       if (x % 2 == y % 2) {
           if (fieldSize % 2 == 0) {
               color = Color.white;
           } else {
               color = Color.gray;
           }
       } else {
           if (fieldSize % 2 == 0) {
               color = Color.gray;
           } else {
               color = Color.white;
           }
       }
       return color;
   }
   
  
	private void buildStatusPanel() {
		final int numberOfLabels = 3;
		statusPanel = new JPanel(new GridLayout(numberOfLabels, 1));

		statusPanel.add(turnLabel);
		statusPanel.add(moveCountLabel);
		statusPanel.add(errorLabel);
	}
	
	private void initLabels() {
		moveCountLabel.setText("Overall number of moves: 0");
		errorLabel.setText("");
		turnLabel.setText("Active color is black!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton)e.getSource();
		clickCount++;
		inputStringBuilder.append(clickedButton.getName());
		if (clickCount == 2){
			gameController.input(inputStringBuilder.toString());
			clickCount = 0;
			inputStringBuilder.delete(0, inputStringBuilder.length());
		}
	}

	/**
     * methode to update the gui
     */
    @Override
	public final void update() {
		String error = gameController.getError();
		errorLabel.setText(error);
		if (error.isEmpty()) {
			switch (gameController.getCurrentState()) {
			case NEW_GAME:
				this.setVisible(false);
				askForInitialization();
				break;
			case RUNNING:
				if (initFrame != null) {
					// init through other view
					initFrame.exit();
				}
				paint();
				if (gameController.checkIfWin()){
					errorLabel.setText(gameController.getInfo());
					winPopUp = new WinPopUp(gameController.getInfo(), new winPopUpListener());
				}
				break;
			default:
				if (winPopUp != null) {
					winPopUp.dispose();
					winPopUp = null;
				}
				if (initFrame != null) {
					initFrame.exit();
					initFrame = null;
				}
				this.dispose();
				// QUIT
				System.exit(0);
				break;
			}
		}
	}
	
	private void paint() {
		if (!this.isVisible()) {
			initGameFrame();
		}
		int i, j;
		for (i = 0; i < fieldSize; i++){
			for(j = 0; j < fieldSize; j++) {
				setFigureOnButton(i, j);
			}
		}

		int moveCount = gameController.getMoveCount();
		moveCountLabel.setText("Overall number of moves: " + moveCount);
		
		if (gameController.isBlackTurn()){
			turnLabel.setText("Active color is black!");
		} else {
			turnLabel.setText("Active color is white!");
		}
	}
	
	private void setFigureOnButton(int i, int j) {
		Icon icon = null;
		
		if (gameController.getFigureOnField(i, j) != null) {
			StringBuilder nameBuilder = new StringBuilder("/images/");
		
			if (gameController.getFigureOnField(i, j).isBlack()) {
				nameBuilder.append("black");
			} else {
				nameBuilder.append("white");
			}
			
			if (gameController.getFigureOnField(i, j).isCrowned()) {
				nameBuilder.append("_checker");
			}
			
			nameBuilder.append("_fig_skal.jpg");
			
			icon = new ImageIcon(getClass().getResource(nameBuilder.toString()));
		}
			
		buttons[i][j].setIcon(icon);
		buttons[i][j].setBackground(getButtonBackground(i, j));
	}
	
    @Override
    public JButton[][] getButtons() {
        return buttons;
    }

    private void startClicked() {
		StringBuilder buildInitParams = new StringBuilder();
		buildInitParams.append(initFrame.getSize());
		if (initFrame.isOnePlayer()) {
			buildInitParams.append(" S ");
			buildInitParams.append(initFrame.getDifficulty());
		} else {
			buildInitParams.append(" M");
		}

		initFrame.exit();
		initFrame = null;

		gameController.input(buildInitParams.toString());
	}
	
	private void controlEntered(String input) {
		winPopUp.dispose();
		gameController.input(input);
	}
	
	private void askForInitialization() {
		if (winPopUp != null) {
			winPopUp.dispose();
			winPopUp = null;
		}
		initFrame = new InitFrame(new initFrameListener(), new loadListener());
	}
}
