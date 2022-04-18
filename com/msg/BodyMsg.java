package com.msg;


import com.net.ByteUtil;
import com.net.LittleEndianByteHandler;
import com.net.SocketClient;
import com.msg.Body;
import com.net.ParsingUtil;


public class BodyMsg implements Body {
	
	private String data;
	private String dataSize;
	private String type;
	private short etx;
	
	private final int BODY_LEN = 100;
	private int[] len = {97, 1, 1, 1};
	
	public String toString(){
		StringBuffer buff = new StringBuffer();
		buff.append("BODY=[");
		buff.append(" data : [").append(data).append("]");
		buff.append(" dataSize : [").append(dataSize).append("]");
		buff.append(" type : [").append(type).append("]");
		buff.append(" EXT : [").append(etx).append("]");
		buff.append("]");
		return buff.toString();
	}
	
	public Body clone(){
		BodyMsg body = new BodyMsg();
		body.setData(data);
		body.setDataSize(dataSize);
		body.setType(type);
		body.setEtx(etx);
	
		return body;
	}
	
	public BodyMsg(){}
	
	public BodyMsg(byte[] data){
		toObject(data);
	}
	
	public BodyMsg(int byteOrder, byte[] data){
		toObject(byteOrder, data , 0);
	}
	
	public BodyMsg(int byteOrder, byte[] data, int iMsgLength){
		toObject(byteOrder, data , iMsgLength);
	}
	
	public byte[] toByte(){
		return toByte(SocketClient.BYTE_ORDER_BIG);
	}
	
	public byte[] toByte(int byteOrder){
		return toByte(byteOrder, new byte[BODY_LEN]);
	}
	
	public byte[] toByte(int byteOrder, byte[] buffer){
		return toByte(byteOrder, buffer, 0);
	}
	
	public byte[] toByte(int byteOrder, byte[] buffer, int offset){
		int idx = 0;
		int startIdx = offset;
		if( byteOrder == SocketClient.BYTE_ORDER_BIG ){
			ByteUtil.fillInBuff(buffer, startIdx            , len[idx  ], ParsingUtil.fixlength(0, len[idx], data));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], dataSize));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], type));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ByteUtil.short2byte( etx ) );
		}
		else{
			ByteUtil.fillInBuff(buffer, startIdx            , len[idx  ], ParsingUtil.fixlength(0, len[idx], data));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], dataSize));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], type));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], LittleEndianByteHandler.shortToByte( etx ) );
		}
		
		return buffer;
	}
	
	public void toObject(byte[] data){
		toObject(SocketClient.BYTE_ORDER_BIG, data, 0);
	}
	
	public void toObject(int byteOrder, byte[] data, int iMsgLength){
		int idx = 0;
		int startIdx = 0;
		
		if( byteOrder == SocketClient.BYTE_ORDER_BIG ){
			this.data 	= new String(ByteUtil.getbytes(data, startIdx            , len[idx  ]));
			dataSize		= new String(ByteUtil.getbytes(data, startIdx+=len[idx++], len[idx  ]));
			type	= new String(ByteUtil.getbytes(data, startIdx+=len[idx++], len[idx  ]));
			etx		= ByteUtil.getshort(ByteUtil.getbytes(data, startIdx+=len[idx++], len[idx  ]), 0);
		}
		else{
			this.data 	= new String(ByteUtil.getbytes(data, startIdx            , len[idx  ]));
			dataSize		= new String(ByteUtil.getbytes(data, startIdx+=len[idx++], len[idx  ]));
			type	= new String(ByteUtil.getbytes(data, startIdx+=len[idx++], len[idx  ]));
			etx 		= LittleEndianByteHandler.byteToShort(ByteUtil.getbytes(data,startIdx+=len[idx++], len[idx]), 0);
		}
	}
	
	public int getBodyLen(){
		return BODY_LEN;
	}
	
	

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataSize() {
		return dataSize;
	}

	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getLen() {
		return len;
	}

	public void setLen(int[] len) {
		this.len = len;
	}

	public int getBODY_LEN() {
		return BODY_LEN;
	}

	public short getEtx() {
		return etx;
	}

	public void setEtx(short etx) {
		this.etx = etx;
	}

}
