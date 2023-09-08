package ITamenities;

import java.util.Date;

public class Item {
	private int id;
	private String name;
	private int quantity;
	private Date expiry;
	private Cafeteria currentroom;
	public Item(String name, int quantity, Date expiry, Cafeteria currentroom) {
		this.name = name;
		this.quantity = quantity;
		this.expiry = expiry;
		this.currentroom = currentroom;
	}
	
	public Item(int id, String name, int quantity, Date expiry, Cafeteria currentroom) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
		this.expiry = expiry;
		this.currentroom = currentroom;
	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) throws Exception {
		this.name = name;
		sqlItem.updateItem(this);
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) throws Exception {
		this.quantity = quantity;
		sqlItem.updateItem(this);
		if(quantity==0) {
			sqlItem.deleteItem(this);
		}else if(quantity==4) {
			String type = "Pantry";
			String desc = this.name+" is low on "+this.currentroom.name;
			Query Q = new Query(type,desc,this);
			sqlquery.addQuery(Q);
		}
	}
	public Date getExpiry() {
		return expiry;
	}
	public void setExpiry(Date expiry) throws Exception {
		this.expiry = expiry;
		sqlItem.updateItem(this);
	}
	public Room getCurrentroom() {
		return currentroom;
	}
	public void setCurrentroom(Cafeteria currentroom) throws Exception {
		this.currentroom = currentroom;
		sqlItem.updateItem(this);
	}
	
	
}


