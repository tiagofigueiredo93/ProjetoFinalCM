package ipvc.estg.projetofinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.projetofinal.R
import ipvc.estg.projetofinal.dao.NoteDao
import ipvc.estg.projetofinal.entities.Notes
import org.w3c.dom.Text


class LineAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<LineAdapter.NoteViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Notes>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitleView: TextView = itemView.findViewById(R.id.titleNotes)
        val noteDescription: TextView = itemView.findViewById(R.id.descriptionNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerline, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]
        holder.noteTitleView.text = "Title: "+current.title
        holder.noteDescription.text = "Description: "+current.description

    }

    internal fun setNotes(notes: List<Notes>){
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}
