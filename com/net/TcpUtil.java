package com.net;

import java.io.*;

public class TcpUtil
{ 
	private static int DEFAULT_BUFFER_SIZE = 2048;
	public static final int INTPUTSTREAM_READ_RETRY_COUNT = 10;
	
	private TcpUtil() { }
	
	/**
	 * The <code>read_data</code> method of <code>SocketUtil</code> reads the
	 * specified length of bytes from the given input stream.
	 * 
	 * @param in
	 *            an inputstream
	 * @param len
	 *            the number of bytes read.
	 * @return The specified number of bytes read until the end of the stream is
	 *         reached.
	 * @exception Exception
	 *                if an I/O error or unexpected EOF occurs
	 */
	public static byte[] getByteReadData(InputStream in, int len) throws Exception {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int bcount = 0;
		byte[] buf = new byte[4096];
		int read_retry_count = 0;
		while (bcount < len) {
			int n = in.read(buf, 0, len - bcount < 4096 ? len - bcount : 4096);
			if (n == -1)
				throw new java.io.IOException(
						"inputstream has returned an unexpected EOF");

			if (n == 0 && (++read_retry_count == INTPUTSTREAM_READ_RETRY_COUNT))
				throw new java.io.IOException("inputstream-read-retry-count( "
						+ INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			if (n != 0) {
				bcount += n;
				bout.write(buf, 0, n);
			}
		}
		bout.flush();
		return bout.toByteArray();
	}
	
	public static byte[] read_data(InputStream in, int len) throws IOException
	{
		// if(len < 1) return new byte[0];
		int bcount = 0, n = 0, read_retry_count = 0;
		
		byte buf[] = new byte[len];
		while(bcount < len) {
			int endLen	= (len - bcount);
			
			n = in.read(buf, bcount, endLen);

			if(n > 0){
				bcount += n;
			}
			else if(n == -1){
				buf = null;
				throw new IOException("inputstream has returned an unexpected EOF");
			}
			else if(n == 0 && ++read_retry_count == 10){
				throw new IOException("inputstream-read-retry-count( 10) exceed !");
			}
		}
		return buf;
	}
	

	public static void read_data_skip(InputStream in, int len)throws Exception
	{
		if(len<1) return;
		int skipcount = 0, read_retry_count = 0;
		long n = 0;
		while(skipcount < len) {
			n = in.skip(len - skipcount);
			if(n > 0) skipcount += n;
			else if(n == -1) throw new IOException("inputstream skip() returned an unexpected EOF");
			else if(n == 0 && ++read_retry_count == 10)    throw new IOException("inputstream-skip-retry-count( 10) exceed, expected:"+len +", but actual:"+skipcount);
		}
	}
	
	public static byte[] getLimitRadData(InputStream in) throws Exception {
		java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
		int bcount = 0;
		byte[] buf = new byte[4096];
		int read_retry_count = 0;
		while (true) {
			int n = in.read(buf);
			if (n == -1)
				break;
			if (n == 0 && (++read_retry_count == INTPUTSTREAM_READ_RETRY_COUNT))
				throw new java.io.IOException("inputstream-read-retry-count( "
						+ INTPUTSTREAM_READ_RETRY_COUNT + ") exceed !");
			if (n != 0) {
				bcount += n;
				bout.write(buf, 0, n);
			}
		}
		bout.flush();
		return bout.toByteArray();
	}
	
	public static byte[] read_data(InputStream in) throws IOException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		int bcount = 0, n=0;
		byte buf[] = new byte[DEFAULT_BUFFER_SIZE];
		int read_retry_count = 0;
		do {
			n = in.read(buf);
			if(n > 0) {
				bcount += n;
				bout.write(buf, 0, n);
			}
			else if(n == -1) break;
			else if(n == 0 && ++read_retry_count == 10)
				throw new IOException("inputstream-read-retry-count( 10) exceed !");
		} while(true);
		bout.flush();
		return bout.toByteArray();
	}

