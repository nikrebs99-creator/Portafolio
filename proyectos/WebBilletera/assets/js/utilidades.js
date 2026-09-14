//console.log("¡El archivo utilidades.js está conectado perfectamente!"); // porsiacaso no me lee xd


//POP-UP! parte 1 (por lo visto, se llaman modales)
function cargarModal() {
    // Se guarda el diseño html para e
    const modalHTML = `
    <div class="modal fade" id="modalGlobal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            
            <div class="modal-content" style="background: rgba(40, 20, 80, 0.95); backdrop-filter: blur(10px); border: 1px solid rgba(255, 255, 255, 0.2); color: white; border-radius: 15px;">
                
                <div class="modal-header" style="border-bottom: 1px solid rgba(255, 255, 255, 0.1);">
                    <h5 class="modal-title" id="modalTitulo">Mensaje</h5>
                </div>
                
                <div class="modal-body" id="modalTexto" style="font-size: 1.1em;">
                    </div>
                
                <div class="modal-footer" style="border-top: 1px solid rgba(255, 255, 255, 0.1);">
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnModalAceptar">Aceptar</button>
                </div>
                
            </div>
        </div>
    </div>
    `;

    // buscamos la etiqueta <body> y append lo añade al final de la misma
    $('body').append(modalHTML);

}

// POP UP paso 2
// window al inicio para que sea una variable global y se pueda usar libremente en cualquier archivo
window.mostrarMensaje = function (titulo, texto, tipo = "info", urlDestino = null) {
    if ($('#modalGlobal').length === 0) {
        cargarModal();
    }
    // al llamar a la funcion, será tipo mostrarMensaje(ADVERTENCIA, SALDO INSUFICIENTE, "info") (ejemplo)
    // las el "tipo" lo definimos más adelante, de esta forma podemos asignar colores distintos más adelante
    $('#modalTitulo').text(titulo);
    // buscamos el titulo que le dimos a la función
    $('#modalTexto').html(texto);
    // y el texto


    // 2. header y btn como variables para establecerles colores dependiendo el tipo
    const $header = $('.modal-header');
    const $btn = $('#btnModalAceptar');

    // clean de los colores, porsiacaso
    $header.css('color', 'white');
    $btn.removeClass('btn-danger btn-success btn-info btn-primary').addClass('btn-primary');

    // tipo: "error" boton color rojo
    if (tipo === "error") {
        $header.css('color', '#ff6b6b');
        $btn.removeClass('btn-primary').addClass('btn-danger');

        // tipo: "éxito" color verde
    }
    else if (tipo === "exito") {
        $header.css('color', '#deff9a');
        $btn.removeClass('btn-primary').addClass('btn-success');
    }
    // lógica, para que no hayan evento asíncronos
    // limpiando "caché" de algún botón anterior
    $('#modalGlobal').off('hidden.bs.modal');

    // Si nos pasaste una página a la cual ir...
    if (urlDestino) {

        // apretaste aceptar?
        $('#modalGlobal').on('hidden.bs.modal', function () {
            window.location.href = urlDestino;
        });

        // 3 segundos o el exitaso de cierra 
        setTimeout(function () {
            if ($('#modalGlobal').is(':visible')) {
                $('#modalGlobal').modal('hide');
            }
        }, 3000);
        
        
    }
$('#modalGlobal').modal('show');

};


// Footer "dinámico"
function cargarFooter() {
    // 
    const diseñoFooter = `
    
    <p class="text-center mb-1" style="color: #27c5ca; font-weight: bold; font-size: 1.1em;" id="relojGlobal"></p>
    <p class="text-center" style="color: #838282;">&copy; 2026 Alke Wallet. Todos los derechos reservados.</p>
    
    `
    $('#footerGlobal').html(diseñoFooter) //jquery.busqueda($)('classID(#) footerglobal') y funciona como const diseñoFooter
}

// Reloj //
function actualizarReloj() {
    // calcula la hora  y la transforma en const
    const ahora = new Date();
    //Darle formato HH:MM:SS
    const horaFormateada = ahora.toLocaleTimeString('es-CL')
    //cambiamos el texto a jquery para poder trabajarlo como corresponde
    //js para hacer calculos, jquery para implementarlo para seguir el esquema del resto de html's
    $('#relojGlobal').text(horaFormateada);
}

// Sin gorrito no hay fiesta
$(document).ready(function () {
    //primero tiene que aparecer el footer y cargar los popups
    cargarModal()
    cargarFooter()

    //luego cargar el reloj
    if ($('#relojGlobal').length > 0) { //el # hace que la variable sea detectable!! $ no basta como señal
        actualizarReloj(); //se ejecuta apenas se detecte un reloj en pantalla, para que no haya delay
        setInterval(actualizarReloj, 1000) //cada 1 segundo se repite la la función
        //$ = jquery.buscar // si coloco solo texto entre parentesis, es búsqueda de todas las etiquetas con ese nombre // # Filtro para buscar específicamente ese ID
    }


});





