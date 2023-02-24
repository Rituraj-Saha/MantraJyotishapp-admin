package com.technologiagroup.adminappmantra.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.technologiagroup.adminappmantra.databinding.ActivityMainBinding
import com.technologiagroup.adminappmantra.util.Logger
import com.technologiyagroup.matrajayotish.adaptor.RecyclerViewAdaptor
import com.technologiyagroup.matrajayotish.model.user.NetworkResult
import com.technologiyagroup.matrajayotish.model.user.ResponseBody
import com.technologiyagroup.matrajayotish.viewModel.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity()  {
    lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels()

    private lateinit var scrollListener: RecyclerView.OnScrollListener
    var mList: ArrayList<ResponseBody> = ArrayList<ResponseBody>()
    var pageCount = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linearLayoutManager =  LinearLayoutManager(this)
        binding.recyclEndless.layoutManager = linearLayoutManager
        binding.recyclEndless.setHasFixedSize(true)
        val adapter = RecyclerViewAdaptor(mList,this)
        binding.recyclEndless.adapter = adapter
        var isItemAllLoaded = false

        userViewModel.userResponse.observe(this){
            when(it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading
                    Logger.log("userNetwork","in loading..")
                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false

                    Logger.log("userNetwork","failed"+it.errorMessage)
                    Toast.makeText(this,"Error occured", Toast.LENGTH_LONG).show()
                }

                is  NetworkResult.Success -> {

                    binding.progressbar.isVisible = false

                    if(it.data.responseCode.equals("200"))
                    {

                        if(it.data.responseBody.size==0)
                        {
                            isItemAllLoaded = true

                        }

                        if(it.data.responseBody.size>0){
                             mList.addAll(it.data.responseBody)
                        }

                        binding.recyclEndless.adapter!!.notifyItemInserted(linearLayoutManager.itemCount)

                        binding.recyclEndless.setOnScrollChangeListener { view, i, i2, i3, i4 ->
                            if(i4<0)
                            {
                                if(getLastItem())
                                {
                                    pageCount++
                                    Logger.log("rcycl",getLastItem().toString()+" "+pageCount)
                                    if(!isItemAllLoaded)
                                    lifecycleScope.launch {
                                        userViewModel.getAllUser(pageCount.toString(),"9");
                                    }

                                }
                            }
                        }
                    }
                    else{
                        Toast.makeText(this,"Error occured", Toast.LENGTH_LONG)
                        Logger.log("userNetwork","Error occurerd")
                    }
                }
            }
        }

        binding.addUserFab.setOnClickListener {
            val intent = Intent(this,CreateUser::class.java)
            startActivity(intent)
            finish()
        }

    }
//    fun isLastVisible(mRecyclerView:RecyclerView): Boolean {
//
//    }
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            userViewModel.getAllUser("0","9");
        }

    }

     fun getLastItem(): Boolean {
        val layoutManager = binding.recyclEndless.getLayoutManager() as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems: Int = binding.recyclEndless.getAdapter()!!.getItemCount()
        return pos >= (numItems-1)
    }


}