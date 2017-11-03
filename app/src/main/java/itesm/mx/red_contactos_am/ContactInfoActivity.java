package itesm.mx.red_contactos_am;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactInfoActivity extends AppCompatActivity {

    TextView Nombre, Categoria, Telefono;
    ImageView Foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Foto = (ImageView) findViewById(R.id.iv_Foto_info);
        Nombre = (TextView) findViewById(R.id.tv_nombre_info);
        Categoria = (TextView) findViewById(R.id.tv_categoria_info);
        Telefono = (TextView) findViewById(R.id.tv_telefono_info);
    }
}
