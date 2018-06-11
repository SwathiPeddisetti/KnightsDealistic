package ucf.knightsdealistic.database.datasource;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ucf.knightsdealistic.DateUtility;
import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Swathi on 3/28/2015.
 */
public class BigDealDataSource {

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public BigDealDataSource(Context context) {
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

    public long createBigDeal(BigDeal bigDeal) {
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BigDeal.COLUMN_BIGDEALNAME, bigDeal.getBigDealName());
        values.put(BigDeal.COLUMN_BIGDEALDESC, bigDeal.getBigDealDesc());
        values.put(BigDeal.COLUMN_AMOUNT, bigDeal.getAmount());
        values.put(BigDeal.COLUMN_TOBEPOSTED, DateUtility.dateToString(bigDeal.getTobePosted()));
        values.put(BigDeal.COLUMN_ADDEDON, DateUtility.getNow());
        values.put(BigDeal.COLUMN_ADMINID,"1");
        values.put(BigDeal.COLUMN_ISAPPROVED, "false");
        values.put(BigDeal.COLUMN_STOREID, bigDeal.getStore().getStoreId());
        // insert row
        long bigDealId = database.insert(BigDeal.TABLE_BIGDEAL, null, values);
        close();
        return bigDealId;
    }

    public void deleteBigDeal(long bigDealId) {
        database = databaseHandler.getWritableDatabase();
        System.out.println("BigDeal deleted with id: " + bigDealId);
        //TODO Implement cascade delete
        database.delete(BigDeal.TABLE_BIGDEAL, BigDeal.COLUMN_BIGDEALID
                + " = " + bigDealId, null);
        close();
    }

    public long updateBigDeal(BigDeal bigDeal) {
        long id = bigDeal.getBigDealId();
        System.out.println("BigDeal deleted with id: " + id);
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BigDeal.COLUMN_BIGDEALNAME, bigDeal.getBigDealName());
        values.put(BigDeal.COLUMN_BIGDEALDESC, bigDeal.getBigDealDesc());
        values.put(BigDeal.COLUMN_TOBEPOSTED,DateUtility.dateToString(bigDeal.getTobePosted()));
        values.put(BigDeal.COLUMN_AMOUNT, bigDeal.getAmount());

