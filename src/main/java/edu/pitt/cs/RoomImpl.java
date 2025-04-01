package edu.pitt.cs;

public class RoomImpl implements Room {

	private String furnishing;
	private String adjective;
	private Item item;
	private String northDoor;
	private String southDoor;
	
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	/**
	 * Constructor. The northDoor or the southDoor can be null if there no doors leading north or south.
	 * 
	 * @param furnishing Furnishing in the room
	 * @param adjective Adjective describing the room
	 * @param item Item present in the room
	 * @param northDoor Description of north door (null if there is no north door)
	 * @param southDoor Description of south door (null if there is no south door)
	 */
	public RoomImpl(String furnishing, String adjective, Item item, String northDoor, String southDoor) {
		this.furnishing = furnishing;
		this.adjective = adjective;
		this.item = item;
		this.northDoor = northDoor;
		this.southDoor = southDoor;
	}
	
	public String getFurnishing() {
		return this.furnishing;
	}

	public String getAdjective() {
		return this.adjective;
	}

	public Item getItem() {
		return this.item;
	}

	public String getNorthDoor() {
		return this.northDoor;
	}

	public String getSouthDoor() {
		return this.southDoor;
	}

	public String getDescription() {
		// north and south door
		if(this.northDoor != null && this.southDoor != null){
			return "You see a " + this.adjective + " room." + newline + "It has a " + this.furnishing + "." + newline + "A " + this.northDoor + " door leads North." + newline + "A " + this.southDoor + " door leads South." + newline;
		}
		// only south door
		if(this.northDoor == null && this.southDoor != null){
			return "You see a " + this.adjective + " room." + newline + "It has a " + this.furnishing + "." + newline + "A " + this.southDoor + " door leads South." + newline;
		}
		// only north door 
		if(this.northDoor != null && this.southDoor == null){
			return "You see a " + this.adjective + " room." + newline + "It has a " + this.furnishing + "." + newline + "A " + this.northDoor + " door leads North." + newline; 
		}
		// no doors
		if(this.northDoor == null && this.southDoor == null){
			return "You see a " + this.adjective + " room." + newline + "It has a " + this.furnishing + "." + newline;
		}
		return "";
	}
}
