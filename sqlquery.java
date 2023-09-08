package ITamenities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class sqlquery {
	
	public static boolean addQuery(Query Q) throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String addQuery;
        if(Q.getRaisedByEmp()!=null) {
        	addQuery = "insert into query(type,description,raisedByEmp) values(?, ?, ?)";
        	
        }else {
        	addQuery = "insert into query(type,description,raisedByItem) values(?, ?, ?)";
        }
         
        PreparedStatement ps = connection.prepareStatement(addQuery);
        ps.setString(1, Q.getType());
        ps.setString(2, Q.getDesc());
        if(Q.getRaisedByEmp()!=null) {
        	ps.setInt(3, Q.getRaisedByEmp().id);
        }else {
        	ps.setInt(3, Q.getRaisedByItem().getId());
        }
        
        int result = ps.executeUpdate();
        connection.close();
		return (result!=0);
	}
	
	public static List<Query> getQueriesbyEmployee(Employee E) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getQueryByEmpId = "select * from query where assignedTo = "+E.id+";";
        PreparedStatement ps = connection.prepareStatement(getQueryByEmpId);
        ResultSet rs = ps.executeQuery(getQueryByEmpId);
        
        List<Query> queries = new ArrayList<Query>();
        
        while(rs.next()) {
        	int id = rs.getInt(1);
        	String type = rs.getString(2);
        	String description = rs.getString(3);
        	Employee assignedTo = sqlEmployee.getEmployeeById(rs.getInt(4));
        	int raisedByEmpId = rs.getInt(5);
        	int raisedByItemId = rs.getInt(6);
        	Query Q =null;
        	if(raisedByEmpId!=0) {
        		Employee raisedByEmployee = sqlEmployee.getEmployeeById(raisedByEmpId);
        		Q = new Query(id,type,description,assignedTo,raisedByEmployee);
        	}else {
        		Item raisedByItem = sqlItem.getItemById(raisedByItemId);
        		Q = new Query(id,type,description,assignedTo,raisedByItem);
        	}
        	queries.add(Q);
        	
        }
        connection.close();
		return queries;
	}
	public static List<Query> getQueriesbyType(String type) throws Exception{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String getQueryByType = "select * from query where type=\'"+type+"\';";
        PreparedStatement ps = connection.prepareStatement(getQueryByType);
        ResultSet rs = ps.executeQuery(getQueryByType);
        
        List<Query> queries = new ArrayList<Query>();
        
        while(rs.next()) {
        	int id = rs.getInt(1);
        	type = rs.getString(2);
        	String description = rs.getString(3);
        	Employee assignedTo = sqlEmployee.getEmployeeById(rs.getInt(4));
        	int raisedByEmpId = rs.getInt(5);
        	int raisedByItemId = rs.getInt(6);
        	Query Q =null;
        	if(raisedByEmpId!=0) {
        		Employee raisedByEmployee = sqlEmployee.getEmployeeById(raisedByEmpId);
        		Q = new Query(id,type,description,assignedTo,raisedByEmployee);
        	}else {
        		Item raisedByItem = sqlItem.getItemById(raisedByItemId);
        		Q = new Query(id,type,description,assignedTo,raisedByItem);
        	}
        	if(assignedTo==null) {
        		queries.add(Q);
        	}
        }
        connection.close();
		return queries;
	}
	public static boolean deleteQueryById(int id) throws Exception{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String deleteQueryById = "delete from query where id = "+id+";";
        PreparedStatement ps = connection.prepareStatement(deleteQueryById);
        int rs = ps.executeUpdate(deleteQueryById);
        connection.close();
        return (rs!=0);
	}
	public static boolean updateQuery(Query Q) throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root","root");
        String updateQuery = "update query set assignedTo = "+Q.getAssignedTo().id+" where id="+Q.getId()+";";
        PreparedStatement ps = connection.prepareStatement(updateQuery);
        
        int rs = ps.executeUpdate(updateQuery);
        connection.close();
        return (rs!=0);
	}
}
