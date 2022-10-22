package com.example.webservice2;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ClaseAdapter extends BaseAdapter {

    private ArrayList<Clase> clases;
    private Context context;

    public ClaseAdapter(Context context, ArrayList<Clase> clases){
        this.clases = clases;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.clases.size();
    }

    @Override
    public Object getItem(int i) {
        return this.clases.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.clase, null);
            ClaseView claseView = new ClaseView();

            claseView.txtNombre = (TextView)view.findViewById(R.id.txtNombre);
            claseView.txtHoraInicio = (TextView)view.findViewById(R.id.txtHoraInicio);
            claseView.txtHoraFin = (TextView)view.findViewById(R.id.txtHoraFin);
            claseView.txtUbicacion = (TextView)view.findViewById(R.id.txtUbicacion);
            claseView.btnEstado = (RadioButton)view.findViewById(R.id.btnEstado);

            Clase clase = (Clase)getItem(i);
            claseView.txtNombre.setText(clase.getEvento());
            claseView.txtHoraInicio.setText(clase.getHoraInicio());
            claseView.txtHoraFin.setText(clase.getHoraFin());
            claseView.txtUbicacion.setText(clase.getAula() + ", " + clase.getArea());
            if (clase.getEstado() == 0){
                int colorInt = context.getResources().getColor(R.color.verde);
                ColorStateList csl = ColorStateList.valueOf(colorInt);
                claseView.btnEstado.setButtonTintList(csl);
            }else{
                int colorInt = context.getResources().getColor(R.color.rojo);
                ColorStateList csl = ColorStateList.valueOf(colorInt);
                claseView.btnEstado.setButtonTintList(csl);
            }

            view.setTag(claseView);
        } else {
            ClaseView claseView = (ClaseView) view.getTag();

            Clase clase = (Clase)getItem(i);
            claseView.txtNombre.setText(clase.getEvento());
            claseView.txtHoraInicio.setText(clase.getHoraInicio());
            claseView.txtHoraFin.setText(clase.getHoraFin());
            claseView.txtUbicacion.setText(clase.getAula() + ", " + clase.getArea());
            if (clase.getEstado() == 0){
                ColorStateList myColorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{context.getResources().getColor(R.color.verde)}
                        },
                        new int[]{context.getResources().getColor(R.color.verde)}
                );

                claseView.btnEstado.setButtonTintList(myColorStateList);
            }else{
                ColorStateList myColorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{context.getResources().getColor(R.color.rojo)}
                        },
                        new int[]{context.getResources().getColor(R.color.rojo)}
                );

                claseView.btnEstado.setButtonTintList(myColorStateList);
            }
        }
        return view;
    }
}

