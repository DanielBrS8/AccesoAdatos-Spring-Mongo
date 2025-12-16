package dam.saruman.controller;

import dam.saruman.model.Enemigo;
import dam.saruman.service.EnemigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // endpoint para buscar enemigos por nombre
    @GetMapping("/enemigos/buscar")
    public List<Enemigo> buscarPorNombre(@RequestParam String nombre){
        return enemigosService.buscarPorNombre(nombre);
    }

    // crea un enemigo nuevo con validacion
    @PostMapping("/enemigo")
    public ResponseEntity<?> crearEnemigo(@RequestBody Enemigo enemigo){
        try{
            Enemigo guardado = enemigosService.guardar(enemigo);
            return ResponseEntity.ok(guardado);
        }catch(IllegalArgumentException e){
            // devolvemos el mensaje de error que viene del servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // actualiza un enemigo existente
    @PutMapping("/enemigo/{id}")
    public ResponseEntity<?> actualizarEnemigo(@PathVariable String id, @RequestBody Enemigo enemigo){
        try{
            Enemigo actualizado = enemigosService.actualizar(id, enemigo);
            if(actualizado != null){
                return ResponseEntity.ok(actualizado);
            }else{
                // no se encontro el enemigo
                return ResponseEntity.notFound().build();
            }
        }catch(IllegalArgumentException e){
            // error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
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
