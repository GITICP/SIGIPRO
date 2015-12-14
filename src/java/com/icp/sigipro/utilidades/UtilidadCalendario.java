
package com.icp.sigipro.utilidades;
import com.icp.sigipro.calendario.dao.EventoDAO;
import com.icp.sigipro.calendario.modelos.Evento;
import com.icp.sigipro.core.SIGIPROException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Esta clase es el API del Calendario, se utiliza para crear y eliminar eventos fácilmente en cualquier parte de la aplicación. 
 * @author Amed
 */
public class UtilidadCalendario {
  
  private static UtilidadCalendario theSingleton = null;
  private EventoDAO dao = new EventoDAO();
  private static SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  private UtilidadCalendario(){}
  
  public static UtilidadCalendario getSingletonUtilidadCalendario(){
    if (theSingleton == null)
    {
      theSingleton = new UtilidadCalendario();
    }
    return theSingleton;
  }
  /**
   * Método un toque complicado para agregar eventos al calendario. Atención a los parámetros: 
   * @param title String con el título. Max 150 caracteres.
   * @param start Timestamp en el formato requerido por el método parseDate de esta clase.
   * @param end Timestamp en el formato requerido por el método parseDate de esta clase.
   * @param description String con la descripción del evento. Max 500 caracteres.
   * @param allDay True o False si es evento de todo el día o no.
   * @param sharing_Type Para esta "API" el evento tiene que ser compartido. Especificar una de tres opciones "Usuarios" "Roles" o "Secciones".
   * @param ids Especificar el array de ids de Usuarios, Roles o Secciones a los cuales se les compartirá el evento.
   * @throws SIGIPROException 
   */
  public void agregarEvento(String title, Timestamp start, Timestamp end, String description, Boolean allDay, String sharing_Type, String[] ids) throws SIGIPROException{
    Evento evento = new Evento();
    evento.setTitle(title);
    evento.setStart_date(start);
    evento.setEnd_date(end);
    evento.setDescription(description);
    evento.setAllDay(allDay);
    dao.insertarEvento(evento, true, sharing_Type, ids);
  }
  /**
   * Método simple para eliminar eventos del calendario.
   * @param id id del evento a eliminar.
   * @throws SIGIPROException 
   */
  public void eliminarEvento(Integer id) throws SIGIPROException{
    dao.eliminarEvento(id);
  }
  /**
   * Método crear los timestamps que recibe el calendario.
   * @param fecha Fecha en formato yyyy-MM-dd
   * @param hora Hora en formato hh:mm
   * @return Lo que hace es retornar el timestamp que recibe el calendario
   * @throws ParseException 
   */
  public Timestamp parseDate(String fecha, String hora) throws ParseException{
    String timestamp_start = fecha + " " + hora + ":00";
    Date parsedDate = date_format.parse(timestamp_start);
    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
    return timestamp;
  }
}
