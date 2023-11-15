import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Product extends Remote {
    public String getName() throws RemoteException;
    public String getDescription() throws RemoteException;
    public double getPrice() throws RemoteException;
    public int getQuantity() throws RemoteException;
    public void incrementQuantity(int addQuantity) throws RemoteException;
    public void decrementQuantity(int addQuantity) throws RemoteException;
}
