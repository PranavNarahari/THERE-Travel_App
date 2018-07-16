package edu.illinois.cs.cs125.there;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ExtraActivity extends AppCompatActivity {

    private static RequestQueue rq;
    public String client_id = "client_id=SH44CRC112QH2HD2TY2SDATV3Y3R4HRHFWX2I0HNNSGZUDQ5";
    public String client_secret = "client_secret=UIGIVE3BBLSUBVPF3YOOVJBHOWFVBTNXBYFHF1XYBBWDT0IV";
    public String url = "https://api.foursquare.com/v2/venues/search";
    public String query = "near=Worcester,MA";
    public JSONObject fullJson;
    public String version = "v=20180628";
    public String addressOfPlace;
    public String nameOfPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        apiCall(query);
    }

    public void apiCall(final String query) {
        rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, url + "?" + query + "&" + client_id + "&" + client_secret + "&" + version,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                fullJson = response;
                try {
                    JSONObject responseData = fullJson.getJSONObject("response");
                    JSONArray venuesData = responseData.getJSONArray("venues");
                    JSONObject place = venuesData.getJSONObject(0);
                    nameOfPlace = place.getString("name");
                    JSONObject location = place.getJSONObject("location");
                    addressOfPlace = location.getString("address");

                    setView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(jsonObjRequest);
    }

    public void setView() {
        TextView name = findViewById(R.id.name);
        name.setText(nameOfPlace);

        TextView address = findViewById(R.id.address);
        address.setText(addressOfPlace);
    }

}
