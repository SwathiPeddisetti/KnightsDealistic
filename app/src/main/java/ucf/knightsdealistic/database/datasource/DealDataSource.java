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
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Swathi on 3/28/2015.
 */
public class DealDataSource {

    private SQLiteDatabase database;
    private DatabaseHandler databaseHandler;

    public DealDataSource(Context context) {
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

    public long createDeal(Deal deal) {
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Deal.COLUMN_DEALNAME, deal.getDealName());
        values.put(Deal.COLUMN_DEALDESC, deal.getDealDescription());
        values.put(Deal.COLUMN_DEALADDEDON, DateUtility.getNow());
        values.put(Deal.COLUMN_DEALACTIVEFROM, DateUtility.dateToString(deal.getDealActiveFrom()));
        values.put(Deal.COLUMN_DEALACTIVETO, DateUtility.dateToString(deal.getDealActiveTo()));
        values.put(Deal.COLUMN_STOREID, deal.getStore().getStoreId());
        // insert row
        long dealId = database.insert(Deal.TABLE_DEAL, null, values);
        close();
        return dealId;
    }

    public void deleteDeal(long dealId) {
        System.out.println("Deal deleted with id: " + dealId);
        //TODO Implement cascade delete
        database = databaseHandler.getWritableDatabase();
        database.delete(Deal.TABLE_DEAL, Deal.COLUMN_DEALID
                + " = " + dealId, null);
    }

    public long updateDeal(Deal deal) {
        long id = deal.getDealId();
        System.out.println("Deal updated with id: " + id);
        database = databaseHandler.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Deal.COLUMN_DEALNAME, deal.getDealName());
        values.put(Deal.COLUMN_DEALDESC, deal.getDealDescription());
        values.put(Deal.COLUMN_DEALACTIVEFROM, DateUtility.dateToString(deal.getDealActiveFrom()));
        values.put(Deal.COLUMN_DEALACTIVETO, DateUtility.dateToString(deal.getDealActiveTo()));

