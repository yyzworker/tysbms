package com.tys.util.vo;

/**
* @author 我是金角大王
* @date 2017年11月20日 下午3:00:04
*/
public class ReturnMessage {
	
	/**
	 * 是否成功
	 */
	private int s;
	/**
	 * 提示语
	 */
	private String m;
	/**
	 * 返回数据
	 */
	private Object d;

	public ReturnMessage(String message) {
		this.s = 0;
		this.m = message;
	}

	public ReturnMessage(String message, Object data) {
		this.s = 1;
		this.m = message;
		this.d = data;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}

	public Object getD() {
		return d;
	}

	public void setD(Object d) {
		this.d = d;
	}
}
