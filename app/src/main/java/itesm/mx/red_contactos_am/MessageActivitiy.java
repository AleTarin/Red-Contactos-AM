package itesm.mx.red_contactos_am;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import itesm.mx.red_contactos_am.Modelos.ContactoAdapterCelda;

public class MessageActivitiy extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lv;
    String sMensaje, telefono;
    Button btnEnviar;
    EditText etMensaje;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 5 ;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_activitiy);
        setTitle("Mandar mensaje");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if (intent.getExtras() != null){
            telefono = intent.getStringExtra("telefono");
        }

        btnEnviar = (Button) findViewById(R.id.btnEnviarMensaje);
        lv = (ListView) findViewById(R.id.listView);
        etMensaje = (EditText) findViewById(R.id.editMensaje);


        lv.setOnItemClickListener(this);
        btnEnviar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (isAirplaneModeOn(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), "Mensaje no enviado, verifique su conexión.",
                            Toast.LENGTH_LONG).show();
                }else{
                    sendSMSMessage();
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         sMensaje = (String)parent.getItemAtPosition(position);
         etMensaje.setText(sMensaje);
    }


    private void sendSMS() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(telefono, null, etMensaje.getText().toString(), null, null);
        Toast.makeText(getApplicationContext(), "Mensaje Enviado.",
                Toast.LENGTH_LONG).show();
        finish();
    }


    protected void sendSMSMessage() {
        sMensaje = etMensaje.getText().toString();
 
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telefono, null, sMensaje, null, null);
                    Toast.makeText(getApplicationContext(), "SMS enviado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "SMS no se envió", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
        finish();
    }

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }
    }


}
