package usac.tesis.com.tesis.menuFragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import usac.tesis.com.tesis.R;
import usac.tesis.com.tesis.profileManagement.customAdapter;
import usac.tesis.com.tesis.profileManagement.perfil;
import usac.tesis.com.tesis.utils.GPSTracker;
import usac.tesis.com.tesis.utils.WSCaller;
import usac.tesis.com.tesis.utils.configApplier;


public class profilesFragment extends Fragment {

    private List<perfil> items;
    private customAdapter listAdapter;
    private ListView list;

    private View progressView;

    private ContentResolver resolver;
    private Window window;
    private AudioManager audioManager;

    public profilesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profiles, container, false);

        this.items = new ArrayList<>();
        this.listAdapter = new customAdapter(getActivity().getApplicationContext(),R.layout.custom_row,items);
        this.list = (ListView)view.findViewById(R.id.profilesList);
        this.list.setAdapter(listAdapter);

        this.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                perfil p = (perfil)list.getItemAtPosition(position);
                Toast.makeText(getActivity().getApplicationContext(),p.getName(),Toast.LENGTH_SHORT).show();
                configApplier a = new configApplier(p,resolver,window,audioManager);
                a.apply();
                //SYNC
                GPSTracker t = new GPSTracker(getActivity().getApplicationContext());
                new profileSetTask(t.getLatitude()+","+t.getLongitude()).execute(p);
            }
        });

        this.progressView = view.findViewById(R.id.list_progress);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        int userId = preferences.getInt("tesisUserID", -1);
        new profileGetTask().execute(userId+"");

        this.resolver = getActivity().getContentResolver();
        this.window = getActivity().getWindow();
        this.audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        return view;
    }

    private void showProgress(boolean show){
        this.progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public class profileGetTask extends AsyncTask<String,Void,Void> {
        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected Void doInBackground(String... params) {
            WSCaller caller = new WSCaller("get_perfiles");
            caller.addStringParam("arg0",params[0]);
            String perfiles = caller.call();
            llenarLista(perfiles);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listAdapter.notifyDataSetChanged();
            showProgress(false);
        }

        private void llenarLista(String perfiles){
            perfiles = perfiles.replace("; ",";").replace("\n", "");
            String[] arr = perfiles.split(";");
            for(String i : arr){
                items.add(new perfil(i));
            }
        }
    }

    public class profileSetTask extends AsyncTask<perfil,Void,Void>{

        private String pos;

        public profileSetTask(String pos){
            this.pos = pos;
        }

        @Override
        protected void onPreExecute() {
            showProgress(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showProgress(false);
        }

        @Override
        protected Void doInBackground(perfil... params) {
            Context ctx = getActivity().getApplicationContext();
            WSCaller caller = new WSCaller("ingresar_perfil");
            GPSTracker tracker = new GPSTracker(ctx);
            Calendar calendar = Calendar.getInstance();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);

            String id = preferences.getInt("tesisUserID",-1)+"";
            String hora = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            String dia_del_mes = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+"";
            String dia = calendar.get(Calendar.DAY_OF_WEEK)+"";

            Log.d("ID",id);
            Log.d("HORA",hora);
            Log.d("POS",this.pos);
            Log.d("DIA_DEL_MES",dia_del_mes);
            Log.d("DIA",dia);
            Log.d("PERFIL",params[0].getSaveString());

            //LLAMAR AL SERVICIO

            return null;
        }
    }
}
