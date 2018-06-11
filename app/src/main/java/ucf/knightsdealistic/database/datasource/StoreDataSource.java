package ucf.knightsdealistic.database.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ucf.knightsdealistic.DateUtility;
import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Swathi on 3/28/2015.
 */
public class StoreDataSource {

    // Database fields
    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public StoreDataSource(Context context) {
        databaseHandler = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        System.out.println("(+)open");
        if (databaseHandler != null && databaseHandler.getWritableDatabase() != null)
            database = databaseHandler.getWritableDatabase();
        System.out.println("(-)open");
    }

    public void close() {
        if (databaseHandler != null)
            databaseHandler.close();
    }

    public long createStore(Store store) {
        Log.d(DatabaseHandler.TAG, "createStore(+)");
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
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
        values.put(Store.COLUMN_STOREACTIVEFROM, DateUtility.getNow());
        values.put(Store.COLUMN_STOREACTIVETO, DateUtility.dateToString(store.getStoreActiveTo()));
        values.put(Store.COLUMN_ADMINID, "1");
        // insert row
        long storeId = database.insert(Store.TABLE_STORE, null, values);
        Log.d(DatabaseHandler.TAG, "createStore successful!");
        close();
        Log.d(DatabaseHandler.TAG, "createStore(-)");
        return storeId;
    }


