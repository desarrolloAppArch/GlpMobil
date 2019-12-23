package ec.gob.arch.glpmobil.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.arch.glpmobil.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PieFragment extends Fragment {


    public PieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pie, container, false);
    }

}
