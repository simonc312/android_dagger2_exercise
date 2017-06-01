package com.simonc312.androidapiexercise

import android.os.AsyncTask

import com.simonc312.androidapiexercise.api.models.Guide
import com.simonc312.androidapiexercise.components.room.GuideDAO
import java.util.concurrent.Executor

/**
 * Created by Simon on 5/29/2017.
 */
@KotlinTestOpen
class GuideRepository(private val guideDAO: GuideDAO,
                      private val backgroundJobExecutor: Executor) {

    operator fun plusAssign(guides: List<Guide>) {
        val insertGuidesTask = object : AsyncTask<List<Guide>, Void, Void>() {
            override fun doInBackground(vararg guides: List<Guide>): Void? {
                this@GuideRepository.guideDAO.insertGuides(guides.first())
                return null
            }
        }
        insertGuidesTask.executeOnExecutor(backgroundJobExecutor, guides)
    }

    operator fun get(query: String= EMPTY_QUERY, callback: Callback) {
        val fetchGuidesTask = object : AsyncTask<Void, Void, List<Guide>>() {
            override fun doInBackground(vararg voids: Void): List<Guide> {
                if (EMPTY_QUERY === query) {
                    return this@GuideRepository.guideDAO.allGuides
                } else {
                    // Todo vulnerable to sql injection from query
                    return this@GuideRepository.guideDAO.getGuidesWithName(GuideDAO.WILDCARD + query + GuideDAO.WILDCARD)
                }
            }

            override fun onPostExecute(guides: List<Guide>) {
                callback.onRepositoryResponse(guides)
            }
        }
        fetchGuidesTask.executeOnExecutor(backgroundJobExecutor)
    }

    interface Callback {
        fun onRepositoryResponse(guides: List<Guide>)
    }

    companion object {
        const val EMPTY_QUERY = ""
    }
}
