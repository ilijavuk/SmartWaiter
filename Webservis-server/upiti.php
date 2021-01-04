<?php

    
    function Upiti($upit){

        switch($upit){
            case "test": {
                $args=["arg1", "arg2"];
                $izvrsi="Select * from Korisnik where id_korisnik=_arg1_ and korisnicko_ime=_arg2_";
                return Formatiraj($args, $izvrsi);
                break;
            }//case

        } //switch
    }//Upiti

    function Formatiraj($args, $izvrsi){
        foreach($args as $arg){
            $repl="_".$arg."_";
            $izvrsi=str_ireplace($repl, '"'.$_GET[$arg].'"', $izvrsi);
        }
        return $izvrsi;
    }


?>