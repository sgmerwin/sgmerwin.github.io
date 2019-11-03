package sample;

import arduino.*;

/*
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

}

void loop() {
  // put your main code here, to run repeatedly:

  Serial.println(5);
  delay(10);

}
 */

public class ArduinoTest {
    Runnable r1 = () -> {
        Arduino mega = new Arduino();

        mega.setPortDescription("/dev/cu.usbmodem14101");
        mega.setBaudRate(9600);

        mega.openConnection();

        System.out.println("open connection  " + mega.openConnection());
        System.out.println("serial port " + mega.getSerialPort());


        while (mega.openConnection()) {
            String i = mega.serialRead(1);
            System.out.println(i);

        }
    };
}
