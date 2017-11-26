package itesm.mx.red_contactos_am.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by aletarin on 10/12/17.
 */

public class ContactoDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ContactoDB.itesm.mx.red_contactos_am.db";
    private static final int DATABASE_VERSION = 4;

    public ContactoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACT_TABLE =
                "CREATE TABLE " + DataBaseSchema.ContactoTable.TABLE_NAME +
                        " ( " +
                        DataBaseSchema.ContactoTable._ID + " INTEGER PRIMARY KEY, " +
                        DataBaseSchema.ContactoTable.COLUMN_NAME_NOMBRE + " TEXT NOT NULL, " +
                        DataBaseSchema.ContactoTable.COLUMN_NAME_TELEFONO + " TEXT NOT NULL, " +
                        DataBaseSchema.ContactoTable.COLUMN_NAME_IMAGEN + " BLOB, " +
                        DataBaseSchema.ContactoTable.COLUMN_NAME_CATEGORIA + " TEXT )";

        Log.i("ContactHelper onCreate", CREATE_CONTACT_TABLE);
        sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DELETE_CONTACT_TABLE = "DROP TABLE IF EXISTS " +
                DataBaseSchema.ContactoTable.TABLE_NAME;
        sqLiteDatabase.execSQL(DELETE_CONTACT_TABLE);
        onCreate(sqLiteDatabase);
    }

}
