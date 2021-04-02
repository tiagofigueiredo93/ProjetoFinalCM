package ipvc.estg.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import ipvc.estg.projetofinal.entities.Notes
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CreateNote : AppCompatActivity() {

    private lateinit var myNotes: ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())


        findViewById<TextView>(R.id.dateTime).text = currentDate



    }

    fun saveNote(view: View) {
        val insertTitle = findViewById<EditText>(R.id.insertTite)
        val insertDescription = findViewById<EditText>(R.id.insertDescription)
        val insertText = findViewById<EditText>(R.id.insertText)

        if (insertTitle.text.isNullOrEmpty()){
            Toast.makeText(this, "Title is Required", Toast.LENGTH_SHORT).show()
        }
        if (insertDescription.text.isNullOrEmpty()){
            Toast.makeText(this, "Description is Required", Toast.LENGTH_SHORT).show()
        }

        if (insertText.text.isNullOrEmpty()){
            Toast.makeText(this, "Insert note text is Required", Toast.LENGTH_SHORT).show()

        }

    }

}