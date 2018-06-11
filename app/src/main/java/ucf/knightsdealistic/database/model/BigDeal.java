package ucf.knightsdealistic.database.model;

import java.util.Date;

/**
 * Created by Swathi on 3/13/2015.
 */
public class BigDeal {

    private long bigDealId;
    private Store store;
    private Admin admin;
    private String bigDealName;
    private String bigDealDesc;
    private Date addedOn;
    private Date tobePosted;
    private boolean isApproved;
    private double amount;

    public static final String TABLE_BIGDEAL= "bigdeal";
    public static final String COLUMN_BIGDEALID = "bigdealid";
    public static final String COLUMN_STOREID = "storeid";
    public static final String COLUMN_ADMINID = "adminid";
    public static final String COLUMN_BIGDEALNAME = "bigdealname";
    public static final String COLUMN_BIGDEALDESC = "bigdealdesc";
    public static final String COLUMN_ADDEDON = "addedon";
    public static final String COLUMN_TOBEPOSTED = "tobeposted";
    public static final String COLUMN_ISAPPROVED = "isapproved";
    public static final String COLUMN_AMOUNT = "amount";

    public BigDeal(){
    }

    public BigDeal(long bigDealId, Store store, Admin admin, String bigDealName, String bigDealDesc, Date addedOn, Date tobePosted, boolean isApproved, double amount) {
        this.bigDealId = bigDealId;
        this.store = store;
        this.admin = admin;
        this.bigDealName = bigDealName;
        this.bigDealDesc = bigDealDesc;
        this.addedOn = addedOn;
        this.tobePosted = tobePosted;
        this.isApproved = isApproved;
        this.amount = amount;
    }

    public BigDeal(String bigDealName, String bigDealDesc, Date addedOn, Date tobePosted, boolean isApproved, double amount,Store store, Admin admin) {
        this.store = store;
        this.admin = admin;
        this.bigDealName = bigDealName;
        this.bigDealDesc = bigDealDesc;
        this.addedOn = addedOn;
        this.tobePosted = tobePosted;
        this.isApproved = isApproved;
        this.amount = amount;
    }

    public long getBigDealId() {
        return bigDealId;
    }

    public void setBigDealId(long bigDealId) {
        this.bigDealId = bigDealId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getBigDealName() {
        return bigDealName;
    }

    public void setBigDealName(String bigDealName) {
        this.bigDealName = bigDealName;
    }

    public String getBigDealDesc() {
        return bigDealDesc;
    }

    public void setBigDealDesc(String bigDealDesc) {
        this.bigDealDesc = bigDealDesc;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }

    public Date getTobePosted() {
        return tobePosted;
    }

    public void setTobePosted(Date tobePosted) {
        this.tobePosted = tobePosted;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
