package ipvc.estg.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.projetofinal.adapter.ReportLineAdapter
import ipvc.estg.projetofinal.api.EndPoints
import ipvc.estg.projetofinal.api.Report
import ipvc.estg.projetofinal.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReports : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_reports)

        //Obtenção de uma instância do retrofit
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var recyclerView = findViewById<RecyclerView>(R.id.reportsRecycler)

        call.enqueue(object : Callback<List<Report>> {
            override fun onResponse(call: Call<List<Report>>, response: Response<List<Report>>){
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@AllReports)
                        adapter = ReportLineAdapter(response.body()!!)

                    }
                }
            }
            override fun onFailure(call: Call<List<Report>>, t: Throwable){
                Toast.makeText(this@AllReports,"${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }


}