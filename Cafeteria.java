package ITamenities;


import java.util.List;

public class Cafeteria extends NonBooking{
	
	
	public Cafeteria(int id, String name, Floor currentFloor, int max_occupancy, String accessTo, int occupancy, String type,double temp) {
		super(id, name, currentFloor, max_occupancy, accessTo, occupancy,type,temp);
		// TODO Auto-generated constructor stub
	}
	
	public Cafeteria(Room R) {
		super(R.id,R.name,R.currentFloor,R.max_occupancy,R.accessTo,R.occupancy,R.type,R.temp);
	}
	
	public List<Item> getItems() throws Exception{
		return sqlItem.getItemsByRoomId(this.id);
	}
	
}
