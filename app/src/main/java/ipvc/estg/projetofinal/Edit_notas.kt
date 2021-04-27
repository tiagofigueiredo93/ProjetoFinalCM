package ipvc.estg.projetofinal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import ipvc.estg.projetofinal.CreateNote.Companion.EXTRA_REPLY_TEXT

class Edit_notas : AppCompatActivity() {
    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_notas)

        var editTituloView: EditText = findViewById(R.id.edit_title)
        var editDescriptionView: EditText = findViewById(R.id.edit_description)
        var editTextView: EditText = findViewById(R.id.edit_text)

        var id = intent.getStringExtra(PARAM_ID)
        var title = intent.getStringExtra(PARAM1_TITULO)
        var descricao = intent.getStringExtra(PARAM2_DESCRICAO)
        val text = intent.getStringExtra(EXTRA_REPLY_TEXT)

        editTituloView.setText(title.toString())
        editDescriptionView.setText(descricao.toString())
        editTextView.setText(text.toString())
        //Apagar nota
        val deleteNote = findViewById<Button>(R.id.button_delete)
        deleteNote.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTituloView.text)  || TextUtils.isEmpty(editDescriptionView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(STATUS, "DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }
        val buttonBack = findViewById<ImageView>(R.id.img_back)

        buttonBack.setOnClickListener {
            val intent = Intent(this@Edit_notas, MainActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }
    companion object {
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"

    }
}