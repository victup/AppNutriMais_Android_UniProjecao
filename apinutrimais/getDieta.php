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

    $idPaciente = "'".$_POST['idPaciente']."'";

   
    $sql = "SELECT u.idusuario, u.nome, u.sobrenome, d.descricao, d.vencimento FROM dieta AS d inner JOIN usuario AS u ON d.idpaciente = u.idusuario WHERE d.idpaciente = $idPaciente";


    if($result = $conn -> query($sql)){
        $response["erro"] = false;
    }


    $response['encontrado'] = false;
    $response['dados'] = array();

     while($registro = $result->fetch_assoc()){

        $array = array($registro['idusuario'], $registro['nome'], $registro['sobrenome'], $registro['descricao'], $registro['vencimento']);

        if($array != null){
            $response['encontrado'] = true;
        }
         
        array_push($response['dados'], $array);
       

          
        }



    $conn -> close();
}

    echo json_encode($response);
            

?>