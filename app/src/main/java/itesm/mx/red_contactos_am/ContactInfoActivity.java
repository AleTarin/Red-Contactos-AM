package itesm.mx.red_contactos_am;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactInfoActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvNombre, tvCategoria, tvTelefono;
    ImageView ivFoto, ivZoomIn, ivZoomOut;
    String nombre, telefono, categoria;
    byte[] foto;
    ImageButton btnLlamada, btnMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        ivFoto = (ImageView) findViewById(R.id.iv_Foto_info);
        tvNombre = (TextView) findViewById(R.id.tv_nombre_info);
        tvCategoria = (TextView) findViewById(R.id.tv_categoria_info);
        tvTelefono = (TextView) findViewById(R.id.tv_telefono_info);
        btnLlamada = (ImageButton) findViewById(R.id.btnllamada);
        btnMensaje = (ImageButton) findViewById(R.id.btnMensajeInfo);
        ivZoomIn = (ImageView) findViewById(R.id.ivZoomIn_ContactoInfo);
        ivZoomOut = (ImageView) findViewById(R.id.ivZoomOut_ContactoInfo);

        Intent intent = getIntent();

        if (intent.getExtras() != null){
            nombre = intent.getStringExtra("name");
            tvNombre.setText(nombre);
            foto = intent.getByteArrayExtra("foto");
            telefono = intent.getStringExtra("telefono");
            tvTelefono.setText(telefono);
            categoria = intent.getStringExtra("categoria");
            tvCategoria.setText(categoria);
        }

        setTitle(nombre);

        if(foto != null){
            Bitmap bmimage = BitmapFactory.decodeByteArray(foto, 0 , foto.length);
            ivFoto.setImageBitmap(bmimage);
        }

        btnMensaje.setOnClickListener(this);
        btnLlamada.setOnClickListener(this);
        ivZoomOut.setOnClickListener(this);
        ivZoomIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnllamada:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+telefono));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }else {     //have got permission
                    try{
                        startActivity(callIntent);  //call activity and make phone call
                    }
                    catch (android.content.ActivityNotFoundException ex){
                        Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnMensajeInfo:
                Intent intent = new Intent(ContactInfoActivity.this, MessageActivitiy.class );
                intent.putExtra("telefono", telefono);
                startActivity(intent);
                break;
            case R.id.ivZoomIn_ContactoInfo:
                ZoomIn();
                break;
            case R.id.ivZoomOut_ContactoInfo:
                ZoomOut();
                break;
        }
    }

     public void ZoomIn(){
            // calculate current scale x and y value of ImageView
            float x = ivFoto.getScaleX();
            float y = ivFoto.getScaleY();
            float textSize = tvTelefono.getTextSize();

         // set increased value of scale x and y to perform zoom in functionality
         float newX = (float) (x + .2);
         float newY = (float) (y + .2);
         if (newX >= 1 && newX < 2.5) {
             ivFoto.setScaleX(newX);
             ivFoto.setScaleY(newY);
         }
         /*
            tvTelefono.setTextSize((float) (textSize + .0002));
            tvCategoria.setTextSize((float) (textSize + .0002));
            tvNombre.setTextSize((float) (textSize + .0002));*/


         // display a toast to show Zoom In Message on Screen
            //Toast.makeText(getApplicationContext(),"Zoom In" + x,Toast.LENGTH_SHORT).show();
        }

    public void ZoomOut() {
       // calculate current scale x and y value of ImageView
            float x = ivFoto.getScaleX();
            float y = ivFoto.getScaleY();
        float textSize = tvTelefono.getTextSize();

        // set decreased value of scale x and y to perform zoom out functionality
        float newX = (float) (x - .2);
        float newY = (float) (y - .2);
        if (newX >= 1 && newX < 2.5) {
            ivFoto.setScaleX(newX);
            ivFoto.setScaleY(newY);
        }
            // display a toast to show Zoom Out Message on Screen
       /* tvTelefono.setTextSize((float) (textSize - .0002));
        tvCategoria.setTextSize((float) (textSize - .0002));
        tvNombre.setTextSize((float) (textSize - .0002));*/
            //Toast.makeText(getApplicationContext(),"Zoom Out" + x,Toast.LENGTH_SHORT).show();
        }
}
