<?php
define("LEDTEST", "/usr/bin/sudo /home/pi/led/ledtest");
define("ADDRESS", "192.168.1.13");
define("PROT", 22);
define("USER", "pi");
define("PASSWORD", "woshishuai4");

$sconnection=ssh2_connect(ADDRESS, PROT);
ssh2_auth_password($sconnection, USER, PASSWORD);

$command=LEDTEST." ".$_GET["num"]." ".$_GET["stat"];
$stdio_stream=ssh2_exec($sconnection, $command);

?>
