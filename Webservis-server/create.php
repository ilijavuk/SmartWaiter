<?php

require 'vendor/autoload.php';


try{
	
	$stripe = new \Stripe\StripeClient("sk_test_51I8lIrDfSGopQFLz2O0uzG6WxMIwnEqeJYCnA1YHJ3J74pMOQQ8ZcVApqPfCv1ijum1k2zOcWriJxmnxnz0ZysxS00HDsLVis1");
$customer = $stripe->customers->create([
]);
	
	 $output = ['customerID' => $customer->id];
echo json_encode($output);
	
} catch (Error $e) {
  http_response_code(500);
  echo json_encode(['error' => $e->getMessage()]);
}

?>