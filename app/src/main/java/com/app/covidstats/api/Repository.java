package com.app.covidstats.api;

import com.app.covidstats.api.model.CountryStats;
import com.app.covidstats.api.response.Response;
import com.app.covidstats.db.StatsDao;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class Repository {
    private static final String TAG = "Repository";
    //
    private ApiService apiService;
    private StatsDao statsDao;

    @Inject
    public Repository(ApiService apiService, StatsDao statsDao) {
        this.apiService = apiService;
        this.statsDao = statsDao;
    }

    public Observable<Response> getStats() {
        return apiService.getStats()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void saveDataToDB(ArrayList<CountryStats> stats) {
        if (stats != null && stats.size() > 0) {
            statsDao.insertMovies(stats);
        }
    }

    public ArrayList<CountryStats> getDataFromDB() {
        ArrayList<CountryStats> stats = new ArrayList<>();
        stats.addAll(statsDao.getStats());
        return stats;
    }

//    public MutableLiveData<Response> getStats() {
//        final MutableLiveData<Response> mutableLiveData = new MutableLiveData<>();
//
//        apiService.getStats().enqueue(new Callback<Response>() {
//            @Override
//            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
//                mutableLiveData.setValue(response.body());
//                Log.d(TAG,new Gson().toJson(response.body()));
//            }
//
//            @Override
//            public void onFailure(Call<Response> call, Throwable t) {
//
//            }
//        });
//
//        return mutableLiveData;
//    }
}
