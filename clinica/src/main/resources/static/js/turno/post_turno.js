let form = document.querySelector('#add_shift');

form.addEventListener('submit' , function(event) {
event.preventDefault();

const data = {
paciente_id : document.querySelector('#paciente_id').value,
odontologo_id : document.querySelector('#odontologo_id').value,
hora: document.querySelector('#hora').value,
fecha: document.querySelector('#fecha').value };

const setting = {
method : 'POST',
headers :  {'Content-Type' : 'application/json'} ,
body : JSON.stringify(data)
}

const url = '/turnos'
fetch( url, setting )
.then(response => response.json())
.then( dataResponse => {
                         console.log(response.status)
                         console.log(dataResponse.status)})
.catch(error => console.log(error))

alert("Turno creado");

} )