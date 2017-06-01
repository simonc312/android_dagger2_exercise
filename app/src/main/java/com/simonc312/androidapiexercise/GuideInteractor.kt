package com.simonc312.androidapiexercise

import android.support.annotation.VisibleForTesting
import com.simonc312.androidapiexercise.api.ApiService
import com.simonc312.androidapiexercise.api.models.Guide
import com.simonc312.androidapiexercise.api.models.Guides
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.UnknownHostException

/**
 * Created by Simon on 5/17/2017.
 */

class GuideInteractor(private val apiService: ApiService,
                      private val guideRepository: GuideRepository) : Callback<Guides>, GuideRepository.Callback {
    private var currentCall: Call<Guides>? = null
    private var interactorOutput: InteractorOutput

    init {
        this.interactorOutput = EmptyInteractorOutput()
    }

    /**
     * Make request to get upcoming guide date
     */
    fun get() {
        this.currentCall = this.apiService.upcomingGuides
        this.currentCall!!.enqueue(this)
    }

    fun get(query: String) {
        getFromLocalStore(query)
    }

    /**git
     * Cancel existing request.
     */
    fun cancel() {
        this.currentCall?.cancel()
    }

    //region Callback
    override fun onResponse(call: Call<Guides>,
                            response: Response<Guides>) {
        val guides = response.body().data
        handleRetrofitResponse(guides)
    }

    @VisibleForTesting
    internal fun handleRetrofitResponse(guides: List<Guide>) {
        this.interactorOutput.onGuidesAvailable(guides)
        this.guideRepository.add(guides)
    }

    override fun onFailure(call: Call<Guides>,
                           t: Throwable) {
        handleRetrofitFailure(t)
    }

    @VisibleForTesting
    internal fun handleRetrofitFailure(t: Throwable) {
        if (t is UnknownHostException) {
            getFromLocalStore() /*query defaults to all*/
        }
        this.interactorOutput.onGuidesUnavailable()
    }

    override fun onRepositoryResponse(guides: List<Guide>) {
        this.interactorOutput.onGuidesAvailableOffline(guides)
    }
    //endregion

    private fun getFromLocalStore(query: String=GuideRepository.EMPTY_QUERY) {
        this.guideRepository[query, this]
    }

    fun setOutput(output: InteractorOutput) {
        this.interactorOutput = output
    }

    interface InteractorOutput {
        fun onGuidesAvailable(guides: List<Guide>)

        fun onGuidesAvailableOffline(guides: List<Guide>)

        fun onGuidesUnavailable()
    }

    private class EmptyInteractorOutput : InteractorOutput {

        override fun onGuidesAvailable(guides: List<Guide>) {}

        override fun onGuidesAvailableOffline(guides: List<Guide>) {}

        override fun onGuidesUnavailable() {}
    }
}
