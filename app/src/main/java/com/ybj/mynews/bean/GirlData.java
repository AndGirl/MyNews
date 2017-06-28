package com.ybj.mynews.bean;

import java.util.List;

/**
 * Created by 杨阳洋 on 2017/5/23.
 */

public class GirlData {
    private boolean isError;
    private List<PhotoGril> results;

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public void setResults(List<PhotoGril> results) {
        this.results = results;
    }

    public List<PhotoGril> getResults() {
        return results;
    }
}
