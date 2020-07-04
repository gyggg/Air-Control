<?php
define("ON", "/usr/bin/sudo /usr/bin/irsend send_once aircon on");
define("OFF", "/usr/bin/sudo /usr/bin/irsend send_once aircon off");
define("UP", "/usr/bin/sudo /usr/bin/irsend send_once aircon up");
define("DOWN", "/usr/bin/sudo /usr/bin/irsend send_once aircon down");
define("HIGH", "/usr/bin/sudo /usr/bin/irsend send_once aircon high");
define("LOW", "/usr/bin/sudo /usr/bin/irsend send_once aircon low");
define("HEAT", "/usr/bin/sudo /usr/bin/irsend send_once aircon heat");
define("COOL", "/usr/bin/sudo /usr/bin/irsend send_once aircon cool");

define("ADDRESS", "192.168.1.13");
define("PROT", 22);
define("USER", "pi");
define("PASSWORD", "jack1234");

$sconnection=ssh2_connect(ADDRESS, PROT);
ssh2_auth_password($sconnection, USER, PASSWORD);

$ctl=$_GET["ctl"];
$stat=$_GET["stat"];
switch ($ctl) {
    case 1:
        if ($stat) {
            $command=ON." ";
        }
        else{
            $command=OFF." ";
        }
        break;
    case 2:
        if ($stat) {
            $command=COOL." ";
        }
        else{
            $command=HEAT." ";
        }
        break;
    case 3:
        if ($stat) {
            $command=HIGH." ";
        }
        else{
            $command=LOW." ";
        }
        break;
    case 4:
        if ($stat) {
            $command=UP." ";
        }
        else{
            $command=DOWN." ";
        }
        break;
    }
$stdio_stream=ssh2_exec($sconnection, $command);

?>