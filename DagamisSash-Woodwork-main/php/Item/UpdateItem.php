<?php
include "db.php";

$id = $_POST['id'] ?? "";
$name = $_POST['name'] ?? "";
$category_id = $_POST['category_id'] ?? "";
$price = $_POST['price'] ?? 0;
$stock = $_POST['stock'] ?? 0;

if ($id === "" || $name === "" || $category_id === "") {
    echo "Required fields missing";
    exit;
}

$sql = "UPDATE items 
        SET name=?, category_id=?, price=?, stock=?
        WHERE id=?";

$stmt = $conn->prepare($sql);
$stmt->bind_param("siddi", $name, $category_id, $price, $stock, $id);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}
?>
