package ipvc.estg.projetofinal.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import ipvc.estg.projetofinal.database.NoteRepository
import ipvc.estg.projetofinal.database.NotesDatabase
import ipvc.estg.projetofinal.entities.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    //ViewModel -> repository
    private val repository: NoteRepository


    val allNotes: LiveData<List<Notes>>

    init {
        val noteDao = NotesDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes
    }

    fun insert(notes: Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(notes)
    }
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun update(id: Int?, titulo: String, descricao: String, noteText: String) = viewModelScope.launch{
        repository.update(id, titulo, descricao, noteText)
    }

    fun delete(id: Int?) = viewModelScope.launch{
        repository.delete(id)
    }


}