package ipvc.estg.projetofinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.projetofinal.R
import ipvc.estg.projetofinal.dataClasses.Notes

class LineAdapter(val list: ArrayList<Notes>):RecyclerView.Adapter<LineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerline, parent, false)
        return LineViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentPlace = list[position]

        //holder.titleNotes.text = currentPlace.title

    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class LineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    //val titleNotes = itemView.title

}
