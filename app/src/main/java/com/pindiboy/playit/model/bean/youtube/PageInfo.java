package com.pindiboy.playit.model.bean.youtube;

/**
 * Created by Jiangwenjin on 2017/3/15.
 * <p>
 * "totalResults": 51,
 * "resultsPerPage": 5
 */
public class PageInfo {
    private int totalResults;
    private int resultsPerPage;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }
}
