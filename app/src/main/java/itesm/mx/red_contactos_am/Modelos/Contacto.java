package itesm.mx.red_contactos_am.Modelos;

import itesm.mx.red_contactos_am.Utils.SearchUtils;

/**
 * Created by aletarin on 10/13/17.
 */

public class Contacto {
    private long id;
    private String sName;
    private String sNameNormalized;
    private String sTelefono;
    private byte[] byPicture;
    private String sCategoria;

    public Contacto(long id, String sName, String sNameNormalized, String sTelefono, byte[] byPicture, String sCategoria) {
        this.id = id;
        this.sName = sName;
        this.sNameNormalized = sNameNormalized;
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
        this.sCategoria = sCategoria;
    }

    public Contacto(long id, String sName, String sTelefono, byte[] byPicture, String sCategoria) {
        this.id = id;
        this.sName = sName;
        this.sNameNormalized = new SearchUtils().normalize(sName);
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
        this.sCategoria = sCategoria;
    }

    public Contacto(String sName, String sTelefono, byte[] byPicture, String sCategoria) {
        this.sName = sName;
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
        this.sCategoria = sCategoria;
    }

    public Contacto() {
        this.id = 0;
        this.sName = null;
        this.sTelefono = null;
        this.byPicture = null;
    }

    public Contacto(String sName, String sTelefono, byte[] byPicture) {
        this.sName = sName;
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
    }

    public Contacto(int id, String sName, String sTelefono, byte[] byPicture) {
        this.id = id;
        this.sName = sName;
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsTelefono() {
        return sTelefono;
    }

    public void setsTelefono(String sTelefono) {
        this.sTelefono = sTelefono;
    }

    public byte[] getByPicture() {
        return byPicture;
    }

    public void setByPicture(byte[] byPicture) {
        this.byPicture = byPicture;
    }

    public String getsCategoria() {
        return sCategoria;
    }

    public void setsCategoria(String sCategoria) {
        this.sCategoria = sCategoria;
    }

    public String getsNameNormalized() {
        return sNameNormalized;
    }

    public void setsNameNormalized(String sNameNormalized) {
        this.sNameNormalized = sNameNormalized;
    }
}
