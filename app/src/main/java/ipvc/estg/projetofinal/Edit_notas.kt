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
        val text = intent.getStringExtra(PARAM3_TEXT)

        editTituloView.setText(title.toString())
        editDescriptionView.setText(descricao.toString())
        editTextView.setText(text.toString())

        //APAGAR NOTA
        val deleteNote = findViewById<Button>(R.id.button_delete)
        deleteNote.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTituloView.text)  || TextUtils.isEmpty(editDescriptionView.text) || TextUtils.isEmpty(editTextView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(STATUS, "DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }

        //EDITAR NOTA
        val button = findViewById<Button>(R.id.edit_button)
        button.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(ID_EDIT, id)
            if (TextUtils.isEmpty(editTituloView.text)  || TextUtils.isEmpty(editDescriptionView.text) ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val edit_titulo = editTituloView.text.toString()
                replyIntent.putExtra(EDIT_TITLE, edit_titulo)

                val edit_description = editDescriptionView.text.toString()
                replyIntent.putExtra(EDIT_DESCRIPTION, edit_description)

                val edit_text = editTextView.text.toString()
                replyIntent.putExtra(TEXT_EDIT,edit_text)

                replyIntent.putExtra(STATUS, "EDIT")
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
        const val EDIT_TITLE = "TITLE_EDIT"
        const val EDIT_DESCRIPTION = "DESCRIPTION_EDIT"
        const val ID_EDIT = "ID_EDIT"
        const val TEXT_EDIT ="TEXT_EDIT"
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"



    }
}