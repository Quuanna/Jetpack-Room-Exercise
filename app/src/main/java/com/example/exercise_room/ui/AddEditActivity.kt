package com.example.exercise_room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exercise_room.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddEditBinding.inflate(layoutInflater)}
    private val editWordView get() = binding.editWord
    companion object {
        const val EXTRA_REPLY = "REPLY_INTENT"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (editWordView.text.isEmpty()) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY, editWordView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }
}