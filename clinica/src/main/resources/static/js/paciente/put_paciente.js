window.addEventListener('load', function () {


    const formulario = document.querySelector('#update_patient_form');
    formulario.addEventListener('submit', function (event) {
    event.preventDefault();
        let patientId = document.querySelector('#patient_id').value;


        const formData = {
            id: document.querySelector('#patient_id').value,
            apellido: document.querySelector('#apellido_modifica').value,
            nombre: document.querySelector('#nombre_modifica').value,
            email : document.querySelector('#email_modifica').value,
            fechaIngreso : document.querySelector('#fecha_ingreso_modifica').value,
//            dni : document.querySelector('#dni_modifica').value,
//            domicilio:{
//                calle: document.querySelector('#calle_modifica').value,
//                nroCalle: document.querySelector('#nro_calle_modifica').value,
//                localidad: document.querySelector('#localidad_modifica').value,
//                provincia: document.querySelector('#provincia_modifica').value
//                }

        };
        const url = '/pacientes';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
         fetch(url,settings)
         .then(response => response.json())
       location.reload()
    })
 })

    function findBy(id) {
          const url = '/pacientes'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let patient = data;
              document.querySelector('#patient_id').value = patient.id;
              document.querySelector('#apellido_modifica').value = patient.apellido;
              document.querySelector('#nombre_modifica').value = patient.nombre;
              document.querySelector('#email_modifica').value = patient.email;
              document.querySelector('#fecha_ingreso_modifica').value = patient.fechaIngreso;
//              document.querySelector('#dni_modifica').value = patient.dni;
//              document.querySelector('#calle_modifica').value = patient.calle;
//              document.querySelector('#nro_calle_modifica').value = patient.nroCalle;
//              document.querySelector('#localidad_modifica').value = patient.localidad;
//              document.querySelector('#provincia_modifica').value = patient.provincia;


            //el formulario por default esta oculto y al editar se habilita
              document.querySelector('#div_patient_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }

