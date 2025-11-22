<?php
include "db.php";

$sql = "SELECT * FROM categories ORDER BY category_name ASC";
$result = $conn->query($sql);

$categories = [];
while ($row = $result->fetch_assoc()) {
    $categories[] = $row;
}

echo json_encode($categories);
?>
