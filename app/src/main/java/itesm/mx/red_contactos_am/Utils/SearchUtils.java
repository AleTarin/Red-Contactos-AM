package itesm.mx.red_contactos_am.Utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.text.Normalizer;

import itesm.mx.red_contactos_am.ContactInfoActivity;
import itesm.mx.red_contactos_am.Modelos.Contacto;
import itesm.mx.red_contactos_am.contactosActivity;

/**
 * Created by Alejandro De la Cruz on 02-Dec-17.
 */

public class SearchUtils {

    String s;

    public String normalize(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");  //Quitar acentos
        s = s.replace("h", ""); //Quitar h
        s = s.toLowerCase(); // A minusculas

        return s;
    }


}
