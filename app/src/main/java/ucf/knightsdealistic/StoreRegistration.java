package ucf.knightsdealistic;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.regex.Pattern;
import android.content.Intent;

import ucf.knightsdealistic.database.DatabaseHandler;
import ucf.knightsdealistic.database.datasource.AdminDataSource;
import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Admin;
import ucf.knightsdealistic.database.model.Store;



public class StoreRegistration extends ActionBarActivity {

    EditText storenameTxt, storedescriptionTxt, storepasswordTxt, storelocationTxt, emailidTxt, mobilenumberTxt, securityquestionTxt, securityanswerTxt, storeactivetoTxt;
    DatabaseHandler dbhandler;
    StoreDataSource storeDataSource;
    Calendar myCalendar = Calendar.getInstance();
    private Spinner spinner;
    //private static final String[]paths = {"Mother firstname", "first car", "Favourite professor"};
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_storeregistration);

        storenameTxt = (EditText) findViewById(R.id.txtStoreName);
        storedescriptionTxt = (EditText) findViewById(R.id.txtStoreDescription);
        storepasswordTxt = (EditText) findViewById(R.id.txtStorePassword);
        storelocationTxt = (EditText) findViewById(R.id.txtStoreLocation);
        emailidTxt = (EditText) findViewById(R.id.txtEmailId);
        mobilenumberTxt = (EditText) findViewById(R.id.txtMobileNumber);
        spinner = (Spinner)findViewById(R.id.ddlSecurityQuestion);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(StoreRegistration.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        //  securityquestionTxt = (EditText) findViewById(R.id.txtSecurityQuestion);
        securityanswerTxt = (EditText) findViewById(R.id.txtSecurityAnswer);
        storeactivetoTxt = (EditText) findViewById(R.id.dtStoreActiveTo);
        Log.d("Store Registration",storenameTxt.getText().toString());

        dbhandler = new DatabaseHandler(getApplicationContext());
        //Validations
        if( storenameTxt.getText().toString().length() == 0 )
            storenameTxt.setError( "Store name is required!" );
        if( storedescriptionTxt.getText().toString().length() == 0 )
            storedescriptionTxt.setError( "Store Description is required!" );
        if( storepasswordTxt.getText().toString().length() == 0 )
            storepasswordTxt.setError( "Store Password is required!" );
        if( storelocationTxt.getText().toString().length() == 0 )
            storelocationTxt.setError( "Store Location is required!" );
        if( emailidTxt.getText().toString().length() == 0 )
            emailidTxt.setError( "EmailId is required!" );
        if( mobilenumberTxt.getText().toString().length() == 0 )
/*       if( spinner.().toString().length() == 0 )
            securityquestionTxt.setError( "Sequrity Question is required!" );*/
        if( securityanswerTxt.getText().toString().length() == 0 )
            securityanswerTxt.setError( "Security Answer is required!" );
        if( storeactivetoTxt.getText().toString().length() == 0 )
            storeactivetoTxt.setError( "Store activeTo is required!" );
        Log.d("Store Registration",storenameTxt.getText().toString());
        //StoreDataSource storeDataSource;
        //storeDataSource=new StoreDataSource(this);

        final Button submitBtn = (Button) findViewById(R.id.btnSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            //long adminId, String storeName, String storeDesc, String storePassword, String location, String emailId, long mobileNumber, boolean isApproved, String securityQuestion, String securityAnswer, Date requestedOn, java.util.Date storeActiveFrom, Date storeActiveTo

            //   Date activeto = DateUtility.stringToDate("2/5/2015"); //TODO


            //Validations
            //   Pattern pattern1 = Pattern.compile( "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+");

            //  Matcher matcher1 = pattern1.matcher(emailId);

         /*   if (!matcher1.matches()) {
                //show your message if not matches with email pattern
            }*/

            @Override
            public void onClick(View v) {
                try {


                    final Spinner spin = (Spinner) findViewById((R.id.ddlSecurityQuestion));
                    String securityQuestion = String.valueOf(spin.getSelectedItem());


                    Log.d("Store Registration","Clicked");
                    //   Log.d("Store Registration",storeName+" " +storenameTxt);
                    String storeName = storenameTxt.getText().toString();
                    String storeDesc = storedescriptionTxt.getText().toString();
                    String storePassword = storepasswordTxt.getText().toString();
                    String location = storelocationTxt.getText().toString();
                    String emailId = emailidTxt.getText().toString();
                    String MobileNum = mobilenumberTxt.getText().toString();
                    Long mobileNumber = Long.parseLong(MobileNum);

                    //String securityQuestion = securityquestionTxt.getText().toString();
                    //   String securityQuestion = securityquestionTxt.getText().toString();


                    String securityAnswer = securityanswerTxt.getText().toString();
                    //   String securityQuestion = "default";
                    //   String securityAnswer = "default";
                    String activeTill = storeactivetoTxt.getText().toString();
                    Store s1 = new Store();
                    s1.setStoreName(storeName);
                    s1.setStoreDesc(storeDesc);
                    s1.setStorePassword(storePassword);
                    s1.setLocation(location);
                    s1.setEmailId(emailId);
                    s1.setMobileNumber(mobileNumber);
                    s1.setSecurityQuestion(securityQuestion);
                    s1.setSecurityAnswer(securityAnswer);
                    s1.setStoreActiveTo(DateUtility.stringToDate(activeTill));

                    AdminDataSource adminDataSource = new AdminDataSource(getApplicationContext());
                    Admin admin = adminDataSource.getAdminById(new Long(1L));
                    s1.setAdmin(admin);
                    //     Store store =new Store(storeName,storeDesc, storePassword, location, emailId,mobileNumber,false,securityQuestion, securityAnswer, DateUtility.stringToDate(DateUtility.getNow()), null, activeto,admin);

                    StoreDataSource s = new StoreDataSource(getApplicationContext());
                    s.createStore(s1);
                    Toast.makeText(getApplicationContext(), "Registration request sent successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final Button btnBack = (Button) findViewById(R.id.btnBackStoreReg);
        btnBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           //Toast.makeText(EditDeal.this, "Back to home page", Toast.LENGTH_SHORT).show();
                                           Intent intent = new Intent(StoreRegistration.this, FirstScreen.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );

        final Button btnClearStoreReg = (Button) findViewById(R.id.btnClearStoreReg);
        btnClearStoreReg.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           storenameTxt.setText("");
                                           storedescriptionTxt.setText("");
                                           storepasswordTxt.setText("");
                                           storelocationTxt.setText("");
                                           emailidTxt.setText("");
                                           mobilenumberTxt.setText("");
                                           securityanswerTxt.setText("");
                                           storeactivetoTxt.setText("");
                                       }
                                   }
        );

        storenameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                submitBtn.setEnabled(storenameTxt.getText().toString().trim() != null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            /*List<Store> addableStore = dbhandler.getAllStores();
            int storeCount = dbhandler.getstorescount();

            for(int i = 0; i < storeCount; i++)
            {
                Store.add(addableStore.get(i));
            }

            if(!addablestore.IsEmpty())
            populatelist(); */

        });

        storeactivetoTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(StoreRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {


                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabeldeal1();

        }
    };

    private void updateLabeldeal1() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        storeactivetoTxt.setText(sdf.format(myCalendar.getTime()));
    }



    // validating email id
    private boolean isValidEmail(String emailId) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String storePassword) {
        if (storePassword != null && storePassword.length() > 6) {
            return true;
        }
        return false;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storeregistration, menu);
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
