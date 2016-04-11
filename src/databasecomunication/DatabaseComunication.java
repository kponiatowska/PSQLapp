package databasecomunication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DatabaseComunication {
    
    
    private static Connection connectWithDatabase(Connection c) 
            throws SQLException, ClassNotFoundException {
        
        Class.forName("org.postgresql.Driver");
        c = DriverManager
           .getConnection("jdbc:postgresql://localhost:5432/postgres",
           "postgres", "admin");
         
        System.out.println("Opened database successfully");
        return c;
    }
    
    private static void createTableOfStudents(Connection c) 
            throws SQLException {

        Statement stmt = null;
        stmt = c.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS STUDENTS " +
                      "(ID INT PRIMARY  KEY            NOT NULL," +
                      " NAME            VARCHAR(50)    NOT NULL, " +
                      " AGE             INT            NOT NULL, " +
                      " ADDRESS         CHAR(50), " +
                      " SEX             VARCHAR(1))";
        stmt.executeUpdate(sql);
        System.out.println("Table created successfully");
        stmt.close();      
    }
    
    public static void instertStudent (Connection c, int id, String name, int age,
            String address, String sex) throws SQLException {
     
         PreparedStatement prepstmt = null;

         String insertTableSQL = "INSERT INTO STUDENTS"
				+ "(ID, NAME, AGE, ADDRESS, SEX) VALUES"
				+ "(?,?,?,?,?);";
          
          prepstmt = c.prepareStatement(insertTableSQL);
          
          prepstmt.setInt(1, id);
          prepstmt.setString(2, name);
          prepstmt.setInt(3, age);
          prepstmt.setString(4, address);
          prepstmt.setString(5, sex);
        
          prepstmt.executeUpdate();
          System.out.print("Inserted.");
          prepstmt.close();
          
    }

    public static void main(String args[]) throws IOException {
      Connection c = null;
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          
      int id = 0;
      String address = null;
      int age = 0;
      String name = null;
      String sex = null;
      
      System.out.print("Enter your name: \n");
      name = br.readLine();
      
      System.out.print("Enter you id: \n");
      try{
          id = Integer.parseInt(br.readLine());
      }catch(NumberFormatException nfe){
          System.err.println("Invalid Format!");
      }
       
      System.out.print("Enter you age: \n");
      try{
          age = Integer.parseInt(br.readLine());
      }catch(NumberFormatException nfe){
          System.err.println("Invalid Format!");
      }
       
      System.out.print("Enter your address: \n"); 
      address = br.readLine();
       
      System.out.print("Enter your sex: \n");
      sex = br.readLine();
      
      try {
          c = connectWithDatabase(c);
          createTableOfStudents(c);
          instertStudent(c, id, name, age, address, sex);         
          c.close(); 
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }

   }
    
}
