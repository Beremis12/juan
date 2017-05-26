/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.dao;

import com.sapmi.persistencia.Validar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Auxiliares
 */
@Local
public interface ValidarFacadeLocal {

    void create(Validar validar);

    void edit(Validar validar);

    void remove(Validar validar);

    Validar find(Object id);

    List<Validar> findAll();

    List<Validar> findRange(int[] range);

    int count();
    
}
