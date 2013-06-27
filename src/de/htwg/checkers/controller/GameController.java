package de.htwg.checkers.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.htwg.checkers.controller.bot.IBot;
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
 *
 * @author Julian Mayer, Marcel Loevenich
 */
public class GameController extends Observable implements IGameController {
	
	private Field field;
	private final int rowsToFill;
	private int size;
	private List<Figure> blacks;
	private List<Figure> whites;
	private boolean blackTurn;
	private int moveCount;
	private PossibleMovesLowerLeft lowerLeft;
	private PossibleMovesLowerRight lowerRight;
	private PossibleMovesUpperLeft upperLeft;
	private PossibleMovesUpperRight upperRight;
	private String error;
	private boolean hasMoreKills;
	private boolean singelplayer;
	private IBot bot;
	private int difficulty;
	
	/**
     * Construcor for the gamecontroller
     * @param size
     */
    @Inject
	public GameController(@Named("size") int size, @Named("onePlayer") boolean singelplayer,
			@Named("difficulty") int difficulty) {		
		final int minSize = 4;
		if (size < minSize){
			throw new IllegalArgumentException("Minimun size is 4!");
		} else {
			rowsToFill = size/2 - 1;
		}
		field = new Field(size);
		this.lowerLeft = new PossibleMovesLowerLeft(field);
		this.lowerRight = new PossibleMovesLowerRight(field);
		this.upperLeft = new PossibleMovesUpperLeft(field);
		this.upperRight = new PossibleMovesUpperRight(field);
		this.size = size;
		this.hasMoreKills = false;
		this.singelplayer = singelplayer;
		this.difficulty = difficulty;
		
	}
	
    /**
     * @return the gamefield
     */
	public Field getField() {
		return this.field;
	}
	
	/**
     * @return the gamefieldsize
     */
	public int getFieldSize() {
		return this.size;
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
	
	/**
     * @return the actual move count
     */
	public int getMoveCount() {
		return moveCount;
	}
	
	public List<Figure> getWhites() {
		return whites;
	}
	
	public List<Figure> getBlacks() {
		return blacks;
	}

	/**
     * method to do a game init
     */
	public void gameInit() {
		whites = new LinkedList<Figure>();
		blacks = new LinkedList<Figure>();
		createBlackFigures();
		createWhiteFigures();
		// black starts
		blackTurn = true;
		moveCount = 0;
		error = null;
		
		switch (difficulty) {
			case 0:
				this.bot = new SimpleBot(whites);
				break;
			default:
				this.bot = new SimpleBot(whites);
				break;					
		}
	}
	
	private void createWhiteFigures() {
		for (int y = 0; y < rowsToFill; y++){
			fillRow(y,false);
		}
	}
	
	private void createBlackFigures() {
		for (int y = size - rowsToFill;y < size; y++){
			fillRow(y,true);
		}
	}
	
	private void fillRow(int y, boolean isBlack) {
		for (int x = 0; x < size; x++) {
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
	
	public boolean input(String input) {
		
		int moveFromX, moveFromY, moveToX, moveToY;
		StringBuilder sb = new StringBuilder();
		String[] splitInput = input.split(" ");
		final int four = 4;
		
		if (splitInput.length != four) {
			error = "Input to short, must be fromX formY toX toY: " + input;
			notifyObservers();
			return false;
		}
		
		final int three = 3;
		moveFromX = Integer.valueOf(splitInput[0]);
		moveFromY = Integer.valueOf(splitInput[1]);
		moveToX = Integer.valueOf(splitInput[2]);
		moveToY = Integer.valueOf(splitInput[three]);
						
		if (!field.isValidCoordinate(moveFromX,moveFromY) || !field.isValidCoordinate(moveToX,moveToY)){
			error = "Input not valid, coordinates not in field!: " + input;
			notifyObservers();
			return false;
		}
		
		Figure figure = field.getCellByCoordinates(moveFromX, moveFromY).getOccupier();

		if (figure == null) {
			error = "No figure selected!" + input;
			notifyObservers();
			return false;
		}
		
		if (!hasMoreKills) {
			createAllMoves();
		}
			
		if (validateSelectedMove(figure, sb, moveToX, moveToY)){
			//possible move
			hasMoreKills = move(figure, moveToX, moveToY);
		} else {
			error = sb.toString() + input;
			notifyObservers();
			return false;
		}
		
		if (!hasMoreKills) {
			blackTurn = !blackTurn;
		}
		
		error = null;
		moveCount++;
		notifyObservers();
		if (!checkIfWin(sb) && singelplayer && !blackTurn) {
			input(bot.move());
		}
		return checkIfWin(sb);
	}

	/**
     * @return if someone has won or not
     */	
	public boolean checkIfWin(StringBuilder stringOutput) {
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
		
		if (blacks.size() == 0 || (list.equals(blacks) && !hasMoves)){
			stringOutput.append("White wins! Congratulations!");
			return true;
		} else if (whites.size() == 0 || (list.equals(whites) && !hasMoves)){
			stringOutput.append("Black wins! Congratulations!");
			return true;
		} else {
			return false;
		}
	}
	
	/**
     * @return the figure on the field
     * @param x
     * @param y
     */
	public Figure getFigureOnField(int x, int y) {
		return field.getCellByCoordinates(x, y).getOccupier();
	}
	
	private boolean validateSelectedMove(Figure figure, StringBuilder stringOutput, int x, int y) {
		Move selectedMove = new Move(figure.getPosition(), field.getCellByCoordinates(x, y));
		if (figure.isBlack() && !blackTurn){
			stringOutput.append("Please select a white figure!");
			return false;
		} else if (!figure.isBlack() && blackTurn){
			stringOutput.append("Please select a black figure!");
			return false;
		} else if (figure.getPossibleMoves().contains(selectedMove)){
			//possible move
			return true;
		} else {
			stringOutput.append("This is no possible move!");
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
		if(!figure.isBlack() && figure.getPosition().getY() == size-1){
			figure.setCrowned(true);
		}
	}
}
