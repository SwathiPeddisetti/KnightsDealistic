package ucf.knightsdealistic.database.model;

import java.util.Date;

/**
 * Created by Swathi on 3/13/2015.
 */
public class Store {

    private long storeId;
    private Admin admin;
    private String storeName;
    private String storeDesc;
    private String storePassword;
    private String location;
    private String emailId;
    private long mobileNumber;
    private boolean isApproved;
    private String securityQuestion;
    private String securityAnswer;
    private Date requestedOn;
    private Date storeActiveFrom;
    private Date storeActiveTo;

    public static final String TABLE_STORE= "store";
    public static final String COLUMN_STOREID = "storeid";
    public static final String COLUMN_ADMINID = "adminid";
    public static final String COLUMN_STORENAME = "storename";
    public static final String COLUMN_STOREDESC = "storedesc";
    public static final String COLUMN_STOREPASSWORD = "storepassword";
    public static final String COLUMN_STORELOCATION = "location";
    public static final String COLUMN_EMAILID = "emailid";
    public static final String COLUMN_MOBILENUMBER = "mobilenumber";
    public static final String COLUMN_ISAPPROVED = "isapproved";
    public static final String COLUMN_SECURITYQUESTION = "securityquestion";
    public static final String COLUMN_SECURITYANSWER = "securityanswer";
    public static final String COLUMN_REQUESTEDON = "requestedon";
    public static final String COLUMN_STOREACTIVEFROM = "storeactivefrom";
    public static final String COLUMN_STOREACTIVETO = "storeactiveto";

    public Store(long storeId, Admin admin, String storeName, String storeDesc, String storePassword, String location, String emailId, long mobileNumber, boolean isApproved, String securityQuestion, String securityAnswer, Date requestedOn, Date storeActiveFrom, Date storeActiveTo) {
        this.storeId = storeId;
        this.admin = admin;
        this.storeName = storeName;
        this.storeDesc = storeDesc;
        this.storePassword = storePassword;
        this.location = location;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.isApproved = isApproved;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.requestedOn = requestedOn;
        this.storeActiveFrom = storeActiveFrom;
        this.storeActiveTo = storeActiveTo;
    }

    public Store( String storeName, String storeDesc, String storePassword, String location, String emailId, long mobileNumber, boolean isApproved, String securityQuestion, String securityAnswer, Date requestedOn, Date storeActiveFrom, Date storeActiveTo,Admin admin) {
        this.admin = admin;
        this.storeName = storeName;
        this.storeDesc = storeDesc;
        this.storePassword = storePassword;
        this.location = location;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
        this.isApproved = isApproved;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.requestedOn = requestedOn;
        this.storeActiveFrom = storeActiveFrom;
        this.storeActiveTo = storeActiveTo;
    }

    public Store()
    {
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDesc() {
        return storeDesc;
    }

    public void setStoreDesc(String storeDesc) {
        this.storeDesc = storeDesc;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public Date getStoreActiveFrom() {
        return storeActiveFrom;
    }

    public void setStoreActiveFrom(Date storeActiveFrom) {
        this.storeActiveFrom = storeActiveFrom;
    }

    public Date getStoreActiveTo() {
        return storeActiveTo;
    }

    public void setStoreActiveTo(Date storeActiveTo) {
        this.storeActiveTo = storeActiveTo;
    }

}
