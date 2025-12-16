package dam.saruman.service;

import dam.saruman.model.Enemigo;
import dam.saruman.repository.EnemigosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnemigosService {

    @Autowired
    private EnemigosRepository enemigosRepository;

    public List<Enemigo> obtenerTodos() {
        return enemigosRepository.findAll();
    }

    public Enemigo obtenerPorId(String id) {
        Optional<Enemigo> enemigo = enemigosRepository.findById(id);
        return enemigo.orElse(null);
    }

    // valida el nombre antes de guardar y lanza excepcion si hay error
    public Enemigo guardar(Enemigo enemigo) {
        // primero validamos que el nombre tenga al menos 3 caracteres
        if(enemigo.getNombre() == null || enemigo.getNombre().trim().length() < 3){
            throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
        }

        // comprobamos que no exista otro enemigo con el mismo nombre
        Optional<Enemigo> existente = enemigosRepository.findByNombre(enemigo.getNombre().trim());
        if(existente.isPresent()){
            throw new IllegalArgumentException("Ya existe un enemigo con ese nombre");
        }

        // si pasa las validaciones lo guardamos
        enemigo.setNombre(enemigo.getNombre().trim());
        return enemigosRepository.save(enemigo);
    }

    public Enemigo actualizar(String id, Enemigo enemigoActualizado) {
        if (enemigosRepository.existsById(id)) {
            // validamos el nombre tambien al actualizar
            if(enemigoActualizado.getNombre() == null || enemigoActualizado.getNombre().trim().length() < 3){
                throw new IllegalArgumentException("El nombre debe tener al menos 3 caracteres");
            }

            // comprobamos que no exista otro enemigo con el mismo nombre (pero que no sea el mismo que estamos editando)
            Optional<Enemigo> existente = enemigosRepository.findByNombre(enemigoActualizado.getNombre().trim());
            if(existente.isPresent() && !existente.get().getId().equals(id)){
                throw new IllegalArgumentException("Ya existe un enemigo con ese nombre");
            }

            enemigoActualizado.setId(id);
            enemigoActualizado.setNombre(enemigoActualizado.getNombre().trim());
            return enemigosRepository.save(enemigoActualizado);
        }
        return null;
    }

    public boolean eliminar(String id) {
        if (enemigosRepository.existsById(id)) {
            enemigosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // busca enemigos por nombre (busqueda parcial)
    public List<Enemigo> buscarPorNombre(String nombre) {
        return enemigosRepository.findByNombreContainingIgnoreCase(nombre);
    }
}