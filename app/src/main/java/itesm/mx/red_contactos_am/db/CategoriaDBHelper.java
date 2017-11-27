package itesm.mx.red_contactos_am.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aletarin on 10/13/17.
 */

public class CategoriaDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CategoriaDB.itesm.mx.red_contactos_am.db";
    private static final int DATABASE_VERSION = 3;

    public CategoriaDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CATEGORIA_TABLE =
                "CREATE TABLE " + DataBaseSchema.CategoriaTable.TABLE_NAME + " (" +
                        DataBaseSchema.CategoriaTable._ID + "INTEGER PRIMARY KEY, " +
                        DataBaseSchema.CategoriaTable.COLUMN_NAME_NOMBRE + "TEXT NOT NULL, " +
                        DataBaseSchema.CategoriaTable.COLUMN_NAME_COLOR  + "TEXT, " +
                        DataBaseSchema.CategoriaTable.COLUMN_NAME_IMAGEN + "BLOB, " +
                        " FOREIGN KEY ( " + DataBaseSchema.CategoriaTable.COLUMN_NAME_CONTACTO + " ) " +
                        " REFERENCES (" + DataBaseSchema.CategoriaTable._ID + " )" +
                        " )";

        Log.i("CategoryHelper onCreate", CREATE_CATEGORIA_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORIA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DELETE_CATEGORIA_TABLE = "DROP TABLE IF EXIST" +
                DataBaseSchema.CategoriaTable.TABLE_NAME;
        sqLiteDatabase.execSQL(DELETE_CATEGORIA_TABLE);
        onCreate(sqLiteDatabase);
    }
}
