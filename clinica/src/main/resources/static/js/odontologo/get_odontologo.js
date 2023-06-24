window.addEventListener('load', function (event) {
    event.preventDefault();
    (function(){
      const url = '/odontologos';
      const settings = {
        method: 'GET'
    }
    fetch(url,settings)
    .then(response => response.json())

    .then(data => {
         console.log(data)
         for(dentist of data){

          let table = document.getElementById("dentistTable");
          let dentistRow = table.insertRow();
          let tr_id = 'tr_' + dentist.id;
          dentistRow.id = tr_id;

           let deleteButton = '<button' +
                                      ' id=' + '\"' + 'btn_delete_' + dentist.id + '\"' +
                                      ' type="button" onclick="deleteBy('+dentist.id+')" class="btn btn-danger btn_delete">' +
                                      '&times' +
                                      '</button>';


          let updateButton = '<button' +
                                      ' id=' + '\"' + 'btn_id_' + dentist.id + '\"' +
                                      ' type="button" onclick="findBy('+dentist.id+')" class="btn btn-info btn_id">' +
                                      'update' +
                                      '</button>';

         dentistRow.innerHTML =
                              '<td class=\"td_odontologo_id\">' + dentist.id + '</td>' +
                              '<td class=\"td_first_name\">' + dentist.nombre + '</td>' +
                              '<td class=\"td_last_name\">' + dentist.apellido + '</td>' +
                              '<td>' + updateButton + '</td>' +
                              '<td>' + deleteButton + '</td>';

        };

})
})

(function(){
  let pathname = window.location.pathname;
  if (pathname == "/odontologoList.html") {
      document.querySelector(".nav .nav-item a:last").addClass("active");
  }
})


})