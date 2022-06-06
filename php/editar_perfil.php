<?php
include 'conexion.php';
$nombre=$_POST['nombre'];
$nickname=$_POST['nickname'];
$email=$_POST['email'];
$password=$_POST['password'];
$imagen=$_POST['imagen'];
$path = "fotosPerfil/aguadix" . ".png";

$url = "http://localhost/TattooAlly_php/$path";



$sql = "UPDATE usuario SET nombre=$nombre, nickname=$nickname, email=$email, fotoPerfil=$url WHERE id_usuario=1";

if(mysqli_query($conexion,$sql)){
    if(file_exists($path)){
        unlink($path);
        file_put_contents($path, base64_decode($imagen));
    } else {
        file_put_contents($path, base64_decode($imagen));
    }
    echo "publicado";
}else{
    echo  mysqli_error($conexion);
}
$conexion->close();

?>