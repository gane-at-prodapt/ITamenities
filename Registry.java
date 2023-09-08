package ITamenities;

import java.util.Date;

public class Registry {

	private int id;
	private Room room;
	private Date from;
	private Date to;
	private Employee bookedBy;
	
	public Registry(int id, Room room, Date from, Date to, Employee bookedBy) {
		this.id = id;
		this.room = room;
		this.from = from;
		this.to = to;
		this.bookedBy = bookedBy;
	}
	public Registry(Room room,Date from, Date to, Employee bookedBy) {
		this.room = room;
		this.from = from;
		this.to = to;
		this.bookedBy = bookedBy;
	}
	
	
	public int getId() {
		return id;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public Employee getBookedBy() {
		return bookedBy;
	}
	public void setBookedBy(Employee bookedBy) {
		this.bookedBy = bookedBy;
	}
	
	
}