        long noofrows = database.update(BigDeal.TABLE_BIGDEAL, values, BigDeal.COLUMN_BIGDEALID + " = ?", new String[] { String.valueOf(id) });
        database.close();
        return noofrows;
    }

    public List<BigDeal> getAllBigDealsByStoreId(long storeId) throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<BigDeal> bigDeals = new ArrayList<BigDeal>();
        String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL+ " WHERE " + BigDeal.COLUMN_STOREID + " = ?" + "ORDER BY " + BigDeal.COLUMN_TOBEPOSTED +" DESC";
        Cursor c = database.rawQuery(selectQuery, new String[] { storeId + "" });
        BigDeal bigDeal=null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                bigDeal = new BigDeal();
                bigDeal.setBigDealId(c.getLong(c.getColumnIndex(BigDeal.COLUMN_BIGDEALID)));
                bigDeal.setBigDealName(c.getString(c.getColumnIndex(BigDeal.COLUMN_BIGDEALNAME)));
                bigDeal.setBigDealDesc(c.getString((c.getColumnIndex(BigDeal.COLUMN_BIGDEALDESC))));
                bigDeal.setApproved((c.getInt(c.getColumnIndex(BigDeal.COLUMN_ISAPPROVED))== 1)? true : false);
                bigDeal.setTobePosted(DateUtility.stringToDate(c.getString(c.getColumnIndex(BigDeal.COLUMN_TOBEPOSTED))));
                bigDeal.setAmount(c.getLong(c.getColumnIndex(BigDeal.COLUMN_AMOUNT)));
                // adding to tags list
                bigDeals.add(bigDeal);
            } while (c.moveToNext());
        }
        close();
        return bigDeals;
    }

    public BigDeal getBigDealOfTheDay() throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<BigDeal> bigDeals = new ArrayList<BigDeal>();
        String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL+ " WHERE " + BigDeal.COLUMN_TOBEPOSTED + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { DateUtility.getNow() + "" });
        BigDeal bigDeal=null;
        // looping through all rows and adding to list
        if (c.moveToNext()) {
                bigDeal = new BigDeal();
                bigDeal.setBigDealId(c.getLong(c.getColumnIndex(BigDeal.COLUMN_BIGDEALID)));
                bigDeal.setBigDealName(c.getString(c.getColumnIndex(BigDeal.COLUMN_BIGDEALNAME)));
                bigDeal.setBigDealDesc(c.getString((c.getColumnIndex(BigDeal.COLUMN_BIGDEALDESC))));
        }
        close();
        return bigDeal;
    }

    public List<BigDeal> getAllPendingBigDeals() throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<BigDeal> bigDeals = new ArrayList<BigDeal>();
        String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL+ " WHERE " + BigDeal.COLUMN_ADMINID + " = ?" + " AND " + BigDeal.COLUMN_ISAPPROVED + "= ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { "1","0" });
        BigDeal bigDeal=null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                bigDeal = new BigDeal();
                bigDeal.setBigDealId(c.getLong(c.getColumnIndex(BigDeal.COLUMN_BIGDEALID)));
                bigDeal.setBigDealName(c.getString(c.getColumnIndex(BigDeal.COLUMN_BIGDEALNAME)));
                bigDeal.setBigDealDesc(c.getString((c.getColumnIndex(BigDeal.COLUMN_BIGDEALDESC))));
                bigDeal.setApproved((c.getInt(c.getColumnIndex(BigDeal.COLUMN_ISAPPROVED))== 1)? true : false);
                bigDeal.setTobePosted(DateUtility.stringToDate(c.getString(c.getColumnIndex(BigDeal.COLUMN_TOBEPOSTED))));
                bigDeal.setAmount(c.getLong(c.getColumnIndex(BigDeal.COLUMN_AMOUNT)));
                // adding to tags list
                bigDeals.add(bigDeal);
            } while (c.moveToNext());
        }
        close();
        return bigDeals;
    }

    public List<BigDeal> getAllPendingBigDealsOfTheDay() throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<BigDeal> bigDeals = new ArrayList<BigDeal>();
        String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL+ " WHERE " + BigDeal.COLUMN_ISAPPROVED + "= ?"+ " AND " + BigDeal.COLUMN_TOBEPOSTED + "= ?" + "ORDER BY " + BigDeal.COLUMN_AMOUNT +" DESC";
        Cursor c = database.rawQuery(selectQuery, new String[] { "false",DateUtility.add3DaysToCurrentDate() });
        BigDeal bigDeal=null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                bigDeal = new BigDeal();
                bigDeal.setBigDealId(c.getLong(c.getColumnIndex(BigDeal.COLUMN_BIGDEALID)));
                bigDeal.setBigDealName(c.getString(c.getColumnIndex(BigDeal.COLUMN_BIGDEALNAME)));
                bigDeal.setBigDealDesc(c.getString((c.getColumnIndex(BigDeal.COLUMN_BIGDEALDESC))));
                bigDeal.setApproved((c.getInt(c.getColumnIndex(BigDeal.COLUMN_ISAPPROVED))== 1)? true : false);
                bigDeal.setTobePosted(DateUtility.stringToDate(c.getString(c.getColumnIndex(BigDeal.COLUMN_TOBEPOSTED))));
                bigDeal.setAmount(c.getLong(c.getColumnIndex(BigDeal.COLUMN_AMOUNT)));
                // adding to tags list
                bigDeals.add(bigDeal);
            } while (c.moveToNext());
        }
        close();
        return bigDeals;
    }

    public long approveADeal(List<BigDeal> bigDeals,long adminId) {
        database = databaseHandler.getWritableDatabase();
        ContentValues values = null;
        long result =0L;
        for(BigDeal bigDeal:bigDeals){
            values = new ContentValues();
            values.put(BigDeal.COLUMN_ISAPPROVED, bigDeal.isApproved());
            values.put(BigDeal.COLUMN_ADMINID,adminId);
            result = database.update(BigDeal.TABLE_BIGDEAL, values,
                    BigDeal.COLUMN_BIGDEALID + " =?",
                    new String[]{String.valueOf(bigDeal.getBigDealId())});
        }
        close();
        return result;
    }

    public BigDeal getBigDealById(long bigDealId) throws Exception{
        database = databaseHandler.getReadableDatabase();
        BigDeal bigDeal=null;
        String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL + " WHERE " + BigDeal.COLUMN_BIGDEALID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { bigDealId + "" });

        if (c.moveToNext()) {
            bigDeal=new BigDeal();
            bigDeal.setBigDealId(c.getLong(c.getColumnIndex(BigDeal.COLUMN_BIGDEALID)));
            bigDeal.setBigDealName(c.getString(c.getColumnIndex(BigDeal.COLUMN_BIGDEALNAME)));
            bigDeal.setBigDealDesc(c.getString((c.getColumnIndex(BigDeal.COLUMN_BIGDEALDESC))));
            bigDeal.setApproved((c.getInt(c.getColumnIndex(BigDeal.COLUMN_ISAPPROVED))== 1)? true : false);
            bigDeal.setTobePosted(DateUtility.stringToDate(c.getString(c.getColumnIndex(BigDeal.COLUMN_TOBEPOSTED))));
            bigDeal.setAmount(c.getLong(c.getColumnIndex(BigDeal.COLUMN_AMOUNT)));
        }
        return bigDeal;
    }

    public boolean isBigDealDuplicate(String dealName,long storeId,Date toBePosted){
        boolean isDealDuplicate = false;
        try {
            database = databaseHandler.getReadableDatabase();
            String selectQuery = "SELECT  * FROM " + BigDeal.TABLE_BIGDEAL + " WHERE " + BigDeal.COLUMN_BIGDEALNAME + " = ?" + " AND " + BigDeal.COLUMN_STOREID + " = ?" + " AND " + BigDeal.COLUMN_TOBEPOSTED + " = ?";
            Cursor c = database.rawQuery(selectQuery, new String[]{dealName + "", String.valueOf(storeId) + "",DateUtility.dateToString(toBePosted)});

            if (c.moveToNext()) {
               return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isDealDuplicate;
    }

}
