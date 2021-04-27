package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.projetofinal.CreateNote.Companion.EXTRA_REPLY_TEXT
import ipvc.estg.projetofinal.adapter.LineAdapter
import ipvc.estg.projetofinal.entities.Notes
import ipvc.estg.projetofinal.viewModel.NoteViewModel
import java.util.*
const val PARAM_ID: String = "id"
const val PARAM1_TITULO: String = "titulo"
const val PARAM2_DESCRICAO: String = "descricao"
const val PARAM3_TEXT: String = "text"
class MainActivity : AppCompatActivity(), CellClickListener {

    private lateinit var noteViewModel : NoteViewModel
    private val newWordActivityRequestCode = 1
    private val newNotasActivityRequestCode2 = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("***TAG", "onCreate")

        val recyclerLine = findViewById<RecyclerView>(R.id.recyclerView_Notes)
        val adapter = LineAdapter(this, this)
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


    override fun onCellClickListener(data: Notes) {
        val intent = Intent(this@MainActivity, Edit_notas::class.java)
        intent.putExtra(PARAM_ID, data.id.toString())
        intent.putExtra(PARAM1_TITULO, data.title.toString())
        intent.putExtra(PARAM2_DESCRICAO, data.description.toString())
        intent.putExtra(EXTRA_REPLY_TEXT, data.noteText.toString())
        startActivityForResult(intent, newNotasActivityRequestCode2)
        Log.e("***ID", data.id.toString())
        Log.e("***Text", data.noteText.toString())
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Adicionar Notas
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {

            val ptitle = data?.getStringExtra(CreateNote.EXTRA_REPLY_TITLE)
            val pdescription = data?.getStringExtra(CreateNote.EXTRA_REPLY_DESCRIPTION)
            val pdate = data?.getStringExtra(CreateNote.EXTRA_REPLY_DATE)
            val ptext = data?.getStringExtra(CreateNote.EXTRA_REPLY_TEXT)

            if (ptitle !=null && pdescription != null){
                val note = Notes(title = ptitle, description = pdescription, dateTime = pdate, noteText = "")

                noteViewModel.insert(note)
            }

        }else{
            Toast.makeText(applicationContext, "Nota não inserida",Toast.LENGTH_SHORT).show()
        }



        //EDITAR E APAGAR NOTA
        if (requestCode == newNotasActivityRequestCode2 && resultCode == Activity.RESULT_OK) {
            var id = data?.getStringExtra(Edit_notas.ID_EDIT)
            var edit_titulo = data?.getStringExtra(Edit_notas.EDIT_TITLE).toString()
            var edit_observacao = data?.getStringExtra(Edit_notas.EDIT_DESCRIPTION).toString()
            var id_delete = data?.getStringExtra(Edit_notas.DELETE_ID)
              if(data?.getStringExtra(Edit_notas.STATUS) == "DELETE"){
                noteViewModel.delete(id_delete?.toIntOrNull())

            }else if(data?.getStringExtra(Edit_notas.STATUS) == "EDIT"){
                  noteViewModel.update(id?.toIntOrNull(), edit_titulo, edit_observacao)
                  Toast.makeText(this, "Nota editada!", Toast.LENGTH_SHORT).show()
              }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            if(data?.getStringExtra(Edit_notas.STATUS) == "EDIT"){
                Toast.makeText(this, "Nota não editada, campos vazios!", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(Edit_notas.STATUS) == "DELETE"){
                Toast.makeText(this, "Nota não editada, campos vazios!", Toast.LENGTH_SHORT).show()
            }
        }
    }





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




}