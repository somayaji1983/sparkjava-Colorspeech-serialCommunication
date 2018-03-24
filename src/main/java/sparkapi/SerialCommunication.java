package sparkapi;

import java.io.*;
import com.fazecast.jSerialComm.*;

public class SerialCommunication
{
	public static String messageString = "1";
	public SerialPort comunicationPort = null;
	public OutputStream outputStream;
	public String comPortName;
	public int baudRate;

	// Constructor with all details
	public SerialCommunication(String comPortName, int baudRate) throws Exception
	{
		this.comPortName = comPortName;
		this.baudRate = baudRate;

		// get the COM port
		SerialPort sp[] = SerialPort.getCommPorts();
		for (SerialPort loopSp : sp)
		{
			if (comPortName.equalsIgnoreCase(loopSp.getSystemPortName()))
			{
				comunicationPort = loopSp;
			}

		}

		if (null == comunicationPort)
		{
			throw new Exception("Defined port doesnt exists");
		}
	}

	// Open connection
	public void openConnection() throws Exception
	{
		comunicationPort.setBaudRate(this.baudRate);
		if (comunicationPort.openPort())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				closeConnection();
				throw new Exception("Other internal exception during port operation");
			}
		}
	}

	// Method for sending message to arduino through serial port
	public void sendMessage(String message) throws Exception
	{
		try
		{
			serialWrite(message);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			closeConnection();
			throw new Exception("Other internal exception during write message operation");
		}
	}

	// SerialWrite method for converting into steam and sending
	private void serialWrite(String s) throws Exception
	{
		// writes the entire string at once.
		comunicationPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
		try
		{
			Thread.sleep(5);
		}
		catch (Exception e)
		{
			throw new Exception("Internal error due to sleep");
		}

		// Steam of information
		PrintWriter pout = new PrintWriter(comunicationPort.getOutputStream());

		// Writing stream
		pout.print(s);
		pout.flush();
	}

	// Closing the serial port
	public void closeConnection()
	{
		// closing port at any cost
		if (null != comunicationPort)
		{
			comunicationPort.closePort();
		}
	}

}
