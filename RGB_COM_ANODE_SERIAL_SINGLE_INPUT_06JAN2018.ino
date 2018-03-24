//This program is for entering R,G,B as single string
// like 255,255,0
// As this is for Common ANODE RBG 255,255,0 gives blue color

//Defining pins for mapping
int redPin=11;
int greenPin = 10;
int bluePin = 9;

int redIntValue;
int greenIntValue;
int blueIntValue;
String userLEDChoice;

//baudRate for serial communication
int baudRate=9600;

void setup() 
{  
  //setting serial input with defined baudRate
  Serial.begin(baudRate);
  
  // defining pin modes
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
}

void loop() 
{
  //prompt user about input
  Serial.println("Enter value for R,G,B like 255,255,0 :");  
  
  //waiting for user input
  while(Serial.available()==0)
  {
    
  }  
  // user input to be read and printed
  userLEDChoice=Serial.readString();

  String colorString;
  colorString=userLEDChoice.substring(0,userLEDChoice.indexOf(','));
  redIntValue=colorString.toInt();  
  
  userLEDChoice.remove(0,colorString.length()+1);
  colorString=userLEDChoice.substring(0,userLEDChoice.indexOf(','));
  greenIntValue=colorString.toInt();
  
  userLEDChoice.remove(0,colorString.length()+1);

  blueIntValue=userLEDChoice.toInt();
      
  //sending colors
  setColor(redIntValue, greenIntValue, blueIntValue);
  
}

void setColor(int redValue, int greenValue, int blueValue) {
  analogWrite(redPin, redValue);
  analogWrite(greenPin, greenValue);
  analogWrite(bluePin, blueValue);
}
