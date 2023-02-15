package com.technologiagroup.adminappmantra.ui

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.technologiagroup.adminappmantra.databinding.ActivityCreateUserBinding
import com.technologiagroup.adminappmantra.model.user.StartInfo
import com.technologiagroup.adminappmantra.util.Logger
import com.technologiyagroup.matrajayotish.model.user.NetworkResult
import com.technologiyagroup.matrajayotish.model.user.User
import com.technologiyagroup.matrajayotish.viewModel.user.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreateUser : AppCompatActivity() {
    lateinit var binding: ActivityCreateUserBinding
    private val userViewModel: UserViewModel by viewModels()
    var selectedStarInfo = StartInfo(null,"");
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pStatus = arrayOf<String>("paid","not paid")


        // access the spinner
        if (binding.spinnerPayment != null) {
            val adapter = ArrayAdapter(this,
                R.layout.simple_spinner_item, pStatus)
            binding.spinnerPayment.adapter = adapter

            binding.spinnerPayment.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
//                    Toast.makeText(this@CreateUser, "" + pStatus[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        userViewModel.starResponse.observe(this){
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
                        // access the spinner
                        val spinner = binding.spinner
                        val stars =  it.data.responseBody
                        var mStars = ArrayList<String>()
                        for(i in stars)
                        {
                            mStars.add(i.starInfo.star_name)
                        }
                        if (spinner != null) {
                            val adapter = ArrayAdapter(this,
                                R.layout.simple_spinner_item,mStars)
                            spinner.adapter = adapter
                            spinner.onItemSelectedListener = object :
                                AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>,
                                                            view: View, position: Int, id: Long) {
//                                    Toast.makeText(this@CreateUser, "" + stars[position].starInfo.star_name, Toast.LENGTH_SHORT).show()
                                selectedStarInfo = stars[position].starInfo

                                }

                                override fun onNothingSelected(parent: AdapterView<*>) {
                                    // write code to perform some action
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


        binding.btnSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val dob = binding.etDob.text.toString()
            val tob = binding.etTob.text.toString()
            val pob = binding.etPob.text.toString()
            val payAmtStat = binding.spinnerPayment.selectedItem.toString()
            val pAmt = binding.etPamt.text.toString()

            val starInfo = selectedStarInfo
            val starId = starInfo.id.toString()
            if(validator(name,phone,dob,tob,pob,payAmtStat,pAmt, starId))
            {
                //call create User
                val c: Date = Calendar.getInstance().getTime()
                val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                val formattedDate: String = df.format(c)
                val newUser = User(null, name, phone, dob, tob, pob,formattedDate,payAmtStat,pAmt,formattedDate,starId,"Logged in")
                Logger.log("newuSer",newUser.toString())
                lifecycleScope.launch {
                    userViewModel.createUser(newUser)
                }

            }
            else
            {
                Toast.makeText(this@CreateUser,"Some value is missed",Toast.LENGTH_LONG).show()
            }
        }


        userViewModel.userResponse2.observe(this){
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
                      val intent = Intent(this@CreateUser,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Error occured", Toast.LENGTH_LONG)
                        Logger.log("userNetwork","Error occurerd")
                    }
                }
            }
        }

    }

    fun validator(name:String,phone:String,dob:String,tob:String,pob:String,pAmtStat:String,pAmt:String,starId:String):Boolean{
        if(name.equals("")){
            return false
        }
        if(phone.equals(""))
        {
            return false
        }
        if (dob.equals(""))
        {
            return false
        }
        if(tob.equals(""))
        {
            return false
        }
        if(pob.equals("")){
            return false
        }
        if(pAmtStat.equals(""))
        {
            return false
        }
        if(pAmt.equals(""))
        {
            return false
        }
        if(starId.equals(""))
        {
            return false
        }
        return true
    }
    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
           userViewModel.getAllStars()
        }

    }
}