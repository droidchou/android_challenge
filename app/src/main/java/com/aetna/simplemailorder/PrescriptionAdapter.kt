package com.aetna.simplemailorder

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.text.color
import androidx.recyclerview.widget.RecyclerView
import com.aetna.simplemailorder.data.Prescription


class PrescriptionAdapter(
    private val addToCartButtonListener: AddToCartButtonListener
) :
    RecyclerView.Adapter<PrescriptionAdapter.BaseViewHolder<Prescription>>() {

    var prescriptions: List<Prescription> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Prescription> {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_prescription, parent, false)
        return PrescriptionViewHolder(view, addToCartButtonListener)
    }

    fun setData(newPrescriptions: List<Prescription>) {
        prescriptions = newPrescriptions
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Prescription>, position: Int) {
        holder.bind(prescriptions[position])
    }


    override fun getItemCount(): Int = prescriptions.size

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stringBuilder = SpannableStringBuilder()

        fun formatDisplayString(
            start: String,
            end: String,
            color: Int,
            relationship: String
        ): SpannableStringBuilder {
            stringBuilder.clear()
            return stringBuilder
                .append(start)
                .color(color) { append(end).append(relationship) }
        }

        fun formatDisplayString(start: String, end: String, color: Int): SpannableStringBuilder {
            stringBuilder.clear()
            return stringBuilder
                .append(start)
                .color(color) { append(end) }
        }

        abstract fun bind(item: T)
    }

    class PrescriptionViewHolder(itemView: View, private val listener: AddToCartButtonListener) :
        BaseViewHolder<Prescription>(itemView) {

        private val txtLastFill = itemView.findViewById<TextView>(R.id.prescriptions_last_fill)
        private val txtDrugName = itemView.findViewById<TextView>(R.id.prescriptions_drug_name)
        private val txtDaysSupply = itemView.findViewById<TextView>(R.id.prescriptions_days_supply)
        private val txtMemberName = itemView.findViewById<TextView>(R.id.prescriptions_member_name)
        private val txtRemaining = itemView.findViewById<TextView>(R.id.prescriptions_remaining)
        private val txtPrescribed = itemView.findViewById<TextView>(R.id.prescriptions_prescribed)
        private val txtFillBy = itemView.findViewById<TextView>(R.id.prescriptions_filled_by)
        private val txtPrice = itemView.findViewById<TextView>(R.id.prescriptions_price)
        private val btnAddToCart = itemView.findViewById<Button>(R.id.prescriptions_add_to_cart)

        override fun bind(item: Prescription) {


            txtLastFill.text = item.lastfilldate
            txtDrugName.text = item.drugname
            txtDaysSupply.text = item.dayssupply
            txtMemberName.text =
                "${itemView.resources.getString(R.string.prescription_member)}${item.memberfirstname}"
            txtRemaining.text = formatDisplayString(
                itemView.resources.getString(R.string.prescription_remaining),
                item.refillsleft,
                getColor(itemView.context, R.color.Anatomy_black)
            )
            txtPrescribed.text = item.prescriberfirstname
            txtFillBy.text = item.fulfilledby
            txtPrice.text = "$${item.estimatedcost}"
            btnAddToCart.setOnClickListener {
                listener.onAddToCartButtonClicked(item)
            }
        }
    }


    interface AddToCartButtonListener {

        fun onAddToCartButtonClicked(prescription: Prescription)
    }
}
