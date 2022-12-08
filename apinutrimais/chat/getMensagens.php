<?php
header('Content-Type: text/html; charset=utf-8');

$response = array();
$response["erro"] = true;

 if($_SERVER['REQUEST_METHOD'] == 'POST'){

    include '../dbConnection.php';

    $conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

    mysqli_set_charset($conn, "utf8" );

    if($conn -> connect_error){
        //die("Ops, falhou....".$conn-> connect_error);
        echo "Falhou";
    }


    //$nome_usuario = "'".$_POST['nome_usuario']."'";


    $idremetente = "'".$_POST['idremetente']."'";
    $iddestinatario = "'".$_POST['iddestinatario']."'";
    //$mensagem = "'".$_POST['mensagem']."'";

   
    $sql = "SELECT * FROM mensagens as m WHERE (m.idremetente = $idremetente OR m.idremetente = $iddestinatario) AND (m.iddestinatario = $iddestinatario OR m.iddestinatario =  $idremetente );";

    $result = $conn -> query($sql);


    $response['mensagens'] = array();

    while($registro = $result->fetch_assoc()){
     
        $idUsuario1 = $registro['idremetente'];
        $idUsuario2 = $registro['iddestinatario'];;

        $queryInfosRemetente = "SELECT nome_usuario, idusuario FROM usuario WHERE idusuario = $idUsuario1";
        $infosRemetente = $conn -> query($queryInfosRemetente);
        $rowRemetente = mysqli_fetch_assoc($infosRemetente);

        $queryInfosDestinatario = "SELECT nome_usuario, idusuario FROM usuario WHERE idusuario = $idUsuario2";
        $infosDestinatario = $conn -> query($queryInfosDestinatario);
        $rowDestinatario = mysqli_fetch_assoc($infosDestinatario);


        $array['idmsg'] = $registro['idmsg'];
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
         
        $array['msg'] = $registro['msg']; 
        $array['chat_idchat'] = $registro['chat_idchat'];

        array_push($response['mensagens'], $array);
      
            
    }


    $conn -> close();
}

if($response != null){
    $response['erro'] = false;   
} 

echo json_encode($response);

?>