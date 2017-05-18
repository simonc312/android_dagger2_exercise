package com.simonc312.androidapiexercise;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.simonc312.androidapiexercise.api.models.Guide;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements UpcomingGuidePresenter.View, GuideAdapter.ItemActionHandler {

    @Inject
    UpcomingGuidePresenter presenter;
    @Inject
    Picasso picasso;
    @Inject
    CustomTabsIntent.Builder customTabsBuilder;

    @Nullable
    private GuideAdapter adapter;
    @Nullable
    private ProgressBar progressBar;

    public MainActivity() {
        super();
        //TODO move to a base InjectActivity class
        MainApplication.getGuideComponent(this).injectsMainActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.progressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);
        this.progressBar.setVisibility(View.VISIBLE);
        this.adapter = new GuideAdapter(this, picasso);
        this.adapter.setItemActionHandler(this);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        this.presenter.get();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.cancel();
        }

        if (this.adapter != null) {
            this.adapter.setItemActionHandler(null);
            this.adapter = null;
        }

        if (this.progressBar != null) {
            this.progressBar.clearAnimation();
            this.progressBar = null;
        }
    }

    //region UpcomingGuidePresenter.View
    @Override
    public void update(@NonNull List<Guide> newGuides) {
        if (this.progressBar != null) {
            this.progressBar.setVisibility(View.GONE);
        }
        if (this.adapter != null) {
            this.adapter.update(newGuides);
        }
    }

    @Override
    public void showToast(@StringRes int messageRes) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region ItemActionHander
    @Override
    public void onClicked(@NonNull final Guide guide) {
        final Uri guideUri = Uri.parse(guide.getGuideUrl());
        this.customTabsBuilder.build().launchUrl(this, guideUri);
    }
    //endregion
}
