package sparkapi;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import spark.Request;
import spark.Response;

import sparkapi.SerialCommunication;

/**
 * 
 * @author Somayaji
 * @version 1.1
 *
 */
public class GetRGBAPI
{
	public static Properties rbgCodes = new Properties();
	public static SerialCommunication scObj =null;

	public static void main(String[] args)
	{

		try
		{
			rbgCodes.load(new FileReader(
					"C:\\Users\\Somayaji\\Techno\\workspace\\sparkjava-RGB-API\\src\\main\\java\\sparkapi\\colors.txt"));
		}
		catch (IOException e)
		{
			// printing stack trace
			e.printStackTrace();
		}

		// registering a port
		port(8080);
		
		//Registering serial connections
		try
		{
			scObj = new SerialCommunication("COM3", 9600);
			scObj.openConnection();
			
		}
		catch (Exception e)
		
		{
			e.printStackTrace();
		}		 

		// simple way of creating a route in latest Java spark
		// no need to register a specific object with an inner class
		get("/hello", (req, res) -> "Hello World");
		get("/color/:colorName", (req, res) -> getRGBcodeString(req, res));

	}

	//getting RBG string from configured list
	public static String getRGBcodeString(Request req, Response res)
	{
		String colorName = req.params(":colorName");
		String rbgString = "255,255,255";

		if (-1 != colorName.indexOf(","))
		{
			String[] colorsArray = colorName.split(",");
			for (String colorStr : colorsArray)
			{
				
				try{
					Thread.sleep(1000);
					rbgString = sendColorsToSerial(colorStr);
				}
				catch(Exception e)
				{
					
				}
				
			}
		}
		else
		{
			rbgString = sendColorsToSerial(colorName);
		}

		return rbgString;
	}
	
	//get color codes and sending to Serial ports
	public static String sendColorsToSerial(String colorName)
	{
		String afterProcessRGB = "";
		if (null != rbgCodes)
		{
			String rbgString = rbgCodes.getProperty(colorName.toLowerCase());

			String rgbTemp[] = rbgString.split(",");
			for (int i = 0; i < rgbTemp.length; i++)
			{
				rgbTemp[i] = String.valueOf(255 - Integer.parseInt(rgbTemp[i]));
				afterProcessRGB = afterProcessRGB.concat(rgbTemp[i]).concat(",");
			}

			afterProcessRGB = afterProcessRGB.substring(0, afterProcessRGB.length() - 1);
			System.out.println("After Processing : " + afterProcessRGB);
			try
			{
				scObj.sendMessage(afterProcessRGB);
			}
			catch (Exception e)

			{
				e.printStackTrace();
			}

		}
		return afterProcessRGB;
	}

	public static String ledLight(Request req, Response res)
	{
		String status = req.params(":status");
		try
		{
			scObj.sendMessage(status);
		}
		catch (Exception e)
		
		{
			e.printStackTrace();
		}

		return "OK";
	}

}
