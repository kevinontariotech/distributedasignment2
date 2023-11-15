import java.rmi.RemoteException;

import java.util.Date;

public class CouponImpl implements Coupon {
    public String code;
    public double discount;
    public Date expiryDate;

    public CouponImpl(String code, double discount, Date expiryDate) throws RemoteException {
        this.code = code;
        this.discount = discount;
        this.expiryDate = expiryDate;
    }

    public String getCode() throws RemoteException {
        return this.code;
    }

    public double getDiscount() throws RemoteException {
        return this.discount;
    }

    public Date getExpiryDate() throws RemoteException {
        return this.expiryDate;
    }
}