<?php
$hostname = 'localhost';
$database = 'tattooally';
$username = 'tattooally';
$password = '123abc.';

$conexion = new mysqli ($hostname,$username,$password,$database);
if($conexion-> connect_errno){
    echo "Error al conectar con la base de datos";
}
?>