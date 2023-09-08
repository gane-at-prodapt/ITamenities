package ITamenities;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;




public class Employee {
	
	
	
	int id;
	String name;
	String email;
	String type;
	Floor currentFloor;
	Room currentRoom;
	String role;
	String comm;
	
	public Employee(String name,String email, String type, String role) {
		this.name = name;
		this.email = email;
		this.type = type;
		this.role = role;
	}
	
	public Employee(int id, String name, String type, String role, String comm,Room currentRoom,Floor currentFloor, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.type = type;
		this.role = role;
		this.comm = comm;
		this.currentRoom = currentRoom;
		this.currentFloor = currentFloor;
	}
	
	
	//TODO: employee methods
	public boolean move_in(Room R) throws Exception {
		this.currentRoom = R;
		this.currentRoom.setOccupancy(this.currentRoom.occupancy+1);
		this.currentFloor = null;
		return sqlEmployee.updateEmployee(this);
		
	}
	public boolean move_in(Floor F) throws Exception {
		this.currentFloor=F;
		return sqlEmployee.updateEmployee(this);
		
	}
	public boolean move_out() throws Exception {
		if(this.currentRoom!=null) {
			this.currentRoom.setOccupancy(this.currentRoom.occupancy-1);
			this.currentFloor = this.currentRoom.currentFloor;
			this.currentRoom=null;
		}else {
			this.currentFloor=null;
		}
		return sqlEmployee.updateEmployee(this);
	}
	public ArrayList<Room> getRooms(){
		return null;
		
	}
	public ArrayList<Room> getRoomsOccupancy(){
		return null;
	}
	public void bookRoom(Room R) {
		
	}
	public ArrayList<Cafeteria> getCafs(){
		return null;
	}
	public void raiseRequest(Query Q) {
		
	}
	
	public ArrayList<Query> getQueries(){
		return null;
	}
		
	static Map<String,Operations> ops = new HashMap<String,Operations>();

	public static void main(String args[]) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		Employee E = null;
		do {
			System.out.println("Enter Credentials\n");
			System.out.println("Email Id: ");
			String emailId = sc.next();
			System.out.println("Password: ");
			String password = sc.next();
			
			E = Auth.signIn(emailId, Auth.toHexString(Auth.getSHA(password)));
			
			
			if(E!=null) {
				boolean logged_in = true;
				System.out.println("\nEmployee portal");
				System.out.println("Name: "+E.name);
				System.out.println("Email: "+E.email);
				System.out.println("Type: "+E.type);
				System.out.println("Role: "+E.role);
				while(logged_in) {
					
					List<String> keys = new ArrayList<String>(Operation.keys);
					List<Operations> values = new ArrayList<Operations>(Operation.values);
					if(E.role.equals("Team Lead") || E.role.equals("Manager")) {
						keys.add("Book a meeting room");
						values.add(Operation.book_rooms);
						keys.add("Cancel Booking");
						values.add(Operation.cancel_booking);
					}
					System.out.println("\nEmployee current Position");
					
					if(E.currentRoom!=null) {
						System.out.println("Currently in Room "+E.currentRoom.name+" in "+E.currentRoom.currentFloor.getName());
						keys.remove(0);
						values.remove(0);
						if(E.currentRoom.type.equals("Cafeteria")) {
							keys.add("Get a snack");
							values.add(Operation.get_a_snack);
							if(E.type.equals("Pantry")) {
								keys.add(0,"Delete an item");
								values.add(0,Operation.delete_item);
								keys.add(0, "Restock an item");
								values.add(0,Operation.restock_items);
								keys.add(0, "Stock an item");
								values.add(0,Operation.stock_item);
							}
						}
					}
					else if(E.currentFloor==null) {
						System.out.println("On the way to get into the floor");
						keys.remove(1);
						values.remove(1);
					}else {
						System.out.println("In the hallways of "+E.currentFloor.getName());
					}
					
					if(E.currentFloor!=null || E.currentRoom!=null) {
						if(E.type.equals("Front-desk")) {
							keys.add(0,"Change Temperature");
							values.add(0,Operation.change_temperature);
						}
					}
					
					int key_count=0;
					
					System.out.println("\nEmployee Operations: ");
					for(String s : keys) {
						System.out.println(++key_count+") "+s);
					}
					System.out.println(++key_count+") Log Off");
					System.out.println("\nChoose an operation:");
					int operation_choice=sc.nextInt()-1;
					if(operation_choice<keys.size()) {
						values.get(operation_choice).evaluvate(E);
					}else if(operation_choice==keys.size()){
						logged_in=false;
						System.out.println("Program terminating, see you again tommorow!");
					}else {
						System.out.println("Enter a valid operation");
					}
				}
			}else {
				System.out.println("Invalid credentials, Try again!");
			}
			
		}while(E==null);
	}
	

}
