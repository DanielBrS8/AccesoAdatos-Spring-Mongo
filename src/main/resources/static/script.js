
// cuando carga la pagina pedimos los enemigos
document.addEventListener('DOMContentLoaded', function() {
    cargarEnemigos();
});

// funcion para traer todos los enemigos del servidor
async function cargarEnemigos(){
    try{
        const response = await fetch('/api/enemigos');
        const enemigos = await response.json();
        mostrarEnemigos(enemigos);
    }catch (error){
        console.error('Error al cargar enemigos: ' + error);
    }
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
        tr.innerHTML = `
            <td>${enemigo.id}</td>
            <td>${enemigo.nombre}</td>
            <td>${enemigo.pais}</td>
            <td>${enemigo.afiliacion}</td>
            <td>
                <button class="btn-editar" onclick="abrirFormularioEditar(${enemigo.id}, '${enemigo.nombre}', '${enemigo.pais}', '${enemigo.afiliacion}')">Editar</button>
                <button class="btn-eliminar" onclick="eliminarEnemigo(${enemigo.id})">Eliminar</button>
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
            await cargarEnemigos();
        }else{
            const error = await response.text();
            console.log('Error: ' + error);
        }
    }catch(error){
        console.log('Error al insertar: ' + error);
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
            await cargarEnemigos();
        }else{
            console.log('Error al actualizar');
        }
    }catch(error){
        console.log('Error: ' + error);
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
