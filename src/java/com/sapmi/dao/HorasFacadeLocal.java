/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.dao;

import com.sapmi.persistencia.Horas;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Auxiliares
 */
@Local
public interface HorasFacadeLocal {

    void create(Horas horas);

    void edit(Horas horas);

    void remove(Horas horas);

    Horas find(Object id);

    List<Horas> findAll();

    List<Horas> findRange(int[] range);

    int count();
    
}
