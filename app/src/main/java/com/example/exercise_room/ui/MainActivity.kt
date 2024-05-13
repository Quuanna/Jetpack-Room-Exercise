package com.example.exercise_room.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
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
import com.example.exercise_room.ui.AddEditActivity.Companion.getActivityIntent

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as MyApplication).repository)
    }

    private lateinit var wordListAdapter: WordListAdapter
    private lateinit var startForResult: ActivityResultLauncher<Intent>
    private lateinit var oldWord: String

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
        wordListAdapter = WordListAdapter(setItemClickListener())
        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                if (activityResult.resultCode == Activity.RESULT_OK) {
                    val intent = activityResult.data

                    intent?.getStringExtra(AddEditActivity.EXTRA_REPLY)?.let {
                        wordViewModel.insert(Word(id = null, word = it))
                    }

                    intent?.getStringExtra(AddEditActivity.EXTRA_UPDATE)?.let {
                        wordViewModel.editUpdate(oldWord, Word(word = it))
                    }
                }
            }
        callback()
    }

    private fun initView() {
        binding.apply {
            val layout = LinearLayoutManager(this@MainActivity)
            layout.orientation = LinearLayoutManager.VERTICAL
            recyclerView.apply {
                layoutManager = layout
                adapter = wordListAdapter
                addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
            }
            fab.setOnClickListener(onClickEditListener())
        }
    }

    private fun onClickEditListener() = View.OnClickListener {
        startForResult.launch(getActivityIntent(this))
    }

    private fun setItemClickListener() = object : ((Int, String?) -> Unit) {
        override fun invoke(position: Int, text: String?) {

            binding.apply {
                val slideUpAnimation =
                    AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_up)

                bottomAppBar.apply {
                    replaceMenu(R.menu.item_menu_bottom_app_bar)
                    menu?.forEach { menuItem ->
                        val iconView = bottomAppBar.findViewById<View>(menuItem.itemId)
                        iconView.startAnimation(slideUpAnimation)
                    }

                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.edit -> {
                                startForResult.launch(
                                    getActivityIntent(
                                        this@MainActivity,
                                        text.toString()
                                    )
                                )
                                oldWord = text.toString()
                                return@setOnMenuItemClickListener true
                            }

                            R.id.delete -> {
                                text?.let {
                                    wordViewModel.delete(Word(word = text))
                                }
                                return@setOnMenuItemClickListener true
                            }

//                            R.id.deleteAll -> {
//                                wordViewModel.deleteAll()
//                                return@setOnMenuItemClickListener true
//                            }

                            else -> return@setOnMenuItemClickListener false
                        }
                    };
                }
            }
        }
    }
}