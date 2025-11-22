<?php
require "db.php";

$name = $_POST['name'];

$stmt = $conn->prepare("INSERT INTO categories (name) VALUES (?)");
$stmt->bind_param("s", $name);

if ($stmt->execute()) {
    echo "success";
} else {
    echo "error";
}
?>
