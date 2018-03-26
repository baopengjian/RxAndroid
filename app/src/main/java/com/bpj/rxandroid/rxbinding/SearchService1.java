package com.bpj.rxandroid.rxbinding;


import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/3/12 .
 * 模拟网络请求，起到提供ObservableSource的作用
 */

public class SearchService1 implements Search1.ISearchService1 {


    public static SearchService1 instance;

    public static synchronized SearchService1 getInstance() {
        if (instance == null) {
            instance = new SearchService1();
        }
        return instance;
    }

    @Override
    public ObservableSource<SearchResultPackage> getSearchResults(final String regix) {

        return Observable.create(new ObservableOnSubscribe<SearchResultPackage>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultPackage> emitter) throws Exception {
                try {
                    SearchResultPackage result = new SearchResultPackage();
                    List<SearchResultBean> list = new ArrayList<>();
                    if (regix.contains("a")) {
                        SearchResultBean bean1 = new SearchResultBean(regix + "-a11");
                        SearchResultBean bean2 = new SearchResultBean(regix + "-a22");
                        list.add(bean1);
                        list.add(bean2);
                        Thread.sleep(500);
                    } else if (!TextUtils.isEmpty(regix)) {
                        SearchResultBean bean1 = new SearchResultBean("11");
                        SearchResultBean bean2 = new SearchResultBean("22");
                        SearchResultBean bean3 = new SearchResultBean("33");
                        list.add(bean1);
                        list.add(bean2);
                        list.add(bean3);
                        Thread.sleep(100);
                    }
                    result.code = 1;
                    result.list = list;

                    //注意isDisposed的调用位置
                    if (!emitter.isDisposed()) {
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                } catch (Exception e) {
                    if(!emitter.isDisposed()){
                        emitter.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public ObservableSource<SearchResultPackage> getDetailResults(String regix) {


        return Observable.create(new ObservableOnSubscribe<SearchResultPackage>() {
            @Override
            public void subscribe(ObservableEmitter<SearchResultPackage> emitter) throws Exception {
                try{
                    SearchResultPackage result = new SearchResultPackage();
                    SearchDetail detail = new SearchDetail();
                    detail.data = "详情搜索数据";
                    result.detail = detail;
                    result.code = 2;
                    Thread.sleep(800);

                    if(!emitter.isDisposed()){
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                }catch (Exception e){
                    if(!emitter.isDisposed()){
                        emitter.onError(e);
                    }
                }
            }
        }).subscribeOn(Schedulers.io());
    }

}
