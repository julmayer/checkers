package de.htwg.checkers.view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.checkers.controller.IGameController;
import de.htwg.checkers.util.observer.Observer;
import de.htwg.checkers.view.plugin.IPlugin;

/**
 * main frame with the game
 * @author Julian Mayer, Marcel Loevenich
 */
public class GameFrame extends JFrame implements ActionListener, Observer{
	
	private class initFrameListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startClicked();
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

	private JButton[][] buttons;
	private JPanel statusPanel;
	private JPanel buttonPanel;
	private JLabel moveCountLabel;
	private JLabel errorLabel;
	private JLabel turnLabel;
	private JMenuBar menuBar;
	private JMenu pluginMenu;
	
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
	public GameFrame(final IGameController gameController, Set<IPlugin> plugins) {
		this.gameController = gameController;
		this.gameController.addObserver(this);
		
		
		this.setTitle("Checkers the game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);

		moveCountLabel = new JLabel("");
		errorLabel = new JLabel("");
		turnLabel = new JLabel("");
		statusPanel = buildStatusPanel();
		
		menuBar = new JMenuBar();
		pluginMenu = new JMenu("Plugins");
		pluginMenu.setMnemonic('P');
		
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
		askForInitialization();
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
   
  
	private JPanel buildStatusPanel() {
		final int numberOfLabels = 3;
		JPanel statusPanel = new JPanel(new GridLayout(numberOfLabels, 1));

		statusPanel.add(turnLabel);
		statusPanel.add(moveCountLabel);
		statusPanel.add(errorLabel);
		
		return statusPanel;
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
	public void update() {
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
	
	/**
     * @return the buttons
     */
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
		initFrame = new InitFrame(new initFrameListener());
	}
}
