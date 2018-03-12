package com.bpj.rxandroid.rxbinding;



import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/3/12 .
 * 模拟网络请求，起到提供ObservableSource的作用
 */

public class SearchService implements Search.ISearchService{


    public static SearchService instance;
    public static synchronized SearchService getInstance(){
        if(instance == null){
            instance = new SearchService();
        }
        return instance;
    }

    /**
     * 模拟网络请求获取搜索结果
     * @param regix
     * @return
     */
    @Override
    public  ObservableSource<List<SearchResultBean>> getSearchResults(final String regix) {
        return Observable.create(new ObservableOnSubscribe<List<SearchResultBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchResultBean>> emitter) throws Exception {
//                Log.i(">>>"," regix="+regix);
//
                /* if (regix.contains("a")) {
                    SearchResultBean bean1 = new SearchResultBean(regix+"-a11");
                    SearchResultBean bean2 = new SearchResultBean(regix+"-a22");
                    list.add(bean1);
                    list.add(bean2);
                    Thread.sleep(10000);
                } else if(!TextUtils.isEmpty(regix)){
                    SearchResultBean bean1 = new SearchResultBean("11");
                    SearchResultBean bean2 = new SearchResultBean("22");
                    SearchResultBean bean3 = new SearchResultBean("33");
                    list.add(bean1);
                    list.add(bean2);
                    list.add(bean3);
                    Thread.sleep(3000);
                }*/

                if(!emitter.isDisposed()){
                    Thread.sleep(10000);
                    List list = new ArrayList();
                    emitter.onNext(list);
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }


}
