package edu.pitt.cs;

import java.util.ArrayList;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	// TODO: Add more member variables and methods as needed.
	boolean gameOver = false;
	ArrayList<Room> rooms = new ArrayList<>();
	Room currentRoom = null;
	Player player;

	/**
	 * Constructor. Rooms are laid out from south to north, such that the
	 * first room in rooms becomes the southernmost room and the last room becomes
	 * the northernmost room. Initially, the player is at the southernmost room.
	 * 
	 * @param player Player for this game
	 * @param rooms  List of rooms in this game
	 */
	CoffeeMakerQuestImpl(Player player, ArrayList<Room> rooms) {
		this.rooms = rooms;
		this.player = player;
		this.currentRoom = rooms.get(0);
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * The method returns success if and only if: 1) Th southernmost room has a
	 * north door only, 2) The northernmost room has a south door only, and 3) The
	 * rooms in the middle have both north and south doors. If there is only one
	 * room, there should be no doors.
	 * 
     * Validates if rooms have correct door placement.
     * @param rooms ArrayList of Room objects
     * @return true if doors are correctly placed, false otherwise
     */
	public boolean areDoorsPlacedCorrectly() {
        if (rooms == null || rooms.isEmpty()) return false;

        for (int i = 0; i < rooms.size(); i++) {
            Room currRoom = rooms.get(i);

            if (i == 0) {
                // First (northernmost) room should have no north door
                if (currRoom.getNorthDoor() == null) return false;
				if (currRoom.getSouthDoor() != null) return false;
            } 

			else if (i == rooms.size() - 1) {
                // Last (southernmost) room should have no south door
                if (currRoom.getSouthDoor() == null) return false;
                if (currRoom.getNorthDoor() != null) return false;
            } 
			else {
                // Middle rooms should have both doors connected
                if (currRoom.getNorthDoor() == null) return false;
                if (currRoom.getSouthDoor() == null) return false;
            }
        }

        return true;
	}

	/**
	 * Checks whether each room has a unique adjective and furnishing.
	 * 
	 * @return true if check successful, false otherwise
	 */

	public boolean areRoomsUnique() {
		for(int i = 0; i < rooms.size(); i ++){

			String adj = rooms.get(i).getAdjective();
			String furn = rooms.get(i).getFurnishing();

			for(int j = 0; j < rooms.size(); j ++){

				String currAdj = rooms.get(j).getAdjective();
				String currFurn = rooms.get(j).getFurnishing();

				if(i != j && (adj == currAdj || furn == currFurn)){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */
	public Room getCurrentRoom() {
		return currentRoom;
	}

	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {
		for(int i = 0; i < rooms.size(); i ++){
			if(rooms.get(i) == room){
				currentRoom = rooms.get(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 * */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}

	/**
	 * A helper method for the "H" command. It returns the following help string:
	 * "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect
	 * any items in the room" + newline +
	 * "I - Show inventory of items collected" + newline + "D - Drink coffee made
	 * from items in inventory" + newline.
	 * 
	 * @return help string
	 */
	private String getHelpString() {
		return "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline + "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline;
	}

	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please try giving the "H" command in the
	 * solution jar. Make sure you use Player.getInventoryString() whenever you need
	 * to display the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
		String ret = "";
		switch(cmd){
			case "N":
				if(currentRoom.getNorthDoor() == null){
					ret = "A door in that direction does not exist." + newline;
				}
				else{
					for(int i = 0; i < rooms.size() - 1; i ++){
						if(rooms.get(i) == currentRoom){
							currentRoom = rooms.get(i + 1);
							return ret;
						}
					}
					ret = "A door in that direction does not exist." + newline;	
				}
				break;
			case "S":
				if(currentRoom.getSouthDoor() == null){
					ret = "A door in that direction does not exist." + newline;
				}
				else{
					for(int i = 1; i < rooms.size(); i ++){
						if(rooms.get(i) == currentRoom){
							setCurrentRoom(rooms.get(i - 1));
							ret = newline + currentRoom.getDescription() + newline + getInstructionsString();
						}
					}
					ret = "A door in that direction does not exist." + newline;
				}
				break;
			case "L":
				switch(currentRoom.getItem()){
					case COFFEE:
						ret = "There might be something here..." + newline + "You found some caffeinated coffee!" + newline;
						player.addItem(Item.COFFEE);
						break;
					case CREAM:
						ret = "There might be something here..." + newline + "You found some creamy cream!" + newline;
						player.addItem(Item.CREAM);
						break;
					case SUGAR:
						ret = "There might be something here..." + newline + "You found some sweet sugar!" + newline;
						player.addItem(Item.SUGAR);
						break;
					case NONE:
						ret = "You don't see anything out of the ordinary." + newline;
						break;
					default:
						break;
				}
				break;
			case "I":
				ret = player.getInventoryString();
				break;
			case "D":
				ret = player.getInventoryString() + newline;
				gameOver = true;
				if(player.hasItem(Item.CREAM) && player.hasItem(Item.SUGAR) && player.hasItem(Item.COFFEE)){
					ret += "You drink the beverage and are ready to study!" + newline + "You win!" + newline;
					return ret;
				}
				if(player.hasItem(Item.CREAM) == false && player.hasItem(Item.SUGAR) == false && player.hasItem(Item.COFFEE) == false){
					ret += "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline;
					return ret;
				}
				else{
					ret += "You refuse to drink this half-made sludge. You cannot study." + newline + "You lose!" + newline;
				}
				break;
			case "H":
				ret = getHelpString();
				return ret;
			default:
				break;
	
		}
		return ret;
	}
}
