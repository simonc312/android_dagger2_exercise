package com.simonc312.androidapiexercise;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.simonc312.androidapiexercise.api.models.Guide;

import java.util.List;

/**
 * Presents {@link Guide}
 * data in displayable form through view contract.
 */

public class UpcomingGuidePresenter implements GuideInteractor.InteractorOutput {

    private final GuideInteractor guideInteractor;
    private final View view;

    public UpcomingGuidePresenter(@NonNull final GuideInteractor guideInteractor,
                                  @NonNull final View view) {
        this.guideInteractor = guideInteractor;
        this.view = view;
    }

    /**
     * Make request to get upcoming guide date
     */
    public void get() {
        this.guideInteractor.get();
    }

    /**
     * Cancel existing request.
     */
    public void cancel() {
        this.guideInteractor.cancel();
    }

    //region InteractorOutput
    @Override
    public void onGuidesAvailable(List<Guide> guides) {
        this.view.update(guides);
    }

    @Override
    public void onGuidesAvailableOffline(List<Guide> guides) {
        this.view.update(guides);
        this.view.showToast(R.string.upcoming_guide_offline_mode);
    }

    @Override
    public void onGuidesUnavailable() {
        this.view.showToast(R.string.upcoming_guide_request_error);
    }
    //endregion

    public interface View {
        void update(@NonNull List<Guide> newGuides);

        void showToast(@StringRes int messageRes);
    }
}
