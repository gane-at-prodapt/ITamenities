package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class sqlFloor {
	
	public static boolean addFloor(Floor F) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root", "root");
        
        String addFloorQuery = "insert into floor(name) values(?)"; 
        PreparedStatement ps = connection.prepareStatement(addFloorQuery);
        ps.setString(1, F.getName());
        int result = ps.executeUpdate();
		return result!=0;
	}
	
	public static Floor getFloorById(int id) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getFloorById = "select * from floor where id = "+id+";";
        PreparedStatement ps = connection.prepareStatement(getFloorById);
        ResultSet rs = ps.executeQuery(getFloorById);
        
        Floor F = null;
        
        if(rs.next()) {
        	id = rs.getInt(1);
            String name = rs.getString(2);
            double temperature = rs.getDouble(3);
            
            F = new Floor(id,name,temperature);	
        }
        connection.close();
        return F;
	}
	
	public static List<Floor> getFloors() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getFloors = "select * from floor;";
        PreparedStatement ps = connection.prepareStatement(getFloors);
        ResultSet rs = ps.executeQuery(getFloors);
        
        List<Floor> floors = new ArrayList<Floor>();        
        while(rs.next()) {
        	int id = rs.getInt(1);
            String name = rs.getString(2);
            double temperature = rs.getDouble(3);
            
            Floor F = new Floor(id,name,temperature);	
            floors.add(F);
        }
        connection.close();
        return floors;
	}

}
