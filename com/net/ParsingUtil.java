
package com.net;

import java.util.StringTokenizer;

public class ParsingUtil {
	/**
	 * 하나의 긴 String을 주어진 integer array의 순서대로 tokenize
	 * 
	 * @return String[][]
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 */

	public static String[] parsing(int[] delim, String str) {

		int i, offset = 0;

		// 한글문제로 인하여 byte로 변환한 후 처리
		byte[] input = str.getBytes();
		String[] output = new String[delim.length];
		String temp;

		for (i = 0; i < delim.length; i++) {
			temp = new String(input, offset, delim[i]);
			output[i] = temp;
			offset += delim[i];
		}
		return output;
	}

	/**
	 * 하나의 긴 String을 주어진 integer array의 순서대로 tokenize
	 * 
	 * @return String[][]
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param b
	 *            byte[] parsing할 정보
	 */
	public static String[] parsing(int[] delim, byte[] b) {

		int i, offset = 0;

		// 한글문제로 인하여 byte로 변환한 후 처리
		byte[] input = b;
		String[] output = new String[delim.length];
		String temp;

		for (i = 0; i < delim.length; i++) {
			temp = new String(input, offset, delim[i]);
			output[i] = temp;
			offset += delim[i];
		}
		return output;
	}

	/**
	 * 일정한 structure의 record가 여러개 반복되는 String을 tokenize
	 * 
	 * @return String[][]
	 * @param offset
	 *            int str에서 시작위치
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 */
	public static String[][] multi_parsing(int offset, int[] delim, String str) {

		int i, j;
		byte[] input = str.getBytes();

		String temp;

		// 현재 host에서 내려오는 전문중 반복횟수
		int count = Integer.parseInt(str.substring(50, 53));
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				// System.out.println("["+i+"]["+j+"]:["+temp+"]-"+delim[j]);
				offset += delim[j];
			}
		}

		return output;
	}

	/**
	 * 일정한 structure의 record가 여러개 반복되는 String을 tokenize
	 * 
	 * @return String[][]
	 * @param offset
	 *            int str에서 시작위치
	 * @param count
	 *            int 전체 반복수
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 */
	public static String[][] multi_parsing(int offset, int count, int[] delim,
			String str) {

		int i, j;
		byte[] input = str.getBytes();

		String temp;
		String[][] output = null;

		// 현재 host에서 내려오는 전문중 반복횟수
		if (count == 0) {
			output = new String[0][0];
			return output;
		}
		output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				// System.out.println("["+i+"]["+j+"]:["+temp+"]-"+delim[j]);
				offset += delim[j];
			}
		}

		return output;
	}

	/**
	 * <pre>
	 * 가변적인 String을 고정된 length의 String으로 변환한다.
	 * 입력된 String이 고정된 길이보다 작을 경우 space를 추가한다.
	 * kind '0':left align '1':right align '2':right align set'0', '3':left align set '0'
	 * </pre>
	 * 
	 * @return String
	 * @param kind int
	 * @param out_len int 길이정보
	 * @param str java.lang.String 내용정보
	 */
	public static String fixlength(int kind, int out_len, String str) {

		if (str == null) {
			str = "";
		}
		byte[] input = str.getBytes();
		byte[] temp = new byte[out_len];

		int i, j;
		int in_len = input.length;

		if (kind == 2 || kind == 3) {
			for (i = 0; i < out_len; i++) {
				temp[i] = (byte) '0';
			}
		} else {
			for (i = 0; i < out_len; i++) {
				temp[i] = (byte) ' ';
			}
		}

		// 입력된 길이보다 해당 String이 긴 경우
		if (in_len > out_len) {
			in_len = out_len;

		}
		if (kind == 0 || kind == 3) {
			for (i = 0; i < in_len; i++) {
				temp[i] = input[i];
			}
		} else {
			for (i = (out_len - in_len), j = 0; i < out_len; i++, j++) {
				temp[i] = input[j];
			}
		}

		String output = new String(temp, 0, out_len);

		return output;
	}

	/**
	 * 일정길이의 space로 구성된 String을 return
	 * 
	 * @return String
	 * @param out_len
	 *            int str에서 시작위치
	 */
	public static String makeSpace(int out_len) {
		byte[] temp = new byte[out_len];
		for (int i = 0; i < out_len; i++) {
			temp[i] = (byte) ' ';
		}
		String output = new String(temp, 0, out_len);
		return output;

	}

	/**
	 * 일정한 structure의 record가 여러개 반복되는 String을 tokenize
	 * 
	 * @return String[][]
	 * @param offset
	 *            int str에서 시작위치
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 */
	public static String[][] multi_parsing_long(int offset, int[] delim,
			String str) {

		int i, j;
		// 건수를 가져오기위한...
		int delimLen = 0;
		for (i = 0; i < delim.length; i++) {
			delimLen += delim[i];
		}
		byte[] input = str.getBytes();

		String temp;

		// 건수를 계산...
		int count = (input.length - offset) / delimLen;

		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				// System.out.println("paring["+i+"]["+j+"]:["+temp+"]-"+delim[j]);
				offset += delim[j];
			}
		}

		return output;
	}

	/**
	 * 일자형식의 String에 년월일 사이에 '.'를 첨가한다.
	 * 
	 * @return String
	 * @param str
	 *            java.lang.String
	 */
	public static String date(String str) {

		String temp = null;
		int len = str.length();

		if (len != 8) {
			return str;
		}
		if ((str.equals("00000000")) || (str.equals("       0"))) {
			return "";
		}
		temp = str.substring(0, 4) + "." + str.substring(4, 6) + "."
				+ str.substring(6, 8);

		return temp;
	}

	/**
	 * "1,2,5 ... "형태의 String자료내의 배열정보로 int[]로 변환
	 * 
	 * @return int[]
	 * @param str
	 *            java.lang.String
	 */
	public static int[] str2Aint(String str) {
		int aInfo[] = null;
		int count = 0;
		StringTokenizer st = null;
		String stTmp = null;
		st = new StringTokenizer(str, ",");

		while (st.hasMoreTokens()) {
			stTmp = st.nextToken().trim();
			// System.out.println(stTmp);
			count++;
		}

		aInfo = new int[count];

		st = new StringTokenizer(str, ",");
		count = 0;
		while (st.hasMoreTokens()) {
			stTmp = st.nextToken().trim();
			aInfo[count] = Integer.parseInt(stTmp);
			count++;
		}

		return aInfo;
	}

	/**
	 * "1,2,5 ... "형태의 String자료내의 배열정보를 합산
	 * 
	 * @return int
	 * @param str
	 *            java.lang.String
	 */
	public static int str2length(String str) {
		int tLen = 0;
		int len = 0;
		StringTokenizer st = null;
		String stTmp = null;
		st = new StringTokenizer(str, ",");

		while (st.hasMoreTokens()) {
			stTmp = st.nextToken().trim();
			len = Integer.parseInt(stTmp);
			tLen += len;
		}

		return tLen;
	}

	/**
	 * 일정한 structure의 record가 여러개 반복되는 String을 tokenize
	 * 
	 * @return String[][]
	 * @param offset
	 *            int str에서 시작위치
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 */
	public static String[][] multi_parsing_long(int offset, int[] delim,
			String str, boolean flag) {

		int i, j;
		// 건수를 가져오기위한...
		int delimLen = 0;
		for (i = 0; i < delim.length; i++) {
			delimLen += delim[i];
		}
		byte[] input = str.getBytes();
		String temp;
		// 건수를 계산...
		int count = (input.length - offset) / delimLen;
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				if (flag) {
//					System.out.println("[" + i + "][" + j + "]:[" + temp + "]-"
//							+ delim[j]);
				}
				offset += delim[j];
			}
		}

		return output;
	}

	/**
	 * 일정한 int[]의 전체 길이를 return
	 * 
	 * @return int
	 * @param delim
	 *            int[] delim정보(배열)
	 */
	public static int arrayLen(int[] delim) {
		int nLength = 0;
		for (int i = 0; i < delim.length; i++) {
			nLength += delim[i];
		}
		return nLength;
	}

	/**
	 * 일정한 structure의 record가 여러개 반복되는 String을 tokenize
	 * 
	 * @return String[][]
	 * @param offset
	 *            int str에서 시작위치
	 * @param count
	 *            int 전체 반복수
	 * @param delim
	 *            int[] 한반복내의 delim정보(배열)
	 * @param str
	 *            java.lang.String parsing할 정보
	 * @param flag
	 *            boolean System.out.println의 출력여부
	 */
	public static String[][] multi_parsing(int offset, int count, int[] delim,
			String str, boolean flag) {

		int i, j;
		byte[] input = str.getBytes();

		String temp;

		// 현재 host에서 내려오는 전문중 반복횟수
		String[][] output = new String[count][delim.length];

		for (i = 0; i < count; i++) {
			for (j = 0; j < delim.length; j++) {
				temp = new String(input, offset, delim[j]);
				output[i][j] = temp;
				if (flag) {

					// System.out.println("["+i+"]["+j+"]:["+temp+"]-"+delim[j]);
					offset += delim[j];
				}
			}
		}

		return output;
	}

	public static void main(String[] args) {
//		System.out.println(ParsingUtil.fixlength(0, 5, "aaaa"));
//		System.out.println(ParsingUtil.fixlength(1, 5, "aaaa"));
//		System.out.println(ParsingUtil.fixlength(2, 5, "aaaa"));
	}

}
