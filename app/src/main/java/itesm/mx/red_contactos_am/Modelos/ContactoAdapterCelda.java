package itesm.mx.red_contactos_am.Modelos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import itesm.mx.red_contactos_am.R;

/**
 * Created by Alejandro De la Cruz on 02-Nov-17.
 */

public class ContactoAdapterCelda extends ArrayAdapter<Contacto> {

    private Context context;

    public ContactoAdapterCelda(Context context, ArrayList<Contacto> contactos){
        super(context, 0 , contactos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell, parent, false);
        }

        TextView tvNombre = (TextView) convertView.findViewById(R.id.nombre_contacto_celda);
        ImageView pictureIV = (ImageView) convertView.findViewById(R.id.image_contacto_celda);

        Contacto contacto = getItem(position);
        tvNombre.setText(contacto.getsName());
        byte[] image = contacto.getByPicture();

        if(image != null){
            Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0 , image.length);
            pictureIV.setImageBitmap(bmimage);
        }

        return convertView;
    }

}
