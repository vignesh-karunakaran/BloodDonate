package com.example.vicky.blooddonate;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
public class MySingleton {
    private static MySingleton ms ;
    private RequestQueue requestQueue;
    private static Context ctx;

    private MySingleton(Context context)
    {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getInstance(Context context)
    {
        if(ms==null)
        {
            ms = new MySingleton(context);
        }
        return ms;
    }

    public <T>void addRequstqueue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
