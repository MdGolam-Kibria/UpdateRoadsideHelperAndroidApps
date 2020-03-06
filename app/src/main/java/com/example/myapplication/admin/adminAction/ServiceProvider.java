package com.example.myapplication.admin.adminAction;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.BaseUrl;
import com.example.myapplication.admin.adminAction.ServiceProviderPack.CustomAdapterForAdminShowServiceProviders;
import com.example.myapplication.recyclerViewClickAndDeviderHundle.MyRecyclerViewDividerItemDecoration;
import com.example.myapplication.recyclerViewClickAndDeviderHundle.RecyclerTouchListener;
import com.example.myapplication.serviceProviderCheck.ProvidersCheckInterface;
import com.example.myapplication.serviceProviderCheck.ServicePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceProvider extends Fragment {
    private RecyclerView recyclerView;

    public ServiceProvider() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewServiceProvider);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.BASE_URL)  //using rest api my rest api name is  = forRoadside into my pc
                .addConverterFactory(GsonConverterFactory.create()).build();
        ProvidersCheckInterface providersCheckInterface = retrofit.create(ProvidersCheckInterface.class);
        Call<List<ServicePojo>>listCall = providersCheckInterface.getAllServiceProvidersInfo();
        listCall.enqueue(new Callback<List<ServicePojo>>() {
            @Override
            public void onResponse(Call<List<ServicePojo>> call, Response<List<ServicePojo>> response) {
                showIt(response.body());
            }

            @Override
            public void onFailure(Call<List<ServicePojo>> call, Throwable t) {

            }
        });

        return view;
    }

    private void showIt(final List<ServicePojo> body) {
        CustomAdapterForAdminShowServiceProviders customAdapterForAdminShowServiceProviders = new CustomAdapterForAdminShowServiceProviders(body,getContext());
        recyclerView.addItemDecoration(new MyRecyclerViewDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 16));
        // above parameters for recycler view devider style class and click listener copy from google link below
        //https://www.androidhive.info/2016/01/android-working-with-recycler-view/
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ServicePojo movie = body.get(position);
                Toast.makeText(getContext(), movie.getServiceName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.setAdapter(customAdapterForAdminShowServiceProviders);
    }
}
