package ec.gob.arch.glpmobil.servicios;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtUsuario;
import ec.gob.arch.glpmobil.entidades.Usuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Contiene los servicios para insertar o recuperar datos de la tabla Usuario
 * @author soraya.matute
 *
 */
public class ServiciosUsuario extends ServicioBase {
	/**
	 * Columnas a recuperar de la tabla Usuario
	 */
	String[] columnas = new String[]{CtUsuario.ID_SQLITE,
									 CtUsuario.ID,
									 CtUsuario.NOMBRE,
									 CtUsuario.CLAVE,
									 CtUsuario.RUC,
			                         CtUsuario.CORREO};

	public ServiciosUsuario(Context context) {
		super(context);
        Log.v("log_glp ---------->", "INFO ServiciosUsuario --> call CONSTRUCTOR ");
	}
	
	
	public void insertar(Usuario usuario){
		try {
			abrir();
			
			ContentValues cv = new ContentValues();
			cv.put(CtUsuario.ID, usuario.getId());
			cv.put(CtUsuario.NOMBRE, usuario.getNombre());
			cv.put(CtUsuario.CLAVE, usuario.getClave());
			cv.put(CtUsuario.RUC, usuario.getRuc());
			cv.put(CtUsuario.CORREO, usuario.getCorreo());
			
			db.insert(CtUsuario.TABLA_USUARIO, null, cv);
            Log.v("log_glp ---------->", "INFO ServiciosUsuario --> insertar() usuario id: "+ usuario.getId());
			cerrar();
		} catch (Exception e) {
			Log.v("log_glp ---------->", "ERROR: ServiciosUsuario --> insertar() usuario id: "+ usuario.getId());
			e.printStackTrace();
		}
		
	}

    public void actualizar(Usuario usuario){
        try {
            abrir();

            ContentValues cv = new ContentValues();
            cv.put(CtUsuario.ID, usuario.getId());
            cv.put(CtUsuario.NOMBRE, usuario.getNombre());
            cv.put(CtUsuario.CLAVE, usuario.getClave());
			cv.put(CtUsuario.RUC, usuario.getRuc());
			cv.put(CtUsuario.CORREO, usuario.getCorreo());

            String condicion = CtUsuario.ID+"='"+usuario.getId()+"'";
            if(db.update(CtUsuario.TABLA_USUARIO, cv, condicion, null ) != -1)
            {
                Log.v("log_glp ---------->", "INFO ServiciosUsuario --> actualizar() SI actualizó usuario: "+ usuario);
            }else
            {
                Log.v("log_glp ---------->", "INFO ServiciosUsuario --> actualizar() NO actualizó usuario: "+ usuario);
            }

            cerrar();
        } catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR: ServiciosUsuario --> actualizar() ususario id: "+ usuario.getId());
            e.printStackTrace();
        }

    }

	public boolean esUsuarioUnico(String id)
	{
		boolean esUnico=true;
		Usuario usuario = buscarUsuarioPorId(id);
		if(usuario!=null)
		{
			esUnico=false;
		}
		return  esUnico;
	}

	public Usuario buscarUsuarioPorId(String id)
	{
		Usuario usuario = null;
		abrir();
		String condicion = CtUsuario.ID+"='"+id+"'";
		Cursor cursor = db.query(CtUsuario.TABLA_USUARIO, columnas, condicion, null, null, null,null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			usuario = obtenerUsuario(cursor);
			cursor.moveToNext();
		}
		cerrar();
		return  usuario;
	}


	public List<Usuario> buscarTodos(){
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try {
			abrir();
			Cursor cursor = db.query(CtUsuario.TABLA_USUARIO, columnas, null, null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Usuario usuario = obtenerUsuario(cursor);
				listaUsuarios.add(usuario);
				cursor.moveToNext();
			}
			/**if (cursor!=null) {
				cursor.close();
			}**/
			Log.v("log_glp ---------->", "INFO ServiciosUsuario --> buscarTodos()");
			cerrar();
		}catch(Exception e){
			Log.v("log_glp ---------->", "ERROR ServiciosUsuario --> buscarTodos()");
			e.printStackTrace();
		}
		return listaUsuarios;
		
	}
	
	private Usuario obtenerUsuario(Cursor cursor){
		Usuario usuario = new Usuario();
		usuario.setId_sqlite(cursor.getInt(0));
		usuario.setId(cursor.getString(1));
		usuario.setNombre(cursor.getString(2));
		usuario.setClave(cursor.getString(3));
		usuario.setRuc(cursor.getString(4));
		usuario.setCorreo(cursor.getString(5));
		return usuario;
		
	}

}
