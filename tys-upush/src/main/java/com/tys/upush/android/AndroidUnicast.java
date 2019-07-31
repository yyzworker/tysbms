package com.tys.upush.android;

import com.tys.upush.AndroidNotification;

public class AndroidUnicast extends AndroidNotification {
	public AndroidUnicast() {
		try {
			this.setPredefinedKeyValue("type", "unicast");	
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}
}