<?php
include 'conexion.php';

//$idUsuario = intval($_POST['idUsuario']);

$idUsuario = intval("1");
$sentencia=$conexion->prepare("SELECT id_usuario,nombre,fotoPerfil,email,seguidores,nickname FROM Usuario WHERE id_Usuario = ?");
$sentencia->bind_param('i',$idUsuario);
if($sentencia->execute()){
    $sentencia->bind_result($id,$nombre,$fotoPerfil,$email,$seguidores,$nickname);

$perfil=array();

while($sentencia->fetch()){
    $temp = array();
    $temp['idUsuario']= $id;
    $temp['nombre'] =$nombre;
    $temp['seguidores']= $seguidores;
    $temp['nickname']= $nickname;
    $temp['email']=$email;

    $imagenTemp = explode("TattooAlly_php/",$fotoPerfil);
    if (file_exists($imagenTemp[1])){
        $temp['imagen'] =  base64_encode(file_get_contents($fotoPerfil));
    }else{
        $temp['imagen'] =  base64_encode(file_get_contents("fotosPerfil/fotoPerfilDefault.png"));
       
    }

    array_push($perfil,$temp);
    
    
}

}
echo json_encode($perfil);


$conexion->close();

?>