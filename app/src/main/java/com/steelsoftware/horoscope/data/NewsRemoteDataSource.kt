package com.steelsoftware.horoscope.data

import com.steelsoftware.horoscope.api.NewsService
import com.steelsoftware.horoscope.model.NewsResult
import io.reactivex.Observable

/**
 * Created by ansh on 13/02/18.
 */
class NewsRemoteDataSource(var newsService: NewsService) {

    fun getRepositories(): Observable<NewsResult> {
        return newsService.getTopHeadlines()
    }

    fun getHeadlinesByCategory(category: String): Observable<NewsResult> {
        return newsService.getHeadlinesByCategory(category)
    }

    fun getHeadlinesByKeyword(keyword: String): Observable<NewsResult> {
        return newsService.getHeadlinesByKeyword(keyword)
    }
}