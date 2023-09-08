package ITamenities;


import java.util.List;
import java.util.Scanner;

public class Admin {
	
	private static Scanner sc = new Scanner(System.in);
	
	
	
	public static void main(String args[]) throws Exception {
		/*
		 * add employee
		 * add floor
		 * add room
		 */
		
		System.out.println("IT Amenities");
		
		boolean loop = true;
		
		do {
			System.out.println("Select one of the following options");
			System.out.println("1) Add Employee");
			System.out.println("2) Add Floor");
			System.out.println("3) Add Room");
			
			System.out.println("4) Quit");
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			
			switch(choice){
				case 1:{
					System.out.println("Enter employee details: ");
					System.out.println("Enter name: ");
					String name = sc.next();
					System.out.println("Enter email: ");
					String email = sc.next();
					String[] types = {"Software", "Pantry", "Hygiene", "Front-desk", "Travel"};
					System.out.println("Types of employees:");
					int type_count =0;
					for(String t : types) {
						System.out.println(++type_count+ ") "+ t);
					}
					System.out.println("Select Employee type: ");
					int type_choice = sc.nextInt()-1;
					if(type_choice<types.length && type_choice>=0) {
						String type = types[type_choice];
						String roles[] = {"Manager", "Team Lead", "Developer", "Intern","Other"};
						int role_choice=4;
						if(type_choice==0) {
							System.out.println("Types of role: ");
							int role_count =0;
							for(String t : roles) {
								System.out.println(++role_count+ ") "+ t);
							}
							System.out.println("Enter Employee role:");
							role_choice = sc.nextInt()-1;
						}
						if(role_choice<roles.length && role_choice>=0) {
							String role = roles[role_choice];
							System.out.println("Create password for "+name+" : ");
							String password = sc.next();
							if(password.isEmpty()) {
								password="We1c@me1";
							}
							
							Employee E = new Employee(name,email,type,role);
							boolean result = sqlEmployee.addEmployee(E,Auth.toHexString(Auth.getSHA(password)));
							if(result) {
								System.out.println("Employee successfully added");
							}else {
								System.out.println("Error occured, try again sometime later");
							}
						}else {
							System.out.println("Invalid role choice, try again");
						}
						
					}else {
						System.out.println("Invalid employee type choice, try again");
					}
					
				}
				break;
				case 2:{
					System.out.println("Enter Floor details");
					System.out.println("Enter Floor name: ");
					String name = sc.next();
					Floor F = new Floor(name);
					try {
						boolean result = sqlFloor.addFloor(F);
						if(result) {
							System.out.println("Floor successfully registered");
						}else {
							System.out.println("Error occured, try again sometime later");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
				case 3:{
					List<Floor> floors = sqlFloor.getFloors();
					if(floors.size()!=0) {
						System.out.println("Available Floors");
						int floor_count=0;
						for(Floor F : floors) {
							System.out.println(++floor_count+") "+F.getName());
						}
						System.out.println("Choose floor to add room in");
						int floor_choice = sc.nextInt()-1;
						if(floor_choice>=0 && floor_choice<floors.size()) {
							String[] types = {"Meeting Room", "Conference Room","Hardware Room", "Cafeteria", "Library", "Medical Centre","Other"};
							System.out.println("Types of room:");
							int type_count =0;
							for(String t : types) {
								System.out.println(++type_count+ ") "+ t);
							}
							System.out.println("Enter room type: ");
							int type_choice = sc.nextInt()-1;
							if(type_choice>=0 && type_choice<types.length) {
								String type = types[type_choice];
								System.out.println("Enter room name:");
								String name = sc.next();
								System.out.println("Enter room maximum occupancy: ");
								int max_occupancy = sc.nextInt();
								Room R = new Room(name,floors.get(floor_choice),max_occupancy,"all",type);
								boolean result = sqlRooms.addRoom(R);
								if(result) {
									System.out.println("Successfully added Room "+R.name);
								}else {
									System.out.println("Problem occured, try again later");
								}
							}else {
								System.out.println("invalid type choice");
							}
						}else {
							System.out.println("Invalid floor choice, Try again!");
						}
					}else {
						System.out.println("Register a floor to continue");
					}
					
				}
				break;
				case 4:{
					System.out.println("Program terminating");
					loop=false;
				}
				break;
				default:
					System.out.println("Try again, Enter a valid option!");
				
			}
		}while(loop);
				
		
	}

}
