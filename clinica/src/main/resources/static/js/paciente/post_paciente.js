let form = document.querySelector('#add_patient');

form.addEventListener('submit' , function(event) {
event.preventDefault();

const data = {
apellido : document.querySelector('#apellido').value,
nombre : document.querySelector('#nombre').value,
email: document.querySelector('#mail').value,
dni: document.querySelector('#dni').value,
fechaIngreso: document.querySelector('#fecha_ingreso').value,
domicilio:{
    calle: document.querySelector('#calle').value,
    nroCalle: document.querySelector('#nro_calle').value,
    localidad: document.querySelector('#localidad').value,
    provincia: document.querySelector('#provincia').value
    }

 };

const url = '/pacientes'

const setting = {
method : 'POST',
headers :  {'Content-Type' : 'application/json'} ,
body : JSON.stringify(data)
}

fetch( url, setting )
.then( response => response.json())
.then( dataResponse => {
                         console.log(dataResponse)
                         console.log(response.status)
                         console.log(dataResponse.status)

                         })
.catch(error => console.log(error))

alert("Paciente creado")
location.reload()


} )

