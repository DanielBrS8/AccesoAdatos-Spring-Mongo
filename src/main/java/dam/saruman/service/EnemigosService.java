package dam.saruman.service;

import dam.saruman.model.Enemigo;
import dam.saruman.repository.EnemigosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// servicio que maneja la logica de negocio de enemigos
@Service
public class EnemigosService {

    // inyectamos el repositorio
    @Autowired
    private EnemigosRepository enemigosRepository;

    // devuelve todos los enemigos
    public List<Enemigo> obtenerTodos() {
        List<Enemigo> enemigos = enemigosRepository.findAll();

        for (Enemigo enemigo : enemigos) {
            System.out.println("ID: " + enemigo.getId() + " Nombre: " + enemigo.getNombre());
        }

        if (enemigos.isEmpty()) {
            System.out.println("No hay enemigos en la coleccion");
        }

        return enemigos;
    }

    // busca un enemigo por su id
    public Enemigo obtenerPorId(String id) {
        return enemigosRepository.findById(id);
    }

    // guarda un enemigo nuevo
    public Enemigo guardar(Enemigo enemigo) {
        Enemigo enemigoGuardado = enemigosRepository.save(enemigo);
        System.out.println("Enemigo insertado con id: " + enemigoGuardado.getId());
        return enemigoGuardado;
    }

    // actualiza un enemigo que ya existe
    public Enemigo actualizar(String id, Enemigo enemigoActualizado) {
        Enemigo resultado = enemigosRepository.update(id, enemigoActualizado);
        if (resultado != null) {
            System.out.println("Enemigo actualizado: " + id);
        }
        return resultado;
    }

    // elimina un enemigo por id
    public boolean eliminar(String id) {
        boolean eliminado = enemigosRepository.deleteById(id);
        if (eliminado) {
            System.out.println("Enemigo eliminado: " + id);
        }
        return eliminado;
    }
}
