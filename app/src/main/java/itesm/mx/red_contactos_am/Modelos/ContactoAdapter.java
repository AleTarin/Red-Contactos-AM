package itesm.mx.red_contactos_am.Modelos;

/**
 * Created by Alejandro De la Cruz on 01-Nov-17.
 */

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
 * Created by aletarin on 10/9/17.
 */

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    private Context context;

    public ContactoAdapter(Context context, ArrayList<Contacto> contactos){
        super(context, 0 , contactos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }

        TextView tvId = (TextView) convertView.findViewById(R.id.tv_id_row);
        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_nombre_row);
        TextView tvTelefono = (TextView) convertView.findViewById(R.id.tv_telefono_row);
        TextView tvCategoria = (TextView) convertView.findViewById(R.id.tv_categoria_row);
        ImageView pictureIV = (ImageView) convertView.findViewById(R.id.iv_foto_row);

        Contacto contacto = getItem(position);
        tvId.setText(Long.toString(contacto.getId()));
        tvNombre.setText(contacto.getsName());
        tvTelefono.setText(contacto.getsTelefono());
        tvCategoria.setText(contacto.getsCategoria());
        byte[] image = contacto.getByPicture();

        if(image != null){
            Bitmap bmimage = BitmapFactory.decodeByteArray(image, 0 , image.length);
            pictureIV.setImageBitmap(bmimage);
        }

        return convertView;
    }

}
