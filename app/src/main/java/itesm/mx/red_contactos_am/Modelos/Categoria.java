package itesm.mx.red_contactos_am.Modelos;

/**
 * Created by aletarin on 10/13/17.
 */

public class Categoria {

    private long id;
    private String sName;
    private String sColor;
    private byte[] byPicture;

    public Categoria() {
        this.id = 0;
        this.byPicture = null;
        this.sColor = null;
        this.sName = null;
    }

    public Categoria(long id, String sName, String sColor, byte[] byPicture) {
        this.id = id;
        this.sName = sName;
        this.sColor = sColor;
        this.byPicture = byPicture;
    }

    public Categoria(String sName, String sColor, byte[] byPicture) {
        this.sName = sName;
        this.sColor = sColor;
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

    public String getsColor() {
        return sColor;
    }

    public void setsColor(String sColor) {
        this.sColor = sColor;
    }

    public byte[] getByPicture() {
        return byPicture;
    }

    public void setByPicture(byte[] byPicture) {
        this.byPicture = byPicture;
    }
}
