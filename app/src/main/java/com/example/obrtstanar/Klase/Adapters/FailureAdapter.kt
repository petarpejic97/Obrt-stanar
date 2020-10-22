package com.example.obrtstanar.Klase.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.obrtstanar.Fragmenti.Listener.FailureListener
import com.example.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.failure_item.view.*


class FailureAdapter(failures : MutableList<FailureWithId>, failureListener : FailureListener): RecyclerView.Adapter<FailureViewHolder>() {

    var failures : MutableList<FailureWithId> = mutableListOf()
    var failureListener : FailureListener
    var check = 0
    init {
        Log.w("PPP","PPPP")
        this.failures = failures
        this.failureListener = failureListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FailureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.failure_item, parent,false)

        return FailureViewHolder(view)
    }

    override fun getItemCount(): Int = failures.size

    override fun onBindViewHolder(holder: FailureViewHolder, position: Int) {
        val failure : FailureWithId = failures[position]
        holder.bind(failure,failureListener)

    }
    fun refreshData() {
        this.failures.clear()
        this.failures.addAll(failures)
        this.notifyDataSetChanged()
    }
}
class FailureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnItemSelectedListener {

    var imgTypeOfFailure : ImageView
    var imgRemove : ImageView
    var tvName : TextView
    var tvLastname : TextView
    var tvAddress : TextView
    var tvPhoneNumber : TextView
    var tvRepairTime : TextView
    var tvRepairState : TextView
    var tvFailureDescription : TextView
    var tvTypeOfFailure : TextView
    var tvFailureId : TextView
    var spinnerFailureState : Spinner
    lateinit var failureListener : FailureListener
    private var preferenceManager: PreferenceManager = PreferenceManager()

    var check = 0

    init {
        imgTypeOfFailure = itemView.findViewById(R.id.imgTypeOfFailure)
        imgRemove = itemView.findViewById(R.id.imgRemove)
        tvFailureId = itemView.findViewById(R.id.failureId)
        tvName = itemView.findViewById(R.id.tvName)
        tvLastname = itemView.findViewById(R.id.tvLastname)
        tvAddress = itemView.findViewById(R.id.tvAddress)
        tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber)
        tvRepairTime = itemView.findViewById(R.id.tvRepairTime)
        tvRepairState =itemView.findViewById(R.id.tvRepairState)
        tvFailureDescription = itemView.findViewById(R.id.tvFailureDescription)
        tvTypeOfFailure = itemView.findViewById(R.id.tvTypeOfFailure)

        spinnerFailureState = itemView.findViewById(R.id.spinnerFailureState)
        spinnerFailureState.onItemSelectedListener = this
        ArrayAdapter.createFromResource(ObrtStanar.ApplicationContext, R.array.adminState, R.layout.spinner_admin_item).also {
                adapterFailureState -> adapterFailureState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFailureState.adapter = adapterFailureState
        }


        if (preferenceManager.getLoggedEmail() == "petar.pejic@outlook.com"){
            spinnerFailureState.visibility=View.VISIBLE
            imgRemove.visibility = View.VISIBLE
            tvRepairState.visibility = View.INVISIBLE
        }
    }
    fun bind(failure : FailureWithId,failureListener : FailureListener){

        this.failureListener = failureListener

        itemView.imgTypeOfFailure.setImageResource(setFailureIcon(failure))
        itemView.failureId.text = failure.id
        itemView.tvName.text = failure.name
        itemView.tvLastname.text = failure.lastName
        itemView.tvAddress.text = failure.address
        itemView.tvPhoneNumber.text = failure.phoneNumber
        itemView.imgBulletState.setImageResource(setStateIcon(failure.repairState))
        itemView.tvRepairTime.text = failure.repairTime
        itemView.tvRepairState.text = failure.repairState
        itemView.tvFailureDescription.text = failure.failureDescription
        itemView.tvTypeOfFailure.text = failure.typeOfFailure

        imgRemove.setOnClickListener { failureListener.deleteImage(failure.failureImageUri,failure.id) }

        itemView.setOnLongClickListener { failureListener.onShowDetails(failure.typeOfFailure,failure.failureImageUri) ; true; }
    }
    private fun setFailureIcon(failure : FailureWithId) : Int{
        when(failure.typeOfFailure){
            "Ostali kvarovi"->{
                return R.drawable.mehanicalfailure
            }
            "Električne instalacije"->{
                return R.drawable.electricfailure
            }
            "Vodovodne instalacije" ->{
                return R.drawable.plumbingfailure
            }
            "Oštećenja na zgradi"->{
                return R.drawable.infrastructurefailure
            }
            else -> {
                return R.drawable.infrastructurefailure
            }

        }
    }

    private fun setStateIcon(state : String) : Int{
        when(state){
            "Kvar poslan.Nadstojnik nije vidio." -> {
                spinnerFailureState.setSelection(0)
                return R.drawable.red
            }
            "Viđeno. Tražim majstora." -> {
                spinnerFailureState.setSelection(1)
                return R.drawable.yellow
            }
            "Majstor pozvan" -> {
                spinnerFailureState.setSelection(2)
                return R.drawable.green
            }
            else ->{
                return R.drawable.red
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        if(++check > 1)
            //Log.w("ASD","AAA")
            failureListener.updateWithId(itemView.failureId.text.toString(), parent?.getItemAtPosition(position).toString())
    }

}
