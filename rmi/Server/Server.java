import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String [] args) {
        try {
            //set hostname for server
            System.setProperty("java.rmi.server.hostname", "127.0.0.1");


            StoreImpl store = new StoreImpl();

            Store stub = (Store) UnicastRemoteObject.exportObject(store, 0, null, null, null);

            Registry registry = LocateRegistry.getRegistry("127.0.0.1" , 9101);

            registry.rebind("s", stub);

            System.out.println("Server is ready and waiting for client");
        } catch(Exception e){
            System.out.println("Some server error..." + e);
        }
    }

}
