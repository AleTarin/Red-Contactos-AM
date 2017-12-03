package itesm.mx.red_contactos_am;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import itesm.mx.red_contactos_am.Modelos.ContactoAdapterCelda;

public class MessageActivitiy extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lv;
    String sMensaje, telefono;
    Button btnEnviar;
    EditText etMensaje;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
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
        btnEnviar.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         sMensaje = (String)parent.getItemAtPosition(position);
         etMensaje.setText(sMensaje);
    }

    @Override
    public void onClick(View v) {
        sendSMS();
    }


    private void sendSMS() {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(telefono, null, etMensaje.getText().toString(), null, null);
        Toast.makeText(getApplicationContext(), "Mensaje Enviado.",
                Toast.LENGTH_LONG).show();
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

}
