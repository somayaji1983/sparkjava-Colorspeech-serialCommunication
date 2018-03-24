# sparkjava-Colorspeech-serialCommunication
Colorspeech Webservice API developed using SparkJava framework. Receives request from http and sends serial communication to arduino

## working
- During startup reads configuration file and loades into map
- Receives request via http GET method and check for mapping name
- With retrived mapping system will get the RGB values
- RBG values are with common cathod configuration of RBG where 255 represents HIGH
- As the circuit is common anode configuration of RBG it is converted where 0 is represented as HIGH

## sample URL supported are
1. For displaying in single color
  - 192.168.1.106:8080/color/blue
2. For displaying in multi colors with 1 second interval
  - 192.168.1.106:8080/color/blue,red,green
3. Here the ip and port are the place where API is exposed
