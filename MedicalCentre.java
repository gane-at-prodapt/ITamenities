package ITamenities;


public class MedicalCentre extends NonBooking{

	public MedicalCentre(int id, String name, Floor currentFloor, int max_occupancy, String accessTo, int occupancy, String type, double temp) {
		super(id, name, currentFloor, max_occupancy, accessTo, occupancy,type,temp);
		// TODO Auto-generated constructor stub
	}
	
	public MedicalCentre(Room R) {
		super(R.id,R.name,R.currentFloor,R.max_occupancy,R.accessTo,R.occupancy,R.type,R.temp);
	}

}
