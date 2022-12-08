<?php
header('Content-Type: text/html; charset=utf-8');
$response = array();
$response["sucesso"] = false;

 if($_SERVER['REQUEST_METHOD'] == 'POST'){

    include '../dbConnection.php';

    $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

    mysqli_set_charset($conn, "utf8" );

    if($conn -> connect_error){
        //die("Ops, falhou....".$conn-> connect_error);
        echo "Falhou";
    }


    $idremetente = "'".$_POST['idremetente']."'";
    $iddestinatario = "'".$_POST['iddestinatario']."'";
    $mensagem = "'".$_POST['mensagem']."'";
    $idchat = "'".$_POST['idchat']."'";

   
    $sql = "INSERT INTO mensagens(idremetente, iddestinatario, msg, chat_idchat) VALUES ($idremetente, $iddestinatario, $mensagem, $idchat);";

    if($conn -> query($sql)){
        $response["sucesso"] = true;
    }


    $conn -> close();
}

echo json_encode($response);

?>