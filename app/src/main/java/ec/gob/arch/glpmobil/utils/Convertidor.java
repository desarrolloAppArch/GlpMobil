package ec.gob.arch.glpmobil.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Convertidor {

	
	/**
	 * Captura fecha y hora del Sistema en Date
	 * @return Day MM dd HH:mm:ss COT yyyy
	 */
	public static Date horafechaSistemaDate(){
        return new Date(System.currentTimeMillis());
    }
	
	/**
	 * Captura fecha y hora del Sistema en Timestamp
	 * @return dd-MM-yyyy HH:mm:ss
	 */
	public static Timestamp horafechaSistemaTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    /**
	 * Captura fecha y hora del Sistema en Time
	 * @return HH:mm:ss
	 */
	public static Time horafechaSistemaTime(){
        return new Time(System.currentTimeMillis());
    }

	
	/**
	 * Convierte un Time en String
	 * @param 'HH:mm:ss
	 * @return "HH:mm:ss"
	 */
	public static String timeAString(Time time){
		SimpleDateFormat sdf  = new SimpleDateFormat("HH:mm:ss");
		//format (date o time -> text)
		String strHora = sdf.format(time); 
		return strHora;
	}
	
	/**
	 * Convierte un Date en String
	 * @param 'Day MM dd HH:mm:ss COT yyyy
	 * @return "MM-dd-yyyy"
	 */
	public static String dateAfechaString(Date date){		
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		//format (date o time -> text)
		String strFecha = sdf.format(date); 
		return strFecha;
	}
	
	//SIMILAR AL ANTERIOR PERO LA DIFERENCIA ES QUE EN SimpleDateFormat DEFINO LO QUE QUIERO QUE RETORNE
	/**
	 * Convierte un Date en String
	 * @param 'Day MM dd HH:mm:ss COT yyyy
	 * @return "MM-dd-yyyy HH:mm:ss"
	 */
	public static String dateAString(Date date){		
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//format (date o time -> text)
		String strFecha = sdf.format(date); 
		return strFecha;
	}




	/**
	 * 
	 * @param date
	 * @return "dd/MM/aaaa"
	 */
	public static String dateAStringDEFAULT(Date date) {
		DateFormat df = DateFormat.getDateInstance();
		String s =  df.format(date);
		return s;
	}
	
	/**
	 * 
	 * @param date
	 * @return "dd/MM/aa"
	 */
	public static String dateAStringSHORT(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
		String s =  df.format(date);
		return s;
	}
	
	/**
	 * 
	 * @param date
	 * @return "dd/MM/aaaa"
	 */
	public static String dateAStringMEDIUM(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		String s =  df.format(date);
		return s;
	}
	
	/**
	 * 
	 * @param date
	 * @return "dd de MM de aaaa"
	 */
	public static String dateAStringLONG(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		String s =  df.format(date);
		return s;
	}
	
	/**
	 * 
	 * @param date
	 * @return "dia_semana dd de MM de aaaa"
	 */
	public static String dateAStringFULL(Date date) {
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		String s =  df.format(date);
		return s;
	}	
	
	
	/**
	 * Convierte sola la hora de un Date en String 
	 * @param 'Day MM dd HH:mm:ss COT yyyy
	 * @return "HH:mm:ss"
	 */
	public static String DateAhoraString(Date date){
		Time time = dateATime(date);
		return timeAString(time);
	}

	
	/**
	 * Convierte un String en Date
	 * @param "MM-dd-yyyy HH:mm:ss"
	 * @return Day MM dd HH:mm:ss COT yyyy 
	 */
	public static Date stringADate(String string){
		SimpleDateFormat sdf  = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		//parse (text -> date)
		Date fecha = null;
		try {
			fecha = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return fecha;
	}
	
	/**
	 * Convierte un String en Date
	 * @param "MM-dd-yyyy HH:mm:ss"
	 * @return Day MM dd HH:mm:ss COT yyyy 
	 */
	public static Date stringADateSimple(String string){
		SimpleDateFormat sdf  = new SimpleDateFormat("MM-dd-yyyy");
		//parse (text -> date)
		Date fecha = null;
		try {
			fecha = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return fecha;
	}
	
	/**
	 * Convierte un Timestamp en Time
	 * @param 'yyyy-MM-dd HH:mm:ss.nnn
	 * @return HH:mm:ss
	 */
	public static Time timestampATime(Timestamp timestamp) {
        return new Time(timestamp.getTime());
    }
	
	/**
	 * Convierte un Timestamp en Date
	 * @param 'yyyy-MM-dd HH:mm:ss.nnn
	 * @return Day MM dd HH:mm:ss COT yyyy
	 */
	public static Date timestampADate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
	
	/**
	 * Convierte un Date en Timestamp 
	 * @param 'Day MM dd HH:mm:ss COT yyyy
	 * @return yyyy-MM-dd HH:mm:ss.nnn
	 */
	public static Timestamp dateATimeStamp(Date date){
        return new Timestamp(date.getTime());
    }
	
	/**
	 * Convierte un Date en Time
	 * @param 'Day MM dd HH:mm:ss COT yyyy
	 * @return HH:mm:ss.nnn
	 */
	public static Time dateATime(Date date){
        return new Time(date.getTime());
    }
	
	/**
	 * Convierte un Time en Timestamp
	 * @param 'HH:mm:ss.nnn
	 * @return yyyy-MM-dd HH:mm:ss.nnn
	 */
	public static Timestamp timeATimestamp(Time time) {
        return new Timestamp(time.getTime());
    }
	
	
	public static Date timeADate(Time time) {
        return new Date(time.getTime());
    }
	
	
	/**
	 * Redondea a un n�mero double a los d�gitos decimales que deseemos
	 * @param numero
	 * @param digitos
	 * @return
	 */
	public static double redondear(double numero, int digitos){
		String val = numero+"";
	    BigDecimal big = new BigDecimal(val);
	    big = big.setScale(digitos, RoundingMode.HALF_UP);
	    return big.doubleValue();
	}
	
    

    /**

    * Calcula el numero de dias entre dos fechas
    * @param 'fechaInicio
    * @param 'fechaFin
    * @return dias
    */

	public static  int diferenciasDeFechasDias(Date fechaInicial, Date fechaFinal) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);		
		String fechaInicioString = df.format(fechaInicial);		
		try {		
			fechaInicial = df.parse(fechaInicioString);		
		} catch (ParseException ex) {
			
		}		
				
		String fechaFinalString = df.format(fechaFinal);		
		try {		
			fechaFinal = df.parse(fechaFinalString);		
		} catch (ParseException ex) {
		
		}		
		
		long fechaInicialMs = fechaInicial.getTime();		
		long fechaFinalMs = fechaFinal.getTime();		
		long diferencia = fechaFinalMs - fechaInicialMs;		
		double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));		
		return ((int) dias);
		
	}	
	
	
	/**	
	*Suma meses a una fecha	
	* @return	
	*/
	
	public static Date sumarMeses(Date fecha, int meses){	
	                   Calendar cal= Calendar.getInstance();	
	                   cal.setTime(fecha);	
	                   cal.add(Calendar.MONTH, meses);	
	                   return cal.getTime();
	    }	
	
	
	/**	
	* Calcular meses entre 2 fechas	
	* @param fechaInicio	
	* @param fechaFin	
	* @return	
	*/
	
	public static int calcularMesesAFecha(Date fechaInicio, Date fechaFin) {	
		try {		
			//Fecha inicio en objeto Calendar			
			Calendar startCalendar = Calendar.getInstance();			
			startCalendar.setTime(fechaInicio);			
			//Fecha finalizaci�n en objeto Calendar			
			Calendar endCalendar = Calendar.getInstance();			
			endCalendar.setTime(fechaFin);			
			//C�lculo de meses para las fechas de inicio y finalizaci�n			
			int startMes = (startCalendar.get(Calendar.YEAR) * 12) + startCalendar.get(Calendar.MONTH);			
			int endMes = (endCalendar.get(Calendar.YEAR) * 12) + endCalendar.get(Calendar.MONTH);			
			//Diferencia en meses entre las dos fechas			
			int diffMonth = endMes - startMes;			
			return diffMonth;
		
		} catch (Exception e) {		
			return 0;		
		}	
	}	
	
	
	/**	
	* Comparar 2 fechas	
	* @param fechaInicio	
	* @param fechaFin	
	* @return	
	*/
	
	public static int compararFechas(Date fechaInicio, Date fechaFin) {	
		try {		
			String f1=dateAStringDEFAULT(fechaInicio);			
			String f2=dateAStringDEFAULT(fechaFin);			
			    return f1.compareTo(f2);		
		} catch (Exception e) {		
			e.printStackTrace();
			return 99;		
		}	
	}
	
	public static Integer anioActual(){
        Calendar cal= Calendar.getInstance();
        Integer year= cal.get(Calendar.YEAR); 
        return year;
    }
	
	public static Integer mesActual(){
        Calendar cal= Calendar.getInstance();
        Integer month= cal.get(Calendar.MONTH); 
        return month;
    }

	/**
	 * metodo que calcula la diferencia  en segundos entre dos fechas
	 * @param vinicio
	 * @param vfinal
	 * @return
	 */
	public static long diferenciaEnSegundosFechas(String vinicio, String vfinal){

		Date dinicio = null, dfinal = null;
		long milis1, milis2, diff;

		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			// PARSEO STRING A DATE
			dinicio = sdf.parse(vinicio);
			dfinal = sdf.parse(vfinal);

		} catch (ParseException e) {

			System.out.println("Se ha producido un error en el parseo");
		}

		//INSTANCIA DEL CALENDARIO GREGORIANO
		Calendar cinicio = Calendar.getInstance();
		Calendar cfinal = Calendar.getInstance();

		//ESTABLECEMOS LA FECHA DEL CALENDARIO CON EL DATE GENERADO ANTERIORMENTE
		cinicio.setTime(dinicio);
		cfinal.setTime(dfinal);


		milis1 = cinicio.getTimeInMillis();

		milis2 = cfinal.getTimeInMillis();


		diff = milis2-milis1;


		// calcular la diferencia en segundos

		long diffSegundos =  Math.abs (diff / 1000);


		// calcular la diferencia en minutos

		long diffMinutos =  Math.abs (diff / (60 * 1000));


		long restominutos = diffMinutos%60;



		// calcular la diferencia en horas

		long diffHoras =   (diff / (60 * 60 * 1000));



		// calcular la diferencia en dias

		long diffdias = Math.abs ( diff / (24 * 60 * 60 * 1000) );


     /*
     System.out.println("En segundos: " + diffSegundos + " segundos.");

     System.out.println("En minutos: " + diffMinutos + " minutos.");

     System.out.println("En horas: " + diffHoras + " horas.");

     System.out.println("En dias: " + diffdias + " dias.");
     */


     long devolver = diffSegundos;

		return devolver;
	}
	/**
	 * Devuelve la fecha seteada a 23 horas 59 minutos 59 segundos en formato
	 * calendar
	 *
	 * @param fecha fecha para transformar
	 * @return fecha con formato de hora 23:59:59
	 * @author cggm
	 */
	public static Calendar fechaFinCalendar(Date fecha) {
		Calendar fFin = Calendar.getInstance();
		fFin.setTime(fecha);
		fFin.set(Calendar.HOUR_OF_DAY, 23);
		fFin.set(Calendar.MINUTE, 59);
		fFin.set(Calendar.SECOND, 59);
		fFin.set(Calendar.MILLISECOND, 0);
		return fFin;
	}
	/**
	 * Devuelve la fecha seteada a 00 horas 00 minutos 00 segundos en formato
	 * calendar
	 *
	 * @param fecha fecha para transformar
	 * @return fecha con formato de hora 00:00:00
	 * @author cggm
	 */
	public static Calendar fechaInicioCalendar(Date fecha) {
		Calendar fInicio = Calendar.getInstance();
		fInicio.setTime(fecha);
		fInicio.set(Calendar.HOUR_OF_DAY, 0);
		fInicio.set(Calendar.MINUTE, 0);
		fInicio.set(Calendar.SECOND, 0);
		fInicio.set(Calendar.MILLISECOND, 0);
		return fInicio;
	}

	/**
	 * Devuelve la fecha seteada a 00 horas 00 minutos 00 segundos
	 *
	 * @param fecha fecha para transformar
	 * @return fecha con formato de hora 00:00:00
	 * @author cggm
	 */
	public static Date fechaInicio(Date fecha) {
		try {
			return fechaInicioCalendar(fecha).getTime();
		} catch (Exception e) {
			return fecha;
		}
	}

	/**
	 * Devuelve la fecha seteada a 23 horas 59 minutos 59 segundos
	 *
	 * @param fecha fecha para transformar
	 * @return fecha con formato de hora 23:59:59
	 * @author cggm
	 */
	public static Date fechaFin(Date fecha) {
		try {
			return fechaFinCalendar(fecha).getTime();
		} catch (Exception e) {
			return fecha;
		}
	}
	/**
	 * Permite restar dias a una fecha
	 *
	 * @author Blanca Yanguicela
	 * @param fecha
	 * @param cantidad
	 * @return
	 */
	public static Date restarDia(Date fecha, int cantidad) {
		cantidad=cantidad*-1;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_MONTH, cantidad);
		return calendar.getTime();
	}
	
}
