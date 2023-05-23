import java.rmi.*;

public class Server{
	public static void main(String[] args){
		
		try{
			ServerImplementation serverImpl = new ServerImplementation();
			Naming.rebind("Server",serverImpl);
			System.out.println("Server is Ready.....");
		}
		
		catch(Exception e){
			System.out.println("Exception occured at Server! "+ e.getMessage());
		}	
	}
}
