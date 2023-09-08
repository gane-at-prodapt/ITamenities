package ITamenities;

public class Pantry extends Infrastructure{
	
	
	
	public Pantry(int id, String name, String type, String role, String comm, Room currentRoom, Floor currentFloor,
			String email) {
		super(id, name, type, role, comm, currentRoom, currentFloor, email);
		// TODO Auto-generated constructor stub
	}
	public void getItems() {
		
	}
	
	public void reFill(Item i, int qty) throws Exception {
		i.setQuantity(i.getQuantity()+qty);
	}

}
