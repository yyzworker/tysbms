package com.tys.upush.ios;


import com.tys.upush.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast() {
		try {
			this.setPredefinedKeyValue("type", "groupcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
