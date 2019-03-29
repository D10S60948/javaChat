package ChatApp;

import java.net.*;
import java.io.*;

public class ServerApp
{
	public static void main(String args[])
	{
		ServerSocket server = null;
		MessageBoard mb = new MessageBoard();
		
		try
		{
			server = new ServerSocket(1300);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		Socket socket = null;
		ClientDescriptor client = null;
		ConnectionProxy connection = null;
		
		while(true)
		{
			try
			{
				socket = server.accept();
				connection = new ConnectionProxy(socket);
				client = new ClientDescriptor();
				connection.addConsumer(client);
				client.addConsumer(mb);    
				mb.addConsumer(connection);
				connection.start();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
