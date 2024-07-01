package com.example.exercise_room.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.exercise_room.database.Word
import com.example.exercise_room.databinding.ActivityAddEditBinding
import com.example.exercise_room.serializable

class AddEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddEditBinding.inflate(layoutInflater) }
    private val editWordView get() = binding.editWord
    private var dbItemData: Word? = null

    companion object {
        const val EXTRA_ADD = "EXTRA_ADD"
        const val EXTRA_UPDATE = "EXTRA_UPDATE"
        const val INTENT_KEY = "INTENT_KEY"

        fun getActivityIntent(context: Context, item: Word?): Intent {
            return Intent(context, AddEditActivity::class.java).putExtra(INTENT_KEY, item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        intent.extras?.serializable<Word>(INTENT_KEY)?.let {
            dbItemData = it
        }
        intent.removeExtra(INTENT_KEY)

        initView()
    }

    private fun initView() {
        dbItemData?.let {
            editWordView.setText(it.englishWord)
            setVisibility(true)
        } ?: setVisibility(false)
        binding.buttonSave.setOnClickListener(onClickListener(EXTRA_ADD))
        binding.buttonUpdate.setOnClickListener(onClickListener(EXTRA_UPDATE))
    }

    private fun onClickListener(flag: String) = View.OnClickListener {

        val intent = Intent()
        when {
            editWordView.text.isEmpty() -> setResult(Activity.RESULT_CANCELED, intent)
            dbItemData != null -> {
                intent.putExtra(flag, Word(dbItemData?.id ?:0, editWordView.text.toString()))
                setResult(Activity.RESULT_OK, intent)
            }
            else -> {
                intent.putExtra(flag, editWordView.text.toString())
                setResult(Activity.RESULT_OK, intent)
            }
        }
        finish()
    }

    private fun setVisibility(isUpdate: Boolean) {
        if (isUpdate) {
            binding.buttonSave.isVisible = false
            binding.buttonUpdate.isVisible = true
        } else {
            binding.buttonSave.isVisible = true
            binding.buttonUpdate.isVisible = false
        }
    }
}