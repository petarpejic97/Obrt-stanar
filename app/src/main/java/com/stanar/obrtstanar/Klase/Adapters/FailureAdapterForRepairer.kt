package com.stanar.obrtstanar.Klase.Adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.stanar.obrtstanar.Fragmenti.Listener.FailureListener
import com.stanar.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.stanar.obrtstanar.Klase.ObrtStanar
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R
import kotlinx.android.synthetic.main.failure_item.view.*


class FailureAdapterForRepairer(failures : MutableList<FailureWithId>, failureListener : FailureListener): RecyclerView.Adapter<FailureViewHolderForRepairer>() {

    var failures : MutableList<FailureWithId> = mutableListOf()
    var failureListener : FailureListener
    init {
        this.failures = failures
        this.failureListener = failureListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FailureViewHolderForRepairer {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.failure_item_for_repairer, parent,false)

        return FailureViewHolderForRepairer(view)
    }

    override fun getItemCount(): Int = failures.size

    override fun onBindViewHolder(holder: FailureViewHolderForRepairer, position: Int) {
        val failure : FailureWithId = failures[position]
        holder.bind(failure,failureListener)

    }
    fun refreshData() {
        this.failures.clear()
        this.failures.addAll(failures)
        this.notifyDataSetChanged()
    }
}
class FailureViewHolderForRepairer(itemView: View) : RecyclerView.ViewHolder(itemView), OnItemSelectedListener {

    var imgTypeOfFailure : ImageView
    var imgIsFinished : ImageView
    var tvName : TextView
    var tvLastname : TextView
    var tvAddress : TextView
    var tvPhoneNumber : TextView
    var btnCall : Button
    var tvRepairTime : TextView
    var tvFailureDescription : TextView
    var tvTypeOfFailure : TextView
    var tvFailureId : TextView
    var spinnerFailureState : Spinner
    lateinit var failureListener : FailureListener
    private var preferenceManager: PreferenceManager = PreferenceManager()

    var check = 0

    init {
        imgTypeOfFailure = itemView.findViewById(R.id.imgTypeOfFailure)
        imgIsFinished = itemView.findViewById(R.id.imgIsFinished)
        tvFailureId = itemView.findViewById(R.id.failureId)
        tvName = itemView.findViewById(R.id.tvName)
        tvLastname = itemView.findViewById(R.id.tvLastname)
        tvAddress = itemView.findViewById(R.id.tvAddress)
        btnCall = itemView.findViewById(R.id.btnCall)
        tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber)
        tvRepairTime = itemView.findViewById(R.id.tvRepairTime)
        tvFailureDescription = itemView.findViewById(R.id.tvFailureDescription)
        tvTypeOfFailure = itemView.findViewById(R.id.tvTypeOfFailure)

        spinnerFailureState = itemView.findViewById(R.id.spinnerFailureState)
        spinnerFailureState.onItemSelectedListener = this

        ArrayAdapter.createFromResource(ObrtStanar.ApplicationContext, R.array.repairerState, R.layout.spinner_admin_item).also {
                adapterFailureState -> adapterFailureState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFailureState.adapter = adapterFailureState
        }
    }
    fun bind(failure : FailureWithId,failureListener : FailureListener){

        this.failureListener = failureListener

        itemView.imgTypeOfFailure.setImageResource(setFailureIcon(failure))
        itemView.imgIsFinished.setImageResource(setFinishedIcon(failure.isFinished))
        itemView.failureId.text = failure.id
        itemView.tvName.text = failure.name
        itemView.tvLastname.text = failure.lastName
        itemView.tvAddress.text = failure.address
        itemView.tvPhoneNumber.text = failure.phoneNumber
        itemView.imgBulletState.setImageResource(setStateIcon(failure.repairState))
        itemView.tvRepairTime.text = failure.repairTime
        itemView.tvFailureDescription.text = failure.failureDescription
        itemView.tvTypeOfFailure.text = failure.typeOfFailure

        btnCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + failure.phoneNumber))
            ObrtStanar.ApplicationContext.startActivity(intent)
        }
        tvTypeOfFailure.setOnClickListener{ failureListener.callRepairer(failure.typeOfFailure) }
        itemView.setOnLongClickListener { failureListener.onShowDetails(failure.typeOfFailure,failure.failureImageUri) ; true; }
    }
    private fun setFinishedIcon(isFinished : String) : Int{
        when(isFinished){
            "true"->{
                return R.drawable.ciclefinished
            }
            else -> {
                return R.drawable.nofinished
            }

        }
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
            "Kvar poslan. Nadstojnik nije vidio." -> {
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
            "Viđeno. Nemam definirano vrijeme." -> {
                spinnerFailureState.setSelection(3)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u ponedjeljak." -> {
                spinnerFailureState.setSelection(4)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u utorak." -> {
                spinnerFailureState.setSelection(5)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u srijedu." -> {
                spinnerFailureState.setSelection(6)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u četvrtak." -> {
                spinnerFailureState.setSelection(7)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u petak." -> {
                spinnerFailureState.setSelection(8)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u subotu." -> {
                spinnerFailureState.setSelection(9)
                return R.drawable.checked
            }
            "Viđeno. Dolazim u nedjelju." -> {
                spinnerFailureState.setSelection(10)
                return R.drawable.checked
            }
            else ->{
                spinnerFailureState.setSelection(3)
                return R.drawable.checked
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        if(++check > 1)
            failureListener.updateWithId(itemView.failureId.text.toString(), parent?.getItemAtPosition(position).toString())
    }

}
