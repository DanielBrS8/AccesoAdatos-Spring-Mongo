package dam.saruman.Repository;

import dam.saruman.model.Enemigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Repositorio para acceder a la tabla de enemigos
 * extiende de JpaRepository asi que ya tiene los metodos basicos
 * como findAll, save, deleteById, etc
 */
@Repository
public interface EnemigosRepository extends JpaRepository<Enemigo, Long> {

    // metodo para buscar por nombre (lo genera spring automaticamente)
    List<Enemigo> findByNombre(String nombre);

}
