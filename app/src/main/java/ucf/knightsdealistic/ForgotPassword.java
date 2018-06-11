package ucf.knightsdealistic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.app.Activity;
import android.util.Log;


import java.net.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;


import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ucf.knightsdealistic.database.datasource.StoreDataSource;
import ucf.knightsdealistic.database.model.Deal;
import ucf.knightsdealistic.database.model.Store;


public class ForgotPassword extends ActionBarActivity {

    EditText txtemailid;

    ProgressDialog pdialog = null;
    Context context = null;
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;


    StoreDataSource storeDataSource;
    Session session;
    long storeId;
    Store store;
    String subject;
    String sender="seproject5016@gmail.com";
    String reciever;
    String textMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_forgotpassword);
        storeDataSource = new StoreDataSource(getApplicationContext());
        txtemailid = (EditText) findViewById(R.id.txtEmailId);
        final Button submitBtn = (Button) findViewById(R.id.btnSubmit);
        final Button btnCancel = (Button) findViewById(R.id.btnCancel);
        final Button btnBack = (Button) findViewById(R.id.btnBack);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String emailAddress = txtemailid.getText().toString();
                Store store = null;
                try {
                    store = storeDataSource.getStoreByEmailAddress(emailAddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (store.getEmailId().equals(emailAddress)) {
                    reciever=emailAddress;
                    textMessage = store.getStorePassword().toString();
                    subject="Knights Dealistic:Forgot Password";
                    final String username = "seproject5016@gmail.com";
                    final String password = "teamwork99";

                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.socketFactory.port", "465");
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");


                    Session session = Session.getInstance(props,
                            new javax.mail.Authenticator() {
                                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(username, password);
                                }
                            });



                    //   pdialog = ProgressDialog.show(context, "", "Sending mail...", true);
                    RetreiveFeedTask task = new RetreiveFeedTask();
                    task.execute();

                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtemailid.setText("");
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(ForgotPassword.this, FirstScreen.class);
                                        intent.putExtra("selectedTab",new String("1"));
                                        startActivity(intent);
                                        finish();
                                    }
                                }
        );

    }



    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("seproject5016@gmail.com"));
                message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(reciever));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch(MessagingException e) {
                e.printStackTrace();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //    pdialog.dismiss();

            Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
        }
    }
}






