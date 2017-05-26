/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.visual;

import co.edu.udea.exception.OrgSistemasSecurityException;
import co.edu.udea.wsClient.OrgSistemasWebServiceClient;
import co.edu.udea.wsClient.dto.OrgSistemasWSRequest;
import co.edu.udea.wsClient.dto.OrgSistemasWSRequestObject;
import com.sapmi.dao.ValidarFacadeLocal;
import com.sapmi.persistencia.Validar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Auxiliares
 */
@Path("/Persona")
public class Persona {

  ValidarFacadeLocal validarFacade = lookupValidarFacadeLocal();
  private String ClavePublicaDeCifrado = "251860937072417361372773";
  private String token = "146F48BAAC934830392B9BB45DA7A0D7A1474A8F";
  
//Metodo para validar que el estudiante pertenezca a la escuela
  @GET
  @Path("/validar/{usu}/{cla}")
  public Response validar(@PathParam("usu") String usuario, @PathParam("cla") String clave)
    throws OrgSistemasSecurityException
  {
    if ("".equals(usuario)) {
      return Response.status(200).entity("false Digite el usuario").build();
    }
    if ("".equals(clave)) {
      return Response.status(200).entity("false Digite la clave").build();
    }
    String cedula = validarUsuarioOID(usuario, clave);
    if ("false usuario incorrecto".equals(cedula)) {
      return Response.status(200).entity("false usuario incorrecto").build();
    }
    if ("ErrorOID".equals(cedula)) {
      return Response.status(200).entity("false ErrorOID").build();
    }
    String programa = verificarInformacionPersona(cedula);
    if ("pertenece al programa".equals(programa)) {
      return Response.status(200).entity(cedula).build();
    }
    if ("ErrorVerificarPrograma".equals(programa)) {
      return Response.status(200).entity("false ErrorVerificarPrograma").build();
    }
    if ("No pertenece a ningun programa de la escuela".equals(programa)) {
      try
      {
        List<Validar> vali=validarFacade.findAll();
        String resultado = "false El estudiante no aparece en la base de datos de la escuela";
        for(Validar as:vali){
            if(as.getCedula().equals(cedula))
                resultado=cedula;
        }
        return Response.status(200).entity(resultado).build();
      }
      catch (Exception e)
      {
        e.printStackTrace();
        return Response.status(200).entity("false error").build();
      }
    }
    return Response.status(200).entity("").build();
  }
  
  
  private String validarUsuarioOID(String usuario, String clave)
    throws OrgSistemasSecurityException
  {
    try
    {
      OrgSistemasWebServiceClient wsClient = new OrgSistemasWebServiceClient(this.ClavePublicaDeCifrado, false);
      wsClient.addParam("usuario", usuario);
      wsClient.addParam("clave", clave);
      String cedula = wsClient.obtenerString("validarusuariooidxcn", this.token);
      if ("ERROR 01: El usuario o clave son incorrectos ".equals(cedula)) {
        return "false usuario incorrecto";
      }
      int dato2 = 0;
      int dato = cedula.length() - 1;
      while (cedula.charAt(dato) == ' ')
      {
        dato--;
        dato2++;
      }
      return cedula.substring(0, cedula.length() - dato2);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return "ErrorOID";
  }
  
  private String verificarInformacionPersona(String cedula)
    throws OrgSistemasSecurityException
  {
    try
    {
      OrgSistemasWebServiceClient wsClient2 = new OrgSistemasWebServiceClient(false);
      
      wsClient2.addParam("cedula", cedula);
      
      OrgSistemasWSRequest r = wsClient2.obtener("consultainformacionacademicabasicamares", this.token);
      
      Long programa = null;
      
      String estadoAlumnoPrograma = null;
      for (OrgSistemasWSRequestObject o : r.getValues())
      {
        programa = (Long)o.get(r.getAttributesName()[1].getAttributeName());
        estadoAlumnoPrograma = (String)o.get(r.getAttributesName()[4].getAttributeName());
        if (((programa.longValue() == 784L) || (programa.longValue() == 783L) || (programa.longValue() == 785L) || (programa.longValue() == 789L) || (programa.longValue() == 60110L) || (programa.longValue() == 60035L)) && (
          ("ACTIVO".equals(estadoAlumnoPrograma)) || ("GRADUADO".equals(estadoAlumnoPrograma)) || 
          ("TERMINOMATERIAS".equals(estadoAlumnoPrograma)))) {
          return "pertenece al programa";
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return "ErrorVerificarPrograma";
    }
    return "No pertenece a ningun programa de la escuela";
  }

  
    private ValidarFacadeLocal lookupValidarFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ValidarFacadeLocal) c.lookup("java:global/Services/ValidarFacade!com.sapmi.dao.ValidarFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
  
}
