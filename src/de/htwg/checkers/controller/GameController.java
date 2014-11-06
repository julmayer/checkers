package de.htwg.checkers.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.htwg.checkers.controller.bot.Bot;
import de.htwg.checkers.controller.bot.IBot;
import de.htwg.checkers.controller.bot.MediumBot;
import de.htwg.checkers.controller.bot.SimpleBot;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerRight;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperRight;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Move;
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
	private Field field;
	private int fieldSize;
	private List<Figure> blacks;
	private List<Figure> whites;
	private boolean blackTurn;
	private int moveCount;
	private PossibleMovesLowerLeft lowerLeft;
	private PossibleMovesLowerRight lowerRight;
	private PossibleMovesUpperLeft upperLeft;
	private PossibleMovesUpperRight upperRight;
	private String error;
	private String info;
	private boolean hasMoreKills;
	private boolean singleplayer;
	private IBot bot;
	private boolean quit = false;
	private State currentState;
	
	/**
     * Construcor for the gamecontroller
     * @param fieldSize
     */
    @Inject
	public GameController() {		
		currentState = State.NEW_GAME;
	}
	
    /**
     * @return the gamefield
     */
	public Field getField() {
		return new Field(this.field);
	}
	
	/**
     * @return the gamefieldsize
     */
	public int getFieldSize() {
		return this.fieldSize;
	}

	/**
     * @return if its blackturn or not
     */
	public boolean isBlackTurn() {
		return blackTurn;
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
		return this.currentState;
	}
	
	/**
     * @return the actual move count
     */
	public int getMoveCount() {
		return moveCount;
	}
	
	/**
     * method to do a game init
     */
	public void gameInit(int size, boolean singleplayer, Bot difficulty) {
		if (size < MIN_GAMEFIELD_SIZE){
			throw new IllegalArgumentException(String.format("Minimum size is %d!", MIN_GAMEFIELD_SIZE));
		}

		final int rowsToFill = size/2 - 1;
		this.fieldSize = size;
		this.field = new Field(size);
		this.lowerLeft = new PossibleMovesLowerLeft(field);
		this.lowerRight = new PossibleMovesLowerRight(field);
		this.upperLeft = new PossibleMovesUpperLeft(field);
		this.upperRight = new PossibleMovesUpperRight(field);
		this.singleplayer = singleplayer;
		whites = new LinkedList<Figure>();
		blacks = new LinkedList<Figure>();
		createBlackFigures(rowsToFill);
		createWhiteFigures(rowsToFill);
		// black starts
		blackTurn = true;
		moveCount = 0;
		error = "";
		
		switch (difficulty) {
			case SIMPLE_BOT :
				this.bot = new SimpleBot(whites);
				break;
			case MEDIUM_BOT:
				this.bot = new MediumBot(whites);
				break;
			default:
				this.bot = new SimpleBot(whites);
				break;					
		}
		this.currentState = State.RUNNING;
	}
	
	private void createWhiteFigures(int rowsToFill) {
		for (int y = 0; y < rowsToFill; y++){
			fillRow(y, false);
		}
	}
	
	private void createBlackFigures(int rowsToFill) {
		for (int y = fieldSize - rowsToFill;y < fieldSize; y++){
			fillRow(y, true);
		}
	}
	
	private void fillRow(int y, boolean isBlack) {
		for (int x = 0; x < fieldSize; x++) {
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
		botmove();
		return this.quit;
	}
	
	private void parseGameInit(final String input) {
		final int fieldSizePos = 0, playModePos = 1, botLevelPos = 2;

		final String[] splitInput = input.split(" ");
		
		final int fieldSize = Integer.valueOf(splitInput[fieldSizePos]);
		final boolean singleplayer = splitInput[playModePos].equals("S");
		Bot bot = Bot.NO_BOT;
		if (singleplayer) {
			bot = Bot.valueOf(Integer.valueOf(splitInput[botLevelPos]));
		}
		
		gameInit(fieldSize, singleplayer, bot);
	}
	
	private void parseMove(String input) {
		final int srcXPos = 0, srcYPos = 1, destXPos = 2, destYPos = 3;
		int moveFromX, moveFromY, moveToX, moveToY;
		final String[] splittedInput = input.split(" ");
		
		if (this.currentState != State.RUNNING) {
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
		
		if (!hasMoreKills) {
			createAllMoves();
		}
			
		if (validateSelectedMove(figure, moveToX, moveToY)){
			//possible move
			hasMoreKills = move(figure, moveToX, moveToY);
		} else {
			if (!error.isEmpty()) {
				this.error += "\n";
			}
			this.error += String.format("Input was: %s", input);
			return;
		}
		
		if (!hasMoreKills) {
			blackTurn = !blackTurn;
		}
		
		moveCount++;
		//botmove();
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
		if (!checkIfWin() && singleplayer && !blackTurn) {
			try {
				Thread.sleep(BOT_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			input(bot.move());
		}
	}
	
	private void parseControls(String input) {
		char control = input.charAt(0);
		
		switch (control) {
		case QUIT:
			this.currentState = State.QUIT;
			this.quit = true;
			break;
			
		case RESTART:
			this.gameInit(this.fieldSize, this.singleplayer, this.bot.getDifficulty());
			break;
			
		case NEW_GAME:
			this.currentState = State.NEW_GAME;
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
		if(blackTurn){
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
		if (figure.isBlack() && !blackTurn){
			this.error = "Please select a white figure!";
			return false;
		} else if (!figure.isBlack() && blackTurn){
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
		if (blackTurn) {
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
		if (blackTurn) {
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
		if(!figure.isBlack() && figure.getPosition().getY() == fieldSize-1){
			figure.setCrowned(true);
		}
	}

	@Override
	public String getDrawingOfField() {
		return field.draw();
	}
}
