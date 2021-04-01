package ipvc.estg.projetofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.EditText
import ipvc.estg.projetofinal.dataClasses.Notes

    const val PARAM1_TITLE = "title"
    const val PARAM2_DESCRIPTION = "description"
class CreateNote : AppCompatActivity() {

    private lateinit var myNotes: ArrayList<Notes>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

    }

    fun saveNote(view: View) {

        myNotes = ArrayList<Notes>()

        val intent = Intent(this, MainActivity::class.java).apply {
            val titleInsert = findViewById<EditText>(R.id.insertTite)
            val descriptionInsert = findViewById<EditText>(R.id.insertDescription)

            putExtra(PARAM1_TITLE, titleInsert.text.toString())
            putExtra(PARAM2_DESCRIPTION, descriptionInsert.text.toString())
        }
        startActivity(intent)

    }
}