package dam.saruman.model;


import jakarta.persistence.*;

// Clase que representa a un enemigo en la base de datos
@Entity
@Table(name="ENEMIGOS_DEL_ESTADO")
public class Enemigo {

    // id autogenerado
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String pais;

    @Column
    private String afiliacion;

    // constructor con todos los campos
    public Enemigo(Long id, String pais, String nombre, String afiliacion) {
        this.id = id;
        this.pais = pais;
        this.nombre = nombre;
        this.afiliacion = afiliacion;
    }

    // constructor vacio que necesita jpa
    public Enemigo(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
