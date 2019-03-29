package ChatApp;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

public class ConnectionProxy extends Thread implements StringConsumer, StringProducer
{
	private Socket socket;
	Vector<StringConsumer> consumers;
	private ClientGUI cg;
	
	private InputStream is;
	private DataInputStream dis;
	private OutputStream os;
	private DataOutputStream dos;
	
	public ConnectionProxy(Socket socket) 
	{
		consumers = new Vector<StringConsumer>();
		this.socket = socket;
		cg = null;
		
		try 
		{
			this.is = this.socket.getInputStream();
			this.dis = new DataInputStream(is);
			this.os = this.socket.getOutputStream();
			this.dos = new DataOutputStream(os);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public ConnectionProxy(Socket socket, ClientGUI cg) 
	{
		this(socket);
		this.cg=cg;		
	}

	@Override
	public void addConsumer(StringConsumer sc) 
	{
		consumers.addElement(sc);
	}

	@Override
	public void removeConsumer(StringConsumer sc) 
	{
		consumers.remove(sc);
	}

	@Override
	public void consume(String str) 
	{
		try {
			dos.writeUTF(str);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run() 
	{
		String chatLine = new String();
		
		while(this.socket.isConnected())
		{
			try {
				chatLine=dis.readUTF();
				for(StringConsumer consumer:consumers)
					consumer.consume(chatLine);
				
				if(this.cg!=null)
					cg.consume(chatLine);
			} 
			catch (IOException e) {
				this.removeConsumer(this);
				e.printStackTrace();
			}
			
		}
	}
}
