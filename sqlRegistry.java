package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class sqlRegistry {
	
	public static boolean bookRoom(Registry R) throws Exception {
		if(R.getBookedBy().role.equals("Manager") || R.getBookedBy().role.equals("Team Lead")) {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/itamenities",
	            "root","root");
	        java.sql.Date fromdate = new java.sql.Date(R.getFrom().getTime());
	        java.sql.Date todate = new java.sql.Date(R.getTo().getTime());
	        
	        String checkAvailablityQuery = "select * from registry where roomId="+R.getRoom().id+" and((todate>=\'"+fromdate+"\' and fromdate<=\'"+fromdate+"\')"+"or (todate>=\'"+todate+"\' and fromdate<=\'"+todate+"\')"+"or (\'"+todate+"\'>=fromdate and \'"+fromdate+"\'<=fromdate)"+"or (\'"+todate+"\'>=todate and \'"+fromdate+"\'<=todate));";
	        PreparedStatement ps = connection.prepareStatement(checkAvailablityQuery);
	        ResultSet result = ps.executeQuery();
	        if(result.next()) {
	        	System.out.println(R.getRoom().id);
	        	System.out.println(result.getInt(2));
	        	System.out.println("theres room");
	        	connection.close();
	        	return false;
	        }else {
	        	String bookRoomQuery = "insert into registry(roomId, fromdate, todate, bookedBy) values(?, ?, ?, ?);";
	        	PreparedStatement ps1 = connection.prepareStatement(bookRoomQuery);
	        	ps1.setInt(1, R.getRoom().id);
	        	ps1.setDate(2, fromdate);
	        	ps1.setDate(3, todate);
	        	ps1.setInt(4, R.getBookedBy().id);
	        	int rs = ps1.executeUpdate();
	        	connection.close();
	        	return (rs!=0);
	        }
		}else {
			return false;
		}
	}
	public static List<Registry> getRegistryByEmployee(Employee E) throws Exception{

		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        
        String Query = "select * from registry where bookedBy="+E.id;
        PreparedStatement ps = connection.prepareStatement(Query);
        ResultSet result = ps.executeQuery();
        List<Registry> registries = new ArrayList<Registry>();
        while(result.next()) {
        	int id = result.getInt(1);
        	int roomId = result.getInt(2);
        	Room R = sqlRooms.getRoomById(roomId);
        	Date fromDate = new java.sql.Date(result.getDate(3).getTime());
        	Date todate = new java.sql.Date(result.getDate(4).getTime());
        	Registry reg = new Registry(id,R,fromDate,todate,E);
        	registries.add(reg);
        }
        connection.close();
        return registries;
	}
	public static boolean cancelRoom(Registry R) throws Exception {
		if(R.getBookedBy().role.equals("Manager") || R.getBookedBy().role.equals("Team Lead")) {
			Class.forName("com.mysql.cj.jdbc.Driver");
	        Connection connection = DriverManager.getConnection(
	            "jdbc:mysql://localhost:3306/itamenities",
	            "root","root");
	        String deleteQuery = "delete from registry where id="+R.getId();
	        PreparedStatement ps = connection.prepareStatement(deleteQuery);
	        int result = ps.executeUpdate();
	        connection.close();
	        return result!=0;
		}else {
			return false;
		}
	}
	
}
