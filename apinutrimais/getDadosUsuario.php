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

   
    $sql = "SELECT * FROM usuario AS u inner JOIN endereco AS e ON u.endereco_idendereco = e.idendereco WHERE u.nome_usuario = $nome_usuario;";

    $result = $conn -> query($sql);

    //print_r($result);

    if($result -> num_rows > 0){

        $registro = mysqli_fetch_array($result);

        $response["linhas"] = $result -> num_rows;
        $response["erro"] = false;

        $response["tipo_usuario"] = $registro['tipo_usuario'];
        $response["login"] = $registro['nome_usuario'];
        $response["senha"] = $registro['senha'];
        $response["sobrenome"] = $registro['sobrenome'];
        $response["nome"] = $registro['nome'];

        $response["cep"] = $registro['cep'];
        $response["endereco"] = $registro['endereco'];
        $response["numero"] = $registro['numero'];
        $response["bairro"] = $registro['bairro'];
        $response["cidade"] = $registro['cidade'];
        $response["estado"] = $registro['estado'];

    } else{
        $response["mensagem"] = "Usuário não encontrado";
    }


    $conn -> close();
}

echo json_encode($response);

?>