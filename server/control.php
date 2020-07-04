<?php
define("ADDRESS", "192.168.1.13");
define("PROT", 22);
define("USER", "pi");
define("PASSWORD", "jack1234");
/*
    input : "1_26_1_0"
    output : "/usr/bin/sudo /usr/bin/irsend send_once aircon c_1_26_1_0"
    input : "0_*_*_*"
    output : "/usr/bin/sudo /usr/bin/irsend send_once aircon c_0"
*/
function to_command($c)
{
    return "/usr/bin/sudo /usr/bin/irsend send_once aircon c_".$c;
}

$sconnection=ssh2_connect(ADDRESS, PROT);
ssh2_auth_password($sconnection, USER, PASSWORD);

$run=$_GET["run"];
$temp=$_GET["temp"];
$mode=$_GET["mode"];
$power=$_GET["power"];
switch($run) {
    case 0:
        $command = to_command("0");break;
    case 1:
        $command = to_command($run."_".$temp."_".$mode."_".$power);
}

$stdio_stream=ssh2_exec($sconnection, $command);

?>