package de.htwg.checkers.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import de.htwg.checkers.controller.FieldController;

public class FieldControllerTest {

	@Test
	public void methodTest(){
		FieldController fc = new FieldController();
		fc.gameInit();
		assertNotNull(fc);
	}
}
