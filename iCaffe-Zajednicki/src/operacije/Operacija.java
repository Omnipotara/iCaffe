/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package operacije;

import java.io.Serializable;

/**
 *
 * @author Omnix
 */
public enum Operacija implements Serializable {
    //Musterija
    LOGIN,
    LOGOUT,
    SERVER_LOGOUT,
    REGISTER,
    AZURIRANJE_PASSWORD,
    AZURIRANJE_USERNAME,
    //Prodavac
    LOGIN_PRODAVAC,
    LOGOUT_PRODAVAC,
    VREME_UPDATE,
    DODAJ,
    IZMENI,
    OBRISI,
    VRATI_JEDNOG,
    VRATI_SVE,
    ISTEKLO_VREME,
    UBACI_RACUN,
    IZMENI_RACUN,
    LOGIN_NORTIFIKACIJA,
    VRATI_SVE_MUSTERIJE,
    VRATI_ONLINE_MUSTERIJE;
}
