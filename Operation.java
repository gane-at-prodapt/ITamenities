package ITamenities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface Operations{
	void evaluvate(Employee E) throws Exception;
}

public class Operation {
	
	private static Scanner sc = new Scanner(System.in);
	
	static List<String> keys = new ArrayList<String>();
	static List<Operations> values = new ArrayList<Operations>();
	
	static Operations move_in = new Operations() {
		@Override
		public void evaluvate(Employee E) throws Exception {
			
			if(E.currentFloor==null && E.currentRoom==null) {
				List<Floor> floors = sqlFloor.getFloors();
				
				System.out.println("Available Floors");
				int floor_count = 0;
				for(Floor F : floors) {
					System.out.println(++floor_count + ") "+ F.getName());
				}
				System.out.println("Enter floor to move in:");
				int floor_choice = sc.nextInt()-1;
				if(floor_choice<floors.size()) {
					Floor F = floors.get(floor_choice);
					boolean result = E.move_in(F);
					if(result) {
						System.out.println("Successfully moved into "+F.getName());
					}else {
						System.out.println("Error occured, try again!");
					}
				}else {
					System.out.println("Invalid Floor choice, try again!");
				}
			
				
			}else if(E.currentRoom==null) {
				System.out.println("Choose room to move in");
				List<Room> roomsInFloor = sqlRooms.getRoomsByFloor(E.currentFloor.getId());
				System.out.println("Current rooms in your floor: ");
				int count = 0;
				for(Room R : roomsInFloor) {
					System.out.println(++count +") "+R.name);
				}
				System.out.println("Enter room to move in:");
				int ind = sc.nextInt()-1;
				if(ind<roomsInFloor.size()) {
					boolean opResult = E.move_in(roomsInFloor.get(ind));
					if(opResult) {
						System.out.println("Successfully moved into "+roomsInFloor.get(ind).name);
					}else {
						System.out.println("Error occured, try again!");
					}
				}else {
					System.out.println("Invalid Room choice, try again!");
				}
			}
			
			
		}
	};
	
	static Operations move_out = new Operations() {
		@Override
		public void evaluvate(Employee E) throws Exception {
			Floor previousFloor = null;
			Room previousRoom = null; 
			if(E.currentRoom == null) {
				previousFloor = E.currentFloor;
				
			}else {
				previousRoom = E.currentRoom;
			}
			boolean opResult = E.move_out();
			if(opResult) {
				if(previousRoom==null) {
					System.out.println("Successfully moved out of "+previousFloor.getName());
				}else {
					System.out.println("Successfully moved out of "+previousRoom.name);
				}
			}else {
				System.out.println("Error occured, try again");
			}
		}
	};
	
	static Operations view_items_in_caf = new Operations() {
		@Override
		public void evaluvate(Employee E) throws Exception {
			Floor F = null;
			System.out.println("1) View Items in current Floor");
			System.out.println("2) View Items in other Floor");
			System.out.println("Enter your choice:");
			int choice = sc.nextInt();
			if(choice==1) {
				if(E.currentFloor!=null) {
					F=E.currentFloor;
				}else if(E.currentRoom!=null){
					F=E.currentRoom.currentFloor;
				}else {
					System.out.println("Enter into a floor first");
				}
				
			}else if(choice==2) {
				List<Floor> floors = sqlFloor.getFloors();
				System.out.println("Enter floor to search in: ");
				{
					int count =0;
					for(Floor floor : floors) {
						System.out.println(++count + ") "+floor.getName());
					}
				}
				int ind = sc.nextInt()-1;
				if(ind>=0 && ind<floors.size()) {
					F = floors.get(ind);
				}else {
					System.out.println("Enter a valid Floor choice!");
				}
			}else {
				System.out.println("Enter a valid choice");
			}
			
			if(F!=null) {
				List<Cafeteria> cafs = sqlRooms.getCafsByFloor(F.getId());
				for(Cafeteria C : cafs) {
					System.out.println("Items in "+C.name);
					int item_count = 0;
					List<Item> items = C.getItems();
					if(items.size()==0) {
						System.out.println("Sorry no items available right now, try again another time\n");
					}else {
						for(Item I : items){
							System.out.println(++item_count + ") "+ I.getName());
						}
						System.out.println();
					}
				}
			}
		}
	};
	
