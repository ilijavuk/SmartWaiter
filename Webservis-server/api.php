<?php

include 'conn.php';

	if($_SERVER['REQUEST_METHOD'] == "GET"){

		if(isset($_GET['metoda'])){
			
			if(strcmp($_GET['metoda'],'narudzba')==0){
					$sql="SELECT Stol.id_stol, Narudzba.korisnik_id, Narudzba.stol_id, Stol.broj_stola, Narudzba.stavka_id FROM Stol LEFT OUTER JOIN Narudzba ON Stol.id_stol = Narudzba.stol_id";
			}
			else 
			if(strcmp($_GET['metoda'],'meniPoTagu')==0){
				$sql='SELECT id_stavka, naziv, cijena, opis, slika_path, lokal_id, aktivno FROM Stavka_jelovnika join Stavka_tag on id_stavka = stavka_id join Tag_stavke on tag_id = id_tag WHERE id_tag = "'.$_GET['id_tag'].'" AND lokal_id = "'.$_GET['lokal_id'].'";';
			}
			else $sql=QueryBuilder();
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
				if(strcmp($_GET['metoda'],'insert')==0){
					echo $conn->insert_id;
				}
			}			
		}
	}
	else echo "ŠTA RADIŠ TU";

	function QueryBuilder(){
		$temp="";
		if(strcmp($_GET['metoda'],"select")==0){
			$temp='Select * from '.$_GET["tablica"];
			$arr=$_GET;
			unset($arr['tablica']);
			unset($arr['metoda']);
			if(isset($_GET['operator'])){	
				if(sizeof($arr)>0){
					$temp.=' where ';
					while(sizeof($arr)>1){
						if(strcmp(array_key_last($arr),'operator')==0){
							$temp.=' or ';
							array_pop($arr);
						}
						else{
							
							$temp.=array_key_last($arr).' = "'.array_pop($arr);
							if(strcmp(array_key_last($arr),'operator')==0){
								$temp.='" ';
							}
							else{
								$temp.='" and ';
							}
						}
					}
				}

				if(sizeof($arr)==1){
					$temp.=array_key_last($arr).' = "'. array_pop($arr) .'";';
				}
				
			}
			
//AKO NEMA OPERATOR			
			else{
									
				if(sizeof($arr)>0){
					$temp.=' where ';
					while(sizeof($arr)>1){
						$temp.=array_key_last($arr).' = "'.array_pop($arr).'" and ';
					}
				}

				if(sizeof($arr)==1){
					$temp.=array_key_last($arr).' = "'. array_pop($arr) .'";';
				}
				
			}
			
			return $temp;
		}

		if(strcmp($_GET['metoda'],"insert")==0){
			$temp='INSERT INTO '.$_GET["tablica"]. ' (';
			$arr=$_GET;
			unset($arr['tablica']);
			unset($arr['metoda']);
			
			$key=[];
			$val=[];

			while(!empty($arr)){
				array_push($key, array_key_last($arr));
				array_push($val, array_pop($arr));
			}
			if(empty($key) || empty($val)) return null;
			while(sizeof($key)>1){
				$temp.=' `'. array_pop($key) . '`,';
			}
			if(sizeof($key)==1){
				$temp.=' `'. array_pop($key) . '`)';
			}
			$temp.= ' VALUES (';
			while(sizeof($val)>1){
				$temp.=' "'. array_pop($val) . '",';
			}
			if(sizeof($val)==1){
				$temp.=' "'. array_pop($val) . '");';
			}
			
			return $temp;
	
		}

		if(strcmp($_GET['metoda'],"update")==0){
			
			$temp='Update '.$_GET["tablica"]. ' SET ';
			$arr=$_GET;
			unset($arr['tablica']);
			unset($arr['metoda']);
			$primary_key="";
			$primary_val="";
			$key=[];
			$val=[];

			while(!empty($arr)){

				if(strcmp(array_key_last($arr), "key")==0){
					$primary_key=array_key_last($arr);
					$primary_val=array_pop($arr);
				}
				else{
					array_push($key, array_key_last($arr));
					array_push($val, array_pop($arr));
				}

			}
			if(empty($key) || empty($val)) return null;
			while(sizeof($key)>1){
				$temp.=' `'. array_pop($key) . '`= ';
				$temp.=' "'. array_pop($val) . '",';
			}
			
			if(sizeof($key)==1){
				$temp.=' `'. array_pop($key) . '`= ';
				$temp.=' "'. array_pop($val) . '" ';
			}
			$primary_key=mysql_get_prim_key($_GET["tablica"]);
			if($primary_key){
				$temp.= ' WHERE `'.$_GET["tablica"].'`.`'.$primary_key.'` = "'.$primary_val.'" ;';
			}
			else{
				return false;
			}
			

			
			
			echo $temp;
			return $temp;
	
		}
		
		return null;
		
}


function mysql_get_prim_key($table){
	include 'conn.php';
	$sql1 = "SHOW INDEX FROM $table WHERE Key_name = 'PRIMARY'";
	$gp = $conn->query($sql1);
	$cgp = $gp->num_rows;
	if($cgp > 0){
	// Note I'm not using a while loop because I never use more than one prim key column
	$agp = $gp->fetch_array();
	extract($agp);
	return($Column_name);
	}else{
	return(false);
	}
	}



?>