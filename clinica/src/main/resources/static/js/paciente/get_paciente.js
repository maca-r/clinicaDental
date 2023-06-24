window.addEventListener('load', function (event) {
    event.preventDefault();
    (function(){
      const url = '/pacientes';
      const settings = {
        method: 'GET'
    }
    fetch(url,settings)
    .then(response => response.json())
    .then(data => {

         for(patient of data){
          //por cada estudiante armaremos una fila de la tabla
          //cada fila tendrá un id que luego nos permitirá borrar la fila si eliminamos
          //el estudiante

          let table = document.getElementById("patientTable");
          let patientRow = table.insertRow();
          let tr_id = 'tr_' + patient.id;
          patientRow.id = tr_id;

           let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + patient.id + '\"' +
                                      ' type="button" onclick="deleteBy('+patient.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';


          let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + patient.id + '\"' +
                                      ' type="button" onclick="findBy('+patient.id+')" class="btn btn-info btn_id">' +
                                      'update' +
                                      '</button>';

         patientRow.innerHTML =
                              '<td class=\"td_patient_id\">' + patient.id + '</td>' +
                              '<td class=\"td_first_name\">' + patient.nombre.toUpperCase() + '</td>' +
                              '<td class=\"td_last_name\">' + patient.apellido.toUpperCase() + '</td>' +
                              '<td>' + updateButton + '</td>' +
                              '<td>' + deleteButton + '</td>';

        };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/pacienteList.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})


})