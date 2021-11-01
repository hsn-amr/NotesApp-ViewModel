package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val vmProvider by lazy { VMProvider(application) }
    lateinit var adapter: RVNotes
    lateinit var rvMain: RecyclerView
    lateinit var noteInput: EditText
    lateinit var addNote: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vmProvider.getSize().observe(this,{
            size -> kotlin.run {
            adapter.update()
            noteInput.text.clear()
            rvMain.smoothScrollToPosition(size)
        }
        })
        rvMain = findViewById(R.id.rvMain)
        noteInput = findViewById(R.id.etNoteInput)
        addNote = findViewById(R.id.btnAddNote)

        adapter = RVNotes(vmProvider.getAllNewNotes(), this)
        rvMain.adapter = adapter
        rvMain.layoutManager = LinearLayoutManager(this)

        addNote.setOnClickListener {
            vmProvider.addNote(noteInput)
            Log.e("TAG","${vmProvider.getSize().value}")
        }
    }

}