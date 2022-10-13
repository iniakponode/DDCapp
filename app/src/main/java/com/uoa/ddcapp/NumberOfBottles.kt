package com.uoa.ddcapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.uoa.ddcapp.databinding.FragmentNumberOfBottlesBinding
import com.uoa.ddcapp.db.PhDSafeDriveDB
import com.uoa.ddcapp.model.Trip
//import com.uoa.phdapp.databinding.FragmentNumberOfBottlesBinding

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


private lateinit var binding: FragmentNumberOfBottlesBinding
class NumberOfBottles : Fragment() {
    private lateinit var sf: SharedPreferences
    private lateinit var editor : SharedPreferences.Editor
    private var drinkStatus:Int=0
    private  var tripId: String?=null
    private var numbBottles:Int=0
    private lateinit var intent:Intent
    private lateinit var dToken:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sf = activity?.getSharedPreferences(getString(R.string.deviceToken), AppCompatActivity.MODE_PRIVATE)!!

        dToken=StoredToken.getStoredDeviceToken(sf,getString(R.string.deviceToken))
        // Inflate the layout for this fragment
        binding =FragmentNumberOfBottlesBinding.inflate(inflater,container,false)
        binding.tvFileStoredDeviceToken.text=dToken.toString()
//        Log.i("Device-TokenSaved",dToken.toString())
         drinkStatus=requireArguments().getInt("drinkStatus")
        tripId= requireArguments().getString("tripId")!!.toString()

        val bundle= bundleOf("drinkStatus" to drinkStatus, "tripId" to tripId)
/* Log.i("SelectedDrink",drinkStatus.toString()) */


        binding.btnNoBottles.setOnClickListener {
            if (validateData()){
                numbBottles= Integer.parseInt(binding.etNoBottles.text.toString())
                insertTripData(dToken,drinkStatus,numbBottles, tripId!!)
                intent = Intent(activity, CameraActivity::class.java)
                intent.putExtra("tripId",tripId)
                requireActivity().startActivity(intent)
//                passData()

//                findNavController().navigate(R.id.action_numberOfBottles_to_cameraActivity,bundle)
            }
            else{
                Toast.makeText(activity,"Please Enter quantity Taken",Toast.LENGTH_LONG).show()
            }


        }
        return binding.root
    }
    private fun passData(){
//        dToken=StoredToken.getStoredDeviceToken(sf,getString(R.string.deviceToken))
        intent = Intent(activity, CameraActivity::class.java)
        intent.putExtra("SelectedStat",drinkStatus.toString())
        intent.putExtra("Token",dToken)
        intent.putExtra("tripId",tripId)
        intent.putExtra("noOfDrinks", Integer.parseInt(binding.etNoBottles.text.toString()))
        requireActivity().startActivity(intent)
    }

    private fun insertTripData(deviceToken: String, drinkStatus:Int, noOfDrinks:Int, tripId:String){

        if(deviceToken!=""){
            val trip= Trip(
                deviceToken,
                tripId,
                "",
                "",
                drinkStatus,
                noOfDrinks,
                CurrentDate.currentDateTime()


            )
            GlobalScope.launch(Dispatchers.IO) {
                PhDSafeDriveDB.getInstance(requireActivity().applicationContext)
                    .tripDAO()
                    .insertTrip(trip)
            }
            Toast.makeText(activity,"Trip Start Values added Successfully", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(activity,"Sorry, Start Values could not be added", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateData():Boolean{

        return !(TextUtils.isEmpty(binding.etNoBottles.text))
    }

}