package Modelos;

/**
 * Created by aletarin on 10/13/17.
 */

public class Contacto {
    private long id;
    private String sName;
    private String sTelefono;
    private byte[] byPicture;


    public Contacto(long id, String sName, String sTelefono, byte[] byPicture, String sCategoria) {
        this.id = id;
        this.sName = sName;
        this.sTelefono = sTelefono;
        this.byPicture = byPicture;
        this.sCategoria = sCategoria;
    }

    private String sCategoria;

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

    public Contacto(long id, String sName, String sTelefono, byte[] byPicture) {
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
}
