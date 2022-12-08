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


    $cep = "'".$_POST['cep']."'";
    $endereco = "'".$_POST['endereco']."'";
    $numero = "'".$_POST['numero']."'";
    $bairro = "'".$_POST['bairro']."'";
    $cidade = "'".$_POST['cidade']."'";
    $estado = "'".$_POST['estado']."'";

    $nome_usuario = "'".$_POST['nome_usuario']."'";
    $senha = "'".$_POST['senha']."'";
    $nome = "'".$_POST['nome']."'";
    $sobrenome = "'".$_POST['sobrenome']."'";


    $queryIdUsuario = "SELECT idusuario, endereco_idendereco FROM usuario WHERE nome_usuario = $nome_usuario";

    if($resultSqlDadosUsuario = $conn -> query($queryIdUsuario)){
        $resultado = mysqli_fetch_array($resultSqlDadosUsuario);

        $idUsuario= $resultado['idusuario'];
        $idEndereco= $resultado['endereco_idendereco'];

        $queryUpdateUsuario = "UPDATE usuario AS u SET u.nome_usuario = $nome_usuario, u.senha = $senha, u.nome = $nome, u.sobrenome =  $sobrenome WHERE u.nome_usuario = $nome_usuario;";

        $queryUpdateEndereco = "UPDATE endereco AS e SET e.cep = $cep, e.endereco = $endereco, e.numero = $numero, e.bairro = $bairro, e.cidade = $cidade, e.estado = $estado WHERE e.idendereco  = $idEndereco;";

        if($conn -> query($queryUpdateUsuario) && $conn -> query($queryUpdateEndereco)){
            $response["atualizado"] = true;
            $response["erro"] = false;
        }
   
   
    }

    $conn -> close();
}

echo json_encode($response);

?>