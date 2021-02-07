
<?php


require 'vendor/autoload.php';


try {
	parse_str(file_get_contents("php://input"), $data); 
	
	\Stripe\Stripe::setApiKey("sk_test_51I8lIrDfSGopQFLz2O0uzG6WxMIwnEqeJYCnA1YHJ3J74pMOQQ8ZcVApqPfCv1ijum1k2zOcWriJxmnxnz0ZysxS00HDsLVis1");
	
 $intent = \Stripe\PaymentIntent::create([
	 'customer' => $data["customerID"],
	 'amount' => $data["amount"],
	 'currency' => 'hrk'
]);
$client_secret = $intent->client_secret;
	
echo json_encode($client_secret);

} catch (Error $e) {
  http_response_code(500);
  echo json_encode(['error' => $e->getMessage()]);
}


?>