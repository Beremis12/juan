/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapmi.visual;

import java.util.Calendar;
import java.util.StringTokenizer;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Auxiliares
 */
@Path("/Registro")
public class Registro {
@GET
  @Path("/vertiempo/{ced}")
  public Response vertiempo(@PathParam("ced") String ced)
    throws SQLException
  {
    String tiempoaux = "No";
    
    String tiempo = "";
    
    String fecha = "";
    
    Calendar calendario = Calendar.getInstance();
    fecha = calendario.get(1) + "-" + (calendario.get(2) + 1) + "-" + calendario.get(5);
    try
    {
      Class.forName("org.gjt.mm.mysql.Driver");
      Connection conexion = DriverManager.getConnection(this.url, this.user, this.pass);
      
      Statement s = conexion.createStatement();
      
      String selectquery = "select * from horas WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "'";
      ResultSet rs = s.executeQuery(selectquery);
      if (rs.next()) {
        tiempoaux = rs.getString(5);
      }
      if ("Si".equals(tiempoaux))
      {
        s = conexion.createStatement();
        
        String query = "update horas set TiempoExtra = ? WHERE cedula= ? AND Fecha= ?";
        PreparedStatement preparedStmt = conexion.prepareStatement(query);
        
        preparedStmt.setString(1, "No");
        preparedStmt.setString(2, ced);
        preparedStmt.setString(3, fecha);
        
        preparedStmt.executeUpdate();
      }
      conexion.close();
    }
    catch (ClassNotFoundException e)
    {
      return Response.status(200).entity("Error en la conexi�n con el servidor por favor comun�quese con el administrador de la sala").build();
    }
    return Response.status(200).entity(tiempoaux).build();
  }
  
  @GET
  @Path("/registrar/{ced}/{mac}")
  public Response registrar(@PathParam("ced") String ced, @PathParam("mac") String mac)
    throws SQLException
  {
    String respuesta = "";
    String fecha = "";
    String hora = "";
    String diaSemana = "";
    int equipo = 0;
    int sala = 0;
    
    Calendar calendario = Calendar.getInstance();
    fecha = calendario.get(1) + "-" + (calendario.get(2) + 1) + "-" + calendario.get(5);
    hora = calendario.get(11) + ":" + calendario.get(12);
    diaSemana = String.valueOf(calendario.get(7));
    try
    {
      Class.forName("org.gjt.mm.mysql.Driver");
      Connection conexion = DriverManager.getConnection(this.url, this.user, this.pass);
      
      Statement s = conexion.createStatement();
      
      String selectquery = "select * from estadistica WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "' AND horaFinal='00:00:00'";
      ResultSet rs = s.executeQuery(selectquery);
      if (rs.next()) {
        return Response.status(200).entity("Error usted posee una sesi�n abierta").build();
      }
      Statement ss = conexion.createStatement();
      ResultSet rss = ss.executeQuery("SELECT * FROM mac WHERE mac_id='" + mac + "'");
      
      rss.next();
      sala = rss.getInt(3);
      equipo = rss.getInt(4);
      
      selectquery = "select * from horas WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "'";
      rs = s.executeQuery(selectquery);
      if (rs.next())
      {
        String res = Integer.toString(rs.getInt(4));
        if (rs.getInt(4) <= 0) {
          return Response.status(200).entity("Error usted sobrepaso el tiempo").build();
        }
        String insertQuery = "INSERT INTO estadistica ( mac, Diasemana, cedula, fecha, horaInicial, horaFinal, Sala, Equipo) values(?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStmt = conexion.prepareStatement(insertQuery);
        preparedStmt.setString(1, mac);
        preparedStmt.setString(2, diaSemana);
        preparedStmt.setString(3, ced);
        preparedStmt.setString(4, fecha);
        preparedStmt.setString(5, hora);
        preparedStmt.setString(6, "0:0");
        preparedStmt.setInt(7, sala);
        preparedStmt.setInt(8, equipo);
        preparedStmt.execute();
        conexion.close();
        return Response.status(200).entity(res).build();
      }
      String insertQuery = "INSERT INTO estadistica ( mac, Diasemana, cedula, fecha, horaInicial, horaFinal, Sala, Equipo) values(?,?,?,?,?,?,?,?)";
      
      PreparedStatement preparedStmt = conexion.prepareStatement(insertQuery);
      preparedStmt.setString(1, mac);
      preparedStmt.setString(2, diaSemana);
      preparedStmt.setString(3, ced);
      preparedStmt.setString(4, fecha);
      preparedStmt.setString(5, hora);
      preparedStmt.setString(6, "0:0");
      preparedStmt.setInt(7, sala);
      preparedStmt.setInt(8, equipo);
      preparedStmt.execute();
      
      String insertQueryy = "INSERT INTO horas (cedula, Fecha, totalmin, TiempoExtra) values(?,?,?,?)";
      
      PreparedStatement preparedStmt2 = conexion.prepareStatement(insertQueryy);
      preparedStmt2.setString(1, ced);
      preparedStmt2.setString(2, fecha);
      preparedStmt2.setInt(3, 240);
      preparedStmt2.setString(4, "No");
      preparedStmt2.execute();
      conexion.close();
      return Response.status(200).entity("240").build();
    }
    catch (ClassNotFoundException e) {}
    return Response.status(200).entity("Error en la conexi�n con el servidor por favor comun�quese con el administrador de la sala").build();
  }
  
