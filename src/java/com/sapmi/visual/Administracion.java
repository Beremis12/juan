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
import com.sapmi.dao.AdminFacadeLocal;
import com.sapmi.dao.ClaseFacadeLocal;
import com.sapmi.dao.EstadisticaFacadeLocal;
import com.sapmi.dao.HorasFacadeLocal;
import com.sapmi.persistencia.Clase;
import com.sapmi.persistencia.Horas;
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
@Path("/administracion")
public class Administracion {

   
  private String ClavePublicaDeCifrado = "251860937072417361372773";
  private String token = "146F48BAAC934830392B9BB45DA7A0D7A1474A8F";

// Variables Puente entre la base de datos y lo servicios RESTful (Backend y Frontend)    
    ClaseFacadeLocal claseFacade = lookupClaseFacadeLocal();
    AdminFacadeLocal adminFacade = lookupAdminFacadeLocal();
    EstadisticaFacadeLocal estadisticaFacade1 = lookupEstadisticaFacadeLocal();
    HorasFacadeLocal horasFacade1 = lookupHorasFacadeLocal();
           
  //Metodo que verifica la disponiblidad del aula
  @GET
  @Path("/estado_aula/{id}")
  public String estado_aula(@PathParam("id") String id)
  {
   //Se busca la clase que se desea verificar    
   Clase clase=claseFacade.find(Integer.parseInt(id));
    
    
     //Si no existe retorna null
      if(clase==null)
          return null;
      String disponible;
      //Se Obtiene la disponiblidad de la clase
        disponible= clase.getEstado();
       if ("true".equals(disponible))            
          return "true";
       else
          return "false";  
  }
  
  //Metodo para cambiar la disponibilidad de un aula
  @GET
  @Path("/cambiar_aula/{id}")
  public String cambiar_aula(@PathParam("id") int id)
  {
   //Verifica el estado del aula y lo cambia a su contrario   
    String estado;
    String cambio;
    estado = estado_aula(Integer.toString(id));
    if ("true".equals(estado)) {
      cambio = "false";
    } else {
      cambio = "true";
    }
    try
    {
     //Modifica la base de datos   
      Clase aula;
      aula=claseFacade.find(id);
      aula.setEstado(cambio);
      claseFacade.edit(aula);
      return cambio;
    }
    catch (Exception e) {}
    return "Error con el cambio de estado";
  }
  
