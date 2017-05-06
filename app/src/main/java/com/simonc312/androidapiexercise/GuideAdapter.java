package com.simonc312.androidapiexercise;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simonc312.androidapiexercise.api.models.Guide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final Picasso picasso;
    private List<Guide> dataStore;

    public GuideAdapter(@NonNull final Context context, Picasso picasso) {
        super();
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.picasso = picasso;
        this.dataStore = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = this.layoutInflater.inflate(R.layout.item_view_guide, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = viewHolder.getAdapterPosition();
                if (RecyclerView.NO_POSITION == position) {
                    return;
                }
                final String guideUrl = GuideAdapter.this.dataStore.get(position).getGuideUrl();
                final Intent openUrlIntent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(guideUrl));
                GuideAdapter.this.context.startActivity(openUrlIntent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Guide guide = this.dataStore.get(position);
        this.bindGuideIcon(holder, guide);
        holder.bind(guide);
    }

    private void bindGuideIcon(@NonNull final ViewHolder holder,
                               @NonNull final Guide guide) {
        picasso.load(guide.getIconUrl())
                .into(holder.iconImageView);
    }

    @Override
    public int getItemCount() {
        return dataStore.size();
    }

    public void update(@NonNull final List<Guide> newGuides) {
        //TODO if more time permitted would have used DiffUtil helper
        this.dataStore.clear();
        this.dataStore.addAll(newGuides);
        notifyDataSetChanged(); //expensive
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iconImageView;
        private final TextView nameTextView;
        private final TextView venueTextView;
        private final TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.iconImageView = (ImageView) itemView.findViewById(R.id.item_view_guide_icon);
            this.nameTextView = (TextView) itemView.findViewById(R.id.item_view_guide_name);
            this.venueTextView = (TextView) itemView.findViewById(R.id.item_view_guide_venue);
            this.dateTextView = (TextView) itemView.findViewById(R.id.item_view_guide_date);
        }

        public void bind(@NonNull final Guide guide) {
            this.nameTextView.setText(guide.getName());
            this.venueTextView.setText(guide.getVenueDisplayValue());
            this.dateTextView.setText(guide.getDateRangeDisplayValue());
        }
    }
}
