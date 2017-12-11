package ph.edu.uic.lastname.mytasksv2;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsMessage;

    private Button sendBtn;
    private TextView smsStatus;
    private TextView deliveryStatus;

    public static final String SMS_SENT_ACTION = "ph.edu.uic.lastname.mytasksv2.SMS_SENT_ACTION";
    public static final String SMS_DELIVERED_ACTION = "ph.edu.uic.lastname.mytasksv2.SMS_DELIVERED_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        smsMessage = (EditText) findViewById(R.id.smsMessage);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        smsStatus = (TextView) findViewById(R.id.smsStatus);
        deliveryStatus = (TextView) findViewById(R.id.dekiveryStatus);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }else{
            // do nothing
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phoneNumber.getText().toString();
                String sms = smsMessage.getText().toString();

                try{
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(MainActivity.this, "Sent!", Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1: {
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this, "No permission granted!", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
            }
        }
    }

    /*        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = phoneNumber.getText().toString();
                String smsBody = smsMessage.getText().toString();

                if(phoneNum.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter a Phone Number", Toast.LENGTH_LONG).show();
                }else{
                    sendSMS(phoneNum, smsBody);
                }
            }
        });

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = null;
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        message = "Message Sent Successfully!";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        message= "Error.";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        message = "Error: No Service.";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        message = "Error: Null PDU.";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        message = "Error: Radio off.";
                        break;
                }

                smsStatus.setText(message);
            }
        }, new IntentFilter(SMS_SENT_ACTION));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                phoneNumber.setText("");
                smsMessage.setText("");

                deliveryStatus.setText("SMS Delivered");
            }
        }, new IntentFilter(SMS_DELIVERED_ACTION));
    }

    public void sendSMS(String phoneNumber, String smsMessage){
        SmsManager sms = SmsManager.getDefault();
        List<String> messages = sms.divideMessage(smsMessage);
        for(String message:messages){
            sms.sendTextMessage(phoneNumber, null, message, PendingIntent.getBroadcast(
                    this, 0, new Intent (SMS_SENT_ACTION), 0), PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED_ACTION), 0));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }*/
}
