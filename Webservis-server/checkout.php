<?php
/*
ini_set('display_errors', '1');
ini_set('display_startup_errors', '1');
error_reporting(E_ALL);
*/

require 'vendor/autoload.php';

try{
	parse_str(file_get_contents("php://input"), $data);             
	$customerID = json_encode($data["customerID"]);
	$api_version = json_encode($data["api_version"]);
	//print_r($data);

\Stripe\Stripe::setApiKey("sk_test_51I8lIrDfSGopQFLz2O0uzG6WxMIwnEqeJYCnA1YHJ3J74pMOQQ8ZcVApqPfCv1ijum1k2zOcWriJxmnxnz0ZysxS00HDsLVis1");
	
	$key = \Stripe\EphemeralKey::create(
		['customer' => $data["customerID"]],
		['stripe_version' => $data["api_version"]]
	);
	
	//$output = ['id' => $key->id];
	
	echo json_encode($key);
	
} catch (Error $e) {
  http_response_code(500);
  echo json_encode(['error' => $e->getMessage()]);
}

?>