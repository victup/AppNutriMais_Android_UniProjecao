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
    $tipo_usuario = "'".$_POST['tipo_usuario']."'";
   
    $queryEndereco = "INSERT INTO endereco(cep, endereco, numero, bairro, cidade, estado) VALUES ($cep, $endereco,$numero,$bairro,$cidade, $estado)";


    if($result = $conn -> query($queryEndereco)){

        $queryIdEndereco = "SELECT idendereco FROM endereco WHERE cep = $cep AND endereco = $endereco AND numero = $numero";

        $resultado = mysqli_query($conn, $queryIdEndereco);

        $idEndereco = -1;

        while($row_endereco = mysqli_fetch_assoc($resultado)){
            $idEndereco = $row_endereco['idendereco'];
        }

        $queryCadastroUsuario = "INSERT INTO usuario(nome_usuario, senha, nome, sobrenome, tipo_usuario, endereco_idendereco) VALUES ($nome_usuario,$senha,$nome,$sobrenome, $tipo_usuario, $idEndereco)";

        if($result = $conn -> query($queryCadastroUsuario)){
            $response["cadastrado"] = true;
            $response["erro"] = false;
        }else{
            $response["cadastrado"] = false;
        }

       
    } else{
        $response["cadastrado"] = false;
    }


    $conn -> close();
}

echo json_encode($response);

?>