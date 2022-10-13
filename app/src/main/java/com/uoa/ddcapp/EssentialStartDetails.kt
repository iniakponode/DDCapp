package com.uoa.ddcapp

//import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
//import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.raxeltelematics.v2.sdk.TrackingApi
import com.uoa.ddcapp.databinding.FragmentEssentialStartDetailsBinding
import com.uoa.ddcapp.db.PhDSafeDriveDB
import com.uoa.ddcapp.model.VehicleEssentials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EssentialStartDetails : Fragment() {
    private val channelID="com.uoa.notificationapp.channel1"
    private lateinit var trackingApi: TrackingApi

    private lateinit var binding: FragmentEssentialStartDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentEssentialStartDetailsBinding.inflate(inflater,container,false)

//        val bundle= bundleOf("DeviceRegStatus" to binding.etOriginalMiles.text.toString())

        binding.btnEssentials.setOnClickListener {
            if (validateData()){
                insertVehicleData()
//                it.findNavController().navigate(R.id.action_essentialStartDetails2_to_home2)
            }
            else{
                Toast.makeText(activity,"Please fill all",Toast.LENGTH_LONG).show()
            }

        }
        return binding.root
    }
private fun validateData():Boolean{

    return !(TextUtils.isEmpty(binding.etVCapacity.text) || TextUtils.isEmpty(binding.etOriginalMiles.text))
}
    private fun insertVehicleData(){
        if(!binding.etOriginalMiles.text.isEmpty() && !binding.etVCapacity.text.isEmpty()){
            val miles=binding.etOriginalMiles.text.toString()
            val passengers=binding.etVCapacity.text.toString()
            val deviceToken=requireArguments().getString("deviceToken").toString()
            val vehicleEssentials= VehicleEssentials(
                miles,
                passengers,
                0,
                deviceToken
            )
            GlobalScope.launch(Dispatchers.IO) {
                PhDSafeDriveDB.getInstance(requireActivity().applicationContext)
                    .vehicleEssentialsDAO()
                    .insertVehicleEssentialData(vehicleEssentials)
            }
            Log.i("DeviceToken-Essentials",deviceToken)
            Toast.makeText(activity,"Vehicle Essentials Added Successfully",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(activity,"Please enter vehicle details",Toast.LENGTH_LONG).show()
        }
    }


}