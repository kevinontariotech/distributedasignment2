import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


public interface Coupon extends Remote {
    public String getCode() throws RemoteException;
    public double getDiscount() throws RemoteException;
    public Date getExpiryDate() throws RemoteException;
}
