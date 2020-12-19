<?php

if(isset($_GET['stvar'])){
    switch($_GET['stvar']){
        case 'listaStolova': ListaStolova(); break;
    }
}



function ListaStolova(){
    $sql="Select * from Stol";
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

?>