	static Operations view_current_occupancy = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			Floor F=null;
			System.out.println("1) View Room Occupancy in current Floor");
			System.out.println("2) View Room Occupancy in other Floor");
			System.out.println("Enter your choice:");
			int choice = sc.nextInt();
			if(choice==1) {
				if(E.currentFloor!=null) {
					F = E.currentFloor;
				}else if(E.currentRoom!=null){
					F=E.currentRoom.currentFloor;
				}else {
					System.out.println("Enter into a floor first");
				}
			}else if(choice==2) {
				List<Floor> floors = sqlFloor.getFloors();
				System.out.println("Enter floor to see in: ");
				{
					int count =0;
					for(Floor floor : floors) {
						System.out.println(++count + ") "+floor.getName());
					}
				}
				int ind = sc.nextInt()-1;
				if(ind>=0 && ind<floors.size()) {
					F = floors.get(ind);
				}else {
					System.out.println("Enter a valid floor choice!");
				}
			}else {
				System.out.println("Enter a valid choice");
			}
			if(F!=null) {
				List<Room> rooms = sqlRooms.getRoomsByFloor(F.getId());
				System.out.println("Rooms in "+ F.getName());
				for(Room R : rooms) {
					System.out.println("Room: "+R.name+"["+R.temp+"C]");
					System.out.println("Occupancy: "+ R.occupancy+"/"+R.max_occupancy+"\n");
				}
			}
		}
	};
	
	static Operations book_rooms = new Operations() {
		@Override
		public void evaluvate(Employee E) throws Exception {
			Floor F = null;
			
			System.out.println("1) Book Meeting Room in current Floor");
			System.out.println("2) Book Meeting Room in other Floor");
			System.out.println("Enter choice: ");
			int choice = sc.nextInt();
			if(choice==1) {
				if(E.currentFloor!=null) {
					F = E.currentFloor;
				}else if(E.currentRoom!=null){
					F = E.currentRoom.getcurrentFloor();
				}else {
					System.out.println("Enter into a floor first!");
				}
			}else if(choice==2) {
				List<Floor> floors = sqlFloor.getFloors();
				System.out.println("Enter floor to book in: ");
				{
					int count =0;
					for(Floor floor : floors) {
						System.out.println(++count + ") "+floor.getName());
					}
				}
				System.out.println("Enter choice: ");
				int floor_ind = sc.nextInt()-1;
				if(floor_ind<floors.size()) {
					F = floors.get(floor_ind);
				}else {
					System.out.println("Room choice invalid");
				}
			}else {
				System.out.println("Try again!");
			}
			if(F!=null) {
				List<Booking> rooms = sqlRooms.getmeetRoomsByFloor(F.getId());
				System.out.println("Rooms in "+ F.getName());
				int room_count = 0;
				for(Room R : rooms) {
					System.out.println(++room_count +") "+R.name+" [ max occupancy: "+R.max_occupancy+" ]");
				}
				System.out.println("Select room: ");
				int room_ind = sc.nextInt()-1;
				Booking R = rooms.get(room_ind);
				System.out.println("Enter from date(dd/mm/yyyy): ");
				Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(sc.next());
				System.out.println("Enter to date(dd/mm/yyyy): ");
				Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(sc.next());
				
				Registry reg = new Registry(R,fromDate,toDate,E);
				
				boolean result = sqlRegistry.bookRoom(reg);
				if(result) {
					System.out.println("Successfully booked");
				}else {
					System.out.println("Booking unsuccessful");
					//TODO: Request a booked room
				}
			}
		}
	};
	
	static Operations raise_request = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			String[] types = {"Software", "Pantry", "Hygiene", "Front-desk", "Travel"};
			System.out.println("Types of query:");
			int type_count =0;
			for(String t : types) {
				System.out.println(++type_count+ ") "+ t);
			}
			System.out.println("Enter choice: ");
			int tid = sc.nextInt()-1;
			if(tid<types.length) {
				String type = types[tid];
				System.out.println("Describe query: ");
				sc.nextLine();
				String desc = sc.nextLine()+" raised by "+E.name+" email: "+E.email;
				Query Q = new Query(type,desc,E);
				boolean result = sqlquery.addQuery(Q);
				if(result) {
					System.out.println("Request raised successfully");
				}else {
					System.out.println("Request failed successfully");
				}
			}else {
				System.out.println("Invalid choice, try again!");
			}
		}
		
	};
	
	static Operations view_my_queries = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			System.out.println("My Queries: ");
			List<Query> queries = sqlquery.getQueriesbyEmployee(E);
			int query_count =0;
			for(Query Q : queries) {
				System.out.println(++query_count + ") "+ Q.getDesc());
			}
			System.out.println(++query_count+") Exit");
			System.out.println("Select Query to close: ");
			int query_ind = sc.nextInt()-1;
			if(query_ind<queries.size()) {
				boolean result = sqlquery.deleteQueryById(queries.get(query_ind).getId());
				if(result) {
					System.out.println("Query successfully closed");
				}else {
					System.out.println("Problem occured, Try again!");
				}
			}else if(query_ind>queries.size()){
				System.out.println("Invalid option");
			}
			
		
		}
		
	};
	
	static Operations all_queries = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			System.out.println("My Queries: ");
			List<Query> queries = sqlquery.getQueriesbyType(E.type);
			int query_count =0;
			for(Query Q : queries) {
				System.out.println(++query_count + ") "+ Q.getDesc());
			}
			System.out.println(++query_count+") Exit");
			System.out.println("Select Query to work on: ");
			int query_ind = sc.nextInt()-1;
			if(query_ind<queries.size()) {
				boolean result = queries.get(query_ind).setAssignedTo(E);
				if(result) {
					System.out.println("Query assigned to you successfully");
				}else {
					System.out.println("Problem occured, Try again!");
				}
			}else if(query_ind>queries.size()) {
				System.out.println("Invalid option");
			}
			
		
		}
		
	};
	
	static Operations stock_item = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			
			System.out.println("Items details");
			System.out.println("Enter name: ");
			String name = sc.next();
			System.out.println("Enter quantity");
			int quantity = sc.nextInt();
			System.out.println("Enter date of expiry(dd/mm/yyyy): ");
			Date expiry = new SimpleDateFormat("dd/mm/yyyy").parse(sc.next());
			Cafeteria C = new Cafeteria(E.currentRoom);
			Item I = new Item(name,quantity,expiry,C);
			boolean result = sqlItem.addItem(I);
			if(result) {
				System.out.println("Successfully stocked "+I.getName()+" in "+C.name);
			}else {
				System.out.println("Problem occured, try again!");
			}
			
		}
			
	};
	
	static Operations restock_items = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			
			System.out.println("Items in "+E.currentRoom.name);
			int item_count = 0;
			List<Item> items = new Cafeteria(E.currentRoom).getItems(); 
			for(Item I : items) {
				System.out.println(++item_count + ") " +I.getName());
			}
			System.out.println("Select item to restock: ");
			int item_choice = sc.nextInt()-1;
			if(item_choice<items.size()) {
				Item I = items.get(item_choice);
				System.out.println("Enter quantity to add:");
				int quantity = sc.nextInt();
				I.setQuantity(I.getQuantity()+quantity);
				
			}else {
				System.out.println("Enter a valid item choice, Try again!");
			}
			
		}
			
	};

	static Operations get_a_snack = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			
			Cafeteria C = new Cafeteria(E.currentRoom);
			List<Item> items = C.getItems();
			if(items.size()==0) {
				System.out.println("Sorry, No Items available, Try raising a query to pantry department");
			}else {
				System.out.println("Available items");
				int itemCount = 0;
				for(Item i : items) {
					System.out.println(++itemCount +") "+ i.getName());
				}
				System.out.println("Select an item: ");
				int item_choice = sc.nextInt()-1;
				if(item_choice<items.size()) {
					
					Item I = items.get(item_choice);
					I.setQuantity(I.getQuantity()-1);
					System.out.println("Enjoy your "+I.getName());
					
				}else {
					System.out.println("Enter a valid item choice, Try again!");
				}

			}
		}
	};
	
	static Operations cancel_booking = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			
			List<Registry> registries = sqlRegistry.getRegistryByEmployee(E);
			if(registries.size()>0) {
				int reg_count=0;
				System.out.println("Meeting rooms booked by you");
				for(Registry R : registries) {
					System.out.println(++reg_count+") "+R.getRoom().name+" booked from "+R.getFrom()+" to "+R.getTo());
				}
				System.out.println(++reg_count+") exit");
				System.out.println("Select room to cancel: ");
				int choice = sc.nextInt()-1;
				if(choice<registries.size()) {
					Registry R = registries.get(choice); 
					boolean result = sqlRegistry.cancelRoom(R);
					if(result) {
						System.out.println("Room booking is successfully cancelled");
					}else {
						System.out.println("Problem Occured, Try again!");
					}
				}else if(choice>registries.size()){
					System.out.println("Enter a valid meeting-room choice");
				}
				
			}else {
				System.out.println("No Rooms booked, Use book rooms operation to book rooms");
			}
			
			
		}
		
	};
	
	static Operations delete_item = new Operations() {

		@Override
		public void evaluvate(Employee E) throws Exception {
			
			System.out.println("Items in "+E.currentRoom.name);
			int item_count = 0;
			List<Item> items = new Cafeteria(E.currentRoom).getItems(); 
			for(Item I : items) {
				System.out.println(++item_count + ") " +I.getName());
			}
			System.out.println("Select item to dispose: ");
			int item_choice = sc.nextInt()-1;
			if(item_choice<items.size()) {
				Item I = items.get(item_choice);
				boolean result = sqlItem.deleteItem(I);
				if(result) {
					System.out.println("Item disposed successfully");
				}else {
					System.out.println("Problem occured, try again!");
				}
				
			}else {
				System.out.println("Enter a valid item choice, Try again!");
			}
			
		}
		
	};
	
	static Operations change_temperature = new Operations() {
		@Override
		public void evaluvate(Employee E) throws Exception {
			Floor F = null;
			if(E.currentFloor==null) {
				F=E.currentRoom.currentFloor;
			}else {
				F=E.currentFloor;
			}
			List<Room> rooms = sqlRooms.getRoomsByFloor(F.getId());
			int room_count = 0;
			System.out.println("Rooms in floor: ");
			for(Room R : rooms) {
				System.out.println(++room_count+") "+R.name+"["+R.temp+"C]");
			}
			System.out.println("Select room to change temperature");
			int choice = sc.nextInt()-1;
			if(choice<rooms.size()) {
				Room R = rooms.get(choice);
				System.out.println("Enter temperature: ");
				double temp = sc.nextDouble();
				R.setTemp(temp);
				boolean result = sqlRooms.updateTemperature(R);
				if(result) {
					System.out.println("Temperature changed successfully");
				}else {
					System.out.println("Problem occured, try again!");
				}
			}else {
				System.out.println("Enter a valid room choice, Try again!");
			}
		}
	};
	
	
	
	static {
		keys.add("move in");
		values.add(move_in);
		keys.add("move out");
		values.add(move_out);
		keys.add("view items in caf");
		values.add(view_items_in_caf);
		keys.add("view a room occupancy");
		values.add(view_current_occupancy);
		keys.add("raise a request");
		values.add(raise_request);
		keys.add("view my queries");
		values.add(view_my_queries);
		keys.add("view all queries");
		values.add(all_queries);
	}
}
