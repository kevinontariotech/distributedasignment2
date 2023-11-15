import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main (String [] args) {
        try {
            
            //locate registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 9101);

            //get the reference of the exported object from the RMI registry.
            Store store = (Store) registry.lookup("s");

            System.out.println("Welcome to the butcher shop. \nHere is our selection of meat:");
            System.out.println(store.showProducts());
            System.out.println(store.addProductToCart("Prime Striploin", "10oz", 2));
            System.out.println(store.addProductToCart("Prime Striploin", "12oz", 3));
            System.out.println(store.addProductToCart("Beef Burger", "18 burgers - 4oz/each", 6));
            System.out.println(store.addProductToCart("Lamb Rack", "~28oz", 1));
            System.out.println(store.addProductToCart("Lamb Rack", "28oz", 1));
            System.out.println(store.showCart());
            System.out.println(store.removeProductFromCart("Beef Burger", "18 burgers - 4oz/each", 3));
            System.out.println(store.removeProductFromCart("Lamb Rack", "28oz", 2));
            System.out.println(store.removeProductFromCart("Chicken Breast", "4oz", 1));
            System.out.println(store.showCart());
            System.out.println(store.useCoupon("coupon3"));
            System.out.println(store.useCoupon("coupon2"));
            System.out.println(store.useCoupon("coupon1"));
            System.out.println(store.checkOut());
            System.out.println(store.showCart());
            System.out.println(store.printReceipt());

        } catch (Exception e) {
            System.out.println("Client side error..." + e);
        }
    }
}