  @GET
  @Path("/salir/{ced}")
  public Response salir(@PathParam("ced") String ced)
    throws SQLException
  {
    String fecha = "";
    String hora = "";
    String horainicialcompleta = "";
    
    Calendar calendario = Calendar.getInstance();
    fecha = calendario.get(1) + "-" + (calendario.get(2) + 1) + "-" + calendario.get(5);
    int minutosfinal = calendario.get(12);
    int horafinal = calendario.get(11);
    hora = calendario.get(11) + ":" + calendario.get(12);
    try
    {
      Class.forName("org.gjt.mm.mysql.Driver");
      Connection conexion = DriverManager.getConnection(this.url, this.user, this.pass);
      
      String selectquery = "select * from estadistica WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "' AND horaFinal='00:00:00'";
      Statement s = conexion.createStatement();
      ResultSet rs = s.executeQuery(selectquery);
      if (rs.next()) {
        horainicialcompleta = rs.getString(6);
      }
      StringTokenizer st = new StringTokenizer(horainicialcompleta, ":");
      String horainicial = st.nextToken();
      String minutosinicial = st.nextToken();
      
      int totalmininicial = Integer.parseInt(horainicial) * 60 + Integer.parseInt(minutosinicial);
      int totalminfinal = horafinal * 60 + minutosfinal;
      int total = totalminfinal - totalmininicial;
      
      selectquery = "select * from horas WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "'";
      s = conexion.createStatement();
      rs = s.executeQuery(selectquery);
      if (!rs.next())
      {
        String insertQuery = "INSERT INTO horas (cedula, Fecha, totalmin, TiempoExtra) values(?,?,?,?)";
        
        PreparedStatement preparedStmt = conexion.prepareStatement(insertQuery);
        preparedStmt.setString(1, ced);
        preparedStmt.setString(2, fecha);
        preparedStmt.setInt(3, total);
        preparedStmt.setString(4, "No");
        preparedStmt.execute();
      }
      else
      {
        total = rs.getInt(4) - total;
        String query = "update horas set  totalmin = ? WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "'";
        PreparedStatement preparedStmt = conexion.prepareStatement(query);
        preparedStmt.setInt(1, total);
        preparedStmt.execute();
      }
      String query = "update estadistica set  horaFinal = ? WHERE cedula= '" + ced + "' AND Fecha= '" + fecha + "' AND horaFinal='00:00:00'";
      PreparedStatement preparedStmt = conexion.prepareStatement(query);
      preparedStmt.setString(1, hora);
      preparedStmt.execute();
      
      return Response.status(200).entity("ok").build();
    }
    catch (ClassNotFoundException e) {}
    return Response.status(200).entity("Error en la base de datos").build();
  }
    
}
