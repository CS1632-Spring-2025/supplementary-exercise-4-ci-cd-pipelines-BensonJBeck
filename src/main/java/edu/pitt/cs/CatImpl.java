package edu.pitt.cs;

public class CatImpl implements Cat {

	String c_name;
	int c_id;
	boolean rented;

	public CatImpl(int id, String name) {
		c_id = id;
		c_name = name;
		rented = false;
	}

	public void rentCat() {
		rented = true;
	}

	public void returnCat() {
		rented = false;
	}

	public void renameCat(String name) {
		c_name = name;
	}

	public String getName() {
		return c_name;
	}

	public int getId() {
		return c_id;
	}

	public boolean getRented() {
		return rented;
	}

	public String toString() {
		return "ID " + c_id + ". " + c_name;
	}

}
