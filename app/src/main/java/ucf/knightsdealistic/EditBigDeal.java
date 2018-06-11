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
import java.util.HashMap;
import java.util.Locale;

import ucf.knightsdealistic.database.datasource.BigDealDataSource;
import ucf.knightsdealistic.database.datasource.DealDataSource;
import ucf.knightsdealistic.database.model.BigDeal;
import ucf.knightsdealistic.database.model.Deal;

public class EditBigDeal extends ActionBarActivity {
    EditText bigdealname, bigdealdesc, tobepostedon, amount;
    Button backbtn, postbtn, cancelbtn;
    BigDealDataSource bigDealDataSource;
    long bigDealId;
    BigDeal bigdeal;
    UserSessionManager session;
    long storeId;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_big_deal);
        bigDealId = Long.parseLong(getIntent().getStringExtra("clickedbigDealId"));
        backbtn = (Button) findViewById(R.id.btn_back);
        postbtn = (Button) findViewById(R.id.post_btn);
        cancelbtn = (Button) findViewById(R.id.cancel_btn);

        bigdealname = (EditText) this.findViewById(R.id.name_txt);
        bigdealdesc = (EditText) this.findViewById(R.id.desc_txt);
        tobepostedon = (EditText) this.findViewById(R.id.added_txt_posted);
        amount = (EditText) this.findViewById(R.id.amount_txt);
        bigDealDataSource = new BigDealDataSource(this);
        session = new UserSessionManager(getApplicationContext());
        if(!session.isLogggedIn())
            finish();
        HashMap<String, String> user = session.getUserDetails();
        storeId = Long.parseLong(user.get(UserSessionManager.KEY_ID));
     //   Validation.lengthValidation(bigdealname);
     //   Validation.lengthValidation(bigdealdesc);


        try {
            bigdeal = bigDealDataSource.getBigDealById(bigDealId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.bigdealname.setText(bigdeal.getBigDealName());
        this.bigdealdesc.setText(bigdeal.getBigDealDesc());
        this.tobepostedon.setText(DateUtility.dateToString(bigdeal.getTobePosted()));
        this.amount.setText(String.valueOf(bigdeal.getAmount()));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(EditBigDeal.this, "Back to homepage", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditBigDeal.this, StoreHomePage.class);
                intent.putExtra("selectedTab", new String("3"));
                startActivity(intent);
                finish();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //      Toast.makeText(EditBigDeal.this, "Details are cleared", Toast.LENGTH_SHORT).show();
                bigdealname.setText(bigdeal.getBigDealName());
                bigdealdesc.setText(bigdeal.getBigDealDesc());
                tobepostedon.setText(DateUtility.dateToString(bigdeal.getTobePosted()));
                amount.setText(String.valueOf(bigdeal.getAmount()));

            }
        });

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bigdealname.getText().toString().length() == 0 || bigdealdesc.getText().toString().length() == 0 || tobepostedon.getText().toString().length() == 0 || amount.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "All fields are mandatory", Toast.LENGTH_LONG).show();
                } else if(bigDealDataSource.isBigDealDuplicate(bigdealname.getText().toString(), storeId, DateUtility.stringToDate(tobepostedon.getText().toString()))){
                    Toast.makeText(getApplicationContext(),"BigDeal already exists with same name/date. Choose a different name/date",Toast.LENGTH_LONG).show();
                } else {
                    bigdeal.setBigDealName(bigdealname.getText().toString());
                    bigdeal.setBigDealDesc(bigdealdesc.getText().toString());
                    bigdeal.setAmount(Double.parseDouble(amount.getText().toString()));
                    bigdeal.setTobePosted(DateUtility.stringToDate(tobepostedon.getText().toString()));


                    bigDealDataSource.updateBigDeal(bigdeal);
                    Toast.makeText(EditBigDeal.this, "BigDeal is updated", Toast.LENGTH_LONG).show();
                }
            }
        });

        tobepostedon.setOnClickListener(new View.OnClickListener() {

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

            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditBigDeal.this,date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();


            }
        });


    }


    private void updateLabeldeal1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        tobepostedon.setText(sdf.format(myCalendar1.getTime()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_big_deal, menu);
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
