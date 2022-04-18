package com.msg;



import com.net.SocketClient;

public class Message {
	
	private Header header;
	private Body body;
	private Class<?> ackBody;
	private String ackMsg;
	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		if( header != null ) buff.append(header.toString());
		if( body != null ) buff.append(body.toString());
		return buff.toString();
	}
	
	public Message(){
		
	}

	public Message(Header header, Body body){
		this(header, body, null);
	}
	
	public Message(Header header, Body body, Class<?> ackBody){
		this.header	 		= header;
		this.body 			= body;
		this.ackBody 		= ackBody;
	}
	
	public byte[] toByte(){
		return toByte(SocketClient.BYTE_ORDER_BIG);
	}
	
	public byte[] toByte(int byteOrder){
		byte[] buffer = new byte[header.getHeaderLen()+body.getBodyLen()];
		header.toByte(byteOrder, buffer, 0);

		body.toByte(byteOrder, buffer, header.getHeaderLen());

		
		return buffer;
	}
	
	public Header getHeader() {
		return header;
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public Body getBody() {
		return body;
	}
	public void setBody(Body body) {
		this.body = body;
	}
	
	public Class<?> getAckBody() {
		return ackBody;
	}

	public void setAckBody(Class<?> ackBody) {
		this.ackBody = ackBody;
	}

	public String getAckMsg() {
		return ackMsg;
	}

	public void setAckMsg(String ackMsg) {
		this.ackMsg = ackMsg;
	}

	
}
