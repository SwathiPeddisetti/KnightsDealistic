package ucf.knightsdealistic.database.model;

/**
 * Created by Swathi on 3/13/2015.
 */
public class Admin {

    private long adminId;
    private String adminName;
    private String adminPassword;
    private String adminEmailId;

    public static final String TABLE_ADMIN = "admin";
    public static final String COLUMN_ADMINID = "adminid";
    public static final String COLUMN_ADMINNAME = "adminname";
    public static final String COLUMN_ADMINPASSWORD = "adminpassword";
    public static final String COLUMN_ADMINEMAILID = "adminemailid";

    public Admin(long adminId, String adminName, String adminPassword,String adminEmailId) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminEmailId= adminEmailId;
    }

    public Admin( String adminName, String adminPassword,String adminEmailId) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.adminEmailId= adminEmailId;
    }
    public Admin( String adminName, String adminPassword) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;

    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminEmailId() {
        return adminEmailId;
    }

    public void setAdminEmailId(String adminEmailId) {
        this.adminEmailId = adminEmailId;
    }

    public Admin() {

    }


}
