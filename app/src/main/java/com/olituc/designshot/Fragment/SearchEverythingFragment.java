package com.olituc.designshot.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.olituc.designshot.R;

/**
 * Created by olituc on 3/12/18.
 * All Rights Reserved by olituc
 */

public class SearchEverythingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searcheverything,container,false);
        return view;
    }
}
