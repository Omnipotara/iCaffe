/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author Omnix
 */
public enum Smene implements Serializable {
    PRVA(LocalTime.of(6, 0), LocalTime.of(14, 0)),
    DRUGA(LocalTime.of(14, 0), LocalTime.of(22, 0)),
    TRECA(LocalTime.of(22, 0), LocalTime.of(6, 0));

    private final LocalTime vremeOd;
    private final LocalTime vremeDo;

    Smene(LocalTime vremeOd, LocalTime vremeDo) {
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }

    public LocalTime getVremeOd() {
        return vremeOd;
    }

    public LocalTime getVremeDo() {
        return vremeDo;
    }

    @Override
    public String toString() {
        return name() + " SMENA";
    }
}
