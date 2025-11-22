<?php
header("Content-Type: application/json");
require "db.php";

$id = $_POST['id'];

$sql = "DELETE FROM employees WHERE id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $id);

if ($stmt->execute()) {
    echo json_encode(["success" => true, "message" => "Employee deleted"]);
} else {
    echo json_encode(["success" => false, "message" => "Error deleting employee"]);
}
?>
