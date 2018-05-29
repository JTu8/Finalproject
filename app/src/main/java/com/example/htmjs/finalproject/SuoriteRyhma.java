package com.example.htmjs.finalproject;

import com.android.volley.toolbox.StringRequest;

public class SuoriteRyhma {

    private int ID;
    private String workgroup_name;


    public SuoriteRyhma(String _workgroup_name) {

        this.workgroup_name = _workgroup_name;
    }

    public String getWorkgroup_name() {
        return workgroup_name;
    }

    public void setWorkgroup_name(String workgroup_name) {
        this.workgroup_name = workgroup_name;
    }
}
