package ChatApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class ClientGUI implements StringConsumer, StringProducer
{
	private JFrame frame;
    private JPanel panelTop,panelBottom, panelCenter;
	private JTextArea textArea;
	private JTextField nickNameField, ip, port, writingArea;
	private JButton connect, send;
	private JScrollPane scroll; 
	
	private boolean isConnected;
	private String nickName;
	private ConnectionProxy con;
	private Socket socket;
	
	Vector<StringConsumer> consumers;

	public ClientGUI()
	{
		isConnected=false;
		nickNameField = new JTextField(10);
		ip = new JTextField(10);
		port = new JTextField(10);
		writingArea = new JTextField(50);
		textArea = new JTextArea(25,78);
		connect = new JButton("Connect");
		send = new JButton("Send");
		frame = new JFrame("The best madafakin CHAT ever");
		scroll = new JScrollPane(textArea);
		
		panelTop = new JPanel();	
		panelBottom = new JPanel();
		panelCenter = new JPanel();
		panelTop.setBackground(Color.BLACK);
		panelBottom.setBackground(Color.BLUE);
		
        frame.setLayout(new BorderLayout());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
		panelTop.add(nickNameField);
        panelTop.add(ip);
        panelTop.add(port);
        panelBottom.add(writingArea);
        panelBottom.add(send);
		panelTop.add(connect);
		panelCenter.add(scroll);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(panelTop,BorderLayout.NORTH);
        frame.add(panelBottom,BorderLayout.SOUTH);
        frame.add(panelCenter,BorderLayout.CENTER);
        
		frame.setSize(900, 550);
		frame.setVisible(true);
	}
	
	public void setConnected(boolean isConnected) 
	{
		this.isConnected = isConnected;
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
		textArea.append(str+"\n");
	}
	
	public static void main(String[] args) 
	{
		ClientGUI gui = new ClientGUI();
		
		gui.connect.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	try 
				{
					gui.socket = new Socket(gui.ip.getText(),Integer.parseInt(gui.port.getText()));
					gui.con = new ConnectionProxy(gui.socket);
					gui.nickName = new String(gui.nickNameField.getText());
					gui.con.addConsumer(gui);
					gui.con.start();
					gui.con.consume(gui.nickName + " CONNECTED");
				} 
				catch (IOException er) 
				{
					er.printStackTrace();
				}
		    	gui.setConnected(true);
		    }
		});
		
    	System.out.println("connected successfully");

		gui.send.addActionListener(new ActionListener() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) {
				gui.con.consume(gui.nickName + ": " + gui.writingArea.getText());
				gui.writingArea.setText("");
		    }
		});
	}
}