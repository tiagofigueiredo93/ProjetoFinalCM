package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.projetofinal.adapter.LineAdapter
import ipvc.estg.projetofinal.entities.Notes
import ipvc.estg.projetofinal.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel : NoteViewModel
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("***TAG", "onCreate")

        val recyclerLine = findViewById<RecyclerView>(R.id.recyclerView_Notes)
        val adapter = LineAdapter(this)
        recyclerLine.adapter = adapter
        recyclerLine.layoutManager = LinearLayoutManager(this)


        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.allNotes.observe(this, {notes ->
            notes?.let { adapter.setNotes(it) }
        })


        //Criar uma nota local
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

            val intent = Intent(this@MainActivity, CreateNote::class.java)

            startActivityForResult(intent, newWordActivityRequestCode)
        }





    }





    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)




        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
//            data?.getStringExtra(CreateNote.EXTRA_REPLY)?.let {
//                val note = Notes(title=it, description = "", noteText = "", dateTime = "")
//                noteViewModel.insert(note)
            val ptitle = data?.getStringExtra(CreateNote.EXTRA_REPLY_TITLE)
            val pdescription = data?.getStringExtra(CreateNote.EXTRA_REPLY_DESCRIPTION)
            val pdate = data?.getStringExtra(CreateNote.EXTRA_REPLY_DATE)


            if (ptitle !=null && pdescription != null){
                val note = Notes(title = ptitle, description = pdescription, dateTime = pdate, noteText = "")

                noteViewModel.insert(note)
            }

        }else{
            Toast.makeText(applicationContext, "Nota não inserida",Toast.LENGTH_SHORT).show()
        }
    }



//    fun openActivityCreateNotes(view: View) {
//        val intent = Intent(this, CreateNote::class.java).apply {
//
//        }
//        startActivity(intent)
//    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
           R.id.apagarNotas -> {
                noteViewModel.deleteAll()
               Toast.makeText(this,"Notas apagadas.", Toast.LENGTH_SHORT).show()

          true
           }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun deleteLine(view: View) {

        //noteViewModel.deleteAll()
    }
}