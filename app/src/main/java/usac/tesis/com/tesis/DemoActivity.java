package usac.tesis.com.tesis;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

public class DemoActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        this.fab = (FloatingActionButton) findViewById(R.id.exit_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_demo, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            TextView title = (TextView) rootView.findViewById(R.id.demo_title);
            ProgressBar progreso = (ProgressBar) rootView.findViewById(R.id.demo_progress);
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(section){
                case 1:
                    fab.setVisibility(View.GONE);
                    progreso.setVisibility(View.VISIBLE);
                    title.setText("¿Que es?");
                    textView.setText("{Nombre de la app} es una aplicacion para el manejo" +
                            "automatizado de los perfiles de tu dispositivo movil.\n" +
                            "Desliza hacia la izquierda para continuar...");
                    progreso.setProgress(33);
                    break;
                case 2:
                    fab.setVisibility(View.GONE);
                    progreso.setVisibility(View.VISIBLE);
                    title.setText("¿Como lo hace?");
                    textView.setText("{Nombre de la app} utiliza la informacion del GPS de tu dispositivo" +
                            "para monitorear la configuracion que el dispositivo tenga aplicada en diferentes lugares." +
                            " Despues de un periodo de aprendizaje de 1 semana {Nombre de la app} sera capaz de cambiar" +
                            " la configuracion de tu dispositivo automaticamente basandose en tu comportamiento habitual.");
                    progreso.setProgress(66);
                    break;
                case 3:
                    fab.setVisibility(View.VISIBLE);
                    progreso.setVisibility(View.GONE);
                    title.setText("¿Como se usa?");
                    textView.setText("Durante el perido de aprendizaje {Nombre de la app} leera la configuracion que tengas aplicada " +
                            "a tu dispositivo en diferentes momentos del dia y en los diferentes lugares que frecuentes. Tambien podras " +
                            "definir paquetes de configuraciones dentro de la aplicacion para aplicarlos en conjunto.\n\n" +
                            "Presiona el boton para continuar...");
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
