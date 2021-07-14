package com.stanar.obrtstanar.Klase.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.stanar.obrtstanar.Fragmenti.Listener.UpdateFinishedFailure
import com.stanar.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.stanar.obrtstanar.R
import kotlinx.android.synthetic.main.failure_item.view.failureId
import kotlinx.android.synthetic.main.failure_item.view.imgBulletState
import kotlinx.android.synthetic.main.failure_item.view.imgTypeOfFailure
import kotlinx.android.synthetic.main.failure_item.view.tvAddress
import kotlinx.android.synthetic.main.failure_item.view.tvFailureDescription
import kotlinx.android.synthetic.main.failure_item.view.tvLastname
import kotlinx.android.synthetic.main.failure_item.view.tvName
import kotlinx.android.synthetic.main.failure_item.view.tvPhoneNumber
import kotlinx.android.synthetic.main.failure_item.view.tvRepairState
import kotlinx.android.synthetic.main.failure_item.view.tvRepairTime
import kotlinx.android.synthetic.main.failure_item.view.tvTypeOfFailure
import kotlinx.android.synthetic.main.failure_item_for_users.view.*


class FailureAdapterForNormalUsers(failures : MutableList<FailureWithId>, updateFinishedFailure : UpdateFinishedFailure): RecyclerView.Adapter<FailureViewHolderForNormalUsers>() {

    var failures : MutableList<FailureWithId> = mutableListOf()
    var updateFinishedFailure : UpdateFinishedFailure
    var check = 0
    init {
        this.failures = failures
        this.updateFinishedFailure = updateFinishedFailure
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FailureViewHolderForNormalUsers {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.failure_item_for_users, parent,false)

        return FailureViewHolderForNormalUsers(view)
    }

    override fun getItemCount(): Int = failures.size

    override fun onBindViewHolder(holder: FailureViewHolderForNormalUsers, position: Int) {
        val failure : FailureWithId = failures[position]
        holder.bind(failure,updateFinishedFailure)

    }
    fun refreshData() {
        this.failures.clear()
        this.failures.addAll(failures)
        this.notifyDataSetChanged()
    }
}
class FailureViewHolderForNormalUsers(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imgTypeOfFailure : ImageView
    var imgIsFinished : ImageView
    var tvName : TextView
    var tvLastname : TextView
    var tvAddress : TextView
    var tvPhoneNumber : TextView
    var tvRepairTime : TextView
    var tvRepairState : TextView
    var tvFailureDescription : TextView
    var tvTypeOfFailure : TextView
    var tvFailureId : TextView
    var tvNotFinished : TextView
    var finishedLayout : View
    lateinit var updatefailureListener : UpdateFinishedFailure

    init {
        imgTypeOfFailure = itemView.findViewById(R.id.imgTypeOfFailure)
        imgIsFinished = itemView.findViewById(R.id.imgFinished)
        tvFailureId = itemView.findViewById(R.id.failureId)
        tvName = itemView.findViewById(R.id.tvName)
        tvLastname = itemView.findViewById(R.id.tvLastname)
        tvAddress = itemView.findViewById(R.id.tvAddress)
        tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber)
        tvRepairTime = itemView.findViewById(R.id.tvRepairTime)
        tvRepairState =itemView.findViewById(R.id.tvRepairState)
        tvFailureDescription = itemView.findViewById(R.id.tvFailureDescription)
        tvTypeOfFailure = itemView.findViewById(R.id.tvTypeOfFailure)
        finishedLayout = itemView.findViewById(R.id.linearLayoutFinished)
        tvNotFinished = itemView.findViewById(R.id.tvNotFinished)
    }
    fun bind(failure : FailureWithId,updateFinishedFailure : UpdateFinishedFailure){
        this.updatefailureListener = updateFinishedFailure
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
        if (failure.isFinished == "true"){
            itemView.tvNotFinished.visibility=View.VISIBLE
            itemView.linearLayoutFinished.visibility=View.INVISIBLE
        }
        else {
            itemView.tvNotFinished.visibility=View.INVISIBLE
            itemView.linearLayoutFinished.visibility=View.VISIBLE
        }

        itemView.imgFinished.setOnClickListener { updateFinishedFailure.updateFinished(failure.id,"true") }
        itemView.tvNotFinished.setOnClickListener { updateFinishedFailure.updateFinished(failure.id,"false") }
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
                return R.drawable.red
            }
            "Viđeno. Tražim majstora." -> {
                return R.drawable.yellow
            }
            "Majstor pozvan" -> {
                return R.drawable.green
            }
            else ->{
                return R.drawable.checked
            }
        }
    }

}