  //Metodo para validar el usuario que va a ingresar
  @GET
  @Path("/validarUsuarioOID/{usu}/{cla}")
  public Response validarUsuarioOID(@PathParam("usu") String usuario, @PathParam("cla") String clave)
    throws OrgSistemasSecurityException
  {
    try
    {
      OrgSistemasWebServiceClient wsClient = new OrgSistemasWebServiceClient(this.ClavePublicaDeCifrado, false);
      wsClient.addParam("usuario", usuario);
      wsClient.addParam("clave", clave);
      String cedula = wsClient.obtenerString("validarusuariooidxcn", this.token);
      if ("ERROR 01: El usuario o clave son incorrectos ".equals(cedula)) {
        return Response.status(200).entity("false usuario incorrecto").build();
      }
      int dato2 = 0;
      int dato = cedula.length() - 1;
      while (cedula.charAt(dato) == ' ')
      {
        dato--;
        dato2++;
      }
      cedula = cedula.substring(0, cedula.length() - dato2);
      return Response.status(200).entity(cedula).build();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return Response.status(200).entity("").build();
  }
  
  //Metodo que valida la informacion personal del usuario
  @GET
  @Path("/verificarInformacionPersona/{cc}")
  public Response verificarInformacionPersona(@PathParam("cc") String cedula)
    throws OrgSistemasSecurityException
  {
    String Respuesta = "";
    try
    {
      OrgSistemasWebServiceClient wsClient2 = new OrgSistemasWebServiceClient(false);
      
      wsClient2.addParam("cedula", cedula);
      
      OrgSistemasWSRequest r = wsClient2.obtener("consultainformacionacademicabasicamares", this.token);
      
      Long semestre = null;
      Long programa = null;
      String nombrePrograma = null;
      String estadoAlumnoPrograma = null;
      Respuesta = "<table>";
      Respuesta = Respuesta + "<th>Programa</th>";
      Respuesta = Respuesta + "<th>estado</th>";
      Respuesta = Respuesta + "<th>semestre</th>";
      for (OrgSistemasWSRequestObject o : r.getValues())
      {
        semestre = (Long)o.get(r.getAttributesName()[0].getAttributeName());
        programa = (Long)o.get(r.getAttributesName()[1].getAttributeName());
        nombrePrograma = (String)o.get(r.getAttributesName()[2].getAttributeName());
        estadoAlumnoPrograma = (String)o.get(r.getAttributesName()[4].getAttributeName());
        if (((programa.longValue() == 784L) || (programa.longValue() == 783L) || (programa.longValue() == 785L) || (programa.longValue() == 789L) || (programa.longValue() == 60110L) || (programa.longValue() == 60035L)) && (
          ("ACTIVO".equals(estadoAlumnoPrograma)) || ("GRADUADO".equals(estadoAlumnoPrograma)) || 
          ("TERMINOMATERIAS".equals(estadoAlumnoPrograma)))) {
          System.out.println("pertenece al programa: " + nombrePrograma + "_" + programa + "materias: " + estadoAlumnoPrograma + "semestre" + semestre);
        }
        Respuesta = Respuesta + "<tr>";
        Respuesta = Respuesta + "<td>" + nombrePrograma + "  " + programa + "</td>";
        Respuesta = Respuesta + "<td>" + estadoAlumnoPrograma + "</td>";
        Respuesta = Respuesta + "<td>" + semestre + "</td>";
        Respuesta = Respuesta + "</tr>";
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    Respuesta = Respuesta + "</table>";
    return Response.status(200).entity(Respuesta).build();
  }
  
  
  @GET
  @Path("/InformacionAlumno/{id}")
  public Response InformacionAlumno(@PathParam("id") String cedula)
  {
    String respuesta = "";
    try
    {
      
      List<Horas> listaHoras=horasFacade1.findAll();
     
      
      String selectquery = "SELECT * FROM `horas` WHERE `cedula`=" + cedula + " order by `Fecha` desc";
      ResultSet rs = s.executeQuery(selectquery);
      
      rs.next();
      respuesta = rs.getString("Fecha");
      respuesta = respuesta + "$";
      respuesta = respuesta + rs.getString("totalmin");
      respuesta = respuesta + "$";
      
      String selectquery2 = "SELECT * FROM `estadistica` WHERE `cedula`=" + cedula + " order by `Fecha` desc";
      ResultSet rs2 = s.executeQuery(selectquery2);
      
      rs2.next();
      respuesta = respuesta + rs2.getString("horaFinal");
      
      conexion.close();
    }
    catch (Exception e)
    {
      return Response.status(200).entity("Error en la comunicacion con la base de datos").build();
    }
    return Response.status(200).entity(respuesta).build();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  //@EJB conexion entre frontend y backend
    private AdminFacadeLocal lookupAdminFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (AdminFacadeLocal) c.lookup("java:global/Services/AdminFacade!com.sapmi.dao.AdminFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ClaseFacadeLocal lookupClaseFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ClaseFacadeLocal) c.lookup("java:global/Services/ClaseFacade!com.sapmi.dao.ClaseFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HorasFacadeLocal lookupHorasFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (HorasFacadeLocal) c.lookup("java:global/Services/HorasFacade!com.sapmi.dao.HorasFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private EstadisticaFacadeLocal lookupEstadisticaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EstadisticaFacadeLocal) c.lookup("java:global/Services/EstadisticaFacade!com.sapmi.dao.EstadisticaFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
  
  

    
}
