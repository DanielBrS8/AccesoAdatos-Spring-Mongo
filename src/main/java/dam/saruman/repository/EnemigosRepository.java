package dam.saruman.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dam.saruman.model.Enemigo;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// repositorio que maneja el acceso a datos de MongoDB
@Repository
public class EnemigosRepository {

    // la coleccion de mongo donde guardamos los enemigos
    private MongoCollection<Document> collection;

    // constructor donde conectamos a mongo
    public EnemigosRepository() {
        // uri de conexion a mongo local
        String uri = "mongodb://localhost:27017";

        try {
            // creamos el cliente de mongo
            MongoClient mongoClient = MongoClients.create(uri);
            // cogemos la base de datos test
            MongoDatabase database = mongoClient.getDatabase("test");
            // cogemos la coleccion enemigo
            this.collection = database.getCollection("enemigo");
            System.out.println("Conexion a MongoDB exitosa");
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    // devuelve todos los enemigos
    public List<Enemigo> findAll() {
        List<Enemigo> enemigos = new ArrayList<>();

        // recorremos todos los documentos de la coleccion
        for (Document doc : collection.find()) {
            // convertimos cada documento a un objeto Enemigo
            Enemigo enemigo = documentToEnemigo(doc);
            enemigos.add(enemigo);
        }

        return enemigos;
    }

    // busca un enemigo por su id
    public Enemigo findById(String id) {
        // creamos el filtro para buscar por _id
        Document filtro = new Document("_id", new ObjectId(id));
        Document doc = collection.find(filtro).first();

        if (doc != null) {
            return documentToEnemigo(doc);
        }
        return null;
    }

    // guarda un enemigo nuevo
    public Enemigo save(Enemigo enemigo) {
        // creamos el documento con los datos
        Document doc = new Document("nombre", enemigo.getNombre())
                .append("pais", enemigo.getPais())
                .append("afiliacion", enemigo.getAfiliacion());

        // lo insertamos en la coleccion
        collection.insertOne(doc);

        // le ponemos el id que genero mongo
        enemigo.setId(doc.getObjectId("_id").toString());

        return enemigo;
    }

    // actualiza un enemigo que ya existe
    public Enemigo update(String id, Enemigo enemigoActualizado) {
        // filtro para buscar el documento por id
        Document filtro = new Document("_id", new ObjectId(id));

        // los nuevos valores que queremos poner
        Document nuevosValores = new Document("$set",
                new Document("nombre", enemigoActualizado.getNombre())
                        .append("pais", enemigoActualizado.getPais())
                        .append("afiliacion", enemigoActualizado.getAfiliacion()));

        // hacemos el update
        var resultado = collection.updateOne(filtro, nuevosValores);

        if (resultado.getModifiedCount() > 0) {
            enemigoActualizado.setId(id);
            return enemigoActualizado;
        }

        return null;
    }

    // elimina un enemigo por id
    public boolean deleteById(String id) {
        // filtro para buscar por id
        Document filtro = new Document("_id", new ObjectId(id));

        // borramos el documento
        var resultado = collection.deleteOne(filtro);

        return resultado.getDeletedCount() > 0;
    }

    // metodo auxiliar para convertir un Document a Enemigo
    private Enemigo documentToEnemigo(Document doc) {
        Enemigo enemigo = new Enemigo();
        enemigo.setId(doc.getObjectId("_id").toString());
        enemigo.setNombre(doc.getString("nombre"));
        enemigo.setPais(doc.getString("pais"));
        enemigo.setAfiliacion(doc.getString("afiliacion"));
        return enemigo;
    }
}
