package ITamenities;

public class Query {
	
	private int id;
	private String type;
	private String desc;
	private Employee assignedTo;
	private Employee raisedByEmp;
	private Item raisedByItem;
	
	
	
	
	public Query(String type, String desc,Employee raisedByEmp) {
		this.type = type;
		this.desc = desc;
		this.setRaisedByEmp(raisedByEmp);
	}
	
	public Query(String type, String desc,Item raisedByItem) {
		this.type = type;
		this.desc = desc;
		this.setRaisedByItem(raisedByItem);
	}
	
	public Query(int id, String type, String desc, Employee assignedTo,Employee raisedByEmployee) {
		this.id = id;
		this.type = type;
		this.desc = desc;
		this.assignedTo = assignedTo;
		this.setRaisedByEmp(raisedByEmployee);
	}
	
	public Query(int id, String type, String desc, Employee assignedTo,Item raisedByItem) {
		this.id = id;
		this.type = type;
		this.desc = desc;
		this.assignedTo = assignedTo;
		this.setRaisedByItem(raisedByItem);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Employee getAssignedTo() {
		return assignedTo;
	}
	public boolean setAssignedTo(Employee assignedTo) throws Exception {
		this.assignedTo = assignedTo;
		return sqlquery.updateQuery(this);
	}

	public Item getRaisedByItem() {
		return raisedByItem;
	}

	public void setRaisedByItem(Item raisedByItem) {
		this.raisedByItem = raisedByItem;
	}

	public Employee getRaisedByEmp() {
		return raisedByEmp;
	}

	public void setRaisedByEmp(Employee raisedByEmp) {
		this.raisedByEmp = raisedByEmp;
	}
	
	

}
