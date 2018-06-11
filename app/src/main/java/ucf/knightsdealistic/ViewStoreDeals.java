package ucf.knightsdealistic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;

/**
 * Created by Vaishnavi on 4/6/2015.
 */
public class ViewStoreDeals extends Activity {
    TableLayout viewStoreDealsTable;
    DealDataSource dealDataSource;
    StoreDataSource storeDataSource;
    UserSessionManager session;
    long storeId;
    AlertDialog.Builder builder;
    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy ");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_homepage);
        View b = findViewById(R.id.logo);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.gridLayout);
        b.setVisibility(View.GONE);
        b = findViewById(R.id.View_deals);
        b.setVisibility(View.VISIBLE);
        viewStoreDealsTable = (TableLayout) this.findViewById(R.id.viewStoreDealsTable);
        System.out.println("view store table");
        dealDataSource = new DealDataSource(this);
        session = new UserSessionManager(getApplicationContext());
        if (!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        storeId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
        //  builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        deleteRowsFromTable();
        addRowsToTable(viewStoreDealsTable, storeId);

    }

    public void deleteRowsFromTable() {
        if (viewStoreDealsTable.getChildCount() > 1)
            viewStoreDealsTable.removeViews(1, viewStoreDealsTable.getChildCount() - 1);
    }

    private void addRowsToTable(TableLayout table, Long storeId) {
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
        tv.setText("Desc");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("From");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("To");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Edit");
        header.addView(tv);
        tv = new TextView(this);
        tv.setWidth(80);
        tv.setLayoutParams(lptextview);
        tv.setText("Delete");
        header.addView(tv);

        TableRow.LayoutParams lptablerow = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lptablerow);
        header.setBackgroundColor(Color.parseColor("#CC9900"));
        header.setHorizontalFadingEdgeEnabled(true);
        table.addView(header);

        List<Deal> deals = null;
        try {
            deals = dealDataSource.getAllDealsByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int dealno = 1;
        for (final Deal deal : deals) {
            System.out.println(deal.getDealId());

            TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.viewdealsrow, null);
            Button editbtn = (Button) row.findViewById(R.id.btn_edit);
            Button delete = (Button) row.findViewById(R.id.btn_delete);

            ((TextView) row.findViewById(R.id.txt_dealno)).setText(String.valueOf(dealno));
            ((TextView) row.findViewById(R.id.txt_dealname)).setText(deal.getDealName());
            ((TextView) row.findViewById(R.id.txt_dealdesc)).setText(deal.getDealDescription());
            ((TextView) row.findViewById(R.id.txt_from)).setText(DateUtility.dateToString(deal.getDealActiveFrom()));
            ((TextView) row.findViewById(R.id.txt_to)).setText(DateUtility.dateToString(deal.getDealActiveTo()));


            Date date = new Date();
            dateFormat.format(date);

            if (deal.getDealActiveTo()!=null && deal.getDealActiveTo().before(DateUtility.stringToDate(DateUtility.getNow())))
            {
                delete.setClickable(false);
                delete.setEnabled(false);
                delete.setVisibility(View.INVISIBLE);
                editbtn.setEnabled(false);
                editbtn.setClickable(false);
                editbtn.setVisibility(View.INVISIBLE);
            }
            else
            {

                editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewStoreDeals.this, EditDeal.class);
                        intent.putExtra("clickedDealId", String.valueOf(deal.getDealId()));
                        startActivity(intent);
                        finish();
                    }
                });


                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                ViewStoreDeals.this);

                        // Set Alert Dialog Title
                        builder.setTitle("Delete Deal");

                        // Set an Icon for this Alert Dialog
                        builder.setIcon(R.drawable.cross);

                        // Set Alert Dialog Message
                        builder.setMessage("Do you want to delete Deal?")

                                // Positive button functionality
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int arg0) {
                                                Toast.makeText(
                                                        ViewStoreDeals.this,
                                                        "Deal is deleted",
                                                        Toast.LENGTH_LONG).show();
                                                dealDataSource.deleteDeal(deal.getDealId());
                                                Context context = v.getContext();
                                                Intent intent = new Intent(context, StoreHomePage.class);
                                                intent.putExtra("selectedTab", new String("2"));
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
                                                        ViewStoreDeals.this,
                                                        "Deal is not deleted",
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

            dealno++;
            table.addView(row);

        }
    }


}



