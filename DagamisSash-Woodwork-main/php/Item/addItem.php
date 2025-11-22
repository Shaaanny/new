<?php
include "db.php";

$name = $_POST['name'] ?? "";
$category_id = $_POST['category_id'] ?? "";
$price = $_POST['price'] ?? 0;
$stock = $_POST['stock'] ?? 0;

if ($name === "" || $category_id === "") {
    echo "Required fields missing";
    exit;
}

$sql = "INSERT INTO items (name, category_id, price, stock)
        VALUES (?, ?, ?, ?)";

$stmt = $conn->prepare($sql);
$stmt->bind_param("sidd", $name, $category_id, $price, $stock);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}
?>
