<?php
header('Content-Type: text/html; charset=utf-8');

$response = array();
$response["suceso"] = false;

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

   
     
    

    $queryDeleteMensagens = "DELETE FROM mensagens WHERE (idremetente = $idremetente OR idremetente = $iddestinatario) AND (iddestinatario = $iddestinatario OR iddestinatario =  $idremetente);";

    $queryDeleteConversa = "DELETE FROM chat WHERE (idremetente = $idremetente OR idremetente = $iddestinatario) AND (iddestinatario = $iddestinatario OR iddestinatario =  $idremetente);";

    $querySelectMensagens = "SELECT idmsg FROM mensagens AS m WHERE (m.idremetente = $idremetente OR m.idremetente = $iddestinatario) AND (m.iddestinatario = $iddestinatario or m.iddestinatario =  $idremetente );";
   

    if($result = $conn -> query($querySelectMensagens)){
        if($result -> num_rows > 0){    

            if($conn -> query($queryDeleteMensagens)){
                $conn -> query($queryDeleteConversa);
                $response["suceso"] = true;
            }
        }else{
            $conn -> query($queryDeleteConversa);
            $response["suceso"] = true;
        }    
    }
        
    

    
    $conn -> close();
}

echo json_encode($response);

?>