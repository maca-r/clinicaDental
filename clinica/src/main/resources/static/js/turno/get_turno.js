window.addEventListener('load', function () {

    (function(){
      const url = '/turnos';
      const settings = {
        method: 'GET'
    }
    fetch(url,settings)
    .then(response => response.json())
    .then(data => {

         for(shift of data){

          let table = document.getElementById("shiftTable");
          let shiftRow = table.insertRow();
          let tr_id = 'tr_' + shift.id;
          shiftRow.id = tr_id;

           let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + shift.id + '\"' +
                                      ' type="button" onclick="deleteBy('+shift.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';


          let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + shift.id + '\"' +
                                      ' type="button" onclick="findBy('+shift.id+')" class="btn btn-info btn_id">' +
                                      'update' +
                                      '</button>';

         shiftRow.innerHTML =
                              '<td class=\"td_first_name\">' + shift.paciente_id + '</td>' +
                              '<td class=\"td_first_name\">' + shift.odontologo_id + '</td>' +
                              '<td class=\"td_first_name\">' + shift.hora + '</td>' +
                              '<td class=\"td_last_name\">' + shift.fecha + '</td>' +
                              '<td>' + updateButton + '</td>' +
                              '<td>' + deleteButton + '</td>';

        };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/turnoList.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})


})