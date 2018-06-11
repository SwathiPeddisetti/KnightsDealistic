package ucf.knightsdealistic.database.datasource;

/**
 * Created by Swathi on 3/14/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Store;

public class AdminDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public AdminDataSource(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        System.out.println("(+)open");
       if(databaseHandler!=null && databaseHandler.getWritableDatabase()!=null)
            database = databaseHandler.getWritableDatabase();
        System.out.println("(-)open");
    }

    public void close() {
        if(databaseHandler!=null)
             databaseHandler.close();
    }

    public long createAdmin(Admin admin) {
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Admin.COLUMN_ADMINNAME, admin.getAdminName());
        values.put(Admin.COLUMN_ADMINPASSWORD, admin.getAdminPassword());
        values.put(Admin.COLUMN_ADMINEMAILID, admin.getAdminEmailId());
        // insert row
        long adminId = database.insert(Admin.TABLE_ADMIN, null, values);
        close();
        return adminId;
    }

    public void deleteAdmin(Admin admin) {
        long id = admin.getAdminId();
        System.out.println("Admin deleted with id: " + id);
        //TODO Implement cascade delete
        database.delete(Admin.TABLE_ADMIN, Admin.COLUMN_ADMINID
                + " = " + id, null);
    }

    public List<Admin> getAllAdmins(){
        database = databaseHandler.getReadableDatabase();
        List<Admin> admins = new ArrayList<Admin>();
        String selectQuery = "SELECT  * FROM " + Admin.TABLE_ADMIN;
        Cursor c = database.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Admin a = new Admin();
                a.setAdminId(c.getLong(c.getColumnIndex(Admin.COLUMN_ADMINID)));
                a.setAdminName(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINNAME)));
                a.setAdminPassword(c.getString((c.getColumnIndex(Admin.COLUMN_ADMINPASSWORD))));
                a.setAdminEmailId(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINEMAILID)));
                // adding to tags list
                admins.add(a);
            } while (c.moveToNext());
        }
        close();
        return admins;
    }

    public Admin getAdminByName(String adminName){
        database = databaseHandler.getReadableDatabase();
        Admin admin=null;
        String selectQuery = "SELECT  * FROM " + Admin.TABLE_ADMIN+ " WHERE " + Admin.COLUMN_ADMINNAME + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { adminName + "" });

        // looping through all rows and adding to list
        if (c.moveToNext()) {
            admin= new Admin();
            admin.setAdminId(c.getLong(c.getColumnIndex(Admin.COLUMN_ADMINID)));
            admin.setAdminName(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINNAME)));
            admin.setAdminPassword(c.getString((c.getColumnIndex(Admin.COLUMN_ADMINPASSWORD))));
            admin.setAdminEmailId(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINEMAILID)));
        }
        close();
        return admin;
    }

    public Admin getAdminById(Long adminId){
        database = databaseHandler.getReadableDatabase();
        Admin admin=null;
        String selectQuery = "SELECT  * FROM " + Admin.TABLE_ADMIN+ " WHERE " + Admin.COLUMN_ADMINID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { adminId + "" });

        // looping through all rows and adding to list
        if (c.moveToNext()) {
            admin= new Admin();
            admin.setAdminId(c.getLong(c.getColumnIndex(Admin.COLUMN_ADMINID)));
            admin.setAdminName(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINNAME)));
            admin.setAdminPassword(c.getString((c.getColumnIndex(Admin.COLUMN_ADMINPASSWORD))));
            admin.setAdminEmailId(c.getString(c.getColumnIndex(Admin.COLUMN_ADMINEMAILID)));
        }
        close();
        return admin;
    }

}


