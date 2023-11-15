import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Store extends Remote{
    public List<Product> getProducts() throws RemoteException;
    public StringBuilder showProducts() throws RemoteException;
    public StringBuilder showCart() throws RemoteException;
    public String addProductToCart(String productName, String productDes, int quantity) throws RemoteException;
    public String removeProductFromCart(String productName, String productDes, int quantity) throws RemoteException;
    public String useCoupon(String coupon) throws RemoteException;
    public double checkOut() throws RemoteException;
    public String printReceipt() throws RemoteException;
    public int inShop(String name, String des) throws RemoteException;
    public int inCart(String name, String des) throws RemoteException;
    public StringBuilder getReceipt(double subtotal) throws RemoteException;
}
