package ITamenities;

import java.util.ArrayList;
import java.util.List;

public class Room {
	
	int id;
	String name;
	Floor currentFloor;
	int occupancy;
	public int getOccupancy() {
		return occupancy;
	}

	public boolean setOccupancy(int occupancy) throws Exception {
		this.occupancy = occupancy;
		return sqlRooms.updateOccupancy(this);
	}

	int max_occupancy;
	String accessTo;
	String type;
	double temp;

	public Room(String name,Floor currentFloor,int max_occupancy, String accessTo, String type) {
		this.name = name;
		this.currentFloor = currentFloor;
		this.occupancy=0;
		this.max_occupancy = max_occupancy;
		this.accessTo = accessTo;
		this.type = type;
	}
	
	public Room(int id, String name,Floor currentFloor,int max_occupancy, String accessTo, int occupancy, String type,double temp) {
		this.id = id;
		this.name = name;
		this.currentFloor = currentFloor;
		this.occupancy = occupancy;
		this.max_occupancy = max_occupancy;
		this.accessTo = accessTo;
		this.type = type;
		this.temp = temp;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Floor getcurrentFloor() {
		return currentFloor;
	}
	public void setcurrentFloor(Floor currentFloor) {
		this.currentFloor = currentFloor;
	}
	public String getAccessTo() {
		return accessTo;
	}
	public void setAccessTo(String accessTo) {
		this.accessTo = accessTo;
	}
	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	public List<Employee> getEmployeesInside() throws Exception{
		
		return sqlEmployee.getEmployeeByRoom(this.id);
	}

}
