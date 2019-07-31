package com.tys.upush.android;


import com.tys.upush.AndroidNotification;

public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast() {
		try {
			this.setPredefinedKeyValue("type", "broadcast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}
