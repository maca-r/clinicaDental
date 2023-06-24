window.addEventListener('load', function () {


    const formulario = document.querySelector('#update_shift_form');
    formulario.addEventListener('submit', function (event) {
        let shiftId = document.querySelector('#shift_id').value;


        const formData = {
            id: document.querySelector('#shift_id').value,
            paciente: document.querySelector('#paciente').value,
            odontologo: document.querySelector('#odontologo').value,
            hora : document.querySelector('#hora').value,
            fecha : document.querySelector('#fecha').value,


        };
        const url = '/turnos';
        const settings = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }
          fetch(url,settings)
          .then(response => response.json())

    })
 })

    function findBy(id) {
          const url = '/turnos'+"/"+id;
          const settings = {
              method: 'GET'
          }
          fetch(url,settings)
          .then(response => response.json())
          .then(data => {
              let shift = data;
              document.querySelector('#shift_id').value = shift.id;
              document.querySelector('#paciente_modifica').value = shift.paciente;
              document.querySelector('#odontologo_modifica').value = shift.odontologo;
              document.querySelector('#hora_modifica').value = shift.hora;
              document.querySelector('#fecha_modifica').value = shift.fecha;


              document.querySelector('#div_shift_updating').style.display = "block";
          }).catch(error => {
              alert("Error: " + error);
          })
      }