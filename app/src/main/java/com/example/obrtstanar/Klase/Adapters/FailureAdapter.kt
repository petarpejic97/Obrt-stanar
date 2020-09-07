package com.example.obrtstanar.Klase.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.obrtstanar.Klase.FirebaseClass.Failure
import com.example.obrtstanar.R
import kotlinx.android.synthetic.main.failure_item.view.*


class FailureAdapter(failures : MutableList<Failure>): RecyclerView.Adapter<FailureViewHolder>() {
    var failures : MutableList<Failure> = mutableListOf()

    init {
        this.failures = failures
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FailureViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.failure_item, parent,false)
        return FailureViewHolder(view)
    }

    override fun getItemCount(): Int = failures.size

    override fun onBindViewHolder(holder: FailureViewHolder, position: Int) {
        val failure : Failure = failures[position]
        holder.bind(failure)
    }
}
class FailureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imgTypeOfFailure : ImageView
    var tvName : TextView
    var tvLastname : TextView
    var tvAddress : TextView
    var tvPhoneNumber : TextView
    var tvRepairTime : TextView
    var tvRepairState : TextView
    var tvFailureDescription : TextView
    var tvTypeOfFailure : TextView

    init {
        imgTypeOfFailure = itemView.findViewById(R.id.imgTypeOfFailure)
        tvName = itemView.findViewById(R.id.tvName)
        tvLastname = itemView.findViewById(R.id.tvLastname)
        tvAddress = itemView.findViewById(R.id.tvAddress)
        tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber)
        tvRepairTime = itemView.findViewById(R.id.tvRepairTime)
        tvRepairState =itemView.findViewById(R.id.tvRepairState)
        tvFailureDescription = itemView.findViewById(R.id.tvFailureDescription)
        tvTypeOfFailure = itemView.findViewById(R.id.tvTypeOfFailure)
    }
    fun bind(failure : Failure){
        /*Picasso
            .get()
            .load(failure.failureImageUri)
            .into(itemView.imgTypeOfFailure, object: com.squareup.picasso.Callback {
                override fun onError(e: Exception?) {
                    Log.w("EEEERRRRROOOORRRR",e.toString())
                }
                override fun onSuccess() {
                    Log.w("AAA","USPJELO")
                }
            })*/
        itemView.imgTypeOfFailure.setImageResource(setFailureIcon(failure))
        itemView.tvName.text = failure.name
        itemView.tvLastname.text = failure.lastName
        itemView.tvAddress.text = failure.address
        itemView.tvPhoneNumber.text = failure.phoneNumber
        itemView.imgBulletState.setImageResource(setStateIcon(failure.repairState))
        itemView.tvRepairTime.text = failure.repairTime
        itemView.tvRepairState.text = failure.repairState
        itemView.tvFailureDescription.text = failure.failureDescription
        itemView.tvTypeOfFailure.text = failure.typeOfFailure
    }
    private fun setFailureIcon(failure : Failure) : Int{
        when(failure.typeOfFailure){
            "Mehanički kvar"->{
                return R.drawable.mehanicalfailure
            }
            "Električni kvar"->{
                return R.drawable.electricfailure
            }
            "Kvar s grijanjem"->{
                return R.drawable.heatinfailure
            }
            "Vodoinstalaterski kvar" ->{
                return R.drawable.plumbingfailure
            }
            "Infrastrukturni kvar"->{
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
                return R.drawable.red
            }
        }
    }
}