// Script para insertar datos de prueba en MongoDB
// Ejecutar en mongosh o en MongoDB Compass

// primero nos conectamos a la base de datos test
use test

// creamos la coleccion enemigo si no existe y metemos datos
db.enemigo.insertMany([
    {
        nombre: "Gandalf el Gris",
        pais: "La Comarca",
        afiliacion: "Istari"
    },
    {
        nombre: "Aragorn",
        pais: "Gondor",
        afiliacion: "Dunedain"
    },
    {
        nombre: "Legolas",
        pais: "Bosque Negro",
        afiliacion: "Elfos Silvanos"
    },
    {
        nombre: "Gimli",
        pais: "Montanas Azules",
        afiliacion: "Enanos de Erebor"
    },
    {
        nombre: "Frodo Bolson",
        pais: "La Comarca",
        afiliacion: "Hobbits"
    },
    {
        nombre: "Samwise Gamyi",
        pais: "La Comarca",
        afiliacion: "Hobbits"
    },
    {
        nombre: "Theoden",
        pais: "Rohan",
        afiliacion: "Rohirrim"
    },
    {
        nombre: "Eomer",
        pais: "Rohan",
        afiliacion: "Rohirrim"
    },
    {
        nombre: "Faramir",
        pais: "Gondor",
        afiliacion: "Montaraces de Ithilien"
    },
    {
        nombre: "Elrond",
        pais: "Rivendel",
        afiliacion: "Elfos Noldor"
    }
])

// para ver que se insertaron
db.enemigo.find()
