package se.mah.k3.skaneAPI;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import se.mah.k3.skaneAPI.control.Constants;
import se.mah.k3.skaneAPI.model.Journey;
import se.mah.k3.skaneAPI.model.Journeys;
import se.mah.k3.skaneAPI.xmlparser.MyAdapter;
import se.mah.k3.skaneAPI.xmlparser.Parser;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRese extends Fragment implements AdapterView.OnItemSelectedListener {

    private ArrayList<Journey> myJourney = new ArrayList<Journey>();
    private MyAdapter adapter;
    private Spinner sp1;
    private Spinner sp2;
    ExpandableListView ev;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public FragmentRese() {
        // Required empty public constructor
    }

     @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_rese_alt, container, false);
            ///Do whatever
            ev = (ExpandableListView) v.findViewById(R.id.expandableListView);
            adapter = new MyAdapter(getActivity(),myJourney);

            //MyAsyncTask myAsyncTask = new MyAsyncTask();
            //myAsyncTask.execute("KD323B-20151-K3522");
            sp1 = (Spinner)v.findViewById(R.id.avgangISpinner);
            sp1.setOnItemSelectedListener(this);
            sp2 = (Spinner)v.findViewById(R.id.destinationISpinner);
            sp2.setOnItemSelectedListener(this);


         //kollar så att man är uppkopplad mot internet
         ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo ni = cm.getActiveNetworkInfo();
         if(ni == null || !ni.isConnected()) {
             Toast.makeText(getActivity(), "INGET INTERNET DUMMA LARS", Toast.LENGTH_LONG).show();
         } else {
             Toast.makeText(getActivity(), "Så himla uppkopplad!", Toast.LENGTH_LONG).show();
         }


            return v;
        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i("Fragment", "MenuSelection. " + id);
        if (id == R.id.refresh) {
            int fromNumber = sp1.getSelectedItemPosition();
            int toNumber = sp2.getSelectedItemPosition();
            String[] sa = getActivity().getResources().getStringArray(R.array.station_numbers);
            String searchURL = Constants.getURL(sa[fromNumber], sa[toNumber], 10);

            new MyAsyncTask().execute(searchURL);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

        //Listens to the spinner

        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int fromNumber = sp1.getSelectedItemPosition();
            int toNumber = sp2.getSelectedItemPosition();
            String[] courses = getActivity().getResources().getStringArray(R.array.station_numbers);
            String searchURL = Constants.getURL(courses[fromNumber], courses[toNumber], 10);
            Log.d("From Number", courses[fromNumber]);

            //Log.i("ExpFragment", "Course. " + course);
            new MyAsyncTask().execute(searchURL);
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }


        //And the thread
        private class MyAsyncTask extends AsyncTask<String,Void,Void> {
            @Override
            protected Void doInBackground(String... params) {
                Journeys journeys = Parser.getJourneys(params[0]);
                myJourney.clear();
                myJourney.addAll(journeys.getJourneys());


                Log.d("Skane", journeys.getJourneys().toString());

                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                ev.setAdapter(adapter);
            }




    }


}


