import java.rmi.RemoteException;

public class ProductImpl implements Product {
    public String name;
    public String description;
    public double price;
    public int quantity;

    public ProductImpl(int quantity, String newName, String newDescription, double newPrice) throws RemoteException {
        this.quantity = quantity;
        this.name = newName;
        this.description = newDescription;
        this.price = newPrice;
    }

    public ProductImpl(String newName, String newDescription, double newPrice) throws RemoteException {
        this.quantity = 0;
        this.name = newName;
        this.description = newDescription;
        this.price = newPrice;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public String getDescription() throws RemoteException {
        return this.description;
    }

    public double getPrice() throws RemoteException {
        return this.price;
    }

    public int getQuantity() throws RemoteException {
        return this.quantity;
    }

    public void incrementQuantity(int addQuantity) throws RemoteException {
        this.quantity += addQuantity;
    }

    public void decrementQuantity(int addQuantity) throws RemoteException {
        this.quantity -= addQuantity;
    }
}
