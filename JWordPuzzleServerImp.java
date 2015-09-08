import java.net.InetAddress;
import java.net.NetworkInterface;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class JWordPuzzleServerImp extends UnicastRemoteObject implements PuzzleInterface {
	private Message msg;
	
	public JWordPuzzleServerImp(String m) throws RemoteException {
		super();
		msg = new Message(m);
	}
	
	// To check whether the user is online
	public boolean isOnLine(String ID) throws RemoteException {
		boolean status = false;
		return status;
	}
	
	// To check whether one user has registered
	public boolean isValid(String ID) throws RemoteException {
		boolean status = false;
		return status;
	}
	
	public static void main(String argv[]) {
		RMISecurityManager sm = new RMISecurityManager();
		System.setSecurityManager(sm);
		
		try {
			JWordPuzzleServerImp obj = new JWordPuzzleServerImp("Server");
			String hostIP = InetAddress.getLocalHost().getHostAddress();
			Naming.rebind("//"+hostIP+"/PuzzleServer", obj);
			
		} catch(Exception e) {
			System.out.println("Puzzle Server error:" + e.getMessage());
			e.printStackTrace();
		}
	}

}
