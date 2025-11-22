<?php
include "db.php";

$sql = "SELECT items.id, items.name, items.price, items.stock,
               categories.category_name
        FROM items
        INNER JOIN categories ON items.category_id = categories.id
        ORDER BY items.id DESC";

$result = $conn->query($sql);

$items = [];
while ($row = $result->fetch_assoc()) {
    $items[] = $row;
}

echo json_encode($items);
?>
