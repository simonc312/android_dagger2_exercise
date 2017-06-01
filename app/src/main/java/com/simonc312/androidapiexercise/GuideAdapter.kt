package com.simonc312.androidapiexercise

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.simonc312.androidapiexercise.api.models.Guide
import com.simonc312.androidapiexercise.api.models.getDateDisplay
import com.simonc312.androidapiexercise.api.models.getVenueDisplay
import com.squareup.picasso.Picasso
import java.util.*

class GuideAdapter(private val context: Context,
                   private val picasso: Picasso) : RecyclerView.Adapter<GuideAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater
    private val dataStore: MutableList<Guide>
    private var itemActionHandler: ItemActionHandler? = null

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.dataStore = ArrayList<Guide>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = this.layoutInflater.inflate(R.layout.item_view_guide, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            val position = viewHolder.adapterPosition
            if (RecyclerView.NO_POSITION == position) {
                return@OnClickListener
            }
            val guide = this@GuideAdapter.dataStore[position]
            this@GuideAdapter.itemActionHandler?.onClicked(guide)
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val guide = this.dataStore[position]
        this.bindGuideIcon(holder, guide)
        holder.bind(guide)
    }

    private fun bindGuideIcon(holder: ViewHolder,
                              guide: Guide) {
        picasso.load(guide.iconUrl)
                .into(holder.iconImageView)
    }

    override fun getItemCount(): Int {
        return dataStore.size
    }

    fun update(newGuides: List<Guide>) {
        //TODO if more time permitted would have used DiffUtil helper
        this.dataStore.clear()
        this.dataStore.addAll(newGuides)
        notifyDataSetChanged() //expensive
    }

    internal fun setItemActionHandler(itemActionHandler: ItemActionHandler?) {
        this.itemActionHandler = itemActionHandler
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val iconImageView: ImageView
        private val nameTextView: TextView
        private val venueTextView: TextView
        private val dateTextView: TextView

        init {
            this.iconImageView = itemView.findViewById(R.id.item_view_guide_icon) as ImageView
            this.nameTextView = itemView.findViewById(R.id.item_view_guide_name) as TextView
            this.venueTextView = itemView.findViewById(R.id.item_view_guide_venue) as TextView
            this.dateTextView = itemView.findViewById(R.id.item_view_guide_date) as TextView
        }

        fun bind(guide: Guide) {
            this.nameTextView.text = guide.name
            this.venueTextView.text = guide.getVenueDisplay()
            this.dateTextView.text = guide.getDateDisplay()
        }
    }

    internal interface ItemActionHandler {
        fun onClicked(guide: Guide)
    }
}
