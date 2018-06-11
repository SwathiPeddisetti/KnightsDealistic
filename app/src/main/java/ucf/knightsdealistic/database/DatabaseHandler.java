package ucf.knightsdealistic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import ucf.knightsdealistic.DateUtility;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;
import ucf.knightsdealistic.database.model.Student;

/**
 * Created by Swathi on 3/13/2015.
 */


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "knightsdeals.db";
    public static final String TAG = "Knights Dealistic";
    Context context = null;

    public static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + Student.TABLE_STUDENT  + "("
            + Student.COLUMN_NID  + " TEXT PRIMARY KEY ,"
            + Student.COLUMN_STUDENTEMAILID + " TEXT, "
            + Student.COLUMN_STUDENTNAME + " TEXT, "
            + Student.COLUMN_STUDENTPASSWORD + " TEXT )";

    public static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + Admin.TABLE_ADMIN  + "("
            + Admin.COLUMN_ADMINID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
            + Admin.COLUMN_ADMINNAME + " TEXT, "
            + Admin.COLUMN_ADMINPASSWORD + " TEXT, "
            + Admin.COLUMN_ADMINEMAILID + " TEXT )";

    public static final String CREATE_TABLE_STORE = "CREATE TABLE " + Store.TABLE_STORE  + "("
            + Store.COLUMN_STOREID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Store.COLUMN_ADMINID  + " INTEGER,"
            + Store.COLUMN_STORENAME + " TEXT, "
            + Store.COLUMN_STOREDESC + " TEXT, "
            + Store.COLUMN_STOREPASSWORD + " TEXT, "
            + Store.COLUMN_STORELOCATION + " TEXT, "
            + Store.COLUMN_EMAILID + " TEXT, "
            + Store.COLUMN_MOBILENUMBER + " INTEGER, "
            + Store.COLUMN_ISAPPROVED+ " TEXT, "
            + Store.COLUMN_SECURITYQUESTION + " TEXT, "
            + Store.COLUMN_SECURITYANSWER + " TEXT, "
            + Store.COLUMN_REQUESTEDON + " DATETIME, "
            + Store.COLUMN_STOREACTIVEFROM + " DATETIME, "
            + Store.COLUMN_STOREACTIVETO + " DATETIME )";
     //       + "FOREIGN KEY(" + Store.COLUMN_ADMINID  + ") REFERENCES "
      //      + Admin.COLUMN_ADMINID + " )";

    public static final String CREATE_TABLE_DEAL = "CREATE TABLE " + Deal.TABLE_DEAL  + "("
            + Deal.COLUMN_DEALID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + Deal.COLUMN_STOREID  + " INTEGER,"
            + Deal.COLUMN_DEALNAME + " TEXT, "
            + Deal.COLUMN_DEALDESC + " TEXT, "
            + Deal.COLUMN_DEALADDEDON + " DATETIME, "
            + Deal.COLUMN_DEALACTIVEFROM + " DATETIME, "
            + Deal.COLUMN_DEALACTIVETO + " DATETIME)";
       //     + "FOREIGN KEY(" + Deal.COLUMN_STOREID  + ") REFERENCES "
       //     + Store.COLUMN_STOREID + " )";

    public static final String CREATE_TABLE_BIGDEAL = "CREATE TABLE " + BigDeal.TABLE_BIGDEAL  + "("
            + BigDeal.COLUMN_BIGDEALID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
            + BigDeal.COLUMN_STOREID  + " INTEGER,"
            + BigDeal.COLUMN_ADMINID + " INTEGER, "
            + BigDeal.COLUMN_BIGDEALNAME + " TEXT, "
            + BigDeal.COLUMN_BIGDEALDESC + " TEXT, "
            + BigDeal.COLUMN_ADDEDON + " DATETIME, "
            + BigDeal.COLUMN_TOBEPOSTED + " DATETIME, "
            + BigDeal.COLUMN_ISAPPROVED + " TEXT, "
            + BigDeal.COLUMN_AMOUNT + " REAL)";
      //      + "FOREIGN KEY(" + BigDeal.COLUMN_STOREID  + ") REFERENCES "
        //    + Store.COLUMN_STOREID + " , "
      //      + "FOREIGN KEY(" + BigDeal.COLUMN_ADMINID  + ") REFERENCES "
        //    + Admin.COLUMN_ADMINID + " )";

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    private static DatabaseHandler instance;

    public static synchronized DatabaseHandler getHelper(Context context) {
        if (instance == null)
            instance = new DatabaseHandler(context);
        return instance;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("oncreate(+)");
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_STORE);
        db.execSQL(CREATE_TABLE_DEAL);
        db.execSQL(CREATE_TABLE_BIGDEAL);

        ContentValues values = new ContentValues();
        long adminId =0L;
        Admin admin= null;
        admin = new Admin("a1","p1","e1@gmail.com");
        values.put(Admin.COLUMN_ADMINNAME, admin.getAdminName());
        values.put(Admin.COLUMN_ADMINPASSWORD, admin.getAdminPassword());
        values.put(Admin.COLUMN_ADMINEMAILID, admin.getAdminEmailId());
        // insert row
        adminId = db.insert(Admin.TABLE_ADMIN, null, values);
        System.out.println(adminId);

        admin = new Admin("a2","p2","e1@gmail.com");
        values = new ContentValues();
        values.put(Admin.COLUMN_ADMINNAME, admin.getAdminName());
        values.put(Admin.COLUMN_ADMINPASSWORD, admin.getAdminPassword());
        values.put(Admin.COLUMN_ADMINEMAILID, admin.getAdminEmailId());
        // insert row
        adminId = db.insert(Admin.TABLE_ADMIN, null, values);
        System.out.println(adminId);

        Student student=null;
        long studentid=0L;
        student=new Student("nid1", "nid1@gmail.com", "s1", "p1");
        values = new ContentValues();
        values.put(Student.COLUMN_NID, student.getNID());
        values.put(Student.COLUMN_STUDENTNAME, student.getStudentName());
        values.put(Student.COLUMN_STUDENTPASSWORD, student.getStudentPassword());
        values.put(Student.COLUMN_STUDENTEMAILID,student.getStudentEmailId());
        studentid=db.insert(Student.TABLE_STUDENT,null,values);
        System.out.println(studentid);

        student=new Student("nid2", "nid2@gmail.com", "s2", "p2");
        values = new ContentValues();
        values.put(Student.COLUMN_NID, student.getNID());
        values.put(Student.COLUMN_STUDENTNAME, student.getStudentName());
        values.put(Student.COLUMN_STUDENTPASSWORD, student.getStudentPassword());
        values.put(Student.COLUMN_STUDENTEMAILID,student.getStudentEmailId());
        studentid=db.insert(Student.TABLE_STUDENT,null,values);
        System.out.println(studentid);

        student=new Student("nid3", "nid2@gmail.com", "s2", "p2");
        values = new ContentValues();
        values.put(Student.COLUMN_NID, student.getNID());
        values.put(Student.COLUMN_STUDENTNAME, student.getStudentName());
        values.put(Student.COLUMN_STUDENTPASSWORD, student.getStudentPassword());
        values.put(Student.COLUMN_STUDENTEMAILID,student.getStudentEmailId());
        studentid=db.insert(Student.TABLE_STUDENT,null,values);
        System.out.println(studentid);

        Store store=null;
        store=new Store("s1","storeDesc", "p1", "location", "emailId",9999L,false,"securityQuestion", "securityAnswer", DateUtility.stringToDate(DateUtility.getNow()), new Date("2/4/2015"), new Date("2/4/2016"),admin);
        values = new ContentValues();
        values.put(Store.COLUMN_STORENAME, store.getStoreName());
        values.put(Store.COLUMN_STOREDESC, store.getStoreDesc());
        values.put(Store.COLUMN_STOREPASSWORD, store.getStorePassword());
        values.put(Store.COLUMN_STORELOCATION, store.getLocation());
        values.put(Store.COLUMN_EMAILID, store.getEmailId());
        values.put(Store.COLUMN_MOBILENUMBER, store.getMobileNumber());
        values.put(Store.COLUMN_ISAPPROVED, "false");
        values.put(Store.COLUMN_SECURITYQUESTION, store.getSecurityQuestion());
        values.put(Store.COLUMN_SECURITYANSWER, store.getSecurityAnswer());
        values.put(Store.COLUMN_REQUESTEDON, DateUtility.getNow());
        values.put(Store.COLUMN_STOREACTIVEFROM, DateUtility.dateToString(store.getStoreActiveFrom()));
        values.put(Store.COLUMN_STOREACTIVETO, DateUtility.dateToString(store.getStoreActiveTo()));
        values.put(Store.COLUMN_ADMINID, "1");
        // insert row
        long storeId = db.insert(Store.TABLE_STORE, null, values);
        System.out.println(storeId);

        store=new Store("s2","storeDesc", "p2", "location", "emailId",9999L,false,"securityQuestion", "securityAnswer", DateUtility.stringToDate(DateUtility.getNow()), new Date("2/4/2015"), new Date("2/4/2016"),admin);
        values = new ContentValues();
        values.put(Store.COLUMN_STORENAME, store.getStoreName());
        values.put(Store.COLUMN_STOREDESC, store.getStoreDesc());
        values.put(Store.COLUMN_STOREPASSWORD, store.getStorePassword());
        values.put(Store.COLUMN_STORELOCATION, store.getLocation());
        values.put(Store.COLUMN_EMAILID, store.getEmailId());
        values.put(Store.COLUMN_MOBILENUMBER, store.getMobileNumber());
        values.put(Store.COLUMN_ISAPPROVED, "false");
        values.put(Store.COLUMN_SECURITYQUESTION, store.getSecurityQuestion());
        values.put(Store.COLUMN_SECURITYANSWER, store.getSecurityAnswer());
        values.put(Store.COLUMN_REQUESTEDON, DateUtility.getNow());
        values.put(Store.COLUMN_STOREACTIVEFROM, DateUtility.dateToString(store.getStoreActiveFrom()));
        values.put(Store.COLUMN_STOREACTIVETO, DateUtility.dateToString(store.getStoreActiveTo()));
        values.put(Store.COLUMN_ADMINID, "1");
        // insert row
        storeId = db.insert(Store.TABLE_STORE, null, values);
        System.out.println(storeId);

        System.out.println("oncreate(-)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onupdate(+)");
        db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + BigDeal.TABLE_BIGDEAL);
        db.execSQL("DROP TABLE IF EXISTS " + Deal.TABLE_DEAL);
        db.execSQL("DROP TABLE IF EXISTS " + Store.TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + Admin.TABLE_ADMIN);
        onCreate(db);

        System.out.println("onupdate(-)");

    }



}