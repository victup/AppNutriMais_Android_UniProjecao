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

    $descricao = "'".$_POST['descricao']."'";
    $vencimento = "'".$_POST['vencimento']."'";
    $idPaciente = "'".$_POST['idPaciente']."'";
   
    $sql = "INSERT INTO dieta(descricao, vencimento, idpaciente) VALUES ($descricao, $vencimento, $idPaciente)";


    if($result = $conn -> query($sql)){

        $response["cadastrado"] = true;
    } else{
        $response["cadastrado"] = false;
    }


    $conn -> close();
}

echo json_encode($response);

?>