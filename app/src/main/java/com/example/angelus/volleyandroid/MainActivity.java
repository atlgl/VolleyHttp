package com.example.angelus.volleyandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnrefrescar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnrefrescar=(Button) findViewById(R.id.btnrefresh);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerproductos);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        final RequestQueue colamensajes= Volley.newRequestQueue(getBaseContext());
        //String url="http://www.legionx.com.mx/alumnos/data/apiproductos/getproductos.php";
        String url="http://10.0.2.2/wsciudades/ejericioandroid/getproductos.php";

        StringRequest peticion=new StringRequest(
                Request.Method.GET,url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("Resultado",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        final List<Producto> productos=new ArrayList<>();






        final JsonObjectRequest peticionJSON=new JsonObjectRequest(
                Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray arreglo=response.getJSONArray("productos");
                            for (int i=0;i<arreglo.length();i++){
                                JSONObject obj=(JSONObject) arreglo.get(i);
                                Producto p=new Producto(
                                        obj.getInt("id"),
                                        obj.getString("nombre"));
                                p.setDescripcion(obj.getString("descripcion"));
                                productos.add(p);
                            }
                            RecyclerViewProductos recyclerViewProductos=new
                                    RecyclerViewProductos(productos);
                            recyclerView.setAdapter(recyclerViewProductos);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("JSON",response.toString());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }
        );

        colamensajes.add(peticionJSON);

        JSONObject parametros=new JSONObject();
        try {
            parametros.put("nombre","Chetos");
            parametros.put("descripcion","...");
            parametros.put("precio",10.0);
            parametros.put("fecha","2017/01/01");
            parametros.put("urlfoto",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final JsonObjectRequest peticionpost=new JsonObjectRequest(
                Request.Method.POST, "http://localhost/wsciudades/ejericioandroid/insertproducto.php", parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getBaseContext(),response.toString(),Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );



        //colamensajes.add(peticion);

        btnrefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productos.clear();
                colamensajes.add(peticionJSON);
                colamensajes.add(peticionpost);
            }
        });
   }
}
