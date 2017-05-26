/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.dao;

import com.sapmi.persistencia.Mac;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Auxiliares
 */
@Local
public interface MacFacadeLocal {

    void create(Mac mac);

    void edit(Mac mac);

    void remove(Mac mac);

    Mac find(Object id);

    List<Mac> findAll();

    List<Mac> findRange(int[] range);

    int count();
    
}
