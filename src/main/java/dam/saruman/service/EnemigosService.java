package dam.saruman.service;

import dam.saruman.Repository.EnemigosRepository;
import dam.saruman.model.Enemigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// servicio para manejar la logica de negocio de los enemigos
@Service
public class EnemigosService {

    @Autowired
    private EnemigosRepository enemigoRepository;

    // devuelve todos los enemigos de la bbdd
    public List<Enemigo> obtenerTodos(){
        List<Enemigo> enemigos = enemigoRepository.findAll();
        if(enemigos.isEmpty()){
            System.out.println("Lista esta vacia");
        }else{
            // mostramos por consola para ver que funciona
            enemigos.forEach(enemigo -> {
                System.out.println("ID: "+enemigo.getId()+" Nombre "+enemigo.getNombre()+" pais "+enemigo.getPais());
            });
        }
        return enemigos;
    }

    // busca un enemigo por su id
    public Optional<Enemigo> obtenerPorId(Long id){
        return enemigoRepository.findById(id);
    }

    // guarda un enemigo nuevo
    public Enemigo guardar(Enemigo enemigo){
        return enemigoRepository.save(enemigo);
    }

    // actualiza un enemigo que ya existe
    public Enemigo actualizar(Long id, Enemigo enemigoActualizado){
        // primero comprobamos que existe
        Optional<Enemigo> enemigoExistente = enemigoRepository.findById(id);

        if(enemigoExistente.isPresent()){
            Enemigo enemigo = enemigoExistente.get();
            // actualizamos los campos
            enemigo.setNombre(enemigoActualizado.getNombre());
            enemigo.setPais(enemigoActualizado.getPais());
            enemigo.setAfiliacion(enemigoActualizado.getAfiliacion());
            return enemigoRepository.save(enemigo);
        }else{
            // si no existe devolvemos null y ya lo manejamos en el controller
            return null;
        }
    }

    // elimina un enemigo por id
    public boolean eliminar(Long id){
        // comprobamos si existe antes de borrar
        if(enemigoRepository.existsById(id)){
            enemigoRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
