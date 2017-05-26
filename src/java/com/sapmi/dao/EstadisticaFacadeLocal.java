/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.dao;

import com.sapmi.persistencia.Estadistica;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Auxiliares
 */
@Local
public interface EstadisticaFacadeLocal {

    void create(Estadistica estadistica);

    void edit(Estadistica estadistica);

    void remove(Estadistica estadistica);

    Estadistica find(Object id);

    List<Estadistica> findAll();

    List<Estadistica> findRange(int[] range);

    int count();
    
}
