package ChatApp;

import java.util.*;

public class ClientDescriptor implements StringConsumer, StringProducer
{
	//private String nickName;
	Vector<StringConsumer> consumers;
		
	
	public ClientDescriptor() 
	{		
		consumers = new Vector<StringConsumer>();
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
		for(StringConsumer consumer:consumers)
			consumer.consume(str);
	}
}
