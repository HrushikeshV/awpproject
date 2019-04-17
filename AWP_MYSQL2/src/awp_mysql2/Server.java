package awp_mysql2;

import java.io.*; 
import java.text.*; 
import java.net.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

// Server class 
public class Server 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 

// ClientHandler class 
class ClientHandler extends Thread 
{ 
	DateFormat fordate = new SimpleDateFormat("yyyy/MM/dd"); 
	DateFormat fortime = new SimpleDateFormat("hh:mm:ss"); 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	

        public static void appendStrToFile(String fileName, 
                                       String str) 
        { 
            try { 

                // Open given file in append mode. 
                BufferedWriter out = new BufferedWriter( 
                       new FileWriter(fileName, true)); 
                out.write(str); 
                out.close(); 
            } 
            catch (IOException e) { 
                System.out.println("exception occoured" + e); 
            } 
        } 
        
	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 

	@Override
	public void run() 
	{ 
		String received; 
		String username ;
                
		while (true) 
		{ 
			try { 
                                username = dis.readUTF();
                                System.out.println(username);
				received = dis.readUTF(); 
				

//                            if(received.equals("New"))
//                            {
                                //appendStrToFile("C:\\Users\\Admin\\Desktop\\client1.txt","New");
                                try{  
                                    Class.forName("com.mysql.jdbc.Driver");  
                                    Connection con=DriverManager.getConnection(  
                                    "jdbc:mysql://localhost:3306/awp","root","");  
                                    //here sonoo is database name, root is username and password  
                                    Statement stmt=con.createStatement(); 
                                    //String sql ="INSERT INTO `logs2`(`Username`, `Utility`) VALUES ("+"\""+username+"\""+","+received+")";
                                    String sql = "INSERT INTO `logs2` (`Username`, `Utility`) VALUES ("+"'"+username+"'" +",'"+received+"')";
//                                    PreparedStatement preparedStmt = con.prepareStatement(sql);
//                                    preparedStmt.setString(1,"u");
//                                    preparedStmt.setString(2,"s");
                                    int executeUpdate = stmt.executeUpdate(sql);  
                                    //int rs= preparedStmt.executeUpdate();
                                    //int rs=stmt.executeUpdate(sql);  
                                    con.close();  
                                    }
                                catch(Exception e)
                                { 
                                    System.out.println(e);
                                }  
//                            }
//                            else if(received.equals("Next"))
//                            {
//                                appendStrToFile("C:\\Users\\Admin\\Desktop\\client1.txt","Next");
//                            }
//                            else if(received.equals("Delete"))
//                            {
//                                appendStrToFile("C:\\Users\\Admin\\Desktop\\client1.txt","Delete");
//                            }
//                            else if(received.equals("Rename"))
//                            {
//                                appendStrToFile("C:\\Users\\Admin\\Desktop\\client1.txt","Rename");
//                            }
                            
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
		} 
		
//		try
//		{ 
//			// closing resources 
//			this.dis.close(); 
//			this.dos.close(); 
//			
//		}catch(IOException e){ 
//			e.printStackTrace(); 
//		} 
	} 
} 
