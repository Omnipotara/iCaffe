/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;
import operacije.Operacija;

/**
 *
 * @author Omnix
 */
public class ServerskiOdgovor implements Serializable{
    private Object param;
    private Operacija operacija;

    public ServerskiOdgovor() {
    }

    public ServerskiOdgovor(Object param, Operacija operacija) {
        this.param = param;
        this.operacija = operacija;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Operacija getOperacija() {
        return operacija;
    }

    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }
    
    
}
