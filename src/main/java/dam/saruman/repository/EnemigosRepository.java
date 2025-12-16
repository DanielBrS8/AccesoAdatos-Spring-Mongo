package dam.saruman.repository;

import dam.saruman.model.Enemigo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnemigosRepository extends MongoRepository<Enemigo, String> {



}