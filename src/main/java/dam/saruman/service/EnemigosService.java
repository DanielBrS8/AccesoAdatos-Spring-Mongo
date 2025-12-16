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
        Enemigo enemigo = enemigosRepository.findById(id);
        return enemigo;
    }

    public Enemigo guardar(Enemigo enemigo) {
        return enemigosRepository.save(enemigo);
    }

    public Enemigo actualizar(String id, Enemigo enemigoActualizado) {
        if (enemigosRepository.existsById(id)) {
            enemigoActualizado.setId(id);
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
}