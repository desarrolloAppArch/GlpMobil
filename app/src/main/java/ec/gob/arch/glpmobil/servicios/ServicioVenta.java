package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;

public class ServicioVenta extends ServicioBase {
    String[] columnas = new String[]{CtVenta.ID_SQLITE,
            CtVenta.CODIGO_CUPO_MES,
            CtVenta.USUARIO_VENTA,
            CtVenta.USUARIO_COMPRA,
            CtVenta.NOMBRE_COMPRA,
            CtVenta.LATITUD,
            CtVenta.LONGITUD,
            CtVenta.FECHA_VENTA,
            CtVenta.FECHA_MODIFICACION,
            CtVenta.CANTIDAD
    };

    String[] columnaIdSqlite = new String[]{CtVenta.ID_SQLITE};

    /**
     * Constructor
     *
     * @param context
     * @author soraya.matute
     */
    public ServicioVenta(Context context) {
        super(context);
    }


    public void insertarVenta(Venta venta) throws Exception{
        try {
            abrir();
            ContentValues cv = new ContentValues();
            cv.put(CtVenta.CODIGO_CUPO_MES, venta.getCodigo_cupo_mes());
            cv.put(CtVenta.USUARIO_VENTA, venta.getUsuario_venta());
            cv.put(CtVenta.USUARIO_COMPRA, venta.getUsuario_compra());
            cv.put(CtVenta.NOMBRE_COMPRA, venta.getNombre_compra());
            cv.put(CtVenta.LATITUD, venta.getLatitud());
            cv.put(CtVenta.LONGITUD, venta.getLongitud());
            cv.put(CtVenta.FECHA_VENTA, venta.getFecha_venta());
            cv.put(CtVenta.CANTIDAD, venta.getCantidad());

            db.insert(CtVenta.TABLA_VENTA, null,cv);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> insertarVenta() : "+ venta);

        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR: ServicioVenta --> insertarVenta() : "+ venta);
            e.printStackTrace();
        }finally {
            cerrar();
        }
    }
    public void eliminarVentasEnviadasPorUsuario(String usuarioVenta){
        try {

            abrir();
            String condicion = CtVenta.USUARIO_VENTA +"='"+usuarioVenta+"'";
            long response= db.delete(CtVenta.TABLA_VENTA,condicion, null);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> eliminar() venta : "+ usuarioVenta);
            cerrar();

        } catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR: ServicioVenta --> eliminar() venta : "+ usuarioVenta);
            e.printStackTrace();
        }
    }


    public List<Venta> buscarTodos(){
        List<Venta> listaVentas = new ArrayList<Venta>();
        try {

            abrir();
            Cursor cursor = db.query(CtVenta.TABLA_VENTA, columnas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Venta venta = obtenerVenta(cursor);
                listaVentas.add(venta);
                cursor.moveToNext();
            }
            /**if (cursor!=null) {
             cursor.close();
             }**/
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarTodos()");
            cerrar();
        }catch(Exception e){
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarTodos()");
            e.printStackTrace();
        }
        return listaVentas;

    }
    private Venta obtenerVenta(Cursor cursor){
        Log.v("log_glp ---------->", "INFO ServicioVenta --> obtenerVenta()");
        Venta venta = new Venta();
        
        venta.setId_sqlite(cursor.getInt(0));
        venta.setCodigo_cupo_mes(cursor.getInt(1));
        venta.setUsuario_venta(cursor.getString(2));
        venta.setUsuario_compra(cursor.getString(3));
        venta.setNombre_compra(cursor.getString(4));
        venta.setLatitud(cursor.getString(5));
        venta.setLongitud(cursor.getString(6));
        venta.setFecha_venta(cursor.getString(7));
        venta.setFecha_modificacion(cursor.getString(8));
        venta.setCantidad(cursor.getInt(9));
        return venta;

    }



    /**
     * Métodos que busca las ventas por cliente
     * @param identificacion
     * @return
     */
    public List<Venta> buscarVentaPorIdentificacion(String identificacion)
    {
        List<Venta> listaVentas = new ArrayList<>();
        Cursor cursor = null;
        try {
            abrir();
            String condicion = CtVenta.USUARIO_COMPRA +"='"+identificacion+"'";
            cursor = db.query(CtVenta.TABLA_VENTA, columnas, condicion, null, null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Venta venta = obtenerVenta(cursor);
                listaVentas.add(venta);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion+" , Nro. registros: "+ listaVentas.size());
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion);
        }finally {
            if(cursor!=null){
                cursor.close();
                Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdentificacion() --> Cerrando cursor");
            }
            cerrar();
        }
        return  listaVentas;

    }

    public List<Venta> buscarVentaPorUsuarioVenta(String identificacion)
    {
        List<Venta> listaVentas = null;
        try {
            listaVentas = new ArrayList<>();
            abrir();
            String condicion = CtVenta.USUARIO_VENTA +"='"+identificacion+"'";
            Cursor cursor = db.query(CtVenta.TABLA_VENTA, columnas, condicion, null, null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Venta venta = obtenerVenta(cursor);
                listaVentas.add(venta);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion);
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion);
        }
        return  listaVentas;

    }
    // Método para actalizar la base de datos
    public void actualizar( Venta ventaAActualizar ){

        int idSqlite = ventaAActualizar.getId_sqlite();
        try {
            Log.v("log_glp---------->","VERBOSE: Ingreso al metodo actualizar ");
            abrir();//abrimos la conexión con la base de datos
            ContentValues cv = new ContentValues();//Crea el contenedor de valores
            cv.put(CtVenta.CANTIDAD, ventaAActualizar.getCantidad());//Campo en donde se actualizara el dato
            cv.put(CtVenta.FECHA_MODIFICACION, ventaAActualizar.getFecha_modificacion());
            Log.v("log_glp ---------->", "INFO ServicioVenta --> EL VALOR ES: "+ ventaAActualizar.getCantidad().toString());

            db.update(CtVenta.TABLA_VENTA,cv, CtVenta.ID_SQLITE+"="+idSqlite,null);

            Log.v("log_glp ---------->", "INFO ServicioVenta --> EL VALOR ES: "+ ventaAActualizar.getCantidad().toString());
            cerrar();// cerramos la conexión con la b<se de datos
        }catch (Exception e){
            Log.e("log_glp ---------->", "ERROR: ServicioVenta --> actualizar() : "+ ventaAActualizar);
            e.printStackTrace();
        }
    }

    /**
     * Método que busca una venta por su idSqlite
     * @param idSqlite
     * @return
     */
    public Venta buscarVentaPorIdSqlite(Integer idSqlite)
    {
       Venta ventaEncontrada = null;
        Cursor cursor = null;
        try {
            abrir();
            String condicion = CtVenta.ID_SQLITE +"='"+idSqlite+"'";
            cursor = db.query(CtVenta.TABLA_VENTA, columnas, condicion, null, null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                ventaEncontrada = new Venta();
                ventaEncontrada = obtenerVenta(cursor);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdSqlite(): "+ventaEncontrada);
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarVentaPorIdSqlite(): "+idSqlite);
        }finally {
            if(cursor!=null){
                cursor.close();
                Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdSqlite() --> Cerrando cursor");
            }
            cerrar();
        }
        return  ventaEncontrada;

    }

    /**
     * Método que permite buscar el idSquile de la última venta insertada
     * @return
     * @throws Exception
     * @autor soraya.matute
     */
    public int buscarIdUltimaVenta() throws Exception{
        int idSqlite=0;
        Cursor cursor = null;
        try {
            abrir();
            cursor = db.query(CtVenta.TABLA_VENTA,columnaIdSqlite,null,null,null,null,null);
            if(cursor.moveToLast()){
                idSqlite = cursor.getInt(0);
            }
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarIdUltimaVenta(): "+idSqlite);
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarIdUltimaVenta(): "+idSqlite);
        } finally {
            if(cursor!=null){
                cursor.close();
                Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarIdUltimaVenta() --> Cerrando cursor");
            }
            cerrar();
        }

        return  idSqlite;
    }


    /**
     * Método que elimina una venta de la base de datos del móvil
     * @param venta
     * @throws Exception
     * @autor soraya.matute
     */
    public void eliminarVenta(Venta venta)throws Exception{
        try{
            abrir();
            db.delete(CtVenta.TABLA_VENTA,CtVenta.ID_SQLITE+"="+venta.getId_sqlite(),null);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> eliminarVenta(): "+venta);
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> eliminarVenta(): "+venta);
            e.printStackTrace();
        }finally {
            cerrar();
        }

    }


}
