import java.rmi.*;

public interface PuzzleInterface extends Remote {
	
	public boolean isOnLine(String ID) throws RemoteException;
	public boolean isValid(String ID) throws RemoteException;
}
