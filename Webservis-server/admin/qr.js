UcitajRestorane();
$('#dodajStol').hide();


$('#restorani').on('change', function(){
  if($('#restorani').val()==0){
    $('#dodajStol').hide();
  }
  else{
    $('#dodajStol').show();
  }
  DohvatiStolovePoRestoranu();
})


function DohvatiStolovePoRestoranu(){
  var path="./admin.php?stvar=StoloviPoRestoranu&id="+$('#restorani').val();
  console.log(path);
  $.ajax({
    method: "POST",
    dataType: 'json',
    url: path,
  }).done(function(data){
    
    for(var i=0; i<data.length; i++){
      if(data[i].hash==null){
        data[i].akcija="<a href=# onclick=GenerirajQR("+data[i].id_stol+")>Generiraj</a>";
      }
      else{
        data[i].akcija="<a href=# onclick=UkloniHash("+data[i].id_stol+")>Obriši hash</a> | <a href='#' onclick=ObrisiStol('"+data[i].id_stol+"')>Obriši stol</a>";
        data[i].hash="<a href=# onclick=PrikaziQr('"+data[i].hash+"')>"+data[i].hash+"</a>";
      }
    }
    CreateTableFromJSON(data, "stolovi");
  })
}

function ObrisiStol(id){
  var path="./admin.php?stvar=ObrisiStol&id="+id;
  console.log(path);
  $.ajax({
    method: "POST",
    dataType: 'json',
    url: path,
  }).done(function(){

      DohvatiStolovePoRestoranu();
      
  })
}

function DodajStol(){

  var path="./admin.php?stvar=DodajStol&lokal_id="+$('#restorani').val()+"&oznaka="+$("#oznaka").val()+"&broj="+$("#broj_stola").val();
  console.log(path);
  $.ajax({
    method: "POST",
    dataType: 'json',
    url: path,
  }).done(function(data){
      console.log(data);
  })
}


function UcitajRestorane(){
  $.ajax({
    method: "POST",
    dataType: 'json',
    url: "./admin.php?stvar=ImenaRestorana",
  }).done(function(data){
    for(var i=0; i<data.length; i++){
      var o = new Option(data[i].Ime, data[i].id);
      $("#restorani").append(o);
    }
    
  })
}


function UcitajStolove(){

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
        data[i].hash="<a href=# onclick=PrikaziQr('"+data[i].hash+"')>"+data[i].hash+"</a>";
      }
    }
    CreateTableFromJSON(data, "postavke-panel");
  })

}

function PrikaziQr(hash){
  console.log("nesto");
  document.getElementById("uri").innerHTML=" ";
    document.getElementById("qr-panel").innerHTML=" ";
  new QRCode(document.getElementById("qr-panel"),"https://smartwaiter.app/app.php?"+hash);
    var x = document.getElementById("uri").innerHTML;
    x+="<p>URI za NFC Karticu: <br>https://smartwaiter.app/app.php?"+hash+"</p>";
    document.getElementById("uri").innerHTML=x;
}

function UkloniHash(id_stol){
  $.ajax({
    method: "POST",
    dataType: 'text',
    url: "./admin.php?stvar=UkloniHash&id_stola="+id_stol,
  }).done(function(data){
   
    UcitajStolove();
    document.getElementById("uri").innerHTML=" ";
    document.getElementById("qr-panel").innerHTML=" ";
  })
}

function GenerirajQR(id_stol){
  $.ajax({
    method: "POST",
    dataType: 'text',
    url: "./admin.php?stvar=GenerirajQR&id_stola="+id_stol,
  }).done(function(data){
    new QRCode(document.getElementById("qr-panel"),"https://smartwaiter.app/app.php?"+data);
    var x = document.getElementById("uri").innerHTML;
    x+="<p>URI za NFC Karticu: <br>https://smartwaiter.app/app.php?"+data+"</p>";
    document.getElementById("uri").innerHTML=x;
    console.log(x);
    DohvatiStolovePoRestoranu();
  })
}

function CreateTableFromJSON(myBooks, place) {
  
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
  var divContainer = document.getElementById(place);
  divContainer.innerHTML = "";
  divContainer.appendChild(table);
}