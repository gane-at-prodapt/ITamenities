package ITamenities;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Auth {
	
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
 
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
     
    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
 
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
 
        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    }
	
	public static Employee signIn(String employeeEmail, String passwordHash) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root", "root");
        
        String signUpQuery = "select employeeId from auth where email=\'"+employeeEmail+"\'"+"and password=\'"+passwordHash+"\';";
        PreparedStatement ps = connection.prepareStatement(signUpQuery);
        ResultSet rs = ps.executeQuery();
        
        Employee E = null;
        if(rs.next()) {
        	int employeeId = rs.getInt(1);
        	E = sqlEmployee.getEmployeeById(employeeId);
        }
        return E;
	}
	public static int signUp(int employeeId, String employeeEmail, String passwordHash) throws Exception {
		
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/itamenities",
            "root", "root");
        
        String signUpQuery = "insert into auth(email,password,employeeId) values(?, ?, ?)"; 
        PreparedStatement ps = connection.prepareStatement(signUpQuery);
        ps.setString(1, employeeEmail);
        ps.setString(2, passwordHash);
        ps.setInt(3, employeeId);
        
        int result = ps.executeUpdate();
		return result;
	}
}
