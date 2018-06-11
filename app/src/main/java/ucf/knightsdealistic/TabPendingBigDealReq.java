package ucf.knightsdealistic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ucf.knightsdealistic.database.datasource.BigDealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Swathi on 3/16/2015.
 */
public class TabPendingBigDealReq extends Activity {

    TableLayout pendingBigDealReqTable;
    BigDealDataSource bigDealDataSource;
    UserSessionManager session;
    Long adminId;
    List<BigDeal> bigDeals;

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
        pendingBigDealReqTable = (TableLayout) this.findViewById( R.id.pendingBigDealReqTable);
    }

    @Override
    public void onStart() {
        super.onStart();
        bigDealDataSource=new BigDealDataSource(this);
        HashMap<String, String> user = session.getUserDetails();
        adminId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
        addRowsToTable(pendingBigDealReqTable);
    }

    public void deleteRowsFromTable(int i) {
        if ( pendingBigDealReqTable.getChildCount() > 2)
            pendingBigDealReqTable.removeViewAt(i);
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
        tv.setText("Posted On");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Amount Quoted");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Accept");
        header.addView(tv);

        TableRow.LayoutParams  lptablerow=new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lptablerow);
        header.setBackgroundColor(Color.parseColor("#CC9900"));
        header.setHorizontalFadingEdgeEnabled(true);
        table.addView(header);

        try {
            bigDeals = bigDealDataSource.getAllPendingBigDealsOfTheDay();
          //  bigDeals =createBigDealList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int bigDealNo=1;
        for(final BigDeal bigDeal : bigDeals) {
            System.out.println(bigDeal.getBigDealName());
            try {
                TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.pendingbigdealrow, null);
                ((TextView) row.findViewById(R.id.ReqBigDealNo)).setText(String.valueOf(bigDealNo));
                ((TextView) row.findViewById(R.id.ReqBigDealName)).setText(bigDeal.getBigDealName());
                ((TextView) row.findViewById(R.id.ReqBigDealDesc)).setText(bigDeal.getBigDealDesc());
                ((TextView) row.findViewById(R.id.ReqBigDealPostedOn)).setText(DateUtility.dateToString(bigDeal.getTobePosted()));
                ((TextView) row.findViewById(R.id.ReqBigDealAmount)).setText(String.valueOf(bigDeal.getAmount()));

                ImageButton btnReqBigDealAccept = (ImageButton) row.findViewById(R.id.btnReqBigDealAccept);
                btnReqBigDealAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            boolean approved=true;
                            bigDeal.setApproved(approved);
                            bigDealDataSource.approveADeal(bigDeals,adminId);
                            Context context = v.getContext();
                            Intent intent = new Intent(context, AdminHomePage.class);
                            intent.putExtra("selectedTab",new String("1"));
                            startActivity(intent);
                            finish();
                        }catch(Exception e){
                            System.out.println(e);
                        }
                    }
                });

                table.addView(row);
            }catch(Exception e){
                System.out.println(e);
            }
            bigDealNo ++;
        }
     /*   TextView tv = new TextView(this);
        tv.setTextSize(25);
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.s(table);

        setContentView(tv); */
    }

   /* public static List<BigDeal> createBigDealList() {
        Admin admin = new Admin(1, "a3", "p3", "a3@gmail.com");
        Store store=new Store(1L,admin,"storeName","storeDesc","storePassword","location","emailId",9L,false, "securityQuestion","securityAnswer",null,null,null);
        List<BigDeal> deals=new ArrayList<BigDeal>();
        String date="4/1/2015";
        String fromdate="4/3/2015";
        String todate="10/3/2015";
        double amount=4.0d;

        BigDeal d=new BigDeal(1L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        d=new BigDeal(2L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        d=new BigDeal(3L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        d=new BigDeal(4L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        d=new BigDeal(5L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        d=new BigDeal(6L,store,admin,"deal1","dealsesc1",DateUtility.stringToDate(date),DateUtility.stringToDate(fromdate),false,amount);
        deals.add(d);
        return deals;

    }*/

}

