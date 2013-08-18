package com.up.onetouch.bo;

import com.up.onetouch.activity.AbstractActivity;

public abstract class AbstractBO {
	
	private AbstractActivity activity;
		
	public AbstractBO(AbstractActivity activity) {
		this.setActivity(activity);
	}

	public AbstractActivity getActivity() {
		return activity;
	}

	public void setActivity(AbstractActivity activity) {
		this.activity = activity;
	}
}
