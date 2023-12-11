package com.example.proyectoaccesoadatos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TablaFilas {
    private String[] values;

    public TablaFilas() {

    }

    public TablaFilas(String[] values) {
        this.values = values;
    }
    @XmlElement
    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

}
