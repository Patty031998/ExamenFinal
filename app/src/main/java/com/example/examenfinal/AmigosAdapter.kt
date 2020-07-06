package com.example.examenfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_amigos_list.view.*

class AmigosAdapter(private var data: List<AmigosReponse>, private val listener: AmigosActivity) :
    RecyclerView.Adapter<AmigosAdapter.AmigoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmigoHolder {
        val inflatedView = parent.inflate(R.layout.item_amigos_list, false)
        return AmigoHolder(inflatedView)
    }

    fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    fun updateList(amigosList: List<AmigosReponse>) {
        this.data = amigosList
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AmigoHolder, position: Int) {
        val amigo: AmigosReponse = this.data[position]

        holder.itemView.txt_nombre_amigo.text = amigo.name +" "+  amigo.lastname


        if(!amigo.image.isBlank()) {
            Picasso.get()
                .load(amigo.image)
                .into(holder.itemView.iv_foto_amigo)
        }


        holder.itemView.setOnClickListener { listener.onItemClickListener(amigo) }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    class AmigoHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                Toast.makeText(v.context, "Item", Toast.LENGTH_SHORT).show()
            }
        }

        interface OnAdapterListener {
            fun onItemClickListener( item: AmigosReponse)
        }

    }
}