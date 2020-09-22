package co.paulfran.cleannote.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance
import co.paulfran.cleannote.data.models.NoteData
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var dataList = emptyList<NoteData>()

    class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.titleText.text = dataList[position].title
        holder.itemView.descriptionText.text = dataList[position].description

        val importance = dataList[position].importance

        when (dataList[position].importance) {
            Importance.HIGH -> holder.itemView.importanceIndicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.red))

            Importance.MEDIUM -> holder.itemView.importanceIndicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow))

            Importance.LOW -> holder.itemView.importanceIndicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.green))
        }
    }

    fun setData(noteData: List<NoteData>) {
        this.dataList = noteData
        notifyDataSetChanged()
    }
}