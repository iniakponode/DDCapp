package com.uoa.ddcapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
//import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.telematicssdk.auth.TelematicsAuth
import com.telematicssdk.auth.api.model.Gender
import com.telematicssdk.auth.api.model.MaritalStatus
import com.uoa.ddcapp.databinding.FragmentRegisterDeviceBinding
import com.uoa.ddcapp.db.PhDSafeDriveDB
import com.uoa.ddcapp.model.LocalUserData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterDevice : Fragment() {
private lateinit var binding: FragmentRegisterDeviceBinding
private val INSTANCE_ID="abe10ef5-0cfb-4e3a-9702-859a96ec5abc"
private val INSTANCE_KEY="839f0179-79b8-48c8-9a75-e55ba7b64aaf"
private lateinit var sf: SharedPreferences
private lateinit var editor : SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sf = activity?.getSharedPreferences(getString(R.string.deviceToken), AppCompatActivity.MODE_PRIVATE)!!
        editor = sf.edit()
        binding=FragmentRegisterDeviceBinding.inflate(layoutInflater,container,false)
        createToken()

        return binding.root
    }

    private fun createToken(){
        TelematicsAuth.createDeviceToken(INSTANCE_ID,INSTANCE_KEY)
            .onSuccess{
                Log.i("DeviceToken-RegisterD",it.deviceToken)
//                Store Token into device shared preference (Phone memory accessible only to app)
                storeTokenLocally(it.deviceToken,editor)

//                insert device token into our database
                insertData(it.deviceToken)

//                assign device token to a bundle that can be passed to the next fragment to enable sdk
                val  bundle= bundleOf("deviceToken" to it.deviceToken)

//                Move app to the next fragment (Permissions fragment)
                findNavController().navigate(R.id.action_registerDevice_to_permissionsFragment,bundle)

            }
            .onError{
                    e->e.printStackTrace()
            }
    }

//    Function to add device data to our database
    private fun insertData(deviceToken: String){

//    if device token is not null prepare and assign it to localUserDate variable to be added
//    to database
        if(deviceToken!=""){
            val localUserData= LocalUserData(
                "",
                "",
                CurrentDate.currentDateTime(),
                0,
                deviceToken
            )

//            Launch a global thread to add device token to database
            GlobalScope.launch(Dispatchers.IO) {

                PhDSafeDriveDB.getInstance(requireActivity().applicationContext)
                    .localUserDataInterfaceDAO()
                    .insertLocalUserData(localUserData)
            }
            Toast.makeText(activity,"Token Created and added Successfully",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(activity,"Sorry, Token could not be created",Toast.LENGTH_LONG).show()
        }
    }

//    Store device token on shared preference
private fun storeTokenLocally(deviceToken: String, edtor:SharedPreferences.Editor){

    edtor.apply {
        putString(getString(R.string.deviceToken), deviceToken)
        commit()
    }


}
    fun login(deviceToken:String){
        TelematicsAuth.login(
            INSTANCE_ID,
            INSTANCE_KEY,
            deviceToken
        )
            .onSuccess{
                it.accessToken
                it.refreshToken
            }
            .onError{

            }
    }

    fun getUser(accessToken:String){
        TelematicsAuth.getUserProfile(
            INSTANCE_ID,
            INSTANCE_KEY,
            accessToken
        )
            .onSuccess{
                it.firstName
            }
            .onError{

            }
    }
    fun updateUser(deviceToken: String, accessToken: String){
        TelematicsAuth.updateUserProfile(
            INSTANCE_ID,
            INSTANCE_ID,
            accessToken,
            deviceToken,
            "iniakpothompson@gmail.com",
            "07045598128",
            "abe10ef5-0cfb-4e3a-9702-859a96ec5abc",
            "Test",
            "User",
            "1988-08-07",//format:yyy-mm-dd'T':HH:mm:ss
        MaritalStatus.Divorced,
            0,
            "Yenagoa",
            Gender.Male


        )
            .onSuccess{

            }
            .onError{

            }
    }

    fun refreshToken(accessToken: String, refreshToken:String){
            TelematicsAuth.refreshToken(
                                INSTANCE_ID,
                                INSTANCE_ID,
                                accessToken,
                                refreshToken
                            ).onSuccess{
                            it.refreshToken
                        }
                        .onError{

                        }
    }


}