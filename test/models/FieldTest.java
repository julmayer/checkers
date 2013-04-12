package models;

import static org.junit.Assert.*;

import org.junit.Test;

public class FieldTest {

	@Test
	public void createInstance() {
		Field field = new Field();
		assertNotNull(field);
		assertNotNull(field.getField());
	}

}
