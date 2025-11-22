<?php
$host = "localhost";
$user = "root";
$pass = "";
$dbname = "minicapstone";

$conn = new mysqli($host, $user, $pass, $dbname);

if ($conn->connect_error) {
    die(json_encode(["success" => false, "message" => "DB Connection Failed"]));
}
?>
