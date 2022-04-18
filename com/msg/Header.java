package com.msg;

//전문 header interface  

public interface Header {
	
	public String toString();
	
	public Header clone();
	
	public byte[] toByte();
	
	public byte[] toByte(int byteOrder);
	
	public byte[] toByte(int byteOrder, byte[] buffer);
	
	public byte[] toByte(int byteOrder, byte[] buffer, int offset);
	
	public int getHeaderLen();
	
	public void toObject(byte[] data);
	
	public void toObject(int byteOrder, byte[] data);

	public String getDataSize();
}
