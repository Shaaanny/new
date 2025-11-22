<?php
header("Content-Type: application/json");
require "db.php";

$id        = $_POST['id'];
$full_name = $_POST['full_name'];
$username  = $_POST['username'];
$password  = $_POST['password']; // If blank → do not update password
$email     = $_POST['email'];
$phone     = $_POST['phone'];
$address   = $_POST['address'];
$role      = $_POST['role'];

if ($password === "") {
    $sql = "UPDATE employees SET full_name=?, username=?, email=?, phone=?, address=?, role=? WHERE id=?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("ssssssi", $full_name, $username, $email, $phone, $address, $role, $id);
} else {
    $sql = "UPDATE employees SET full_name=?, username=?, password=?, email=?, phone=?, address=?, role=? WHERE id=?";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sssssssi", $full_name, $username, $password, $email, $phone, $address, $role, $id);
}

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Employee updated"]);

} else {
    echo json_encode(["success" => false, "message" => "Error updating employee"]);
}
?>
