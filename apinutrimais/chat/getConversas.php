<?php
header('Content-Type: text/html; charset=utf-8');


$response["erro"] = true;

 if($_SERVER['REQUEST_METHOD'] == 'POST'){

    include '../dbConnection.php';

    $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

    mysqli_set_charset($conn, "utf8" );

    if($conn -> connect_error){
        //die("Ops, falhou....".$conn-> connect_error);
        echo "Falhou";
    }


    $idremetente = $_POST['idremetente'];

    $idremetente = intval($idremetente);

   
    $sql = "SELECT * FROM chat as c WHERE (c.idremetente = $idremetente) OR (c.iddestinatario = $idremetente);";

    $result = $conn -> query($sql);


    $response['conversas'] = array();

    while($registro = $result->fetch_assoc()){
     
        $idUsuario1 = $registro['idremetente'];
        $idUsuario2 = $registro['iddestinatario'];;

        $queryInfosRemetente = "SELECT nome_usuario, idusuario FROM usuario WHERE idusuario = $idUsuario1";
        $infosRemetente = $conn -> query($queryInfosRemetente);
        $rowRemetente = mysqli_fetch_assoc($infosRemetente);

        $queryInfosDestinatario = "SELECT nome_usuario, idusuario FROM usuario WHERE idusuario = $idUsuario2";
        $infosDestinatario = $conn -> query($queryInfosDestinatario);
        $rowDestinatario = mysqli_fetch_assoc($infosDestinatario);


        $array['idchat'] = $registro['idchat'];
        $array['idremetente'] = $registro['idremetente'];
        $array['usuario_remetente'] = $rowRemetente['nome_usuario'];

        if($registro['idremetente'] != $rowRemetente['idusuario'] ){
            $array['usuario_remetente'] = $rowDestinatario['nome_usuario'];
        }

        $array['iddestinatario'] = $registro['iddestinatario'];
        $array['usuario_destinatario'] = $rowRemetente['nome_usuario'];

        if($registro['iddestinatario'] != $rowRemetente['idusuario'] ){
            $array['usuario_destinatario'] = $rowDestinatario['nome_usuario'];
        }

         array_push($response['conversas'], $array);
      
    }


    $conn -> close();
}

if($response != null){
    $response['erro'] = false;   
} 

echo json_encode($response);


?>