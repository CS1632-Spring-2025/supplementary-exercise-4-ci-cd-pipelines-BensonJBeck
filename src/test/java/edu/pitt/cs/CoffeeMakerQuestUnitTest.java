package edu.pitt.cs;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoffeeMakerQuestUnitTest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	CoffeeMakerQuest cmq;
	Player player;
	ArrayList<Room> rooms = new ArrayList<>();
	ByteArrayOutputStream out;
	PrintStream stdout;

	@Before
	public void setup() {
		// 1. Create a Player with no items (no coffee, no cream, no sugar) and assign to player.
		// GradeScope relies on you to use the Player.createInstance interface to do this.
		player = Player.createInstance(InstanceType.MOCK);

		// 2. Create 6 rooms exactly as specified in rooms.config and add to rooms list.
		// You are expected to hard-code the room configurations, as that is the test fixture.
		// GradeScope relies on you to use the Room.createInstance interface to do this.

		rooms.add(Room.createInstance(InstanceType.MOCK, "Quaint sofa", "Small", Item.CREAM, "Magenta", null));

		rooms.add(Room.createInstance(InstanceType.MOCK, "Sad record player", "Funny", Item.NONE, "Beige", "Massive"));

		rooms.add(Room.createInstance(InstanceType.MOCK, "Tight pizza", "Refinanced", Item.COFFEE, "Dead", "Smart"));

		rooms.add(Room.createInstance(InstanceType.MOCK, "Flat energy drink", "Dumb", Item.NONE, "Vivacious", "Slim"));

		rooms.add(Room.createInstance(InstanceType.MOCK, "Beautiful bag of money", "Bloodthirsty", Item.NONE, "Purple", "Sandy"));

		rooms.add(Room.createInstance(InstanceType.MOCK, "Perfect air hockey table", "Rough", Item.SUGAR, null, "Minimalist"));

		// 3. Create a CoffeeMakerQuest object using player and rooms and assign to cmq.
		// GradeScope relies on you to use the CoffeeMakerQuest.createInstance interface to do this.
		cmq = CoffeeMakerQuest.createInstance(InstanceType.IMPL, player, rooms);
		stdout = System.out;
		out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() {
		System.setOut(stdout);
	}

	/**
	 * Test case for String getHelpString().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getHelpString().
	 * Postconditions: Return value is:
	 *                 "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline +
	 *                 "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline.
	 * </pre>
	 */
	@Test
	public void testGetHelpString() throws Exception{
		// TODO: Fill in
		Method getHelp = cmq.getClass().getDeclaredMethod("getHelpString");
		getHelp.setAccessible(true);
		String result = (String) getHelp.invoke(cmq);
		assertEquals("N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline + "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline, result);
	}

	/**
	 * Test case for Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testGetCurrentRoom() {
		assertEquals(rooms.get(0), cmq.getCurrentRoom());
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(2)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(2)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(2).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom() {
		assertTrue(cmq.setCurrentRoom(rooms.get(2)));
		assertEquals(rooms.get(2), cmq.getCurrentRoom());
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check succeeds.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is true.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectly() {
		assertTrue(cmq.areDoorsPlacedCorrectly());
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(3) is modified so that it has no south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyMissingSouthDoor() {
		Room tempRoom = Room.createInstance(InstanceType.MOCK, "Flat energy drink", "Dumb", Item.NONE, "Vivacious", null);
		rooms.set(3, tempRoom);
		assertFalse(cmq.areDoorsPlacedCorrectly());
	}

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(2) is modified so that its adjective is modified to "Small".
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is false.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueDuplicateAdjective() {
		Room tempRoom = Room.createInstance(InstanceType.MOCK, "Tight pizza", "Small", Item.COFFEE, "Dead", "Smart");
		rooms.set(2, tempRoom);
		assertFalse(cmq.areRoomsUnique());
	}

	/**
	 * Test case for String processCommand("I").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testProcessCommandI() {
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline);
		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline, cmq.processCommand("I"));
	}

	/**
	 * Test case for String processCommand("l").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is: "There might be something here..." + newline + "You found some creamy cream!" + newline.
	 *                 Cream is added to player.
	 * </pre>
	 */
	@Test
	public void testProcessCommandLCream() {
		assertEquals("There might be something here..." + newline + "You found some creamy cream!" + newline, cmq.processCommand("L"));
		verify(player, times(1)).addItem(Item.CREAM);
	}

	/**
	 * Test case for String processCommand("n").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                cmq.setCurrentRoom(rooms.get(3)) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(4).
	 * </pre>
	 */
	@Test
	public void testProcessCommandN() {
		cmq.setCurrentRoom(rooms.get(3));
		assertEquals("", cmq.processCommand("n"));
		assertEquals(rooms.get(3).getDescription(), cmq.getCurrentRoom().getDescription());
	}

	/**
	 * Test case for String processCommand("s").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is: "A door in that direction does not exist." + newline.
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testProcessCommandS() {
		assertEquals("A door in that direction does not exist." + newline, cmq.processCommand("S"));
		assertEquals(rooms.get(0).getDescription(), cmq.getCurrentRoom().getDescription());
	}

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	 *                    "YOU HAVE NO SUGAR!" + newline + newline + "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLose() {
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline);

		when(player.hasItem(Item.CREAM)).thenReturn(false);
		when(player.hasItem(Item.COFFEE)).thenReturn(false);
		when(player.hasItem(Item.SUGAR)).thenReturn(false);	

		assertEquals( "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline + newline + "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline, cmq.processCommand("D"));
		assertTrue(cmq.isGameOver());
	}

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	 *                    "You have some tasty sugar." + newline + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDWin() {

		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + "You have some tasty sugar." + newline);
		
		when(player.hasItem(Item.CREAM)).thenReturn(true);
		when(player.hasItem(Item.COFFEE)).thenReturn(true);
		when(player.hasItem(Item.SUGAR)).thenReturn(true);

		assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + "You have some tasty sugar." + newline + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline, cmq.processCommand("D"));
		assertTrue(cmq.isGameOver());
	}

	@Test
	public void testPartialInv(){
		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + "YOU HAVE NO SUGAR!" + newline);
		when(player.hasItem(Item.CREAM)).thenReturn(true);
		when(player.hasItem(Item.COFFEE)).thenReturn(true);
		when(player.hasItem(Item.SUGAR)).thenReturn(false);
		assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + "YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + "You lose!" + newline, cmq.processCommand("D"));
	}

	@Test
	public void testGetHelp(){
		assertEquals("N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline + "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline, cmq.processCommand("H"));
	}
	@Test
	public void testNorthNull(){
		cmq.setCurrentRoom(rooms.get(5));
		assertEquals("A door in that direction does not exist." + newline, cmq.processCommand("N"));
	}
	@Test
	public void testSouthNull(){
		cmq.setCurrentRoom(rooms.get(0));
		assertEquals("A door in that direction does not exist." + newline, cmq.processCommand("S"));
	}

	@Test
	public void testTravelSouth(){
		cmq.setCurrentRoom(rooms.get(3));
		cmq.processCommand("S");
		assertEquals(rooms.get(2).getDescription(), cmq.getCurrentRoom().getDescription());
	}

	@Test
	public void testPickupNone(){
		cmq.setCurrentRoom(rooms.get(1));
		assertEquals("You don't see anything out of the ordinary." + newline, cmq.processCommand("L"));
	}
}
