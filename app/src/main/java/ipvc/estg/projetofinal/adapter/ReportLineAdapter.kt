package ipvc.estg.projetofinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.projetofinal.R
import ipvc.estg.projetofinal.api.Report

class ReportLineAdapter (val report: List<Report>): RecyclerView.Adapter<ReportLineAdapter.ReportViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewholder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reports_recycler_line, parent, false)
        return ReportViewholder(itemView)
    }

    override fun onBindViewHolder(holder: ReportViewholder, position: Int) {
        return holder.bind(report[position])
    }

    override fun getItemCount(): Int {
        return report.size
    }



    class ReportViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tipoItemView: TextView = itemView.findViewById(R.id.tipoReport)
        val descricaoItemView: TextView = itemView.findViewById(R.id.descricaoReport)

        fun bind(report: Report) {
            tipoItemView.text = "Tipo: " + report.tipo
            descricaoItemView.text = "Descrição: " + report.descricao
        }
    }
}