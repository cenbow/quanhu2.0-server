package com.yryz.common.web;

/**
 * api通用状态返回
 *
 * @author Pxie
 */
public class ReturnCode {
   
    private String rstcode = "100";
    private String showmsg = "success";
    private String errormsg;
    private Object data;
    
    public String getRstcode() {
		return rstcode;
	}


	public void setRstcode(String rstcode) {
		this.rstcode = rstcode;
	}


	public String getShowmsg() {
		return showmsg;
	}


	public void setShowmsg(String showmsg) {
		this.showmsg = showmsg;
	}


	public String getErrormsg() {
		return errormsg;
	}


	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}


	public ReturnCode() {
    }


    /**
	 * @param data
	 * @exception 
	 */
	public ReturnCode(Object data) {
		super();
		this.data = data;
	}


	/**
	 * @param rstcode
	 * @param showmsg
	 * @param errormsg
	 * @param data
	 * @exception 
	 */
	public ReturnCode(String rstcode, String showmsg, String errormsg, Object data) {
		super();
		this.rstcode = rstcode;
		this.showmsg = showmsg;
		this.errormsg = errormsg;
		this.data = data;
	}


	/**
	 * @param rstcode
	 * @param showmsg
	 * @param data
	 * @exception 
	 */
	public ReturnCode(String rstcode, String showmsg, Object data) {
		super();
		this.rstcode = rstcode;
		this.showmsg = showmsg;
		this.data = data;
	}

	/**
	 * @param rstcode
	 * @param showmsg
	 * @param errormsg
	 * @exception 
	 */
	public ReturnCode(String rstcode, String showmsg, String errormsg) {
		super();
		this.rstcode = rstcode;
		this.showmsg = showmsg;
		this.errormsg = errormsg;
	}

	/**
	 * @param rstcode
	 * @param showmsg
	 * @exception 
	 */
	public ReturnCode(String rstcode, String showmsg) {
		super();
		this.rstcode = rstcode;
		this.showmsg = showmsg;
	}


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
