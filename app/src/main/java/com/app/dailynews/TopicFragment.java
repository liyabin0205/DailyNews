package com.app.dailynews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TopicFragment extends Fragment  {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//return View.inflate(getActivity(), R.layout.studyfragment, null);
		View studyView = inflater.inflate(R.layout.topicfragment, container, false);
		        
        return studyView; 
	}

}
