let form = document.querySelector('#add_dentist');


form.addEventListener('submit' , function(event) {
event.preventDefault();

const data = {
nombre : document.querySelector('#nombre').value,
apellido : document.querySelector('#apellido').value,
matricula: document.querySelector('#matricula').value
 };

const url = '/odontologos'
const setting = {
method : 'POST',
headers :  {'Content-Type' : 'application/json'} ,
body : JSON.stringify(data)
}

fetch( url, setting )
.then(response => response.json())
.then( dataResponse => {
                         console.log(response.status)
                         console.log(dataResponse.status)
                         })
.catch(error => console.log(error))

alert("Odontologo creado")

form.addEventListener('click',function(event){
event.preventDefault();
history.back();
})

} )