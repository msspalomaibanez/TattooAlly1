<?php
include 'conexion.php';
$usu_nombre = $_POST['nombre'];
$usu_nickname = $_POST['nickname'];
$usu_email = $_POST['email'];
$usu_contrasena = $_POST['contrasena'];


$sentencia=$conexion->prepare("INSERT INTO Usuario(nombre,nickname,email,contrasena) VALUES (?,?,?,?)");
$sentencia->bind_param('ssss',$usu_nombre,$usu_nickname,$usu_email,$usu_contrasena);
$sentencia->execute();

$sentencia->close();
$conexion->close();

?>