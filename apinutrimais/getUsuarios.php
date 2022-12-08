<?php
header('Content-Type: text/html; charset=utf-8');

$response = array();
$response["erro"] = true;

 if($_SERVER['REQUEST_METHOD'] == 'POST'){

    include 'dbConnection.php';

    $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

    mysqli_set_charset($conn, "utf8" );

    if($conn -> connect_error){
        //die("Ops, falhou....".$conn-> connect_error);
        echo "Falhou";
    }

    $nome_usuario = "'".$_POST['nome_usuario']."'";
    $senha = "'".$_POST['senha']."'";

    $sql = "SELECT * FROM usuario WHERE nome_usuario = $nome_usuario AND senha = $senha";

    $result = $conn -> query($sql);

    //print_r($result);

    if($result -> num_rows > 0){

        $registro = mysqli_fetch_array($result);

        $response["linhas"] = $result -> num_rows;
        $response["erro"] = false;
        $response["idusuario"] = $registro['idusuario'];
        $response["login"] = $registro['nome_usuario'];
        $response["sobrenome"] = $registro['sobrenome'];
        $response["tipo_usuario"] = $registro['tipo_usuario'];
    } else{
        $response["mensagem"] = "Usuário não encontrado";
    }

    $conn -> close();
}

echo json_encode($response);

?>