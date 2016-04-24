package usac.tesis.com.tesis.menuFragments;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import usac.tesis.com.tesis.Principal;
import usac.tesis.com.tesis.R;
import usac.tesis.com.tesis.profileManagement.perfil;
import usac.tesis.com.tesis.utils.WSCaller;


/**
 * A simple {@link Fragment} subclass.
 */
public class newProfileFragment extends Fragment {

    private View progressView;
    private EditText profileName;
    private Switch vibrate;
    private SeekBar bright;
    private SeekBar volume;


    public newProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_new_profile, container, false);

        setListeners(view);
        this.progressView = view.findViewById(R.id.create_progress);

        return view;
    }

    private void setListeners(View view){
        Button cancel = (Button)view.findViewById(R.id.cancel_create);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapFragment f = new mapFragment();
                android.support.v4.app.FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, f);
                ft.commit();
                Principal.lastId = R.id.nav_map;
            }
        });

        Button create = (Button)view.findViewById(R.id.save_profile);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);

                String nombre = profileName.getText().toString();
                String vib = vibrate.isChecked()+"";
                String b = bright.getProgress()+"";
                String vo = volume.getProgress()+"";

                new profileCreateTask().execute(nombre,vib,vo,b);
            }
        });

        this.profileName = (EditText)view.findViewById(R.id.profileName);
        this.vibrate = (Switch)view.findViewById(R.id.vibrateSound);
        this.bright = (SeekBar)view.findViewById(R.id.brightnessBar);
        this.volume = (SeekBar)view.findViewById(R.id.volumeBar);
    }

    private void showProgress(boolean show){
        this.progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void clearForm(){
        this.profileName.setText("");
        this.vibrate.setChecked(false);
        this.bright.setProgress(0);
        this.volume.setProgress(0);
    }

    public class profileCreateTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPostExecute(Boolean result) {
            showProgress(false);
            clearForm();
            Toast.makeText(getActivity().getApplicationContext(),"Perfil creado exitosamente",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            int userId = preferences.getInt("tesisUserID", -1);

            perfil p = new perfil(params[0]+"~~"+params[1]+"~~"+params[2]+"~~"+params[3]);

            WSCaller caller = new WSCaller("set_perfil");
            caller.addStringParam("arg0",userId+"");
            caller.addStringParam("arg1",p.getSaveString());
            String result = caller.call();
            return !result.equals("false");
        }
    }

}
