package com.example.uddishverma.pg_app_beta;

/**
 * Created by naman on 29/9/16.
 */

public class Filter_POJO
{

    private String name = "";
    private boolean checked = false;

    public Filter_POJO()
    {
    }

    public Filter_POJO(String name)
    {
        this.name = name;
    }

    public Filter_POJO(String name, boolean checked)
    {
        this.name = name;
        this.checked = checked;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    public String toString()
    {
        return name;
    }

    public void toggleChecked()
    {
        checked = !checked;
    }





}
