import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class StoreImpl implements Store {
    List<Product> productList = new ArrayList<Product>();
    List<Product> shoppingCart = new ArrayList<Product>();
    List<Coupon> couponList = new ArrayList<Coupon>();
    double discount = 1.0;
    String receipt;
    final double TAX_RATE = 0.13;
    
    public StoreImpl() throws RemoteException{
        //initialize the store with products
        this.productList.add(new ProductImpl("Prime Striploin", "10oz", 20.24));
        this.productList.add(new ProductImpl("Prime Striploin", "12oz", 24.29));
        this.productList.add(new ProductImpl("Prime Striploin", "14oz", 28.34));
        this.productList.add(new ProductImpl("Prime Striploin", "16oz", 32.39));
        this.productList.add(new ProductImpl("Prime Striploin", "18oz", 36.44));
        this.productList.add(new ProductImpl("Prime Striploin", "20oz", 40.49));
        this.productList.add(new ProductImpl("Beef Burger", "18 burgers - 4oz/each", 33.00));
        this.productList.add(new ProductImpl("Beef Burger", "12 burgers - 6oz/each", 33.00));
        this.productList.add(new ProductImpl("Beef Burger", "9 burgers - 8oz/each", 33.00));
        this.productList.add(new ProductImpl("Pork Back Rib", "~26oz", 18.00));
        this.productList.add(new ProductImpl("Chicken Breast", "4oz", 2.6));
        this.productList.add(new ProductImpl("Chicken Breast", "6oz", 3.9));
        this.productList.add(new ProductImpl("Chicken Breast", "8oz", 5.2));
        this.productList.add(new ProductImpl("Chicken Breast", "10oz", 6.5));
        this.productList.add(new ProductImpl("Lamb Rack", "28oz", 50.0));

        //initialize the store with coupons
        this.couponList.add(new CouponImpl("coupon1", .15, new Date(1823223232323L)));
        this.couponList.add(new CouponImpl("coupon2", .25, new Date(1723223232323L)));
        this.couponList.add(new CouponImpl("coupon3", .35, new Date(1623223232323L)));
        this.couponList.add(new CouponImpl("coupon4", .45, new Date(1523223232323L)));
        this.couponList.add(new CouponImpl("coupon5", .55, new Date(1423223232323L)));
    }

    public List<Product> getProducts() throws RemoteException {
        return this.productList;
    }

    //Show in UI------------------------------------------------------------------------------------------------------------
    public StringBuilder showProducts() throws RemoteException {
        StringBuilder newString = new StringBuilder();
        if (productList.isEmpty()) {
            return(newString.append("There are no products in the product list"));
        }
        newString.append(productList.get(0).getName() + "\n");
        newString.append("    " + productList.get(0).getDescription() + ": " + productList.get(0).getPrice() + "\n");
        if (productList.size() == 1) {return newString;}
        for (int i = 1; i < productList.size(); i++) {
            if (!productList.get(i-1).getName().equals(productList.get(i).getName())){
                newString.append(productList.get(i).getName() + "\n");
            }
            newString.append("    " + productList.get(i).getDescription() + ": " + productList.get(i).getPrice() + "\n");
        }
        return newString;
    }

    public StringBuilder showCart() throws RemoteException {
        StringBuilder newString = new StringBuilder();
        newString.append("\nThis is a list of items in your shopping cart:\n\n");
        if (shoppingCart.isEmpty()) {
            return(newString.append("There are no products in your shopping cart"));
        }
        newString.append(shoppingCart.get(0).getName() + "\n");
        newString.append("    " + shoppingCart.get(0).getQuantity() + ": " + shoppingCart.get(0).getDescription() + ": " + shoppingCart.get(0).getPrice() + "\n");
        if (shoppingCart.size() == 1) {return newString;}
        for (int i = 1; i < shoppingCart.size(); i++) {
            if (!shoppingCart.get(i-1).getName().equals(shoppingCart.get(i).getName())){
                newString.append(shoppingCart.get(i).getName() + "\n");
            }
            newString.append("    " + shoppingCart.get(i).getQuantity() + ": " + shoppingCart.get(i).getDescription() + ": " + shoppingCart.get(i).getPrice() + "\n");
        }
        return newString;
    }
    //----------------------------------------------------------------------------------------------------------------------

    //adding to cart--------------------------------------------------------------------------------------------------------
    public String addProductToCart(String productName, String productDes, int quantity) throws RemoteException {
        int indexCart = inCart(productName, productDes);
        if (indexCart >= 0) {
            shoppingCart.get(indexCart).incrementQuantity(quantity);
            return (quantity + ": " + productName + " " + productDes + " has been added to cart");
        }
           
        
        int indexShop = inShop(productName, productDes);
        if (indexShop >= 0){
            shoppingCart.add(productList.get(indexShop));
            shoppingCart.get(shoppingCart.size()-1).incrementQuantity(quantity);
            return (quantity + ": " + productName + " " + productDes + " has been added to cart");
        }
        return ("There is no such item in the shop");
    }
    //----------------------------------------------------------------------------------------------------------------------

    //removing from cart----------------------------------------------------------------------------------------------------
    public String removeProductFromCart(String productName, String productDes, int quantity) throws RemoteException {
        int indexCart = inCart(productName, productDes);
        if (indexCart >= 0) {
            if (shoppingCart.get(indexCart).getQuantity() < quantity) {
                int removableQuantity = shoppingCart.get(indexCart).getQuantity();
                shoppingCart.get(indexCart).decrementQuantity(shoppingCart.get(indexCart).getQuantity());
                shoppingCart.remove(indexCart);
                return ("Only " + removableQuantity + ": " + productName + " " + productDes + " has been removed to cart due to insufficient quantity in your cart");
            }
            shoppingCart.get(indexCart).decrementQuantity(quantity);
            return (quantity + ": " + productName + " " + productDes + " has been removed to cart");
        }
           
        return ("You do not have this item in yout cart");

    }
    //----------------------------------------------------------------------------------------------------------------------
    //use coupon------------------------------------------------------------------------------------------------------------
    public String useCoupon(String coupon) throws RemoteException {
        if (this.discount != 1.0){
            return ("A coupon has already been applied");
        }
        for (Coupon c : couponList){
            if (c.getCode().equals(coupon) && c.getExpiryDate().after(new Date())){
                this.discount = c.getDiscount();
                return (coupon + " was used giving a " + (discount * 100) + "% discount");
            }
        }
        return ("This coupon is invalid");
    }
    //----------------------------------------------------------------------------------------------------------------------
    //checking out----------------------------------------------------------------------------------------------------------
    public double checkOut() throws RemoteException {
        double totalPrice = 0;
        double subtotal;
        double finalPrice;
        DecimalFormat df = new DecimalFormat("0.00");
        for (Product product : shoppingCart) {
            totalPrice += (product.getPrice() * product.getQuantity());
        }
        subtotal = totalPrice * (1 - discount);
        finalPrice = subtotal * (1 + TAX_RATE);
        finalPrice = Double.valueOf(df.format(finalPrice));
        this.receipt = getReceipt(totalPrice).toString();
        shoppingCart.clear();
        this.discount = 1.0;
        return finalPrice;
    }

    public String printReceipt() throws RemoteException {
        return this.receipt;
    }
    //helper functions-----------------------------------------------------------------------------------------------------
    public int inShop(String name, String des) throws RemoteException {
        for (int i = 0; i < productList.size(); i++){
            if (productList.get(i).getName().equals(name) && productList.get(i).getDescription().equals(des)){
                return i;
            }
        }
        return -1;
    }

    public int inCart(String name, String des) throws RemoteException {
        for (int i = 0; i < shoppingCart.size(); i++){
            if (shoppingCart.get(i).getName().equals(name) && shoppingCart.get(i).getDescription().equals(des)){
                return i;
            }
        }
        return -1;
    }

    public StringBuilder getReceipt(double subtotal) throws RemoteException {
        StringBuilder newString = new StringBuilder();
        double postdiscount = subtotal * (1 - this.discount);
        double tax = postdiscount * this.TAX_RATE;
        double finalPrice = postdiscount + tax;
        DecimalFormat df = new DecimalFormat("0.00");
        newString.append("\nThis is a list of items that you purchased:\n\n");
        if (shoppingCart.isEmpty()) {
            return(newString.append("You did not check out yet"));
        }
        newString.append(shoppingCart.get(0).getName() + "\n");
        newString.append("    " + shoppingCart.get(0).getQuantity() + ": " + shoppingCart.get(0).getDescription() + ": " + shoppingCart.get(0).getPrice());
        newString.append(" = " + shoppingCart.get(0).getPrice() * shoppingCart.get(0).getQuantity() + "\n");
        if (shoppingCart.size() == 1) {return newString;}
        for (int i = 1; i < shoppingCart.size(); i++) {
            if (!shoppingCart.get(i-1).getName().equals(shoppingCart.get(i).getName())){
                newString.append(shoppingCart.get(i).getName() + "\n");
            }
            newString.append("    " + shoppingCart.get(i).getQuantity() + ": " + shoppingCart.get(i).getDescription() + ": " + shoppingCart.get(i).getPrice());
            newString.append(" = " + shoppingCart.get(i).getPrice() * shoppingCart.get(i).getQuantity() + "\n");
        }
        newString.append("\n\n===========================================================\n");
        newString.append("Subtotal (before discount): " + subtotal + "\n");
        newString.append("Discount: " + (this.discount * 100) + "%\n");
        newString.append("Subtotal (after discount): " + Double.valueOf(df.format(postdiscount)) + "\n");
        newString.append("Tax: " + Double.valueOf(df.format(tax)) + "\n");
        newString.append("===========================================================\n");
        newString.append("Total: " + Double.valueOf(df.format(finalPrice)));
        return newString;
    }
    //----------------------------------------------------------------------------------------------------------------------
}
