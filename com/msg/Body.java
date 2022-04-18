package com.msg;

//전문 body interface 

public interface Body {

	public int getBodyLen();
	
	public byte[] toByte();
	
	public byte[] toByte(int byteOrder);
	
	public byte[] toByte(int byteOrder, byte[] buffer);
	
	public byte[] toByte(int byteOrder, byte[] buffer, int offset);
	
	public void toObject(byte[] data);
	
	public void toObject(int byteOrder, byte[] data , int iMsgLength);
	
	public Body clone();
}
