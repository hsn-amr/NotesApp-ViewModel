package com.example.notesapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var adapter: RVNotes
    lateinit var rvMain: RecyclerView
    lateinit var noteInput: EditText
    lateinit var addNote: Button

    var allNotes = ArrayList<Note>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notesDBHelper = NotesDBHelper(applicationContext)

        allNotes = notesDBHelper.retrieveAllNotes()

        adapter = RVNotes(allNotes)
        rvMain = findViewById(R.id.rvMain)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        noteInput = findViewById(R.id.etNoteInput)
        addNote = findViewById(R.id.btnAddNote)


        addNote.setOnClickListener {
            val noteText = noteInput.text.toString()
            if(noteText.isNotEmpty()){

                val result = notesDBHelper.saveNote(Note(noteText))
                noteInput.text.clear()
                if(result != -1L){
                    Toast.makeText(this, "Note has added", Toast.LENGTH_LONG).show()
                    allNotes.add(Note(noteText))
                    rvMain.adapter!!.notifyDataSetChanged()
                    rvMain.smoothScrollToPosition(allNotes.size)
                }else{
                    Toast.makeText(this, "Note has not added, try again", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Please, Type a note", Toast.LENGTH_LONG).show()
            }
        }
    }

}