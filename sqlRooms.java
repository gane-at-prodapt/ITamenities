package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class sqlRooms {
	public static boolean addRoom(Room R) throws Exception {
		//TODO: change 
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root", "root");
        
        String addRoomQuery = "insert into room(name,currentFloor,max_occupancy,accessTo,type) values(?,?,?,?,?)"; 
        PreparedStatement ps = connection.prepareStatement(addRoomQuery);
        ps.setString(1, R.getName());
        ps.setInt(2, R.currentFloor.getId());
        ps.setInt(3, R.max_occupancy);
        ps.setString(4, R.accessTo);
        ps.setString(5, R.type);
        int result = ps.executeUpdate();
		return result!=0;
	}
	
	public static boolean updateOccupancy(Room R) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        
        String updateRoomQuery = "update room set occupancy = ? where id="+R.id+";";
        PreparedStatement ps = connection.prepareStatement(updateRoomQuery);
        
        ps.setInt(1, R.occupancy);
        
        int result = ps.executeUpdate();
		return (result!=0);
	}
	
	public static boolean updateTemperature(Room R) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        
        String updateRoomQuery = "update room set temperature = ? where id="+R.id+";";
        PreparedStatement ps = connection.prepareStatement(updateRoomQuery);
        
        ps.setDouble(1, R.temp);
        
        int result = ps.executeUpdate();
		return (result!=0);
	}
	
	public static List<Room> getRoomsByFloor(int floorId) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getRoomsByFloor = "select * from room where currentFloor = "+floorId+";";
        PreparedStatement ps = connection.prepareStatement(getRoomsByFloor);
        ResultSet rs = ps.executeQuery(getRoomsByFloor);
        
        List<Room> rooms = new ArrayList<Room>();
        
        while(rs.next()) {
        	int id = rs.getInt(1);
            String name = rs.getString(2);
            int currentFloorId = rs.getInt(3);
            int occupancy = rs.getInt(4);
            int max_occupancy = rs.getInt(5);
            String accessTo = rs.getString(6);
            String type = rs.getString(7);
            double temp = rs.getDouble(8);
            Room R = new Room(id,name,sqlFloor.getFloorById(currentFloorId),max_occupancy,accessTo,occupancy,type,temp);
            rooms.add(R);
        }
      
        return rooms;
	}
	public static List<Cafeteria> getCafsByFloor(int floorId) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getCafsByFloor = "select * from room where currentFloor = "+floorId+" and type='Cafeteria' ;";
        PreparedStatement ps = connection.prepareStatement(getCafsByFloor);
        ResultSet rs = ps.executeQuery(getCafsByFloor);
        
        List<Cafeteria> cafeterias = new ArrayList<Cafeteria>();
        
        while(rs.next()) {
        	int id = rs.getInt(1);
            String name = rs.getString(2);
            int currentFloorId = rs.getInt(3);
            int occupancy = rs.getInt(4);
            int max_occupancy = rs.getInt(5);
            String accessTo = rs.getString(6);
            String type = rs.getString(7);
            double temp = rs.getDouble(8);
            Cafeteria C = new Cafeteria(id,name,sqlFloor.getFloorById(currentFloorId),max_occupancy,accessTo,occupancy,type,temp);
            cafeterias.add(C);
        }
      
        return cafeterias;
	}
	public static List<Booking> getmeetRoomsByFloor(int floorId) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getMeetsByFloor = "select * from room where currentFloor = "+floorId+" and (type=\'Meeting Room\' or type=\'Conference Room\') ;";
        PreparedStatement ps = connection.prepareStatement(getMeetsByFloor);
        ResultSet rs = ps.executeQuery(getMeetsByFloor);
        
        List<Booking> booking_rooms = new ArrayList<Booking>();
        
        while(rs.next()) {
        	int id = rs.getInt(1);
            String name = rs.getString(2);
            int currentFloorId = rs.getInt(3);
            int occupancy = rs.getInt(4);
            int max_occupancy = rs.getInt(5);
            String accessTo = rs.getString(6);
            String type = rs.getString(7);
            double temp = rs.getDouble(8);
            Booking B = new Booking(id,name,sqlFloor.getFloorById(currentFloorId),max_occupancy,accessTo,occupancy,type,temp);
            booking_rooms.add(B);
        }
      
        return booking_rooms;
	}
	public static Room getRoomById(int id) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getRoomById = "select * from room where id = "+id+";";
        PreparedStatement ps = connection.prepareStatement(getRoomById);
        ResultSet rs = ps.executeQuery(getRoomById);
        
        Room R = null;
        
        if(rs.next()) {
        	id = rs.getInt(1);
            String name = rs.getString(2);
            int currentFloorId = rs.getInt(3);
            int occupancy = rs.getInt(4);
            int max_occupancy = rs.getInt(5);
            String accessTo = rs.getString(6);
            String type = rs.getString(7);
            double temp = rs.getDouble(8);
            R = new Room(id,name,sqlFloor.getFloorById(currentFloorId),max_occupancy,accessTo,occupancy,type,temp);
        }
      
        return R;
	}

	
}
