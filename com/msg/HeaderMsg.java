package com.msg;

/*임의로 정한 headermsg 부분*/

import com.net.ByteUtil;
import com.net.LEByteUtil;
import com.net.LittleEndianByteHandler;
import com.net.SocketClient;
import com.msg.Header;
import com.net.ParsingUtil;



public class HeaderMsg implements Header {
	

	public static final int HEADER_LEN = 85; // 헤더의 전체 길이
	private int[] len = { 1, 1, 4, 4, 40, 8, 1, 20 }; // 각 헤더별 길이
	
	public static final String RESULT_FAIL = "0";
	public static final String RESULT_SUCC = "1";
	
	public static final short RESULT_OK = 0x00;
	public static final short UNKNOWN_ERROR = 0x01;
	
	
	public static final String SPLIT_CHAR = String.format("%c", (char)30);
	
	public static final short STX = (short)0x02;
	public static final short ETX = (short)0x03;
	
	private short stx;
	private short method;
	private String length;
	private String dataSize;
	private String date;
	private String time;
	private String type;
	private String reserved = "";

	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("HEADER=[\n");
		buff.append("\t STX			: [").append(getStx()).append("]\n");
		buff.append("\t METHOD		: [").append(method).append("]\n");
		buff.append("\t LENGTH		: [").append(length).append("]\n");
		buff.append("\t DATA_SIZE 	: [").append(dataSize).append("]\n");
		buff.append("\t DATE 		: [").append(date).append("]\n");
		buff.append("\t TIME 		: [").append(time).append("]\n");
		buff.append("\t TYPE 		: [").append(type).append("]\n");
		buff.append("\t RESERVED	: [").append(reserved).append("]\n");
		buff.append("]\n");
		return buff.toString();
	}

	public HeaderMsg clone() {
		HeaderMsg header = new HeaderMsg();
		header.setMethod(method);
		header.setLength(length);
		header.setDataSize(dataSize);
		header.setDate(date);
		header.setTime(time);
		header.setType(type);
		header.setReserved(reserved);
		return header;
	}

	public HeaderMsg() {
	}

	public HeaderMsg(byte[] data) {
		toObject(data);
	}

	public HeaderMsg(int byteOrder, byte[] data) {
		toObject(byteOrder, data);
	}

	public byte[] toByte() {
		return toByte(SocketClient.BYTE_ORDER_BIG);
	}

	public byte[] toByte(int byteOrder) {
		return toByte(byteOrder, new byte[HEADER_LEN]);
	}

	public byte[] toByte(int byteOrder, byte[] buffer) {
		return toByte(byteOrder, buffer, 0);
	}

	public byte[] toByte(int byteOrder, byte[] buffer, int offset) {
		int idx = 0;
		int startIdx = offset;
		
		if (byteOrder == SocketClient.BYTE_ORDER_BIG) {
			
			ByteUtil.fillInBuff(buffer, startIdx, len[idx] , ByteUtil.short2byte(getStx()));
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] , ByteUtil.short2byte(method));
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] , length);
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] , dataSize);
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] ,date);
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] ,time);
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] ,type);
			ByteUtil.fillInBuff(buffer, startIdx += len[idx++], len[idx] ,reserved);
			
		} else {
			ByteUtil.fillInBuff(buffer, startIdx, len[idx  ], LEByteUtil.shortToByte( getStx()  		) );
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], LEByteUtil.shortToByte( method  		) );
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], length));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx  ], ParsingUtil.fixlength(2, len[idx], dataSize));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx] ,ParsingUtil.fixlength(0, len[idx], date));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx] ,ParsingUtil.fixlength(0, len[idx], time));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx] ,ParsingUtil.fixlength(0, len[idx], type));
			ByteUtil.fillInBuff(buffer, startIdx+=len[idx++], len[idx] ,ParsingUtil.fixlength(0, len[idx], reserved));
		}
		return buffer;
	}

	public int getHeaderLen() {
		return HEADER_LEN;
	}

	public void toObject(byte[] data) {
		toObject(SocketClient.BYTE_ORDER_BIG, data);
	}

	public void toObject(int byteOrder, byte[] data) {
		int idx = 0;
		int startIdx = 0;

		if (byteOrder == SocketClient.BYTE_ORDER_BIG) {
			stx = ByteUtil.getshort(ByteUtil.getbytes(data,startIdx, len[idx]), 0);
			method = ByteUtil.getshort(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]), 0);
			length = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			dataSize = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			date = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			time = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			type = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			reserved = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
		} else {
			stx = LittleEndianByteHandler.byteToShort(ByteUtil.getbytes(data,startIdx, len[idx]), 0);
			method = LittleEndianByteHandler.byteToShort(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]), 0);
			length = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			dataSize = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			date = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			time = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			type = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
			reserved = new String(ByteUtil.getbytes(data,startIdx += len[idx++], len[idx]));
		}
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public short getStx() {
		return STX;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(short method) {
		this.method = method;
	}

	public String getDataSize() {
		return dataSize;
	}

	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public int[] getLen() {
		return len;
	}

	public void setLen(int[] len) {
		this.len = len;
	}
}
