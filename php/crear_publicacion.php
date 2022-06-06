<?php
include 'conexion.php';
$usu_usuario=$_POST['usuario'];
$publi_imagen=$_POST['imagen'];
$nombreArchivo=$_POST['nombreArchivo'];
$publi_desc=$_POST['descripcion'];
$publi_localizacion=$_POST['localizacion'];
$publi_estilo=$_POST['estilo'];
$path = "upload/$nombreArchivo" . ".png";

$url = "http://localhost/TattooAlly_php/$path";



$sql = "INSERT INTO publicacion (id_usuario,imagen,descripcion,localizacion,estilo) VALUES (1,'$url','$publi_desc','$publi_localizacion','$publi_estilo')";
if(mysqli_query($conexion,$sql)){
    file_put_contents($path,base64_decode($publi_imagen));
    echo "publicado";
}else{
    echo  mysqli_error($conexion);
}

$conexion->close();

?>