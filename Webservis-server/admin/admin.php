<?php
ini_set('display_errors', '1');
ini_set('display_startup_errors', '1');
error_reporting(E_ALL);
if(isset($_GET['stvar'])){
    switch($_GET['stvar']){
        case 'listaStolova': ListaStolova(); break;
        case 'GenerirajQR': GenerirajQR(); break;
        case 'UkloniHash': UkloniHash(); break;
        case 'ImenaRestorana': ImenaRestorana(); break;
        case 'StoloviPoRestoranu': StoloviPoRestoranu(); break;
        case 'DodajStol': DodajStol(); break;
        case 'ObrisiStol': ObrisiStol(); break;
    }
}

function StoloviPoRestoranu(){
    $sql="SELECT * from Stol where lokal_id=".$_GET['id'];
    VratiJson($sql);
}

function DodajStol(){

    $sql="INSERT INTO `Stol` (`id_stol`, `lokal_id`, `oznaka_stola`, `broj_stola`, `rezerviran`, `hash`) VALUES (NULL, '".$_GET["lokal_id"]."', '".$_GET["oznaka"]."', '".$_GET["broj"]."', '0', NULL);";
    echo $sql;
    VratiJson($sql);
}


function ObrisiStol(){
    $sql="DELETE FROM `Stol` WHERE `Stol`.`id_stol` = ".$_GET['id'];
    VratiJson($sql);
}
function ImenaRestorana(){

    $sql="SELECT Lokal.id_lokal as id, Concat(Lokal.naziv, ';', Lokal.adresa) as Ime from Lokal";
    VratiJson($sql);
}

function GenerirajQR(){
    $stol=$_GET['id_stola'];
    $hash=md5($stol.time());
    $sql="UPDATE `Stol` SET `hash` = '".$hash."' WHERE `Stol`.`id_stol` =".$stol.";";
    include_once("conn.php");
    $rez=$conn->query($sql);
    echo $hash;
}

function UkloniHash(){
    $stol=$_GET['id_stola'];
    $sql="UPDATE `Stol` SET `hash` = NULL WHERE `Stol`.`id_stol` =".$stol.";";
    include_once("conn.php");
    $rez=$conn->query($sql);
    echo $sql;
}

function ListaStolova(){
    $sql="Select * from Stol;";
    VratiJson($sql);
}




function VratiJson($sql){
    include_once("conn.php");

    if($sql!=null){
				
        $rez=$conn->query($sql);
        $vrati = [];
        if($rez -> num_rows > 0){
            while ($red = $rez->fetch_assoc())
            {
                $vrati[] = $red;
            }  
        echo json_encode($vrati);
        }

    }
}
?>