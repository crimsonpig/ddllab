import java.sql.*;
public class stabbert_Keith_hw3 {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	
	public static void main(String[] args){
//		String host = "athena.ecs.csus.edu";
		String host = "localhost";
		String databaseUrl = "jdbc:mysql://"+host+"/cs174127";
		String user = "cs174127";
		String password = "bolsxrfn";
		
		runAssignment(databaseUrl, user, password);
	}
	
	private static final String QUERY_1 = "SELECT p.name, p.taxPayerID " +
			"FROM Person AS p, Employee AS e, Customer AS c " +
			"WHERE e.taxPayerId = p.taxPayerId " +
			"AND c.taxPayerId = p.taxPayerId " +
			"AND e.taxPayerId = c.taxPayerId ";
	
	private static final String QUERY_2 = "SELECT l.taxPayerId, l.name, l.salary " +
			"FROM empLoan AS l ";
	
	public static void runAssignment(String url, String username, String password){
		try{
			Class.forName(JDBC_DRIVER).newInstance();
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection open to database at: "+url);
			Statement stmt1 = conn.createStatement();
			ResultSet rs1 = stmt1.executeQuery(QUERY_1);
			System.out.println("Query 1 executed");
			while(rs1.next()){
				System.out.println("name: "+rs1.getString("name"));
				System.out.println("taxPayerID: "+rs1.getString("taxPayerID"));
			}
			rs1.close();
			stmt1.close();
			
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(QUERY_2);
			System.out.println("\nQuery 2 executed");
			while(rs2.next()){
				System.out.println("taxPayerID: "+rs2.getString("taxPayerID"));
				System.out.println("name: "+rs2.getString("name"));
				System.out.println("salary: "+rs2.getString("salary"));
			}
			rs2.close();
			stmt2.close();
			
			CallableStatement cstmt3 = conn.prepareCall("{?= call get_no_of_tran(?)}");
			cstmt3.registerOutParameter(1,Types.NUMERIC);
			cstmt3.setString(2,"123456789");
			cstmt3.execute();
			System.out.println("\nno of transactions = "+cstmt3.getInt(1));
			
			conn.close();
			System.out.println("\nDisconnected from database at: "+url);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
