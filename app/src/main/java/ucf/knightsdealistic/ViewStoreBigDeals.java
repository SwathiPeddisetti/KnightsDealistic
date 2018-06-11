package ucf.knightsdealistic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ucf.knightsdealistic.database.datasource.BigDealDataSource;
import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Vaishnavi on 4/6/2015.
 */
public class ViewStoreBigDeals extends Activity {
    TableLayout viewBigDealTable;
    BigDealDataSource bigDealDataSource;
    StoreDataSource storeDataSource;
    long storeId;
    UserSessionManager session;
    AlertDialog.Builder builder;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_homepage);
        Toast.makeText(
                ViewStoreBigDeals.this,
                "Oncreate()",
                Toast.LENGTH_LONG).show();
        View b = findViewById(R.id.logo);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.gridLayout);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.View_bigDeal);
        b.setVisibility(View.VISIBLE);
        viewBigDealTable=(TableLayout) this.findViewById( R.id.viewBigDealTable);
        System.out.println("view store table");
        bigDealDataSource = new BigDealDataSource(this);
        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        storeId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
    }

    @Override
    public void onStart() {
        Toast.makeText(
                ViewStoreBigDeals.this,
                "Onstart()",
                Toast.LENGTH_LONG).show();
        super.onStart();
        deleteRowsFromTable();
        addRowsToTable(viewBigDealTable,storeId);
    }



    public void deleteRowsFromTable() {
        if ( viewBigDealTable.getChildCount() > 1)
            viewBigDealTable.removeViews(1,viewBigDealTable.getChildCount() - 1);
    }

    private void addRowsToTable(TableLayout table,Long storeId) {
        TableRow header = new TableRow(this);
        TableRow.LayoutParams lptextview = new TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
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
        tv.setText("Date");
        header.addView(tv);
        tv=new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Amount");
        header.addView(tv);

        tv = new TextView(this);
        tv.setWidth(120);
        tv.setLayoutParams(lptextview);
        tv.setText("Edit");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(120);
        tv.setLayoutParams(lptextview);
        tv.setText("Delete");
        header.addView(tv);

        TableRow.LayoutParams  lptablerow=new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lptablerow);
        header.setBackgroundColor(Color.parseColor("#CC9900"));
        header.setHorizontalFadingEdgeEnabled(true);
        table.addView(header);

        List<BigDeal> bigDeals = null;
        Log.d("ViewStoreBigDeals",String.valueOf(storeId));
        try {
            bigDeals=bigDealDataSource.getAllBigDealsByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int bigdealno = 1;
        for (final BigDeal bigdeal : bigDeals) {
            System.out.println(bigdeal.getBigDealId());

            TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.viewbigdealsrow, null);

            Button edit = (Button) row.findViewById(R.id.btn_bedit);
            Button delete = (Button) row.findViewById(R.id.btn_bdelete);


            ((TextView) row.findViewById(R.id.txt_bdno)).setText(String.valueOf(bigdealno));
            ((TextView) row.findViewById(R.id.txt_bdname)).setText(bigdeal.getBigDealName());
            ((TextView) row.findViewById(R.id.txt_bdactive)).setText(DateUtility.dateToString(bigdeal.getTobePosted()));
            ((TextView) row.findViewById(R.id.txt_bdamount)).setText(Double.toString(bigdeal.getAmount()));


            Date date = new Date();
            dateFormat.format(date);

            if(bigdeal.getTobePosted().before(DateUtility.stringToDate(DateUtility.getNow())))
            {
                edit.setEnabled(false);
                edit.setClickable(false);
                edit.setVisibility(View.INVISIBLE);
                delete.setClickable(false);
                delete.setVisibility(View.INVISIBLE);
                delete.setEnabled(false);
            }
            else if(bigdeal.isApproved() && !DateUtility.canBigDealBeDeleted(bigdeal.getTobePosted())) {

               edit.setEnabled(true);
                edit.setClickable(true);
                edit.setVisibility(View.VISIBLE);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewStoreBigDeals.this, EditBigDeal.class);
                        intent.putExtra("clickedbigDealId", String.valueOf(bigdeal.getBigDealId()));
                        startActivity(intent);
                        finish();
                    }

                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                ViewStoreBigDeals.this);

                        // Set Alert Dialog Title
                        builder.setTitle("BigDeal");

                        // Set an Icon for this Alert Dialog
                        builder.setIcon(R.drawable.cross);

                        // Set Alert Dialog Message
                        builder.setMessage("You will be charged 50% if you delete the deal now. Please contact Admin if u ran out of stock.Do u still want to delete?")

                                // Positive button functionality
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int arg0) {
                                                Toast.makeText(
                                                        ViewStoreBigDeals.this,
                                                        "BigDeal is deleted",
                                                        Toast.LENGTH_LONG).show();
                                                bigDealDataSource.deleteBigDeal(bigdeal.getBigDealId());
                                                Context context = v.getContext();
                                                Intent intent = new Intent(context, StoreHomePage.class);
                                                intent.putExtra("selectedTab", new String("3"));
                                                context.startActivity(intent);
                                               finish();
                                                // Do more stuffs
                                                // finish();
                                            }
                                        })
                                        // Negative button functionality
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int arg0) {
                                                Toast.makeText(
                                                        ViewStoreBigDeals.this,
                                                        "BigDeal is not deleted",
                                                        Toast.LENGTH_LONG).show();
                                                // Do more stuffs
                                                // dialog.cancel();
                                            }
                                        });

                        // Create the Alert Dialog
                        AlertDialog alertdialog = builder.create();

                        // Show Alert Dialog
                        alertdialog.show();
                    }
                });
            }
             else   {


                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ViewStoreBigDeals.this, EditBigDeal.class);
                            intent.putExtra("clickedbigDealId", String.valueOf(bigdeal.getBigDealId()));
                            startActivity(intent);
                            finish();
                        }

                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    ViewStoreBigDeals.this);

                            // Set Alert Dialog Title
                            builder.setTitle("BigDeal");

                            // Set an Icon for this Alert Dialog
                            builder.setIcon(R.drawable.cross);

                            // Set Alert Dialog Message
                            builder.setMessage("Do you want to delete BigDeal?")

                                    // Positive button functionality
                                    .setPositiveButton("Yes",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int arg0) {
                                                    Toast.makeText(
                                                            ViewStoreBigDeals.this,
                                                            "BigDeal is deleted",
                                                            Toast.LENGTH_LONG).show();
                                                    bigDealDataSource.deleteBigDeal(bigdeal.getBigDealId());
                                                    Context context = v.getContext();
                                                    Intent intent = new Intent(context, StoreHomePage.class);
                                                    intent.putExtra("selectedTab", new String("3"));
                                                    context.startActivity(intent);
                                                    finish();
                                                    // Do more stuffs
                                                    // finish();
                                                }
                                            })
                                            // Negative button functionality
                                    .setNegativeButton("No",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog,
                                                                    int arg0) {
                                                    Toast.makeText(
                                                            ViewStoreBigDeals.this,
                                                            "BigDeal is not deleted",
                                                            Toast.LENGTH_LONG).show();
                                                    // Do more stuffs
                                                    // dialog.cancel();
                                                }
                                            });

                            // Create the Alert Dialog
                            AlertDialog alertdialog = builder.create();

                            // Show Alert Dialog
                            alertdialog.show();
                        }
                    });
                }

            table.addView(row);
            bigdealno++;
        }
    }



}