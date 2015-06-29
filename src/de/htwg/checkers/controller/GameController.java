package de.htwg.checkers.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.checkers.controller.bot.IBot;
import de.htwg.checkers.controller.bot.MediumBot;
import de.htwg.checkers.controller.bot.SimpleBot;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerRight;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperRight;
import de.htwg.checkers.models.Bot;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.GameState;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.models.State;
import de.htwg.checkers.persistence.PersistContainer;
import de.htwg.checkers.util.observer.Observable;

/**
 * Implementation of gameController
 * @author Julian Mayer, Marcel Loevenich
 */
	
@Singleton
public class GameController extends Observable implements IGameController {
	public static final int MIN_GAMEFIELD_SIZE= 4;
	public static final int BOT_DELAY = 500;
	public static final char QUIT = 'Q';
	public static final char NEW_GAME = 'N';
	public static final char RESTART = 'R';
	public static final char WHITE_GAVE_UP = 'W';
	public static final char BLACK_GAVE_UP = 'B';
	private Field field;
	private List<Figure> blacks;
	private List<Figure> whites;
	private PossibleMovesLowerLeft lowerLeft;
	private PossibleMovesLowerRight lowerRight;
	private PossibleMovesUpperLeft upperLeft;
	private PossibleMovesUpperRight upperRight;
	private String error;
	private String info;
	private IBot bot;
	private GameState gameState;
	
	/**
     * Construcor for the gamecontroller
     */
    @Inject
    public GameController() {
        gameState = new GameState();
        this.error = "";
    }
	
    /**
     * @return the gamefield
     */
	public Field getField() {
		return new Field(this.field);
	}
	
	public GameState getGameState() {
	    return this.gameState;
	}
	
	/**
     * @return the gamefieldsize
     */
	public int getFieldSize() {
		return this.field.getSize();
	}

	/**
     * @return if its blackturn or not
     */
	public boolean isBlackTurn() {
		return this.gameState.isBlackTurn();
	}
	
	/**
     * @return the errorstring
     */
	public String getError() {
		return error;
	}
	
	public String getInfo() {
		return info;
	}
	
	public State getCurrentState() {
		return this.gameState.getCurrentState();
	}
	
	/**
     * @return the actual move count
     */
	public int getMoveCount() {
		return this.gameState.getMoveCount();
	}
	
	/**
     * method to do a game init
     */
	public void gameInit(int size, Bot difficulty) {
		if (size < MIN_GAMEFIELD_SIZE){
			throw new IllegalArgumentException(String.format("Minimum size is %d!", MIN_GAMEFIELD_SIZE));
		}

		final int rowsToFill = size/2 - 1;
		this.field = new Field(size);
		this.gameState.reset();
		this.gameState.setBot(difficulty);
		this.gameState.setCurrentState(State.RUNNING);
		this.gameInit();
		createBlackFigures(rowsToFill);
		createWhiteFigures(rowsToFill);
	}
	
	@Override
	public void gameInit(PersistContainer container) {
	    this.gameState = container.getGameState();
	    this.field = container.getField();
	    this.gameInit();
	    
	    int size = field.getSize();
	    for (int i = 0; i < size; ++i) {
	        for (int j = 0; j < size; ++j) {
	            Figure figure = field.getCellByCoordinates(i, j).getOccupier();
	            if (figure == null) {
	                continue;
	            }
	            if (figure.isBlack()) {
	                this.blacks.add(figure);
	            } else {
	                this.whites.add(figure);
	            }
	        }
	    }
	    this.notifyObservers();
	}
	
	private void gameInit() {
        this.lowerLeft = new PossibleMovesLowerLeft(field);
        this.lowerRight = new PossibleMovesLowerRight(field);
        this.upperLeft = new PossibleMovesUpperLeft(field);
        this.upperRight = new PossibleMovesUpperRight(field);
        this.whites = new LinkedList<Figure>();
        this.blacks = new LinkedList<Figure>();
        this.error = "";
        
        switch (this.gameState.getBot()) {
        case SIMPLE_BOT:
            this.bot = new SimpleBot(this.whites);
            break;
        case MEDIUM_BOT:
            this.bot = new MediumBot(this.whites);
            break;
        default:
            this.bot = null;
            break;
        }
	}
	
	private void createWhiteFigures(int rowsToFill) {
		for (int y = 0; y < rowsToFill; y++){
			fillRow(y, false);
		}
	}
	
	private void createBlackFigures(int rowsToFill) {
		int fieldSize = this.field.getSize();
	    for (int y = fieldSize - rowsToFill;y  < fieldSize; y++) {
			fillRow(y, true);
		}
	}
	
	private void fillRow(int y, boolean isBlack) {
		for (int x = 0; x < this.field.getSize(); x++) {
			if (x % 2 == 0 && y % 2 != 0 ){
				fillList(new Figure(field.getCellByCoordinates(x, y),isBlack));
			} else if (x % 2 != 0 && y % 2 == 0 ){
				fillList(new Figure(field.getCellByCoordinates(x, y),isBlack));
			}
		}
	}
	
