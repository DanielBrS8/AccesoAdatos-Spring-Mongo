package dam.saruman.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;


@Document("enemigo")
public class Enemigo {

    @Id
    private String id;

    private String nombre;
    private String pais;
    private String afiliacion;

    public Enemigo() {}

    public Enemigo(String id, String nombre, String pais, String afiliacion) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.afiliacion = afiliacion;
    }

    // getters y setters igual que antes
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getAfiliacion() { return afiliacion; }
    public void setAfiliacion(String afiliacion) { this.afiliacion = afiliacion; }
}