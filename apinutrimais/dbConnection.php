<?php
    $ambiente = false;


    if($ambiente){ //ambiente em produção
        $HostName = "";
        $HostUser = "";
        $HostPass = "";
        $DatabaseName = "";
    } else { // Ambiente de desenvolvimento

        $HostName = "localhost";
        $HostUser = "root";
        $HostPass = "";
        $DatabaseName = "nutrimaisapp";

    }

?>