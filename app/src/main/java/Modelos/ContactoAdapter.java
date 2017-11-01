package Modelos;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import Modelos.Contacto;

/**
 * Created by aletarin on 10/13/17.
 */

public class ContactoAdapter extends ArrayAdapter<Contacto>{

    private Context context;

    public ContactoAdapter(Context context, ArrayList<Contacto> contactos) {
        super(context, 0, contactos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Insert code here

        return convertView;
    }
}
