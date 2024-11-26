package com.sl2425.da.sellersapp.Model;

import java.io.Serializable;

public class RememberObj implements Serializable
{
    private String cif = "";
    private String remember = "false";


    public RememberObj()
    {

    }

    public String getRemember()
    {
        return remember;
    }

    public void setRemember(String remember)
    {
        this.remember = remember;
    }

    public String getCif()
    {
        return cif;
    }

    public void setCif(String cif)
    {
        this.cif = cif;
    }
}
