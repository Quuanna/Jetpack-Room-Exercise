package com.example.exercise_room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise_room.MyApplication
import com.example.exercise_room.R
import com.example.exercise_room.ui.adapter.WordListAdapter
import com.example.exercise_room.ui.viewModel.WordViewModel
import com.example.exercise_room.ui.viewModel.WordViewModelFactory
import com.example.exercise_room.database.Word
import com.example.exercise_room.databinding.ActivityMainBinding
import com.example.exercise_room.serializable
import com.example.exercise_room.ui.AddEditActivity.Companion.EXTRA_ADD
import com.example.exercise_room.ui.AddEditActivity.Companion.EXTRA_UPDATE
import com.example.exercise_room.ui.AddEditActivity.Companion.getActivityIntent

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as MyApplication).repository)
    }

    private val recyclerView get() = binding.recyclerView
    private val bottomAppBar get() = binding.bottomAppBar

    private lateinit var wordListAdapter: WordListAdapter
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lateInitSetup {
            initView()
            setupDateObserved()
        }
    }

    private fun setupDateObserved() {
        wordViewModel.allWord.observe(this) { words ->
            if (words.isEmpty()) {
                // 空列表
                Toast.makeText(this, "空列表", Toast.LENGTH_SHORT).show()
            } else {
                wordListAdapter.submitList(words)
            }
        }
    }

    private fun lateInitSetup(callback: () -> Unit) {
        wordListAdapter = WordListAdapter(setItemClickListener())
        startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { activityResult ->

            if (activityResult.resultCode == Activity.RESULT_OK) {

                activityResult.data?.run {
                    getStringExtra(EXTRA_ADD)?.let {
                        wordViewModel.insert(Word(englishWord = it))
                    }
                    extras?.serializable<Word>(EXTRA_UPDATE)?.let {
                        wordViewModel.editUpdate(it)
                    }

                    removeExtra(EXTRA_ADD)
                    removeExtra(EXTRA_UPDATE)
                }
            }
        }
        callback()
    }

    private fun initView() {
        val layout = LinearLayoutManager(this@MainActivity)
        layout.orientation = LinearLayoutManager.VERTICAL
        recyclerView.apply {
            layoutManager = layout
            adapter = wordListAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
        }
        binding.fab.setOnClickListener { openAddEditActivity() }
    }

    private fun setItemClickListener() = object : ((Int, Word?) -> Unit) {
        override fun invoke(position: Int, dbItem: Word?) {
            val slideUpAnimation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_up)

            bottomAppBar.apply {
                replaceMenu(R.menu.item_menu_bottom_app_bar)
                menu?.forEach { menuItem ->
                    val iconView = findViewById<View>(menuItem.itemId)
                    iconView.startAnimation(slideUpAnimation)
                }
                performShow(true)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit -> {
                            openAddEditActivity(dbItem)
                            performHide(true)
                            return@setOnMenuItemClickListener true
                        }

                        R.id.remove -> {
                            val list = wordListAdapter.currentList.toMutableList()
                            val wordToDelete = list[position]
                            wordViewModel.delete(wordToDelete)
                            list.remove(wordToDelete)
                            wordListAdapter.submitList(list) // 更新列表
                            performHide(true)
                            return@setOnMenuItemClickListener true
                        }

                        R.id.delete -> {
                            wordViewModel.deleteAll()
                            wordListAdapter.submitList(arrayListOf())
                            performHide(true)
                            return@setOnMenuItemClickListener true
                        }

                        else -> return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    private fun openAddEditActivity(item: Word? = null) {
        startForResult.launch(getActivityIntent(this@MainActivity, item))
    }

}