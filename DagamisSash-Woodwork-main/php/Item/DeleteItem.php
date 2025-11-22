<?php
include "db.php";

$id = $_POST['id'] ?? "";

if ($id === "") {
    echo "Missing ID";
    exit;
}

$sql = "DELETE FROM items WHERE id=?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $id);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}
?>
