package de.htwg.checkers.controller;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesLowerRight;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperLeft;
import de.htwg.checkers.controller.possiblemoves.PossibleMovesUpperRight;
import de.htwg.checkers.models.Cell;
import de.htwg.checkers.models.Field;
import de.htwg.checkers.models.Figure;
import de.htwg.checkers.models.Move;
import de.htwg.checkers.util.observer.Observable;

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
	
	@Inject
	public GameController(@Named("size") int size) {		
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
		this.error = null;
		this.hasMoreKills = false;
	}
	
	public Field getField() {
		return this.field;
	}
	
	public int getFieldSize() {
		return this.size;
	}

	public boolean isBlackTurn() {
		return blackTurn;
	}
	
	public String getError() {
		return error;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void changeColor() {
		blackTurn = !blackTurn;
	}

	public void gameInit() {
		whites = new LinkedList<Figure>();
		blacks = new LinkedList<Figure>();
		createBlackFigures();
		createWhiteFigures();
		// black starts
		blackTurn = true;
		moveCount = 0;
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
	
	public boolean input(String input) {
		
		int moveFromX, moveFromY, moveToX, moveToY;
		StringBuilder sb = new StringBuilder();
		String[] splitInput = input.split(" ");
		
		if (splitInput.length != 4) {
			error = "Input to short, must be fromX formY toX toY: " + input;
			notifyObservers();
			return true;
		}
		
		moveFromX = Integer.valueOf(splitInput[0]);
		moveFromY = Integer.valueOf(splitInput[1]);
		moveToX = Integer.valueOf(splitInput[2]);
		moveToY = Integer.valueOf(splitInput[3]);
						
		if (!isValidCoordinate(moveFromX,moveFromY) || !isValidCoordinate(moveToX,moveToY)){
			error = "Input not valid, coordinates not in field!: " + input;
			notifyObservers();
			return true;
		}
		
		Figure figure = field.getCellByCoordinates(moveFromX, moveFromY).getOccupier();

		if (figure == null) {
			error = "No figure selected!" + input;
			notifyObservers();
			return true;
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
			return true;
		}
		
		if (!hasMoreKills) {
			changeColor();
		}
		
		error = null;
		increaseMoveCount();
		notify();
		return checkIfWin(sb);
	}
	
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
	
	public Figure getFigureOnField(int x, int y) {
		return field.getCellByCoordinates(x, y).getOccupier();
	}
	
	public boolean isValidCoordinate(int x, int y) {
		return field.isValidCoordinate(x, y);
	}
	
	public boolean validateSelectedMove(Figure figure, StringBuilder stringOutput, int x, int y) {
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
	
	public boolean isAFigureSelected (Figure figure, StringBuilder stringOutput) {
		if (figure == null){
			stringOutput.delete(0, stringOutput.length());
			stringOutput.append("No figure selected!");
			return false;
		} else {
			return true;
		}
	}
	
	public boolean move(Figure from,int toX,int toY) {
		Move dummyMove = new Move(from.getPosition(),new Cell(toX,toY));
		int index = from.getPossibleMoves().indexOf(dummyMove);
		Move currentMove = from.getPossibleMoves().get(index);
		from.setPosition(currentMove.getTo());
		crownFigureIfNeeded(from);
		if (!currentMove.isKill()){
			changeColor();
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
				 changeColor();
				 return false;
			 }
		}
	}
	
	public void deleteAllMovesWithoutFigure(Figure figure) {
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
	
	public void crownFigureIfNeeded(Figure figure) {
		if(figure.isBlack() && figure.getPosition().getY() == 0){
			figure.setCrowned(true);
		}
		if(!figure.isBlack() && figure.getPosition().getY() == size-1){
			figure.setCrowned(true);
		}
	}
}
