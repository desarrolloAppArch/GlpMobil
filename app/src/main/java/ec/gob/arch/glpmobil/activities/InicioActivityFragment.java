package ec.gob.arch.glpmobil.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.arch.glpmobil.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class InicioActivityFragment extends Fragment {

    public InicioActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }
}
