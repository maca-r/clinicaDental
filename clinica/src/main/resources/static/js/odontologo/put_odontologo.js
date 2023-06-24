window.addEventListener('load', function () {


    const formulario = document.querySelector('#update_dentist_form');
    formulario.addEventListener('submit', function (event) {
    event.preventDefault();
        let dentistId = document.querySelector('#dentist_id').value;


        const formData = {
            id: document.querySelector('#dentist_id').value,
            nombre: document.querySelector('#nombre_modifica').value,
            apellido: document.querySelector('#apellido_modifica').value
        };
        const url = '/odontologos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())
          .then(datos => {
          location.reload();
          })
    })
 })

    function findBy(id) {
          const url = '/odontologos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let dentist = data;
              document.querySelector('#dentist_id').value = dentist.id;
              document.querySelector('#nombre_modifica').value = dentist.nombre;
              document.querySelector('#apellido_modifica').value = dentist.apellido;


              document.querySelector('#div_dentist_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }