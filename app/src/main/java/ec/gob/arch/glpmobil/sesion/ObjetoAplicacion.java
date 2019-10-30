package ec.gob.arch.glpmobil.sesion;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;
import ec.gob.arch.glpmobil.entidades.Usuario;

/**
 * 
 * @author Gaby
 *
 */
public class ObjetoAplicacion extends Application {

	private List<GeVwClientesGlp> listaSujetos;
	private GeVwClientesGlp sujetoSeleccionado;
	private Usuario usuario;
	private List<VwCupoHogar> listaCupoHogar;
	private List<VwPersonaAutorizada> listaPersonas;

	public ObjetoAplicacion() {

		listaSujetos = new ArrayList<GeVwClientesGlp>();
		listaCupoHogar = new ArrayList<VwCupoHogar>();
		listaPersonas = new ArrayList<VwPersonaAutorizada>();
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	public List<GeVwClientesGlp> getListaSujetos() {
		return listaSujetos;
	}

	public void setListaSujetos(List<GeVwClientesGlp> listaSujetos) {
		this.listaSujetos = listaSujetos;
	}

	public GeVwClientesGlp getSujetoSeleccionado() {
		return sujetoSeleccionado;
	}

	public void setSujetoSeleccionado(GeVwClientesGlp sujetoSeleccionado) {
		this.sujetoSeleccionado = sujetoSeleccionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<VwCupoHogar> getListaCupoHogar() {
		return listaCupoHogar;
	}

	public void setListaCupoHogar(List<VwCupoHogar> listaCupoHogar) {
		this.listaCupoHogar = listaCupoHogar;
	}

	public List<VwPersonaAutorizada> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<VwPersonaAutorizada> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}
}
