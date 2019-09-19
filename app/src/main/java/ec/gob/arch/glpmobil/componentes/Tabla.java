package ec.gob.arch.glpmobil.componentes;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import ec.gob.arch.glpmobil.R;

public class Tabla {

    private TableLayout tabla;// Layout donde se pintará la tabla
    private ArrayList<TableRow> filas;
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS;// Filas y columnas de nuestra tabla

    /**
     * Constructor de la tabla
     * @param actividad Actividad donde va a estar la tabla
     * @param tabla TableLayout donde se pintará la tabla
     */
    public Tabla(Activity actividad, TableLayout tabla)
    {
        this.actividad=actividad;
        this.tabla = tabla;
        rs = this.actividad.getResources();
        FILAS = COLUMNAS = 0;
        filas = new ArrayList<TableRow>();
    }


    /**
     * Añade la cabecera a la tabla
     * @param recursoCabecera Recurso (array) donde se encuentra la cabecera de la tabla
     */
    public void agregarCabecera(int recursoCabecera)
    {
        TableRow.LayoutParams tamanioCelda;
        TableRow filaCabecera = new TableRow(actividad);
        TableRow.LayoutParams tamanioFila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                                     TableRow.LayoutParams.WRAP_CONTENT);
        filaCabecera.setLayoutParams(tamanioFila);

        String[] arrayCabecera = rs.getStringArray(recursoCabecera);
        COLUMNAS = arrayCabecera.length;
        //En este for se construye cada calumna de la cabecera
        for (int i=0; i<arrayCabecera.length; i++)
        {
            TextView tvCabecera = new TextView(actividad);
            tamanioCelda = new TableRow.LayoutParams(
                                        obtenerAnchoPixelesTexto(arrayCabecera[i]),
                                        TableRow.LayoutParams.WRAP_CONTENT);
            tvCabecera.setText(arrayCabecera[i]);
            tvCabecera.setGravity(Gravity.CENTER_HORIZONTAL);
            tvCabecera.setLayoutParams(tamanioCelda);
            filaCabecera.addView(tvCabecera);
        }
        tabla.addView(filaCabecera);//Se agrega como 1era fila de la tabla a la cabecera
        filas.add(filaCabecera);
        FILAS++;
    }

    /**
     * Agrega una fila a la tabla
     * @param elementos Elementos de la fila
     */
    public void agregarFila(ArrayList<String> elementos){
        TableRow.LayoutParams tamanioCelda;
        TableRow.LayoutParams tamaniofila = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                                                        TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(tamaniofila);
        for (int i=0; i<elementos.size();i++)
        {
            TextView tvFila = new TextView(actividad);
            tvFila.setText(String.valueOf(elementos.get(i)));
            tvFila.setGravity(Gravity.CENTER_HORIZONTAL);
            //tvFila.setTextAppearance(actividad, R.style.es);
            //tvFila.setBackgroundResource(R.drawable);
            tamanioCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(tvFila.getText().toString()),
                                                TableRow.LayoutParams.WRAP_CONTENT);

            tvFila.setLayoutParams(tamanioCelda);
            fila.addView(tvFila);
        }
        tabla.addView(fila);
        filas.add(fila);
        FILAS++;
    }


    /**
     * Obtiene el ancho en píxeles de un texto en un String
     * @param texto Texto
     * @return Ancho en píxeles del texto
     */
    private int obtenerAnchoPixelesTexto(String texto)
    {
        Paint p = new Paint();
        Rect bounds = new Rect();
        p.setTextSize(30);

        p.getTextBounds(texto, 0, texto.length(), bounds);
        return bounds.width();
    }

}
