package com.example.angelus.volleyandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Angelus on 25/05/2017.
 */

public class RecyclerViewProductos
        extends RecyclerView.Adapter<RecyclerViewProductos.ProductosHolder> {
    private List<Producto> productoList;
    //constructor que pasa de parametro la lista con la que se va ha llenar
    public RecyclerViewProductos( List<Producto> lista) {
        this.productoList=lista;
    }

    //rellena la vista con el holder o el item producto
    @Override
    public ProductosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto,parent,false);
        return new ProductosHolder(v);
    }

    //liga la informacion con el itemproducto
    @Override
    public void onBindViewHolder(ProductosHolder holder, int position) {
        holder.txtnombre.setText(productoList.get(position).getNombre());
        holder.txtdescripcion.setText(productoList.get(position).getDescripcion());

    }

    //regresa el tamaño de la lista
    @Override
    public int getItemCount() {
        return productoList.size();
    }

    public static class ProductosHolder extends RecyclerView.ViewHolder{
        public TextView txtnombre;
        public TextView txtdescripcion;

        public ProductosHolder(View itemView) {
            super(itemView);
            txtnombre=(TextView) itemView.findViewById(R.id.txtNombre);
            txtdescripcion=(TextView) itemView.findViewById(R.id.txtDescripcion);
        }
    }
}
