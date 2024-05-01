package com.example.exercise_room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise_room.MyApplication
import com.example.exercise_room.ui.adapter.WordListAdapter
import com.example.exercise_room.ui.viewModel.WordViewModel
import com.example.exercise_room.ui.viewModel.WordViewModelFactory
import com.example.exercise_room.database.Word
import com.example.exercise_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as MyApplication).repository)
    }

    private lateinit var wordListAdapter: WordListAdapter
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lateInitSetup {
            initView()
            setupObserved()
        }
    }

    private fun setupObserved() {
        wordViewModel.allWord.observe(this) { words ->
            wordListAdapter.submitList(words)
        }
    }

    private fun lateInitSetup(callback: () -> Unit) {
        wordListAdapter = WordListAdapter()
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val intent = activityResult.data
                    intent?.getStringExtra(AddEditActivity.EXTRA_REPLY)?.let {
                        val word = Word(it)
                        wordViewModel.insert(word)
                    }
                }
            }
        callback()
    }

    private fun initView() {
        wordListAdapter = WordListAdapter()
        val layout = LinearLayoutManager(this)
        layout.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerview.apply {
            layoutManager = layout
            adapter = wordListAdapter
        }
        binding.fab.setOnClickListener(onClickEditListener())
    }

    private fun onClickEditListener() = View.OnClickListener {
        startForResult.launch(Intent(this, AddEditActivity::class.java))
    }
}