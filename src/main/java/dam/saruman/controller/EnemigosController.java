package dam.saruman.controller;

import dam.saruman.model.Enemigo;
import dam.saruman.service.EnemigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controlador rest para los endpoints de enemigos
@RestController
@RequestMapping("/api")
public class EnemigosController {

    @Autowired
    private EnemigosService enemigosService;

    // obtiene la lista de todos los enemigos
    @GetMapping("/enemigos")
    public List<Enemigo> obtenerEnemigos(){
        return enemigosService.obtenerTodos();
    }

    // obtiene un enemigo por su id
    @GetMapping("/enemigo/{id}")
    public ResponseEntity<Enemigo> obtenerEnemigoPorId(@PathVariable String id){
        Enemigo enemigo = enemigosService.obtenerPorId(id);
        // si lo encuentra lo devuelve, si no 404
        if(enemigo != null){
            return ResponseEntity.ok(enemigo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // crea un enemigo nuevo
    @PostMapping("/enemigo")
    public Enemigo crearEnemigo(@RequestBody Enemigo enemigo){
        return enemigosService.guardar(enemigo);
    }

    // actualiza un enemigo existente
    @PutMapping("/enemigo/{id}")
    public ResponseEntity<Enemigo> actualizarEnemigo(@PathVariable String id, @RequestBody Enemigo enemigo){
        Enemigo actualizado = enemigosService.actualizar(id, enemigo);
        if(actualizado != null){
            return ResponseEntity.ok(actualizado);
        }else{
            // no se encontro el enemigo
            return ResponseEntity.notFound().build();
        }
    }

    // elimina un enemigo
    @DeleteMapping("/enemigo/{id}")
    public ResponseEntity<Void> eliminarEnemigo(@PathVariable String id){
        boolean eliminado = enemigosService.eliminar(id);
        if(eliminado){
            return ResponseEntity.ok().build();
        }else{
            // no existia ese enemigo
            return ResponseEntity.notFound().build();
        }
    }
}
