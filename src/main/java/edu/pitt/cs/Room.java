package edu.pitt.cs;

import static org.mockito.Mockito.when;

import org.mockito.*;

public interface Room {
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")
	
	public static Room createInstance(InstanceType type, String furnishing, String adjective, Item item,
			String northDoor, String southDoor) {
		switch (type) {
			case IMPL:
				return new RoomImpl(furnishing, adjective, item, northDoor, southDoor);
			case BUGGY:
				return new RoomBuggy(furnishing, adjective, item, northDoor, southDoor);
			case SOLUTION:
				return new RoomSolution(furnishing, adjective, item, northDoor, southDoor);
			case MOCK:
				Room mockRoom = Mockito.mock(Room.class);
				when(mockRoom.getFurnishing()).thenReturn(furnishing);
				when(mockRoom.getAdjective()).thenReturn(adjective);
				when(mockRoom.getItem()).thenReturn(item);
				when(mockRoom.getNorthDoor()).thenReturn(northDoor);
				when(mockRoom.getSouthDoor()).thenReturn(southDoor);
				if(northDoor != null && southDoor != null){
					when(mockRoom.getDescription()).thenReturn("You see a " + adjective + " room." + newline + "It has a " + furnishing + "." + newline + "A " + northDoor + " door leads North." + newline + "A " + southDoor + " door leads South." + newline);
				}
				// only south door
				if(northDoor == null && southDoor != null){
					when(mockRoom.getDescription()).thenReturn("You see a " + adjective + " room." + newline + "It has a " + furnishing + "." + newline + "A " + southDoor + " door leads South." + newline);
				}
				// only north door 
				if(northDoor != null && southDoor == null){
					when(mockRoom.getDescription()).thenReturn("You see a " + adjective + " room." + newline + "It has a " + furnishing + "." + newline + "A " + northDoor + " door leads North." + newline); 
				}
				// no doors
				if(northDoor == null && southDoor == null){
					when(mockRoom.getDescription()).thenReturn("You see a " + adjective + " room." + newline + "It has a " + furnishing + "." + newline);
				}
				return mockRoom;
			default:
				assert (false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.

	public String getFurnishing();

	public String getAdjective();

	public Item getItem();

	public String getNorthDoor();

	public String getSouthDoor();

	public String getDescription();
}
