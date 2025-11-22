<?php
require "db.php";

$result = $conn->query("SELECT * FROM categories ORDER BY id ASC");

$data = array();
while ($row = $result->fetch_assoc()) {
    $data[] = $row;
}

echo json_encode($data);
?>
