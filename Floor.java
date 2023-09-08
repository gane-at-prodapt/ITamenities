package ITamenities;

import java.util.ArrayList;
import java.util.List;

public class Floor {
	
	private int id;
	private String name;
	private double temperature;
	
	Floor(String name){
		this.setName(name);
	}
	
	Floor(int id, String name, double temperature){
		this.setId(id);
		this.setName(name);
		this.setTemperature(temperature);
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

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	public List<Room> getRooms() throws Exception{
		return sqlRooms.getRoomsByFloor(this.id);
	}
	
	public List<Employee> getEmployeesInside() throws Exception{
		
		return sqlEmployee.getEmployeeByFloor(this.id);
	}

}
