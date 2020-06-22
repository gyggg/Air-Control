#include <stdlib.h>
#include <wiringPi.h>

int LED_PIN[]={0,2,3,29,25};

int main(int argc, char *argv[]){
wiringPiSetup();

int led_num=atoi(argv[1])-1;
int on_off=atoi(argv[2]);

pinMode(LED_PIN[led_num],OUTPUT);
if (on_off==1)
{
   digitalWrite(LED_PIN[led_num],HIGH);
}else
{
    digitalWrite(LED_PIN[led_num],LOW);
}
return 0;
}
