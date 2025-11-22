<?php
header("Content-Type: application/json");
require "db.php";

$sql = "SELECT * FROM employees ORDER BY id DESC";
$result = $conn->query($sql);

$employees = [];

while ($row = $result->fetch_assoc()) {
    $employees[] = $row;
}

echo json_encode($employees);
?>
