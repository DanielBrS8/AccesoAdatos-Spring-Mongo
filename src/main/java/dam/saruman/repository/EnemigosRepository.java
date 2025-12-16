package dam.saruman.repository;

import dam.saruman.model.Enemigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EnemigosRepository extends MongoRepository<Enemigo, String> {

    // metodo para buscar por nombre exacto (para validar que no exista)
    Optional<Enemigo> findByNombre(String nombre);

    // metodo para buscar por nombre que contenga el texto (para el buscador)
    List<Enemigo> findByNombreContainingIgnoreCase(String nombre);

}