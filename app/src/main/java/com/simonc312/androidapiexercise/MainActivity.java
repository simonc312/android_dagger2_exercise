package com.simonc312.androidapiexercise;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.simonc312.androidapiexercise.api.ApiService;
import com.simonc312.androidapiexercise.api.models.Guide;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity
        implements UpcomingGuidePresenter.View, GuideAdapter.ItemActionHandler {

    @Inject
    ApiService apiService;
    @Inject
    Picasso picasso;

    @Nullable
    private UpcomingGuidePresenter presenter;
    @Nullable
    private GuideAdapter adapter;

    public MainActivity() {
        super();
        //TODO move to a base InjectActivity class
        MainApplication.getInstance().getMainAppComponent().injectsMainActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.presenter = new UpcomingGuidePresenter(apiService, this);
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
            this.presenter = null;
        }

        if(this.adapter != null) {
            this.adapter.setItemActionHandler(null);
            this.adapter = null;
        }
    }

    //region UpcomingGuidePresenter.View
    @Override
    public void update(@NonNull List<Guide> newGuides) {
        if (this.adapter != null) {
            this.adapter.update(newGuides);
        }
    }

    @Override
    public void onError(@NonNull Throwable error) {
        Toast.makeText(this, R.string.upcoming_guide_request_error, Toast.LENGTH_SHORT).show();
    }
    //endregion

    //region ItemActionHander
    @Override
    public void onClicked(@NonNull final Guide guide) {
        final Uri guideUri = Uri.parse(guide.getGuideUrl());
        final Intent openUrlIntent = new Intent(Intent.ACTION_VIEW).setData(guideUri);
        startActivity(openUrlIntent);
    }
    //endregion
}
