<?php
header("Content-Type: application/json");
require "db.php";

$full_name = $_POST['full_name'];
$username  = $_POST['username'];
$password  = $_POST['password']; // Hash this if needed
$email     = $_POST['email'];
$phone     = $_POST['phone'];
$address   = $_POST['address'];
$role      = $_POST['role'];

$sql = "INSERT INTO employees (full_name, username, password, email, phone, address, role)
        VALUES (?, ?, ?, ?, ?, ?, ?)";

$stmt = $conn->prepare($sql);
$stmt->bind_param("sssssss", $full_name, $username, $password, $email, $phone, $address, $role);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Employee added successfully"]);
} else {
    echo json_encode(["success" => false, "message" => "Error adding employee"]);
}
?>