	private void fillList(Figure figure) {
		if (figure.isBlack()){
			blacks.add(figure);
		} else {
			whites.add(figure);
		}
	}
	
	/**
	 * validate input, move, do stuff
	 * @param input
     * @return if there was an error
     */
	public boolean input(final String input) {
		final String initParamRegex = "^[0-9]+ (S [0-9]|M( [0-9])?)$";
		final String moveRegex = "^([a-zA-Z0-9] [0-9] ?){2}$";
		final String controlRegex = "^[a-zA-Z]$";
		error = "";
		if (Pattern.matches(initParamRegex, input)) {
			parseGameInit(input);
		} else if (Pattern.matches(moveRegex, input)) {
			parseMove(input);
		} else if (Pattern.matches(controlRegex, input)) {
			parseControls(input);
		} else {
			error = "Incorrect input : " + input;
		}
		
		notifyObservers();
		if (gameState.getCurrentState() == State.RUNNING) {
			botmove();
		}
		return gameState.getCurrentState() == State.QUIT;
	}
	
	private void parseGameInit(final String input) {
		final int fieldSizePos = 0, playModePos = 1, botLevelPos = 2;

		final String[] splitInput = input.split(" ");
		
		final int fieldSize = Integer.valueOf(splitInput[fieldSizePos]);
		final boolean singleplayer = splitInput[playModePos].equals("S");
		Bot difficulty = Bot.NO_BOT;
		if (singleplayer) {
		    difficulty = Bot.valueOf(Integer.valueOf(splitInput[botLevelPos]));
		}
		
		gameInit(fieldSize, difficulty);
	}
	
	private void parseMove(String input) {
		final int srcXPos = 0, srcYPos = 1, destXPos = 2, destYPos = 3;
		int moveFromX, moveFromY, moveToX, moveToY;
		final String[] splittedInput = input.split(" ");
		
		if (this.gameState.getCurrentState() != State.RUNNING) {
			error = "Game is not running";
			return;
		}
		
		moveFromX = parseString2int(splittedInput[srcXPos]);
		moveFromY = parseString2int(splittedInput[srcYPos]);
		moveToX = parseString2int(splittedInput[destXPos]);
		moveToY = parseString2int(splittedInput[destYPos]);
						
		if (!field.isValidCoordinate(moveFromX,moveFromY) || !field.isValidCoordinate(moveToX,moveToY)){
			error = "Input not valid, coordinates not in field!: " + input;
			return;
		}
		
		Figure figure = field.getCellByCoordinates(moveFromX, moveFromY).getOccupier();

		if (figure == null) {
			error = "No figure selected!" + input;
			return;
		}
		
		if (!gameState.isHasMoreKills()) {
			createAllMoves();
		}
			
		if (validateSelectedMove(figure, moveToX, moveToY)){
			//possible move
			gameState.setHasMoreKills(move(figure, moveToX, moveToY));
		} else {
			if (!error.isEmpty()) {
				this.error += "\n";
			}
			this.error += String.format("Input was: %s", input);
			return;
		}
		
		if (!gameState.isHasMoreKills()) {
		    this.gameState.changeTurn();
		}
		
		this.gameState.incMoveCount();
	}
	
	private int parseString2int(String s) {
		final char tmp = s.charAt(0);
		int result;
		
		if (Character.isDigit(tmp)) {
			// just parse
			result = Integer.valueOf(s);
		} else {
			// A = 0
			result = Character.toUpperCase(tmp)- Field.ASCII_OFFSET;
		}
		return result;
	}
	
	private void botmove() {
		if (isBotsTurn() && !checkIfWin()) {
			try {
				Thread.sleep(BOT_DELAY);
			} catch (InterruptedException e) {
			    Logger.getGlobal().throwing(GameController.class.getName(), "botmove", e);
			}
			input(bot.move());
		}
	}
	
	/**
	 * Checks if a bot is selected (otherwise its a multiplayer game)
	 * and if the white player is on turn (bot is always white)
	 * @return true if bot is on turn
	 */
	private boolean isBotsTurn() {
	    if (!this.gameState.getBot().equals(Bot.NO_BOT) && !this.isBlackTurn()) {
	        return true;
	    }
	    return false;
	}
	
	private void parseControls(String input) {
		char control = input.charAt(0);
		
		switch (control) {
		case QUIT:
			this.gameState.setCurrentState(State.QUIT);
			break;
			
		case RESTART:
		    this.gameInit(this.field.getSize(), this.gameState.getBot());
			break;
			
		case NEW_GAME:
			this.gameState.setCurrentState(State.NEW_GAME);
			break;
			
		case BLACK_GAVE_UP:
		    blacks.clear();
		    break;
		    
		case WHITE_GAVE_UP:
		    whites.clear();
		    break;

		default:
			break;
		}
	}

