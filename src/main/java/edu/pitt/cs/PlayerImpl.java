package edu.pitt.cs;
import java.util.ArrayList;

class PlayerImpl implements Player {
	ArrayList<Item> inventory = new ArrayList<>();
	// TODO: Add more member variables and methods as needed.
	
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	public PlayerImpl() {
			
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}

	public boolean hasItem(Item item) {
		for(int i = 0; i < inventory.size(); i ++){
			if(inventory.get(i) == item){
				return true;
			}
		}
		return false;
	}

	public String getInventoryString() {
		String ret;

		boolean coffee = false;
		boolean cream = false;
		boolean sugar = false;

		for(int i = 0; i < inventory.size(); i ++){
			if(inventory.get(i) == Item.COFFEE){
				coffee = true;
			}
			if(inventory.get(i) == Item.CREAM){
				cream = true;
			}
			if(inventory.get(i) == Item.SUGAR){
				sugar = true;
			}
		}

		if(coffee){
			ret = "You have a cup of delicious coffee." + newline;
		}
		else{
			ret = "YOU HAVE NO COFFEE!" + newline;
		}
		if(cream){
			ret += "You have some fresh cream." + newline;
		}
		else{
			ret += "YOU HAVE NO CREAM!" + newline;
		}
		if(sugar){
			ret += "You have some tasty sugar." + newline;
		}
		else{
			ret += "YOU HAVE NO SUGAR!" + newline;
		}
		return ret;
	}
}
