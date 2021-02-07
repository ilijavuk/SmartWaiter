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

			case "test2": {
				$args=[];
				$izvrsi="Select * from Korisnik";
				return Formatiraj($args, $izvrsi);
				break;
			}

			case "tagsByMeal": {
             	$args=["meal_id"];
                $izvrsi="select distinct id_tag, tag FROM Stavka_jelovnika left join Stavka_tag on id_stavka = stavka_id right join Tag_stavke on tag_id = id_tag WHERE id_stavka=_meal_id_";
                return Formatiraj($args, $izvrsi);
                break;
            }//case
			case "RemoveTagsFromMeal": {
             	$args=["meal_id"];
                $izvrsi="delete FROM `Stavka_tag` WHERE stavka_id=_meal_id_";
                return Formatiraj($args, $izvrsi);
                break;
            }//case	


        default: return null;   
        } //switch
    }//Upiti


?>