	public static String read_line(InputStream in)
	throws Exception
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		boolean eof = false;
		int b;
		do
		{
			b = in.read();
			if(b == -1)
			{
				eof = true;
				break;
			}
			if(b != 13 && b != 10)
				bout.write((byte)b);
		} while(b != 10);
		bout.flush();
		if(eof && bout.size() == 0)
			return null;
		else
			return bout.toString();
	}

	//2005.06.14
	public static final boolean getboolean(InputStream in) throws Exception{
		return getbyte(in) ==(byte)1;
	}
	// 2005.02.10
	public static final short getshort(InputStream in) throws Exception{
		byte[] buf = read_data(in, 2);
		return ByteUtil.getshort(buf,0);
	}
	public static final int getint(InputStream in) throws Exception{
		byte[] buf = read_data(in, 4);
		return ByteUtil.getint(buf,0);
	}
	public static final byte getbyte(InputStream in) throws Exception{
		byte[] buf = read_data(in, 1);
		return ByteUtil.getbyte(buf,0);
	}
	public static final byte[] getbytes(InputStream in) throws Exception{
		int len = getint(in);
		return getbytes(in,len);
	}
	public static final byte[] getbytes(InputStream in, int len) throws Exception{
		int bcount = 0, n=0;
		byte buf[] = new byte[len];
		int read_retry_count = 0;
		while(len-bcount>0){
			n = in.read(buf,bcount,len-bcount);
			if(n > 0) bcount += n;
			else if(n<0)
				throw new IOException("stream got an unexpected EOF!");
			else if(n == 0 && ++read_retry_count == 10)
				throw new IOException("inputstream-read-retry-count( 10) exceed !");
		}
		return buf;
	}
	public static final long getlong(InputStream in) throws Exception{
		byte[] buf = read_data(in, 8);
		return ByteUtil.getlong(buf,0);
	}
	public static final float getfloat(InputStream in) throws Exception{
		byte[] buf = read_data(in, 4);
		return ByteUtil.getfloat(buf,0);
	}
	public static final double getdouble(InputStream in) throws Exception{
		byte[] buf = read_data(in, 8);
		return ByteUtil.getdouble(buf,0);
	}
	public static final String getstring(InputStream in) throws Exception{
		byte[] buf = read_data(in, 2); //short 2 bytes: 65535
		int len =  ByteUtil.getshort(buf,0) & 0xffff;
		if(len>0) return new String(read_data(in,len));
		return "";//2005.06.13 we could not distinguish between "" and null.
	}
	//2005.10.16
	public static final void skipstring(InputStream in) throws Exception{
		byte[] buf = read_data(in, 2); //short 2 bytes: 65535
		int len =  ByteUtil.getshort(buf,0) & 0xffff;
		if(len>0) read_data_skip(in,len);
	}

	//2005.06.14
	public static final void setboolean(OutputStream out, boolean b) throws Exception{
		if(b) setbyte(out,(byte)1);
		else setbyte(out,(byte)0);
	}
	public static final void setshort(OutputStream out, short s) throws Exception{
		byte[] buf = new byte[2];
		out.write(ByteUtil.setshort(buf,0,s));
	}
	public static final void setbyte(OutputStream out, byte b) throws Exception{
		out.write(b);
	}
	public static final void setbytes(OutputStream out, byte[] b) throws Exception{
		//setint(out,b.length);
		out.write(b);
	}
	public static final void setbytes(OutputStream out, byte[] b, int len) throws Exception{
		//setint(out,b.length);
		out.write(b,0,len);
	}
	public static final void setbytes(OutputStream out, byte[] b, int offset, int len) throws Exception{
		//setint(out,b.length);
		out.write(b,offset,len);
	}
	public static final void setint(OutputStream out, int i) throws Exception{
		byte[] buf = new byte[4];
		out.write(ByteUtil.setint(buf,0,i));
	}
	public static final void setlong(OutputStream out, long l) throws Exception{
		byte[] buf = new byte[8];
		out.write(ByteUtil.setlong(buf,0,l));
	}
	public static final void setfloat(OutputStream out, float f) throws Exception{
		byte[] buf = new byte[4];
		out.write(ByteUtil.setfloat(buf,0,f));
	}
	public static final void setdouble(OutputStream out, double d) throws Exception{
		byte[] buf = new byte[8];
		out.write(ByteUtil.setdouble(buf,0,d));
	}
	public static final void setstring(OutputStream out, String s) throws Exception{
		if(s == null) setshort(out,(short)0);
		else{
			byte[] buf = s.getBytes();
			int len = buf.length;
			if(len==0) {
				setshort(out,(short)0);
				return;
			}
			else if(len>65535)  len = 65535;
			setshort(out,(short)len);
			out.write(buf,0,len);
		}
	}
}
