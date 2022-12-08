<?php

    $cep_informado = "".$_POST['cep_informado']."";

    $url = "https://viacep.com.br/ws/$cep_informado/json/";
    $json = file_get_contents($url);
    
    $json = json_decode($json, true);

    $response['cep'] =  $json['cep'];
    $response['endereco'] =  $json['logradouro'];
    $response['bairro'] =  $json['bairro'];
    $response['cidade'] =  $json['localidade'];
    $response['uf'] =  $json['uf'];


    echo json_encode($response);

?>