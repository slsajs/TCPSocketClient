package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.msg.Message;

public abstract class SocketClient {

	public static final int BYTE_ORDER_BIG = 1;
	public static final int BYTE_ORDER_LITTLE = 2;

	private int byteOrder;

	
	public abstract void send(int byteOrder, Message msg) throws IOException,
	IllegalArgumentException, InstantiationException,
	IllegalAccessException, InvocationTargetException,
	SecurityException, NoSuchMethodException;
	
	protected Socket getSocket(String ip, int port, int timeout)
			throws UnknownHostException, IOException {
		Socket s = new Socket(ip, port);
		s.setSoTimeout(timeout);
		return s;
	}

	protected Socket getSocket(String ip, int port)
			throws UnknownHostException, IOException {
		return getSocket(ip, port, 10000);
	}

	protected int getByteOrder() {
		return byteOrder;
	}

	protected void setByteOrder(int byteOrder) {
		this.byteOrder = byteOrder;
	}

	protected void send(OutputStream out, byte[] data, int offset, int len)
			throws IOException {
		out.write(data, offset, len);
		out.flush();
	}

	protected byte[] receive(InputStream in, int length) throws IOException {
		return TcpUtil.read_data(in, length);
	}


	
	
}
