package Modelos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;

import db.ContactoDBHelper;
import db.DataBaseSchema;

/**
 * Created by aletarin on 10/13/17.
 */

public class ContactoOperations {

    private SQLiteDatabase db;
    private ContactoDBHelper dbHelper;
    private Contacto contact;

    public ContactoOperations(Context context) {
        dbHelper = new ContactoDBHelper(context);
    }

    public void open() throws SQLException {
        try{
            db = dbHelper.getWritableDatabase();
        }catch (SQLException e){
            Log.e("SQLOPEN", e.toString());
        }
    }

    public void close() {
        db.close();
    }

    public long addContact(Contacto contact) {
        long newRowId = 0;
        try {
            ContentValues values = new ContentValues();
            values.put(DataBaseSchema.ContactoTable.COLUMN_NAME_NOMBRE, contact.getsName());
            values.put(DataBaseSchema.ContactoTable.COLUMN_NAME_TELEFONO, contact.getsTelefono());
            values.put(DataBaseSchema.ContactoTable.COLUMN_NAME_IMAGEN, contact.getByPicture());

            newRowId = db.insert(DataBaseSchema.ContactoTable.TABLE_NAME, null, values);
        }catch (SQLException e){
            Log.e("SQLADD", e.toString());
        }
        return newRowId;
    }

    public boolean deleteContact(String contactName) {
        boolean result = false;

        String query = "SELECT * FROM " + DataBaseSchema.ContactoTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.ContactoTable.COLUMN_NAME_NOMBRE +
                " = \"" + contactName + "\"";

        try{
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()){
                int id = Integer.parseInt(cursor.getString(0));
                db.delete(DataBaseSchema.ContactoTable.TABLE_NAME,
                        DataBaseSchema.ContactoTable._ID + " = ?",
                        new String[]{String.valueOf(id)});
                result = true;
            }
            cursor.close();
        }catch (SQLiteException e){
            Log.e("SQLDELETE", e.toString());
        }
        return result;
    }

    public  Contacto findContact (String contactName) {
        String query = "Select * FROM " +
                DataBaseSchema.ContactoTable.TABLE_NAME +
                " WHERE " + DataBaseSchema.ContactoTable.COLUMN_NAME_NOMBRE +
                " = \"" + contactName + "\"";
        try{
            Cursor cursor = db.rawQuery(query, null);
            contact = null;
            if (cursor.moveToFirst()){
                contact = new Contacto(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(3));
            }
            cursor.close();
        }catch (SQLException e ){
            Log.e("SQLFind", e.toString());
        }
        return contact;
    }

    public ArrayList<Contacto> getAllContacts() {
        ArrayList<Contacto> listaContactos = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DataBaseSchema.ContactoTable.TABLE_NAME;

        try{
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()){
                do {
                    contact = new Contacto(Integer.parseInt(cursor.getString(0)),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getBlob(3));
                    listaContactos.add(contact);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (SQLException e) {
            Log.e("SQLList", e.toString());
        }
        return listaContactos;
    }


}

