package itesm.mx.red_contactos_am.Modelos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by aletarin on 10/13/17.
 */

public class CategoriaAdapter extends ArrayAdapter<Contacto> {

    private Context context;

    public CategoriaAdapter(Context context, ArrayList<Contacto> contactos) {
        super(context, 0, contactos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Insert code here

        return convertView;
    }
}
