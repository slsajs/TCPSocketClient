package com.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;



import com.net.SocketClient;
import com.msg.Body;
import com.msg.Header;
import com.msg.Message;
import com.msg.HeaderMsg;




public class WebClient extends SocketClient {
	private static int TIMELIMIT = 30 * 1000; // 15초
	private static String ip = "127.0.0.1";
	private static int prot = 8888;
	
	public WebClient(int i) {
		TIMELIMIT = 3 * 1000;
	}

	public WebClient() {
		TIMELIMIT = 130 * 1000;
	}

	
	// 클라이언트 send 구현	
	public Message send(Message msg , boolean sFlag) throws IOException,
			IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			SecurityException, NoSuchMethodException {
		
		Socket s = null;
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		Message ack = null;
		String errCode = "";

		try {
		    
		    HeaderMsg header = (HeaderMsg) msg.getHeader();
		    int method = header.getMethod();
			
			s 		= getSocket(ip, prot , TIMELIMIT);
			in 		= new BufferedInputStream(s.getInputStream());
			out 	= new BufferedOutputStream(s.getOutputStream());
			
			byte[] data = msg.toByte(getByteOrder());
			
			send(out, data, 0, data.length);
			
			
			if( header != null ){
				Header ackHeader = new HeaderMsg(getByteOrder(), receive(in, HeaderMsg.HEADER_LEN));
				
				int ackMethod = ((HeaderMsg) ackHeader).getMethod();
				
				if(ackMethod ==  HeaderMsg.RESULT_OK){
					
				}else{
					switch(method) {
						case HeaderMsg.UNKNOWN_ERROR:
			                    throw new IOException("UNKNOWN_ERROR");
					}
				}
				
				Class<?> 	ackBodyClass 	= msg.getAckBody();
				
				int	dataSize	= 0;
				dataSize	= Integer.parseInt(ackHeader.getDataSize());
				Class[] 	parameterTypes	= { int.class, byte[].class };
				Object[] 	initargs 			= {getByteOrder(), receive(in, dataSize)};
				
				
				Constructor c 	= null;
				c 					= ackBodyClass.getConstructor(parameterTypes);
				Body ackBody 	= null;
				ackBody 			= (Body) c.newInstance(initargs);
				ack = new Message(ackHeader, ackBody);
				
				
				ack.setAckMsg(errCode);
				
			}
			
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (s != null)
				s.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
			
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (s != null)
				s.close();
		}
		
		
		return ack;
	
	}
	
	@Override
	public void send(int byteOrder,  Message msg) throws IOException,
	IllegalArgumentException, InstantiationException,
	IllegalAccessException, InvocationTargetException,
	SecurityException, NoSuchMethodException {
		
		BufferedOutputStream out = null;
		Socket s = null;
		
		try {
			s = getSocket(ip, prot , TIMELIMIT);
			out = new BufferedOutputStream(s.getOutputStream());
		
			byte[] data = msg.toByte(byteOrder);
			send(out, data, 0, data.length);
			
			if (out != null)
				out.close();
			
			if (s != null)
				s.close();
			
		} catch (IOException e) {
			throw e;
			
		} finally {
			if (out != null)
				out.close();
			if (s != null)
				s.close();
		}
		
	}
			
	public static void main(String[] args) {
		
	}


	
}