	/**
     * @return if someone has won or not
     */	
	public boolean checkIfWin() {
		createAllMoves();
		List<Figure> list;
		boolean hasMoves = false;
		if(this.isBlackTurn()){
			list = blacks;
		} else {
			list = whites;
		}
		for(Figure figure : list){
			if (figure.getPossibleMoves().size() != 0) {
				hasMoves = true;
				break;
			}
		}
		if (blacks.isEmpty() || (list.equals(blacks) && !hasMoves)){
			this.info = "White wins! Congratulations!\n";
			return true;
		} else if (whites.isEmpty() || (list.equals(whites) && !hasMoves)){
			this.info = "Black wins! Congratulations!";
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
     * @return the figure on the field
     * @param x
     * @param y
     */
	public Figure getFigureOnField(int x, int y) {
		return field.getCellByCoordinates(x, y).getOccupier();
	}
	
	private boolean validateSelectedMove(Figure figure, int x, int y) {
		Move selectedMove = new Move(figure.getPosition(), field.getCellByCoordinates(x, y));
		if (figure.isBlack() && !this.isBlackTurn()){
			this.error = "Please select a white figure!";
			return false;
		} else if (!figure.isBlack() && this.isBlackTurn()){
			this.error = "Please select a black figure!";
			return false;
		} else if (figure.getPossibleMoves().contains(selectedMove)){
			//possible move
			return true;
		} else {
			this.error = "This is no possible move!";
			return false;
		}
	}
	
	private boolean move(Figure from,int toX,int toY) {
		Move dummyMove = new Move(from.getPosition(),new Cell(toX,toY));
		int index = from.getPossibleMoves().indexOf(dummyMove);
		Move currentMove = from.getPossibleMoves().get(index);
		from.setPosition(currentMove.getTo());
		crownFigureIfNeeded(from);
		if (!currentMove.isKill()){
			return false;
		} else {
			 Map<String,Integer> moveMap = currentMove.getCoordinatesLastSkipedCell();
			 Figure occupier = field.getCellByCoordinates(moveMap.get("X"), moveMap.get("Y")).getOccupier();
			 blacks.remove(occupier);
			 whites.remove(occupier);
			 occupier.kill();
			 createPossibleMoves(from);
			 if (from.hasKillMoves()){
				 deleteAllMovesWithoutFigure(from);
				 return true;
			 } else {
				 return false;
			 }
		}
	}
	
	private void deleteAllMovesWithoutFigure(Figure figure) {
		List<Figure> list;
		if (this.isBlackTurn()) {
			list = blacks;
		} else {
			list = whites;
		}
		for (Figure current : list) {
			if (!current.equals(figure)) {
				current.setPossibleMoves(new LinkedList<Move>());
			}
		}
	}
	
	
	/**
     * method to create all moves
     */
	public void createAllMoves() {
		if (this.isBlackTurn()) {
			createAllMoves(blacks);
		} else {
			createAllMoves(whites);
		}
	}
	
	private void createAllMoves(List<Figure> figures) {
		boolean mustkill = false;
		// create all Moves
		for (Figure figure : figures) {
			createPossibleMoves(figure);
		}
		// check if moves are must kills
		for (Figure figure : figures) {
			if (figure.hasKillMoves()) {
				mustkill = true;
				break;
			}
		}
		// remove regular moves if only must kills are allowed
		if (mustkill) {
			for (Figure figure : figures) {
				figure.removeNonkillMoves();
			}
		}
	}
	
	private void createPossibleMoves(Figure figure) {
		figure.setPossibleMoves(new LinkedList<Move>());
		if (figure.isCrowned()) {
			crownedMoves(figure);
		} else {
			regulareMoves(figure);
		}
		
	}
	
	private void regulareMoves(Figure figure) {
		if (figure.isBlack()) {
			lowerLeft.getPossibleMoves(figure);
			lowerRight.getPossibleMoves(figure);
		} else {
			upperLeft.getPossibleMoves(figure);
			upperRight.getPossibleMoves(figure);
		}
	}
	
	private void crownedMoves(Figure figure) {
		upperLeft.getPossibleMoves(figure);
		upperRight.getPossibleMoves(figure);
		lowerLeft.getPossibleMoves(figure);
		lowerRight.getPossibleMoves(figure);
	}
	
	private void crownFigureIfNeeded(Figure figure) {
		if(figure.isBlack() && figure.getPosition().getY() == 0){
			figure.setCrowned(true);
		}
		if(!figure.isBlack() && figure.getPosition().getY() == this.field.getSize()-1){
			figure.setCrowned(true);
		}
	}

	@Override
	public String getDrawingOfField() {
		return field.draw();
	}
	
	@Override
	public List<Move> getPossibleMoves() {
	    List<Figure> figures = whites;
	    List<Move> moves = new LinkedList<Move>();
	    
	    if (this.isBlackTurn()) {
	        figures = blacks;
	    }
	    
	    for (Figure f : figures) {
	        moves.addAll(f.getPossibleMoves());
	    }
	    
	    return moves;
	}
}
