
// variable global para guardar los enemigos actuales (para ordenar y eso)
let enemigosActuales = [];

// cuando carga la pagina pedimos los enemigos
document.addEventListener('DOMContentLoaded', function() {
    cargarEnemigos();
});

// funcion para traer todos los enemigos del servidor
async function cargarEnemigos(){
    try{
        const response = await fetch('/api/enemigos');
        const enemigos = await response.json();
        enemigosActuales = enemigos; // guardamos en la variable global
        mostrarEnemigos(enemigos);
        ocultarError(); // quitamos el error si habia alguno
    }catch (error){
        console.error('Error al cargar enemigos: ' + error);
    }
}

// funcion para buscar enemigos por nombre
async function buscarEnemigos(){
    const nombre = document.getElementById('inputBuscar').value;
    if(nombre.trim() === ''){
        // si no hay nada escrito cargamos todos
        cargarEnemigos();
        return;
    }
    try{
        const response = await fetch('/api/enemigos/buscar?nombre=' + encodeURIComponent(nombre));
        const enemigos = await response.json();
        enemigosActuales = enemigos;
        mostrarEnemigos(enemigos);
    }catch(error){
        console.error('Error al buscar: ' + error);
    }
}

// funcion para ordenar la tabla por nombre alfabeticamente
function ordenarTabla(){
    // ordenamos el array de enemigos por nombre
    enemigosActuales.sort((a, b) => {
        return a.nombre.localeCompare(b.nombre);
    });
    mostrarEnemigos(enemigosActuales);
}

// funcion para descargar la tabla en formato CSV
function descargarCSV(){
    if(enemigosActuales.length === 0){
        alert('No hay datos para descargar');
        return;
    }

    // creamos el contenido del csv
    let csv = 'ID,Nombre,Pais,Afiliacion\n';
    enemigosActuales.forEach(enemigo => {
        // escapamos las comas por si acaso
        csv += `"${enemigo.id}","${enemigo.nombre}","${enemigo.pais}","${enemigo.afiliacion}"\n`;
    });

    // creamos un blob y lo descargamos
    const blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.setAttribute('href', url);
    link.setAttribute('download', 'enemigos.csv');
    link.style.visibility = 'hidden';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// funciones para mostrar y ocultar mensajes de error
function mostrarError(mensaje){
    const divError = document.getElementById('mensajeError');
    divError.textContent = mensaje;
    divError.style.display = 'block';
}

function ocultarError(){
    const divError = document.getElementById('mensajeError');
    divError.style.display = 'none';
}

// pinta los enemigos en la tabla
function mostrarEnemigos(enemigos){
    const tbody = document.getElementById('enemigosBody');

    // limpiamos la tabla antes de meter datos
    tbody.innerHTML = '';

    if(enemigos.length === 0){
        console.log("no hay enemigos en la lista");
        return;
    }

    // recorremos el array y creamos una fila por cada enemigo
    enemigos.forEach(enemigo => {
        const tr = document.createElement('tr');
        // el id de mongo es un string asi que hay que ponerle comillas
        tr.innerHTML = `
            <td>${enemigo.id}</td>
            <td>${enemigo.nombre}</td>
            <td>${enemigo.pais}</td>
            <td>${enemigo.afiliacion}</td>
            <td>
                <button class="btn-editar" onclick="abrirFormularioEditar('${enemigo.id}', '${enemigo.nombre}', '${enemigo.pais}', '${enemigo.afiliacion}')">Editar</button>
                <button class="btn-eliminar" onclick="eliminarEnemigo('${enemigo.id}')">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

// listener del formulario de insertar
document.getElementById('formInsertarEnemigo').addEventListener('submit', insertarEnemigo);

// funcion para insertar un enemigo nuevo
async function insertarEnemigo(e){
    e.preventDefault();

    const nombre = document.getElementById('nombre').value;
    const pais = document.getElementById('pais').value;
    const afiliacion = document.getElementById('afiliacion').value;
    const btnSubmit = document.getElementById('btnSubmit');

    // deshabilitamos el boton mientras se envia
    btnSubmit.disabled = true;
    btnSubmit.textContent = 'Enviando...';

    try{
        const response = await fetch('/api/enemigo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: nombre,
                pais: pais,
                afiliacion: afiliacion
            })
        });

        if(response.ok){
            // limpiamos el formulario y recargamos la tabla
            document.getElementById('formInsertarEnemigo').reset();
            ocultarError();
            await cargarEnemigos();
        }else{
            // mostramos el error que viene del servidor
            const error = await response.text();
            mostrarError(error);
        }
    }catch(error){
        mostrarError('Error al insertar: ' + error);
    }finally{
        btnSubmit.disabled = false;
        btnSubmit.textContent = 'Agregar Enemigo';
    }
}

// abre el formulario de editar y lo rellena con los datos
function abrirFormularioEditar(id, nombre, pais, afiliacion){
    document.getElementById('formEditarContainer').style.display = 'block';

    // metemos los valores en los inputs
    document.getElementById('editId').value = id;
    document.getElementById('editNombre').value = nombre;
    document.getElementById('editPais').value = pais;
    document.getElementById('editAfiliacion').value = afiliacion;

    // hacemos scroll para que se vea el form
    document.getElementById('formEditarContainer').scrollIntoView({ behavior: 'smooth' });
}

// cierra el formulario de editar
function cancelarEdicion(){
    document.getElementById('formEditarContainer').style.display = 'none';
    document.getElementById('formEditarEnemigo').reset();
}

// listener del formulario de editar
document.getElementById('formEditarEnemigo').addEventListener('submit', editarEnemigo);

// envia los cambios al servidor
async function editarEnemigo(e){
    e.preventDefault();

    const id = document.getElementById('editId').value;
    const nombre = document.getElementById('editNombre').value;
    const pais = document.getElementById('editPais').value;
    const afiliacion = document.getElementById('editAfiliacion').value;
    const btnEditar = document.getElementById('btnEditar');

    btnEditar.disabled = true;
    btnEditar.textContent = 'Guardando...';

    try{
        const response = await fetch('/api/enemigo/' + id, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                nombre: nombre,
                pais: pais,
                afiliacion: afiliacion
            })
        });

        if(response.ok){
            cancelarEdicion();
            ocultarError();
            await cargarEnemigos();
        }else{
            // mostramos el error que devuelve el servidor
            const error = await response.text();
            mostrarError(error);
        }
    }catch(error){
        mostrarError('Error al actualizar: ' + error);
    }finally{
        btnEditar.disabled = false;
        btnEditar.textContent = 'Guardar cambios';
    }
}

// elimina un enemigo (pide confirmacion antes)
async function eliminarEnemigo(id){
    // preguntamos antes de borrar por si acaso
    if(!confirm('Seguro que quieres eliminar este enemigo?')){
        return;
    }

    try{
        const response = await fetch('/api/enemigo/' + id, {
            method: 'DELETE'
        });

        if(response.ok){
            // recargamos la tabla
            await cargarEnemigos();
        }else{
            console.log('No se pudo eliminar');
        }
    }catch(error){
        console.log('Error al eliminar: ' + error);
    }
}
