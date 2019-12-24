package com.jaxen.makechatrecord.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jaxen.makechatrecord.R;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private RelativeLayout version_check, wxqw, showFiles;
    private TextView showVersion, click_to_file;

    private LinearLayout rec;
    private RelativeLayout tp, set_image;

    private Switch repeat, save_name;

    private String filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);


        return view;
    }

    private void IfShowRec() {

        SharedPreferences makeXML = getActivity().getSharedPreferences("FirstRunNew", MODE_PRIVATE);
        long firstRunTime = makeXML.getLong("firstRunTime", System.currentTimeMillis());
        long deatLine = (System.currentTimeMillis() - firstRunTime) / 1000 / 60 ;

        System.out.println("deadLine:"+deatLine);
        if (deatLine > 20)
        {
            rec.setVisibility(View.VISIBLE);
        }
        else rec.setVisibility(View.GONE);
    }

    //检查按钮状态
    private void checkRepeatState() {
        SharedPreferences sp = Objects.requireNonNull(getActivity()).getSharedPreferences("switch_repeat", MODE_PRIVATE);
        boolean b = sp.getBoolean("CanRepeat", false);
        repeat.setChecked(b);

        SharedPreferences sp0 = Objects.requireNonNull(getActivity()).getSharedPreferences("switch_repeat", MODE_PRIVATE);
        boolean b0 = sp0.getBoolean("CanSaveName", false);
        save_name.setChecked(b0);

        if (save_name.isChecked())
            showFiles.setVisibility(View.VISIBLE);
        else
            showFiles.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId())
        {

        }

    }
}
