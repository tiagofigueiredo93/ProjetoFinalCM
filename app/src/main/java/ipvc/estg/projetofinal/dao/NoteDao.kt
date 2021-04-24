package ipvc.estg.projetofinal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.projetofinal.entities.Notes

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAlphabetizedNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    @Delete
    fun deleteNote(note: Notes)

}