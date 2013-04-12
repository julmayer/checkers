package controller;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldControllerTest {

	@Test
	public void methodTest(){
		FieldController fc = new FieldController();
		fc.gameInit();
		assertNotNull(fc);
	}
}
