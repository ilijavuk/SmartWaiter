<?php
/*KOPIRAJ ODAVDJE

case "test2": {
    $args=["arg1", "arg2"];
    $izvrsi="Select * from Korisnik where id_korisnik=_arg1_ and korisnicko_ime=_arg2_";
    return Formatiraj($args, $izvrsi);
    break;
}//case
DO REDA IZNAD I ZALIJEPI ISPOD ZADNJEG CASEA. 
pod case ide ono sto ce biti ?funkcija="test2"
args pod navodnike svaki argument koji ti treba
u $izvrsi sql upit, i ono sto trebas zamijenit mora biti imena istog kao u $args samo s donjom crom prije i poslije
i tjt.
*/    
    function Upiti($upit){

        switch($upit){
            case "test": {
                $args=["arg1", "arg2"];
                $izvrsi="Select * from Korisnik where id_korisnik=_arg1_ and korisnicko_ime=_arg2_";
                return Formatiraj($args, $izvrsi);
                break;
            }//case




        default: return null;   
        } //switch
    }//Upiti


?>