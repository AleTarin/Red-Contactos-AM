package itesm.mx.red_contactos_am;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    private TextView mTextMessage, mTextContenido;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_mensaje);
                    mTextContenido.setText(R.string.contenido_mensaje);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_indicaciones);
                    mTextContenido.setText(R.string.contenido_indicaciones);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_busquedas);
                    mTextContenido.setText(R.string.contenido_busquedas);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        mTextMessage = (TextView) findViewById(R.id.message);
        mTextContenido = (TextView) findViewById(R.id.tvTextoMensaje_info);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
