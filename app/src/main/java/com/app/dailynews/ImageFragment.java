package com.app.dailynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ImageFragment extends Fragment {

    private Button button_iamge = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View iamgeView = inflater.inflate(R.layout.imagefragment, container, false);
        button_iamge = (Button) iamgeView.findViewById(R.id.button_iamge);
        button_iamge.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageFragment.this.getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
        return iamgeView;
    }
}
