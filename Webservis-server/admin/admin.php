<?php

if(isset($_GET['stvar'])){
    switch($_GET['stvar']){
        case 'listaStolova': ListaStolova(); break;
        case 'GenerirajQR': GenerirajQR(); break;
        case 'UkloniHash': UkloniHash(); break;
    }
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