    public List<Store> getAllPendingStoreRequests() throws Exception {
        database = databaseHandler.getReadableDatabase();
        List<Store> pendingStoreRequests = new ArrayList<Store>();
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_ADMINID + " = ?" + " AND " + Store.COLUMN_ISAPPROVED + "= ?" + "ORDER BY " + Store.COLUMN_REQUESTEDON +" ASC";
        Cursor c = database.rawQuery(selectQuery, new String[] { "1","false" });
        Store store =null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                store = new Store();
                store.setStoreId(c.getLong(c.getColumnIndex(Store.COLUMN_STOREID)));
                store.setStoreName(c.getString(c.getColumnIndex(Store.COLUMN_STORENAME)));
                store.setStoreDesc(c.getString((c.getColumnIndex(Store.COLUMN_STOREDESC))));
                store.setStorePassword(c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD)));
                store.setLocation(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
                store.setEmailId(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
                store.setMobileNumber(c.getLong((c.getColumnIndex(Store.COLUMN_STORELOCATION))));
                store.setApproved((c.getInt(c.getColumnIndex(Store.COLUMN_ISAPPROVED))== 1)? true : false);
                store.setSecurityQuestion(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYQUESTION)));
                store.setSecurityAnswer(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
                store.setRequestedOn(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_REQUESTEDON))));
                store.setStoreActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVEFROM))));
                store.setStoreActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVETO))));

                // adding to tags list
                pendingStoreRequests.add(store);
            } while (c.moveToNext());
        }
        close();
        return pendingStoreRequests;
    }

    public Store getStoreByEmailAddress(String emailAddress) throws Exception{
        database = databaseHandler.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_EMAILID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { emailAddress + "" });
        Store store=null;
        // looping through all rows and adding to list
        if (c.moveToNext()) {
            store = new Store();
            store.setStoreId(c.getLong(c.getColumnIndex(Store.COLUMN_STOREID)));
            store.setStoreName(c.getString(c.getColumnIndex(Store.COLUMN_STORENAME)));
            store.setStoreDesc(c.getString((c.getColumnIndex(Store.COLUMN_STOREDESC))));
            store.setStorePassword(c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD)));
            store.setLocation(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setEmailId(c.getString(c.getColumnIndex(Store.COLUMN_EMAILID)));
            store.setMobileNumber(c.getLong((c.getColumnIndex(Store.COLUMN_STORELOCATION))));
            store.setApproved((c.getInt(c.getColumnIndex(Store.COLUMN_ISAPPROVED))== 1)? true : false);
            store.setSecurityQuestion(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYQUESTION)));
            store.setSecurityAnswer(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setRequestedOn(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_REQUESTEDON))));
            store.setStoreActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVEFROM))));
            store.setStoreActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVETO))));
        }
        return store;
    }


    public Store getStoreByName(String storeName) throws Exception{
        database = databaseHandler.getReadableDatabase();
        Store store=null;
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STORENAME + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { storeName + "" });

        // looping through all rows and adding to list
        if (c.moveToNext()) {
            store = new Store();
            store.setStoreId(c.getLong(c.getColumnIndex(Store.COLUMN_STOREID)));
            store.setStoreName(c.getString(c.getColumnIndex(Store.COLUMN_STORENAME)));
            store.setStoreDesc(c.getString((c.getColumnIndex(Store.COLUMN_STOREDESC))));
            store.setStorePassword(c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD)));
            store.setLocation(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setEmailId(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setMobileNumber(c.getLong((c.getColumnIndex(Store.COLUMN_STORELOCATION))));
            store.setApproved((c.getInt(c.getColumnIndex(Store.COLUMN_ISAPPROVED))== 1)? true : false);
            store.setSecurityQuestion(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYQUESTION)));
            store.setSecurityAnswer(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setRequestedOn(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_REQUESTEDON))));
            store.setStoreActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVEFROM))));
            store.setStoreActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVETO))));
        }
        return store;
    }

    public Store getStoreById(Long storeId) throws Exception{
        database = databaseHandler.getReadableDatabase();
        Store store=null;
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STOREID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { storeId + "" });

        if (c.moveToNext()) {
            store = new Store();
            store.setStoreId(c.getLong(c.getColumnIndex(Store.COLUMN_STOREID)));
            store.setStoreName(c.getString(c.getColumnIndex(Store.COLUMN_STORENAME)));
            store.setStoreDesc(c.getString((c.getColumnIndex(Store.COLUMN_STOREDESC))));
            store.setStorePassword(c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD)));
            store.setLocation(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setEmailId(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
            store.setMobileNumber(c.getLong((c.getColumnIndex(Store.COLUMN_MOBILENUMBER))));
            store.setApproved((c.getInt(c.getColumnIndex(Store.COLUMN_ISAPPROVED))== 1)? true : false);
            store.setSecurityQuestion(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYQUESTION)));
            store.setSecurityAnswer(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYANSWER)));
            store.setRequestedOn(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_REQUESTEDON))));
            store.setStoreActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVEFROM))));
            store.setStoreActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVETO))));
        }
        return store;
    }

    public long changeStoreApprovalStatus(Store store,Long adminId) throws Exception {
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("store.isApproved()"+store.isApproved());
        values.put(Store.COLUMN_ISAPPROVED, store.isApproved());
        values.put(Store.COLUMN_ADMINID,adminId);
        long result = database.update(Store.TABLE_STORE, values,
                Store.COLUMN_STOREID + " =?",
                new String[]{String.valueOf(store.getStoreId())});
        return result;
    }

    public String getStorePasswordByEmailAddress(String emailAddress) throws Exception{
        database = databaseHandler.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_EMAILID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { emailAddress + "" });
        // looping through all rows and adding to list
        if (c.moveToNext()) {
           return c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD));
        }
        return null;
    }

    public Store getSecurityAnswerByQuestion(Long storeId,String securityQuestion) throws Exception{
        database = databaseHandler.getReadableDatabase();
        Store store=null;
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STOREID + " = ?" + " AND " + Store.COLUMN_SECURITYQUESTION + "= ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { String.valueOf(storeId), securityQuestion});
        // looping through all rows and adding to list
            if (c.moveToNext()) {
                store = new Store();
                store.setStoreId(c.getLong(c.getColumnIndex(Store.COLUMN_STOREID)));
                store.setStoreName(c.getString(c.getColumnIndex(Store.COLUMN_STORENAME)));
                store.setStoreDesc(c.getString((c.getColumnIndex(Store.COLUMN_STOREDESC))));
                store.setStorePassword(c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD)));
                store.setLocation(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
                store.setEmailId(c.getString(c.getColumnIndex(Store.COLUMN_STORELOCATION)));
                store.setMobileNumber(c.getLong((c.getColumnIndex(Store.COLUMN_MOBILENUMBER))));
                store.setApproved((c.getInt(c.getColumnIndex(Store.COLUMN_ISAPPROVED))== 1)? true : false);
                store.setSecurityQuestion(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYQUESTION)));
                store.setSecurityAnswer(c.getString(c.getColumnIndex(Store.COLUMN_SECURITYANSWER)));
                store.setRequestedOn(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_REQUESTEDON))));
                store.setStoreActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVEFROM))));
                store.setStoreActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Store.COLUMN_STOREACTIVETO))));
            }
            return store;
    }

    public boolean updateStorePassword(String storeName,String oldPassword,String newPassword) throws Exception {
        database = databaseHandler.getReadableDatabase();
        String oldPasswordfromDB=null;
        long storeId;
        String selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STORENAME + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[]{storeName + ""});
        if (c.moveToNext()) {
            oldPasswordfromDB=c.getString(c.getColumnIndex(Store.COLUMN_STOREPASSWORD));
            storeId=c.getLong(c.getColumnIndex(Store.COLUMN_STOREID));
            if(oldPasswordfromDB.equals(oldPassword)){
                database.close();
                database = databaseHandler.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Store.COLUMN_STOREPASSWORD, newPassword);
                 database.update(Store.TABLE_STORE, values,
                        Store.COLUMN_STOREID + " =?",
                        new String[]{String.valueOf(storeId)});
                return true;
            }
        }
        return false;
    }

    public boolean enterNewPassword(long storeId, String newpasswordtxt) throws Exception {
            database = databaseHandler.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Store.COLUMN_STOREID, storeId);
            values.put(Store.COLUMN_STOREPASSWORD,newpasswordtxt);
            long result = database.update(Store.TABLE_STORE, values,
                    Store.COLUMN_STOREID + " =?",
                    new String[]{String.valueOf(storeId)});
        if(result>0)
            return true;
        else
            return false;
        }
}
