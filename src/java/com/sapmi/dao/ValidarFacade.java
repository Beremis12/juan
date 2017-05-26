/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.dao;

import com.sapmi.persistencia.Validar;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Auxiliares
 */
@Stateless
public class ValidarFacade extends AbstractFacade<Validar> implements ValidarFacadeLocal {

    @PersistenceContext(unitName = "ServicesPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ValidarFacade() {
        super(Validar.class);
    }
    
}
