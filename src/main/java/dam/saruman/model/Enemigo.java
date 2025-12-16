package dam.saruman.model;

// clase que representa un enemigo (lo usamos para pasar datos)
public class Enemigo {

    // el id en mongo es un string (ObjectId)
    private String id;

    private String nombre;

    private String pais;

    private String afiliacion;

    // constructor con todos los campos
    public Enemigo(String id, String pais, String nombre, String afiliacion) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.afiliacion = afiliacion;
    }

    // constructor vacio
    public Enemigo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getAfiliacion() {
        return afiliacion;
    }

    public void setAfiliacion(String afiliacion) {
        this.afiliacion = afiliacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
