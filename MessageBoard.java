package ChatApp;

import java.util.Vector;

public class MessageBoard implements StringConsumer, StringProducer
{
	String message;
	Vector<StringConsumer> consumers;
	
	public MessageBoard() 
	{
		consumers = new Vector<StringConsumer>();
		message = new String("");
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
		this.message=str;
		for(StringConsumer consumer:consumers)
			consumer.consume(str);
	}
}
