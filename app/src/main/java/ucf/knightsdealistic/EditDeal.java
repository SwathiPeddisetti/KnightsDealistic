package ucf.knightsdealistic;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.model.Deal;


public class EditDeal extends ActionBarActivity {
    EditText name, description, from, to;
    Button back, post, cancel;
    DealDataSource dealDataSource;
    long dealId;
    Deal deal;
    UserSessionManager session;
    long storeId;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_deal);
        dealId = Long.parseLong(getIntent().getStringExtra("clickedDealId"));
        back = (Button) findViewById(R.id.btn_back);
        post = (Button) findViewById(R.id.post_btn);
        cancel = (Button) findViewById(R.id.cancel_btn);

        name = (EditText) this.findViewById(R.id.name_txt);
        description = (EditText) this.findViewById(R.id.desc_txt);
        from= (EditText) this.findViewById(R.id.added_txt);
        to = (EditText) this.findViewById(R.id.etxt_to);

      //  Validation.lengthValidation(name);
      //  Validation.lengthValidation(description);


        dealDataSource=new DealDataSource(this);
        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        storeId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
        try {
            deal = dealDataSource.getDealById(dealId);
        }catch(Exception e){
            e.printStackTrace();
        }

        this.name.setText(deal.getDealName());
        this.description.setText(deal.getDealDescription());
        this.from.setText(DateUtility.dateToString(deal.getDealActiveFrom()));
        this.to.setText(DateUtility.dateToString(deal.getDealActiveTo()));

        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(EditDeal.this, StoreHomePage.class);
                                        intent.putExtra("selectedTab",new String("2"));
                                        startActivity(intent);
                                        finish();
                                    }
                                }
        );
        cancel.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                     /*     Intent intent = new Intent(EditDeal.this, EditDeal.class);
                                          intent.putExtra("clickedDealId",String.valueOf(dealId));
                                          startActivity(intent); */
                                          name.setText(deal.getDealName());
                                          description.setText(deal.getDealDescription());
                                          from.setText(DateUtility.dateToString(deal.getDealActiveFrom()));
                                          to.setText(DateUtility.dateToString(deal.getDealActiveTo()));
                                      }
                                  }
        );
        post.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {


                                        if(name.getText().toString().length()==0 || description.getText().toString().length()==0 || from.getText().toString().length()==0 || to.getText().toString().length()==0  )
                                        {
                                            Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                                        }
                                        else
                                        {

                                            if(DateUtility.stringToDate(from.getText().toString()).after(DateUtility.stringToDate(to.getText().toString())))
                                            {
                                                Toast.makeText(getApplicationContext(),"From date cannot be greater than To date",Toast.LENGTH_LONG).show();
                                            }
                                            else if(dealDataSource.isDealDuplicate(name.getText().toString(),DateUtility.stringToDate(from.getText().toString()),DateUtility.stringToDate(to.getText().toString()),storeId)){
                                                Toast.makeText(getApplicationContext(),"Deal already exists with same name/date. Choose a different name/date",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                deal.setDealName(name.getText().toString());
                                                deal.setDealDescription(description.getText().toString());
                                                deal.setDealActiveFrom(DateUtility.stringToDate(from.getText().toString()));
                                                deal.setDealActiveTo(DateUtility.stringToDate(to.getText().toString()));


                                                dealDataSource.updateDeal(deal);
                                                Toast.makeText(EditDeal.this, "Deal is updated", Toast.LENGTH_LONG).show();
                                            }}}
                                }
        );

        from.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDeal.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        to.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditDeal.this, date2, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



    }


    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            if (dayOfMonth < myCalendar1.get(Calendar.DAY_OF_MONTH) || year < myCalendar1.get(Calendar.YEAR) || monthOfYear < myCalendar1.get(Calendar.MONTH)) {
                Toast.makeText(getApplicationContext(), " cannot put past date  ", Toast.LENGTH_LONG).show();
            } else {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal1();
            }
        }
    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


            if (dayOfMonth < myCalendar2.get(Calendar.DAY_OF_MONTH) || year<myCalendar2.get(Calendar.YEAR) || monthOfYear<myCalendar2.get(Calendar.MONTH) ) {
                Toast.makeText(getApplicationContext(), " Cannnot put past date ", Toast.LENGTH_LONG).show();
            } else {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal2();
            }
        }

    };


    private void updateLabeldeal1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        from.setText(sdf.format(myCalendar1.getTime()));
    }

    private void updateLabeldeal2() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        to.setText(sdf.format(myCalendar2.getTime()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_deal, menu);
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
