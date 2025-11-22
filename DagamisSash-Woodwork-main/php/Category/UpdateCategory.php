<?php
require "db.php";

$id   = $_POST['id'];
$name = $_POST['name'];

$stmt = $conn->prepare("UPDATE categories SET name=? WHERE id=?");
$stmt->bind_param("si", $name, $id);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}
?>
