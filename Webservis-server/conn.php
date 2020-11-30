<?php
$servername = "localhost";
$username = "air";
$password = "air2018";
$db = "smart_waiter";
// Create connection
$conn = mysqli_connect($servername, $username, $password,$db);
// Check connection
if (!$conn) {
   die("Connection failed: " . mysqli_connect_error());
}
mysqli_set_charset($conn, "utf8");
$conn->set_charset("utf-8");
mysqli_set_charset($conn, "utf8");
?>