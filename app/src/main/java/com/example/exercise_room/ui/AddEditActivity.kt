package com.example.exercise_room.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.exercise_room.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddEditBinding.inflate(layoutInflater) }
    private val editWordView get() = binding.editWord

    companion object {
        const val EXTRA_REPLY = "REPLY_INTENT"
        const val EXTRA_UPDATE = "EXTRA_UPDATE"
        const val INTENT_KEY = "INTENT_KEY"

        fun getActivityIntent(context: Context, text: String? = null): Intent {
            return Intent(context, AddEditActivity::class.java).putExtra(INTENT_KEY, text)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.getStringExtra(INTENT_KEY)?.let {
            editWordView.setText(it)
            binding.buttonSave.isVisible = false
            binding.buttonUpdate.isVisible = true
        }?: {
            binding.buttonSave.isVisible = true
            binding.buttonUpdate.isVisible = false
        }

        binding.buttonSave.setOnClickListener(onClickListener(EXTRA_REPLY))
        binding.buttonUpdate.setOnClickListener(onClickListener(EXTRA_UPDATE))
    }

    private fun onClickListener(flag: String) = View.OnClickListener {
        val replyIntent = Intent()
        if (editWordView.text.isEmpty()) {
            setResult(Activity.RESULT_CANCELED, replyIntent)
        } else {
            replyIntent.putExtra(flag, editWordView.text.toString())
            setResult(Activity.RESULT_OK, replyIntent)
        }
        finish()
    }
}