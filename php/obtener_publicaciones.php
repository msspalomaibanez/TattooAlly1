<?php
include 'conexion.php';

$sql = "SELECT id_publicacion,id_usuario,imagen,descripcion,localizacion,estilo,FechaPublicacion FROM publicacion ORDER BY FechaPublicacion DESC ";

$resultado = $conexion->prepare($sql);
$resultado->execute();
$resultado->bind_result($id,$idUsuario,$imagen,$descripcion,$localizacion,$estilo,$fechaPublicacion);

$publicaciones=array();

while($resultado->fetch()){
    $temp=array();
    $temp['id'] = $id;
    $temp['idUsuario'] = $idUsuario;
    $temp['descripcion'] = $descripcion;
    $temp['localizacion'] = $localizacion;
    $temp['estilo'] = $estilo;
    
    $imagenTemp = explode("TattooAlly_php/",$imagen);
    if (file_exists($imagenTemp[1])){
        $temp['imagen'] =  base64_encode(file_get_contents($imagen));
        array_push($publicaciones,$temp);
    }
    
    
}

echo json_encode($publicaciones);


$conexion->close();

?>