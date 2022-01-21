package com.example.evaluacion_rogerac.service.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaluacion_rogerac.R;
import com.example.evaluacion_rogerac.service.api.ConfigApi;
import com.example.evaluacion_rogerac.service.entity.service.Cuentas;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CuentasAdapter extends RecyclerView.Adapter<CuentasAdapter.ViewHolder> {
    private List<Cuentas> cuentas;

    public CuentasAdapter(List<Cuentas> cuentas) {
        this.cuentas = cuentas;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuentas, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(this.cuentas.get(position));
    }

    @Override
    public int getItemCount() {
        return this.cuentas.size();
    }

    public void cargarItems(List<Cuentas> cuentas){
        this.cuentas.clear();
        this.cuentas.addAll(cuentas);
        this.notifyDataSetChanged();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setItem(final Cuentas cuentas){
            ImageView imgCuentas = itemView.findViewById(R.id.imgCuentas);
            TextView correo = itemView.findViewById(R.id.correo);
            TextView contra = itemView.findViewById(R.id.contra);

            String url = ConfigApi.baseUrlE + "/api/file/download/" + cuentas.getFoto().getFileName();
            final Picasso picasso = new Picasso.Builder(itemView.getContext())
                    .downloader(new OkHttp3Downloader(ConfigApi.getClient()))
                    .build();
            picasso.load(url)
                    .error(R.drawable.imagen_no_encontrada)
                    .into(imgCuentas);
            correo.setText(cuentas.getCorreo());
            contra.setText(cuentas.getPassword());

        }
    }
}
