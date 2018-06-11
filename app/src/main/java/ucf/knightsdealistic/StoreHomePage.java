package ucf.knightsdealistic;

import android.app.ActivityGroup;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.datasource.BigDealDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;
import  ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.model.Store;


public class StoreHomePage extends ActivityGroup {

    EditText dealtxt, bigdealtxt, dealdetailtxt, bigdealdetailtxt, dealactivefrom, dealactiveto, bigdealdate, bigdealamount;

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    Calendar myCalendar3 = Calendar.getInstance();

    TextView  logouttxt ,txthelloStoreManager;

    DealDataSource dealdatasource ;
    BigDealDataSource bigDealDataSource ;
    StoreDataSource storeDataSource;
    UserSessionManager session;
    String storeName;
    long storeId;
    Store store;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
    int selectedTab=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_homepage);

        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        storeId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
        storeName=user.get(UserSessionManager.KEY_NAME);
        storeDataSource = new StoreDataSource(getApplicationContext());
        try {
            store = storeDataSource.getStoreById(storeId);
            System.out.println("Here");
        }catch(Exception e){
          e.printStackTrace();
        }

        dealdatasource = new DealDataSource(getApplicationContext());
        bigDealDataSource = new BigDealDataSource(getApplicationContext());

        logouttxt = (TextView) findViewById(R.id.txtlogout);

        txthelloStoreManager = (TextView) findViewById(R.id.txthelloStoreManager);
        txthelloStoreManager.setText("Hello, "+ storeName);

        dealtxt = (EditText) findViewById(R.id.txtdealname);
        dealdetailtxt = (EditText) findViewById(R.id.txtdealdetails);
        dealactivefrom = (EditText) findViewById(R.id.txtdealactivefrom);
        dealactiveto = (EditText) findViewById(R.id.txtdealactiveto);
        bigdealtxt = (EditText) findViewById(R.id.txtbigdealname);
        bigdealdetailtxt = (EditText) findViewById(R.id.txtbigdealdescription);
        bigdealdate = (EditText) findViewById(R.id.bigdealdate);
        bigdealamount = (EditText) findViewById(R.id.txtbidamount);
        Button postdeal = (Button) findViewById(R.id.btnpostdeal);
        Button canceldeal = (Button) findViewById(R.id.btncanceldeal);
        Button postbigdeal = (Button) findViewById(R.id.btnbigdealpost);
        Button cancelbigdeal = (Button) findViewById(R.id.btncancelbigdealpost);

        try {
            selectedTab = Integer.parseInt(getIntent().getStringExtra("selectedTab"));
        }catch(Exception e){
            e.printStackTrace();
        }



        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup(this.getLocalActivityManager());
        TabHost.TabSpec postDealTab = tabHost1.newTabSpec("Post a Deal");
        postDealTab.setContent(R.id.tabpostdeal);
        postDealTab.setIndicator("Post a Deal");
        tabHost1.addTab(postDealTab);


        TabHost.TabSpec postBigDealTab = tabHost1.newTabSpec("Post a Big Deal");
        postBigDealTab.setContent(R.id.tabpostbigdeal);
        postBigDealTab.setIndicator("Post a Big Deal");
        tabHost1.addTab(postBigDealTab);


        TabHost.TabSpec viewDealTab = tabHost1.newTabSpec("My Deals");
        viewDealTab.setIndicator("My Deals");
        viewDealTab.setContent(new Intent(this,ViewStoreDeals.class));
        tabHost1.addTab(viewDealTab);

        TabHost.TabSpec viewBigDealTab = tabHost1.newTabSpec("My Big Deals");
        viewBigDealTab.setIndicator("My Big Deals");
        viewBigDealTab.setContent(new Intent(this,ViewStoreBigDeals.class));
        tabHost1.addTab(viewBigDealTab);

        tabHost1.setCurrentTab(selectedTab);

        if( dealtxt.getText().toString().length() == 0 )
            dealtxt.setError( "Deal name is required!" );
        if( dealdetailtxt.getText().toString().length() == 0 )
            dealdetailtxt.setError( "Deal Description is required!" );

        if( bigdealtxt.getText().toString().length() == 0 )
            bigdealtxt.setError( "Big Deal name is required!" );
        if( bigdealdetailtxt.getText().toString().length() == 0 )
            bigdealdetailtxt.setError( "Deal Description is required!" );

        if( bigdealamount.getText().toString().length() == 0 )
            bigdealamount.setError( "Bid Amount is required!" );



        postdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dealtxt.getText().toString().length() == 0 || dealdetailtxt.getText().toString().length() == 0 || dealactivefrom.getText().toString().length() == 0 || dealactiveto.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "All Fields are mandatory  ", Toast.LENGTH_LONG).show();
                } else {
                    String dealName = dealtxt.getText().toString();
                    String dealDesc = dealdetailtxt.getText().toString();
                    String dealFrom = dealactivefrom.getText().toString();
                    String dealTo = dealactiveto.getText().toString();
                    Date date1 = DateUtility.stringToDate(dealFrom);
                    Date date2 = DateUtility.stringToDate(dealTo);

                    Deal d = new Deal();
                    d.setDealName(dealName);
                    d.setDealDescription(dealDesc);
                    d.setDealActiveFrom(date1);
                    d.setDealActiveTo(date2);

                    d.setStore(store);
                    if (dealdatasource.isDealDuplicate(dealName, date1, date2, storeId)) {
                        Toast.makeText(getApplicationContext(), "Deal already exists with same name/date. Choose a different one", Toast.LENGTH_LONG).show();
                    } else {
                        long id = dealdatasource.createDeal(d);
                        ViewGroup group = (ViewGroup) findViewById(R.id.tabpostdeal);
                        clearForm(group);

                        if (id < 0) {
                            Toast.makeText(getApplicationContext(), "Could not post " + dealName, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), " " + dealName + " 's deal has been Posted ", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        postbigdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bigdealtxt.getText().toString().length() == 0 || bigdealdetailtxt.getText().toString().length() == 0 || bigdealdate.getText().toString().length() == 0 || bigdealamount.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), " All Fields are mandatory  ", Toast.LENGTH_LONG).show();
                } else {
                    String bigdealName = bigdealtxt.getText().toString();
                    String bigdealDesc = bigdealdetailtxt.getText().toString();
                    String bigdealFrom = bigdealdate.getText().toString();
                    String bidamount = bigdealamount.getText().toString();
                    Date bigdate1 = DateUtility.stringToDate(bigdealFrom);
                    double amount = Double.parseDouble(bidamount);

                    BigDeal d = new BigDeal();
                    d.setBigDealName(bigdealName);
                    d.setBigDealDesc(bigdealDesc);
                    d.setTobePosted(bigdate1);
                    d.setAmount(amount);
                    d.setStore(store);
                    if (bigDealDataSource.isBigDealDuplicate(bigdealName, storeId, bigdate1)) {
                        Toast.makeText(getApplicationContext(), "BigDeal already exists with same name/date. Choose a different name/date", Toast.LENGTH_LONG).show();
                    } else {
                        long id = bigDealDataSource.createBigDeal(d);

                        ViewGroup group = (ViewGroup) findViewById(R.id.tabpostbigdeal);
                        clearForm(group);

                        if (id < 0) {
                            Toast.makeText(getApplicationContext(), "Could not post " + bigdealName, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), bigdealName + "'s bigdeal has been Requested ", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

        logouttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StoreHomePage.this, FirstScreen.class);
                session.logoutUser();
                startActivity(intent);
                finish();
            }
        });

        canceldeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup group = (ViewGroup) findViewById(R.id.tabpostdeal);
                clearForm(group);
            }
        });

        cancelbigdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup bgroup = (ViewGroup) findViewById(R.id.tabpostbigdeal);
                clearForm(bgroup);
            }
        });

        dealactivefrom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(StoreHomePage.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dealactiveto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(StoreHomePage.this, date2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        bigdealdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(StoreHomePage.this, date3, myCalendar3
                        .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                        myCalendar3.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal1();

        }
    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal2();

        }

    };
    DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

                myCalendar3.set(Calendar.YEAR, year);
                myCalendar3.set(Calendar.MONTH, monthOfYear);
                myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal3();

        }
    };

    private void updateLabeldeal1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dealactivefrom.setText(sdf.format(myCalendar1.getTime()));
    }

    private void updateLabeldeal2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dealactiveto.setText(sdf.format(myCalendar2.getTime()));
    }

    private void updateLabeldeal3() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        bigdealdate.setText(sdf.format(myCalendar3.getTime()));
    }

    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
