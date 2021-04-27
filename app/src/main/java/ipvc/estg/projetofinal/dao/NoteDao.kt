package ipvc.estg.projetofinal.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ipvc.estg.projetofinal.entities.Notes
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes_table ORDER BY id DESC")
    fun getAlphabetizedNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAll()

    @Query("DELETE FROM notes_table WHERE id = :id")
    suspend fun delete(id: Int?)

    @Query("UPDATE notes_table SET title = :titulo, description = :descricao WHERE id = :id")
    suspend fun update(id: Int?, titulo: String, descricao: String)


}