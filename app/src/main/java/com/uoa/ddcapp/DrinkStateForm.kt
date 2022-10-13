package com.uoa.ddcapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.uoa.ddcapp.databinding.FragmentDrinkStateFormBinding
import kotlin.random.Random


class DrinkStateForm : Fragment() {
    private lateinit var sf:SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private  lateinit var binding: FragmentDrinkStateFormBinding
    private lateinit var bundle: Bundle
    private lateinit var intent: Intent

       override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
           sf = activity?.getSharedPreferences(getString(R.string.deviceToken), AppCompatActivity.MODE_PRIVATE)!!
           editor = sf.edit()
        // Inflate the layout for this fragment
           binding=FragmentDrinkStateFormBinding.inflate(inflater,container,false)
//           binding.tvFileStoredDeviceTokendrinkState.text="deviceToken"

           val dToken= StoredToken.getStoredDeviceToken(sf,getString(R.string.deviceToken))

           binding.tvFileStoredDeviceTokendrinkState.text=dToken.toString()
           Log.i("Device-TokenSaved",dToken.toString())
           val tripId=Random.nextInt().toString()
           binding.btnDrinkForm.setOnClickListener {

                       Log.i("RandomString",tripId)
               if(validateData()) {
                   val drinkStatus = selectedStatus()
                   if (drinkStatus==1){
                       Log.i("SelectedStat",drinkStatus.toString())
                       val bundle= bundleOf("drinkStatus" to drinkStatus,"tripId" to tripId)
//                       insertData(dToken,drinkStatus,tripId)
//                       it.findNavController().navigate(R.id.action_drinkStateForm_to_numberOfBottles,bundle)
                   }
                   else{
                       Log.i("SelectedStat",drinkStatus.toString())
                        intent = Intent(activity, CameraActivity::class.java)
                        intent.putExtra("SelectedStat",drinkStatus.toString())
                        requireActivity().startActivity(intent)
                       val bundle= bundleOf("drinkStatus" to drinkStatus, "tripId" to tripId)
//                       it.findNavController().navigate(R.id.action_drinkStateForm_to_cameraActivity,bundle)
                   }

               }
               else{
                   Toast.makeText(activity,"Please Click Yes or No, to answer the question",Toast.LENGTH_LONG).show()
               }
//
           }

        return binding.root
    }
    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }
//    private fun insertData(deviceToken: String,drinkStatus:Int,tripId:String){
//
//        if(deviceToken!=""){
//            val trip= Trip(
//                deviceToken,
//                tripId,
//                "",
//                "",
//                drinkStatus,
//                0,
//                CurrentDate.currentDateTime()
//
//
//            )
//            GlobalScope.launch(Dispatchers.IO) {
//                PhDSafeDriveDB.getInstance(requireActivity().applicationContext)
//                    .tripDAO()
//                    .insertTrip(trip)
//            }
//            Toast.makeText(activity,"Trip Start Values added Successfully", Toast.LENGTH_LONG).show()
//        }
//        else{
//            Toast.makeText(activity,"Sorry, Start Values could not be added", Toast.LENGTH_LONG).show()
//        }
//    }

    private fun selectedStatus():Int{
        val checkedStatus=binding.radioGroup.checkedRadioButtonId
//        Log.i("SelectedRadio",checkedStatus.toString())

        var drinkStatus=0
        if (checkedStatus.equals(binding.rBYes.id)){
//            Log.i("SelectedYes",checkedStatus.equals(binding.rBYes.id).toString())
            drinkStatus=1
        }
        else{
//            Log.i("SelectedNo",checkedStatus.equals(binding.rBYes.id).toString())
            drinkStatus=0
        }
//        Log.i("SelecteddrinkStatus",drinkStatus.toString())
        return drinkStatus
    }

    private fun validateData():Boolean{
        val checkedStatus=binding.radioGroup.checkedRadioButtonId
        return (binding.radioGroup.checkedRadioButtonId.equals(binding.rBYes.id)||binding.radioGroup.checkedRadioButtonId.equals(binding.rBNo.id))
    }


}