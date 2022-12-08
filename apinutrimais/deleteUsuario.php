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


    $queryIdUsuario = "SELECT idusuario, endereco_idendereco FROM usuario WHERE nome_usuario = $nome_usuario";

    if($resultSqlDadosUsuario = $conn -> query($queryIdUsuario)){
        $resultado = mysqli_fetch_array($resultSqlDadosUsuario);

        $idUsuario= $resultado['idusuario'];
        $idEndereco= $resultado['endereco_idendereco'];

        $queryDeleteDietas = "DELETE FROM dieta WHERE idpaciente = $idUsuario";


        if($conn -> query($queryDeleteDietas)){
            $response["status-delete-dietas"] = true;
        }else{
            $response["status-delete-dietas"] = false;
        }

        $queryDeleteUsuario = "DELETE FROM usuario WHERE idusuario = $idUsuario";

        if($conn -> query($queryDeleteUsuario)){
            $response["status-delete-user"] = true;
        }else{
            $response["status-delete-user"] = false;
        }

        $queryDeleteEndereco = "DELETE FROM endereco WHERE idendereco = $idEndereco";

        
        if($conn -> query($queryDeleteEndereco)){
            $response["status-delete-endereco"] = true;
        }else{
            $response["status-delete-endereco"] = false;
        }

    }

    $conn -> close();
}

echo json_encode($response);

?>