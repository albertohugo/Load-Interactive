package hugo.alberto.clustermap;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import hugo.alberto.clustermap.Handler.DatabaseHandler;
import hugo.alberto.clustermap.Handler.JsonParseHandler;
import hugo.alberto.clustermap.Model.EstablishmentModel;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String URL = "http://www.mocky.io/v2/5661d6ee1000007f3a8d920c";
    private JsonParseHandler jsonParseHandler;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        jsonParseHandler = new JsonParseHandler();
        new DataFetch().execute();
        db = new DatabaseHandler(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<EstablishmentModel> establishment = db.getAllEstablishment();
        for (EstablishmentModel data : establishment) {
            String log = "Name: " + data.getName() + " ,Phone: " + data.getPhone() + " ,Description: " + data.getDescription() +
                    " ,LocalName: " + data.getLocalname()+ " ,Lat: " + data.getLat()+ " ,Lon: " + data.getLon();
            Log.d("Id: ", log);

            Double lat = Double.valueOf(data.getLat());
            Double lon = Double.valueOf(data.getLon());
            LatLng marker = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(marker).snippet(data.getDescription()).title(data.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }
                @Override
                public View getInfoContents(Marker marker) {
                    LinearLayout info = new LinearLayout(getApplication());
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(getApplication());
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(getApplication());
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(marker.getSnippet());

                    info.addView(title);
                    info.addView(snippet);

                    return info;
                }
            });
        }
    }
    private void readJsonParse(String json_data) {
        if (json_data != null) {
            try {

                JSONObject json = new JSONObject(json_data);
                JSONArray establishments = json.getJSONArray("establishments");

                for (int i = 0; i < establishments.length(); i++) {
                    JSONObject contactObj = establishments.getJSONObject(i);

                    String name = contactObj.getString("name");
                    String phone = contactObj.getString("phone");
                    String description = contactObj.getString("description");

                    JSONObject local = contactObj.getJSONObject("location");
                    String local_name = local.getString("name");
                    String lat = local.getString("lat");
                    String lon = local.getString("long");

                    Log.v("Establishment: ", name + "," + phone + "," + description + "|" +"Local: " + local_name + "," + lat + "," + lon);
                    db.addEstablishments(new EstablishmentModel(name, phone, description, local_name, lat, lon));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public class DataFetch extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String json_data = jsonParseHandler.serviceCall(URL, JsonParseHandler.GET);
            Log.d("in inBG()", "fetch data" + json_data);
            readJsonParse(json_data);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
