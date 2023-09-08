package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class sqlItem {
	
	public static List<Item> getItemsByRoomId(int roomId) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getItemByRoomId = "select * from item where currentRoom = "+roomId+";";
        PreparedStatement ps = connection.prepareStatement(getItemByRoomId);
        ResultSet rs = ps.executeQuery(getItemByRoomId);
        
        List<Item> items = new ArrayList<Item>();
        
        while(rs.next()) {
        	int itemId = rs.getInt(1);
        	String name = rs.getString(2);
        	int quantity = rs.getInt(3);
        	Date expiry = (Date)rs.getDate(4);
        	int currentRoomId = rs.getInt(5);
        	Cafeteria currentRoom = new Cafeteria(sqlRooms.getRoomById(currentRoomId));
        	
        	Item I = new Item(itemId,name,quantity,expiry,currentRoom);
        	items.add(I);
        }
        return items;
	}
	
	public static Item getItemById(int id) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getItemById = "select * from item where id = "+id+";";
        PreparedStatement ps = connection.prepareStatement(getItemById);
        ResultSet rs = ps.executeQuery(getItemById);
        
        Item I=null;
        
        if(rs.next()) {
        	int itemId = rs.getInt(1);
        	String name = rs.getString(2);
        	int quantity = rs.getInt(3);
        	Date expiry = new Date(rs.getDate(4).getTime());
        	int currentRoomId = rs.getInt(5);
        	Cafeteria currentRoom = new Cafeteria(sqlRooms.getRoomById(currentRoomId));
        	
        	I = new Item(itemId,name,quantity,expiry,currentRoom);
        }
        return I;
	}
	
	public static boolean addItem(Item I) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        
        String addItemQuery = "insert into item(name,quantity,expiry,currentRoom) values(?, ?, ?, ?)"; 
        PreparedStatement ps = connection.prepareStatement(addItemQuery);
        ps.setString(1,I.getName());
        ps.setInt(2, I.getQuantity());
        ps.setDate(3,new java.sql.Date(I.getExpiry().getTime()));
        ps.setInt(4, I.getCurrentroom().id);
        
        int result = ps.executeUpdate();
        
		return (result!=0);
	}
	
	public static boolean updateItem(Item I) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String updateItemQuery = "update item set name = ? , quantity = ? , expiry = ? , currentRoom = ? where id="+I.getId()+";";
        PreparedStatement ps = connection.prepareStatement(updateItemQuery);
        ps.setString(1, I.getName());
        ps.setInt(2,I.getQuantity());
        ps.setDate(3,new java.sql.Date(I.getExpiry().getTime()));
        ps.setInt(4,I.getCurrentroom().id);
		int result = ps.executeUpdate();
		return (result!=0);
	}
	
	public static boolean deleteItem(Item I) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String deleteItemQuery = "delete from item where id="+I.getId()+";";
        PreparedStatement ps = connection.prepareStatement(deleteItemQuery);
		int result = ps.executeUpdate();
		return (result!=0);
	}
}
