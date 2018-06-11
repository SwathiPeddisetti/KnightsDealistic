package ucf.knightsdealistic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Swathi on 3/16/2015.
 */
public class TabPendingStoreReq extends Activity {

    TableLayout pendingStoreReqTable;
    StoreDataSource storeDataSource;
    UserSessionManager session;
    long adminId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        View b = findViewById(R.id.logo);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.txtlogout);
        b.setVisibility(View.GONE);
        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        pendingStoreReqTable = (TableLayout) this.findViewById( R.id.pendingStoreReqTable);
    }

    @Override
    public void onStart() {
        super.onStart();
        storeDataSource=new StoreDataSource(this);
        HashMap<String, String> user = session.getUserDetails();
        adminId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
        addRowsToTable(pendingStoreReqTable);
    }

    public void deleteRowsFromTable(int i) {
        if ( pendingStoreReqTable.getChildCount() > 2)
            pendingStoreReqTable.removeViewAt(i);
           // pendingStoreReqTable.removeViews(2,pendingStoreReqTable.getChildCount() - 2);
    }

    private void addRowsToTable(TableLayout table) {
        TableRow header = new TableRow(this);
        TableRow.LayoutParams  lptextview=new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("No");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Name");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Desc");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Loc");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Active Till");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Accept");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Reject");
        header.addView(tv);

        TableRow.LayoutParams  lptablerow=new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lptablerow);
        header.setBackgroundColor(Color.parseColor("#CC9900"));
        header.setHorizontalFadingEdgeEnabled(true);

        table.addView(header);
        List<Store> stores = null;
        try {
            stores = storeDataSource.getAllPendingStoreRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
         int storeno=1;
      for(final Store store : stores) {
            System.out.println(store.getStoreName());
            try {
                TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.pendingstorerow, null);
                ((TextView) row.findViewById(R.id.ReqStoreNo)).setText(String.valueOf(storeno));
                ((TextView) row.findViewById(R.id.ReqStoreName)).setText(store.getStoreName());
                ((TextView) row.findViewById(R.id.ReqStoreDesc)).setText(store.getStoreDesc());
                ((TextView) row.findViewById(R.id.ReqStoreLocation)).setText(store.getLocation());
                ((TextView) row.findViewById(R.id.ReqStoreActiveTill)).setText(DateUtility.dateToString(store.getRequestedOn()));

                ImageButton btnReqStoreAccept = (ImageButton) row.findViewById(R.id.btnReqStoreAccept);
                btnReqStoreAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    TabPendingStoreReq.this);
                            builder.setTitle("Accept Store Register Request");
                            //builder.setIcon(R.drawable.cross);
                            builder.setMessage("Do you want to accept?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int arg0) {
                                            boolean approved=true;
                                            store.setApproved(approved);
                                            Context context = v.getContext();
                                            try {
                                                storeDataSource.changeStoreApprovalStatus(store,adminId);
                                            }catch(Exception e){
                                                e.printStackTrace();
                                            }
                                            Intent intent = new Intent(context, AdminHomePage.class);
                                            intent.putExtra("selectedTab", new String("0"));
                                            context.startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int arg0) {
                                            Context context = v.getContext();
                                          /*  Spinner spin = (Spinner)dialog.findViewById(R.id.spinState);
                                            ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(MainActivity.this,  android.R.layout.simple_spinner_item, states);
                                            adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spin.setAdapter(adapter_state); */
                                            Intent intent = new Intent(context, AdminHomePage.class);
                                            intent.putExtra("selectedTab", new String("0"));
                                            context.startActivity(intent);
                                            finish();
                                        }
                                    });
                            AlertDialog alertdialog = builder.create();
                            alertdialog.show();
                        }
                });

                ImageButton btnReqStoreReject = (ImageButton) row.findViewById(R.id.btnReqStoreReject);
                btnReqStoreReject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                TabPendingStoreReq.this);
                        builder.setTitle("Reject Store Register Request");
                        //builder.setIcon(R.drawable.cross);
                        builder.setMessage("Do you want to reject?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg0) {
                                    boolean approved = false;
                                    store.setApproved(approved);
                                    Context context = v.getContext();
                                    try {
                                        storeDataSource.changeStoreApprovalStatus(store, adminId);
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    Intent intent = new Intent(context, AdminHomePage.class);
                                    intent.putExtra("selectedTab", new String("0"));
                                    context.startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg0) {
                                    Context context = v.getContext();
                                    Intent intent = new Intent(context, AdminHomePage.class);
                                    intent.putExtra("selectedTab", new String("0"));
                                    context.startActivity(intent);
                                    finish();
                                }
                            });
                             AlertDialog alertdialog = builder.create();
                             alertdialog.show();
                            }
                });

                table.addView(row);
            }catch(Exception e){
                System.out.println(e);
            }
            storeno ++;
        }
     /*   TextView tv = new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.s(table);

        setContentView(tv); */
    }

   /* public static List<Store> createStoreList(){
        Admin admin=new Admin(1,"a3","p3","a3@gmail.com");
        List<Store> stores=new ArrayList<Store>();
        String date="4/3/2015";
        Store store=new Store(1L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
        stores.add(store);
        store=new Store(2L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
        stores.add(store);
        store=new Store(3L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
        stores.add(store);
        store=new Store(4L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
        stores.add(store);
        return stores;
    }*/
   public static List<Deal> createDealList() {
       Admin admin = new Admin(1, "a3", "p3", "a3@gmail.com");
       Store store=new Store(1L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
       List<Deal> deals=new ArrayList<Deal>();
       String date="4/1/2015";
       String fromdate="4/3/2015";
       String todate="10/3/2015";
       Deal d=new Deal(1L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
       d=new Deal(2L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
        d=new Deal(3L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
        d=new Deal(4L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
        d=new Deal(5L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
       d=new Deal(6L,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),DateUtility.stringToDate(todate),store);
       deals.add(d);
       return deals;

   }

}

