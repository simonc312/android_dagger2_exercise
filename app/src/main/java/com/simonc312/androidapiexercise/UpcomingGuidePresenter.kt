package com.simonc312.androidapiexercise

import android.support.annotation.StringRes

import com.simonc312.androidapiexercise.api.models.Guide

/**
 * Presents [Guide]
 * data in displayable form through view contract.
 */

class UpcomingGuidePresenter(private val guideInteractor: GuideInteractor,
                             private val view: UpcomingGuidePresenter.View)
    //implements
    : GuideInteractor.InteractorOutput {

    /**
     * Make request to get upcoming guide date
     */
    fun get() {
        this.guideInteractor.get()
    }

    fun get(query: String) {
        this.guideInteractor.get(query)
    }

    /**
     * Cancel existing request.
     */
    fun cancel() {
        this.guideInteractor.cancel()
    }

    //region InteractorOutput
    override fun onGuidesAvailable(guides: List<Guide>) {
        this.view.update(guides)
    }

    override fun onGuidesAvailableOffline(guides: List<Guide>) {
        this.view.update(guides)
        this.view.showToast(R.string.upcoming_guide_offline_mode)
    }

    override fun onGuidesUnavailable() {
        this.view.showToast(R.string.upcoming_guide_request_error)
    }

    //endregion

    interface View {
        fun update(newGuides: List<Guide>)

        fun showToast(@StringRes messageRes: Int)
    }
}
