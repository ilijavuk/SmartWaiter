$.ajax({
  method: "POST",
  dataType: 'json',
  url: "./admin.php?stvar=listaStolova",
}).done(function(data){
  for(var i=0; i<data.length; i++){
    if(data[i].hash==null){
      data[i].akcija="<a href=# onclick=GenerirajQR("+data[i].id_stol+")>Generiraj</a>";
    }
    else{
      data[i].akcija="<a href=# onclick=UkloniHash("+data[i].id_stol+")>Obriši hash</a>";
    }
  }
  CreateTableFromJSON(data);
})

function UkloniHash(id_stol){
  $.ajax({
    method: "POST",
    dataType: 'text',
    url: "./admin.php?stvar=UkloniHash&id_stola="+id_stol,
  }).done(function(data){
    alert(data);
    
  })
}

function GenerirajQR(id_stol){
  $.ajax({
    method: "POST",
    dataType: 'text',
    url: "./admin.php?stvar=GenerirajQR&id_stola="+id_stol,
  }).done(function(data){
    new QRCode(document.getElementById("qr-panel"),"https://smartwaiter.app/app.php?"+data);
    
  })
}

function CreateTableFromJSON(myBooks) {
  
  // EXTRACT VALUE FOR HTML HEADER. 
  // ('Book ID', 'Book Name', 'Category' and 'Price')
  var col = [];
  for (var i = 0; i < myBooks.length; i++) {
      for (var key in myBooks[i]) {
          if (col.indexOf(key) === -1) {
              col.push(key);
          }
      }
  }

  // CREATE DYNAMIC TABLE.
  var table = document.createElement("table");

  // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

  var tr = table.insertRow(-1);                   // TABLE ROW.

  for (var i = 0; i < col.length; i++) {
      var th = document.createElement("th");      // TABLE HEADER.
      th.innerHTML = col[i];
      tr.appendChild(th);
  }

  // ADD JSON DATA TO THE TABLE AS ROWS.
  for (var i = 0; i < myBooks.length; i++) {

      tr = table.insertRow(-1);

      for (var j = 0; j < col.length; j++) {
          var tabCell = tr.insertCell(-1);
          tabCell.innerHTML = myBooks[i][col[j]];
      }
  }

  // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
  var divContainer = document.getElementById("postavke-panel");
  divContainer.innerHTML = "";
  divContainer.appendChild(table);
}