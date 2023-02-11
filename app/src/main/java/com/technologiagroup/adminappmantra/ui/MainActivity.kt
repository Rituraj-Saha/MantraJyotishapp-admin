package com.technologiagroup.adminappmantra.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.technologiagroup.adminappmantra.databinding.ActivityMainBinding
import com.technologiagroup.adminappmantra.util.Logger
import com.technologiyagroup.matrajayotish.model.user.NetworkResult
import com.technologiyagroup.matrajayotish.viewModel.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.userResponse.observe(this){
            when(it) {
                is NetworkResult.Loading -> {
//                    binding.progressbar.isVisible = it.isLoading
                    Logger.log("userNetwork","in loading..")
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
//                    binding.progressbar.isVisible = false

                    Logger.log("userNetwork","failed"+it.errorMessage)
                    Toast.makeText(this,"Error occured", Toast.LENGTH_LONG).show()
                }

                is  NetworkResult.Success -> {
//                    movieAdapter.updateMovies(it.data)
//                    binding.progressbar.isVisible = false

                    if(it.data.responseCode.equals("200"))
                    {
                        Logger.log("userNetwork",it.data.responseBody.get(0).user.toString())
                    }
                    else{
                        Toast.makeText(this,"Error occured", Toast.LENGTH_LONG)
                        Logger.log("userNetwork","Error occurerd")
                    }
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            userViewModel.getAllUser("0","2");
        }

    }
}