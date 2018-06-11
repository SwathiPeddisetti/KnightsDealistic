package ucf.knightsdealistic.database.model;

import java.util.Date;

/**
 * Created by Swathi on 3/13/2015.
 */
public class Deal {

    private long dealId;
    private Store store;
    private String dealName;
    private String dealDescription;
    private Date dealAddedOn;
    private Date dealActiveFrom;
    private Date dealActiveTo;

    public static final String TABLE_DEAL= "deal";
    public static final String COLUMN_DEALID = "dealid";
    public static final String COLUMN_STOREID = "storeid";
    public static final String COLUMN_DEALNAME = "dealname";
    public static final String COLUMN_DEALDESC = "dealdesc";
    public static final String COLUMN_DEALADDEDON = "dealaddedon";
    public static final String COLUMN_DEALACTIVEFROM = "dealactivefrom";
    public static final String COLUMN_DEALACTIVETO = "dealactiveto";

    public Deal(){

    }

    public Deal(long dealId, String dealName, String dealDescription, Date dealAddedOn, Date dealActiveFrom, Date dealActiveTo, Store store) {
        this.dealId = dealId;
        this.store = store;
        this.dealName = dealName;
        this.dealDescription = dealDescription;
        this.dealAddedOn = dealAddedOn;
        this.dealActiveFrom = dealActiveFrom;
        this.dealActiveTo = dealActiveTo;
    }

    public Deal(String dealName, String dealDescription, Date dealAddedOn, Date dealActiveFrom, Date dealActiveTo, Store store) {
        this.store = store;
        this.dealName = dealName;
        this.dealDescription = dealDescription;
        this.dealAddedOn = dealAddedOn;
        this.dealActiveFrom = dealActiveFrom;
        this.dealActiveTo = dealActiveTo;
    }

    public long getDealId() {
        return dealId;
    }

    public void setDealId(long dealId) {
        this.dealId = dealId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public Date getDealAddedOn() {
        return dealAddedOn;
    }

    public void setDealAddedOn(Date dealAddedOn) {
        this.dealAddedOn = dealAddedOn;
    }

    public Date getDealActiveFrom() {
        return dealActiveFrom;
    }

    public void setDealActiveFrom(Date dealActiveFrom) {
        this.dealActiveFrom = dealActiveFrom;
    }

    public Date getDealActiveTo() {
        return dealActiveTo;
    }

    public void setDealActiveTo(Date dealActiveTo) {
        this.dealActiveTo = dealActiveTo;
    }

}
