package com.example.proyectoaccesoadatos;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement
public class TablaColumnas {
    private List<TablaFilas> rows;


    @XmlElement
    public List<TablaFilas> getRows() {
        return rows;
    }

    public void setRows(List<TablaFilas> rows) {
        this.rows = rows;
    }
}