        long noofrows = database.update(Deal.TABLE_DEAL, values, Deal.COLUMN_DEALID + " = ?", new String[] { String.valueOf(id) });
        database.close();
        return noofrows;
    }



    public List<Deal> getAllDealsByStoreId(long storeId) throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<Deal> deals = new ArrayList<Deal>();
        String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL+ " WHERE " + Deal.COLUMN_STOREID + " = ?" + "ORDER BY " + Deal.COLUMN_DEALACTIVEFROM +" DESC";
        Cursor c = database.rawQuery(selectQuery, new String[] { storeId + "" });
        Deal deal=null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                deal = new Deal();
                deal.setDealId(c.getLong(c.getColumnIndex(Deal.COLUMN_DEALID)));
                deal.setDealName(c.getString(c.getColumnIndex(Deal.COLUMN_DEALNAME)));
                deal.setDealDescription(c.getString((c.getColumnIndex(Deal.COLUMN_DEALDESC))));
                deal.setDealActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))));
                deal.setDealActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))));
               // adding to tags list
                deals.add(deal);
            } while (c.moveToNext());
        }
        return deals;
    }

    public List<Deal> getAllUpcomingDealsByActiveFromDate(Date activeFromDate) throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<Deal> deals = new ArrayList<Deal>();
        String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL+ " WHERE " + Deal.COLUMN_DEALACTIVEFROM + " > ?" + "ORDER BY " + Deal.COLUMN_DEALACTIVEFROM +" DESC";
        Cursor c = database.rawQuery(selectQuery, new String[] { activeFromDate + "" });
        Deal deal=null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                deal = new Deal();
                deal.setDealId(c.getLong(c.getColumnIndex(Deal.COLUMN_DEALID)));
                deal.setDealName(c.getString(c.getColumnIndex(Deal.COLUMN_DEALNAME)));
                deal.setDealDescription(c.getString((c.getColumnIndex(Deal.COLUMN_DEALDESC))));
                deal.setDealActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))));
                deal.setDealActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))));
                // adding to tags list
                deals.add(deal);
            } while (c.moveToNext());
        }
        close();
        return deals;
    }

    public List<Deal> getDealsOfTheDay() throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<Deal> deals = new ArrayList<Deal>();
        String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL+ " WHERE " + Deal.COLUMN_DEALACTIVEFROM + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { DateUtility.getNow() + "" });
        Deal deal=null;
        Store store=null;
        Cursor c1 =null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                deal = new Deal();
                deal.setDealId(c.getLong(c.getColumnIndex(Deal.COLUMN_DEALID)));
                deal.setDealName(c.getString(c.getColumnIndex(Deal.COLUMN_DEALNAME)));
                deal.setDealDescription(c.getString((c.getColumnIndex(Deal.COLUMN_DEALDESC))));
                deal.setDealActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))));
                deal.setDealActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))));

                //retrieve the storeid from deal table..then retrieve the store from store table using this storeid
                selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STOREID + " = ?";
                c1 = database.rawQuery(selectQuery, new String[] { c.getString(c.getColumnIndex(Deal.COLUMN_STOREID)) + "" });
                if (c1.moveToNext()) {
                    store = new Store();
                    store.setStoreId(c1.getLong(c1.getColumnIndex(Store.COLUMN_STOREID)));
                    store.setStoreName(c1.getString(c1.getColumnIndex(Store.COLUMN_STORENAME)));
                    store.setStoreDesc(c1.getString((c1.getColumnIndex(Store.COLUMN_STOREDESC))));
                    store.setLocation(c1.getString(c1.getColumnIndex(Store.COLUMN_STORELOCATION)));
                    store.setEmailId(c1.getString(c1.getColumnIndex(Store.COLUMN_EMAILID)));
                    store.setMobileNumber(c1.getLong((c1.getColumnIndex(Store.COLUMN_MOBILENUMBER))));
                }
                deal.setStore(store);
                // adding to tags list
                deals.add(deal);
            } while (c.moveToNext());
        }
        close();
        return deals;
    }

    public List<Deal> getDealsBySearch(String searchItem) throws Exception{
        database = databaseHandler.getReadableDatabase();
        List<Deal> deals = new ArrayList<Deal>();
        String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL+ " WHERE " + Deal.COLUMN_DEALNAME + "LIKE ?" + " OR " + Deal.COLUMN_DEALDESC + "LIKE ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { "%"+searchItem+"%" + "", "%"+searchItem+"%" + "" });
        Deal deal=null;
        Store store=null;
        Cursor c1 =null;
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                deal = new Deal();
                deal.setDealId(c.getLong(c.getColumnIndex(Deal.COLUMN_DEALID)));
                deal.setDealName(c.getString(c.getColumnIndex(Deal.COLUMN_DEALNAME)));
                deal.setDealDescription(c.getString((c.getColumnIndex(Deal.COLUMN_DEALDESC))));
                deal.setDealActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))));
                deal.setDealActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))));

                //retrieve the storeid from deal table..then retrieve the store from store table using this storeid
                selectQuery = "SELECT  * FROM " + Store.TABLE_STORE + " WHERE " + Store.COLUMN_STOREID + " = ?";
                c1 = database.rawQuery(selectQuery, new String[] { c.getString(c.getColumnIndex(Deal.COLUMN_STOREID)) + "" });
                if (c1.moveToNext()) {
                    store = new Store();
                    store.setStoreId(c1.getLong(c1.getColumnIndex(Store.COLUMN_STOREID)));
                    store.setStoreName(c1.getString(c1.getColumnIndex(Store.COLUMN_STORENAME)));
                    store.setStoreDesc(c1.getString((c1.getColumnIndex(Store.COLUMN_STOREDESC))));
                    store.setLocation(c1.getString(c1.getColumnIndex(Store.COLUMN_STORELOCATION)));
                    store.setEmailId(c1.getString(c1.getColumnIndex(Store.COLUMN_EMAILID)));
                    store.setMobileNumber(c1.getLong((c1.getColumnIndex(Store.COLUMN_MOBILENUMBER))));
                }
                deal.setStore(store);
                // adding to tags list
                deals.add(deal);
            } while (c.moveToNext());
        }
        close();
        return deals;
    }

    public Deal getDealById(long dealId) throws Exception{
        database = databaseHandler.getReadableDatabase();
        Deal deal=null;
        String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL + " WHERE " + Deal.COLUMN_DEALID + " = ?";
        Cursor c = database.rawQuery(selectQuery, new String[] { dealId + "" });

        if (c.moveToNext()) {
            deal = new Deal();
            deal.setDealId(c.getLong(c.getColumnIndex(Deal.COLUMN_DEALID)));
            deal.setDealName(c.getString(c.getColumnIndex(Deal.COLUMN_DEALNAME)));
            deal.setDealDescription(c.getString((c.getColumnIndex(Deal.COLUMN_DEALDESC))));
            deal.setDealActiveFrom(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))));
            deal.setDealActiveTo(DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))));
        }
        return deal;
    }

    public boolean isDealDuplicate(String dealName,Date activeFrom,Date activeTo,long storeId){
        boolean isDealDuplicate = false;
        try {
            database = databaseHandler.getReadableDatabase();
            String selectQuery = "SELECT  * FROM " + Deal.TABLE_DEAL + " WHERE " + Deal.COLUMN_DEALNAME + " = ?" + " AND " + Deal.COLUMN_STOREID + " = ?";
            Cursor c = database.rawQuery(selectQuery, new String[]{dealName + "", String.valueOf(storeId) + ""});

            if (c.moveToNext()) {
                if (DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVEFROM))).compareTo(activeFrom) == 0)
                    return true;
                else if (DateUtility.stringToDate(c.getString(c.getColumnIndex(Deal.COLUMN_DEALACTIVETO))).compareTo(activeTo) == 0)
                    return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return isDealDuplicate;
    }



}
