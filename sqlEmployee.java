package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class sqlEmployee {
	
	public static boolean addEmployee(Employee E, String password) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root", "root");
        
        String addEmployeeQuery = "insert into employee(name,email,type,role) values(?, ?, ?, ?)"; 
        PreparedStatement ps = connection.prepareStatement(addEmployeeQuery);
        ps.setString(1, E.name);
        ps.setString(2, E.email);
        ps.setString(3, E.type);
        ps.setString(4, E.role);
        ps.executeUpdate();
        
        String getEmpIdquery = "select id from employee where email=\'"+E.email+ "\';";
        PreparedStatement ps1 = connection.prepareStatement(getEmpIdquery);
        ResultSet rs = ps1.executeQuery();
        if(rs.next()) {
        	E.id = rs.getInt(1);
        }
        
        
        int result = Auth.signUp(E.id,E.email,password);
        
		return result!=0;
	}
	
	public static boolean updateEmployee(Employee E) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        int currentFloorId = (E.currentFloor!=null)? E.currentFloor.getId() : 0;
        int currentRoomId = (E.currentRoom!=null)? E.currentRoom.id : 0;
        String updateEmployeeQuery = "update employee set email = ?, name = ?, type = ?, currentFloor = ?, currentRoom = ?, role = ?, connection = ? where id="+E.id+";";
        PreparedStatement ps = connection.prepareStatement(updateEmployeeQuery);
        
        ps.setString(1, E.email);
        ps.setString(2, E.name);
        ps.setString(3, E.type);
        if(currentFloorId!=0) {
        	ps.setInt(4,currentFloorId);
        }else {
        	ps.setNull(4, java.sql.Types.INTEGER);
        }
        if(currentRoomId!=0) {
        	ps.setInt(5, currentRoomId);
        }else {
        	ps.setNull(5, java.sql.Types.INTEGER);
        }
        
        ps.setString(6,E.role);
        ps.setString(7, E.comm);
        int result = ps.executeUpdate();
		return (result!=0);
	}
	
	public static Employee getEmployeeById(int id)throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getEmployeeById = "select * from employee where id = "+id+";";
        PreparedStatement ps = connection.prepareStatement(getEmployeeById);
        ResultSet rs = ps.executeQuery(getEmployeeById);
        
        Employee E = null;
        if(rs.next()) {
        	id = rs.getInt(1);
            String email = rs.getString(2);
            String name = rs.getString(3);
            String type = rs.getString(4);
            int floorId = rs.getInt(5);
            int roomId = rs.getInt(6);
            String role = rs.getString(7);
            String connexion = rs.getString(8);
            
            E = new Employee(id,name,type,role,connexion,sqlRooms.getRoomById(roomId),sqlFloor.getFloorById(floorId),email);
        }
        return E;
	}
	
	public static List<Employee> getEmployeeByFloor(int floorid)throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getEmployeeByFloor = "select * from employee where currentFloor = "+floorid+";";
        PreparedStatement ps = connection.prepareStatement(getEmployeeByFloor);
        ResultSet rs = ps.executeQuery(getEmployeeByFloor);
        
        List<Employee> employees = new ArrayList<Employee>();
        if(rs.next()) {
        	int id = rs.getInt(1);
            String email = rs.getString(2);
            String name = rs.getString(3);
            String type = rs.getString(4);
            floorid = rs.getInt(5);
            int roomId = rs.getInt(6);
            String role = rs.getString(7);
            String connexion = rs.getString(8);
            
            Employee E = new Employee(id,name,type,role,connexion,sqlRooms.getRoomById(roomId),sqlFloor.getFloorById(floorid),email);
            employees.add(E);
        }
        return employees;
	}
	
	public static List<Employee> getEmployeeByRoom(int roomId)throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getEmployeeByRoom = "select * from employee where currentRoom = "+roomId+";";
        PreparedStatement ps = connection.prepareStatement(getEmployeeByRoom);
        ResultSet rs = ps.executeQuery(getEmployeeByRoom);
        
        List<Employee> employees = new ArrayList<Employee>();
        if(rs.next()) {
        	int id = rs.getInt(1);
            String email = rs.getString(2);
            String name = rs.getString(3);
            String type = rs.getString(4);
            int floorid = rs.getInt(5);
            roomId = rs.getInt(6);
            String role = rs.getString(7);
            String connexion = rs.getString(8);
            
            Employee E = new Employee(id,name,type,role,connexion,sqlRooms.getRoomById(roomId),sqlFloor.getFloorById(floorid),email);
            employees.add(E);
        }
        return employees;
	}

}
