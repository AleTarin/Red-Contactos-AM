package itesm.mx.red_contactos_am.db;

/**
 * Created by aletarin on 10/12/17.
 */

import android.provider.BaseColumns;

public final class
DataBaseSchema {

    private DataBaseSchema(){

    }
    public static class ContactoTable implements BaseColumns{
        public static final String TABLE_NAME = "Contacto";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_TELEFONO = "Telefono";
        public static final String COLUMN_NAME_IMAGEN = "Imagen";
        public static final String COLUMN_NAME_CATEGORIA = "Categoria";
    }

    public static class CategoriaTable implements BaseColumns{
        public static final String TABLE_NAME = "Categoria";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_COLOR = "Color";
        public static final String COLUMN_NAME_IMAGEN = "Imagen";
        public static final String COLUMN_NAME_CONTACTO = "Contactos";
    }
}
