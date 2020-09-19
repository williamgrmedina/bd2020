/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashSet;

/**
 *
 * @author Medina
 */

//Uma classe que implementa uma lista de logins únicos
public abstract class HashSetLogin {
    HashSet<String> uq_login = new HashSet<>();
    
    /*tenta adicionar login de um funcionario. Se login ja existe, retorna false,
    caso contrario, retorna true*/
    public boolean addLogin(String login){
        return uq_login.add(login);
    }
    
    
}
