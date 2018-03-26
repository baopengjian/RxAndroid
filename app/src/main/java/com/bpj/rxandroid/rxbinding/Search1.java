package com.bpj.rxandroid.rxbinding;

import android.text.TextUtils;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by Ray on 2018/3/12 .
 */

public class Search1 {

    /**
     * 说明
     * 1. getEditTextObservable
     * 2.
     * 3.
     **/
    public static Disposable rxBindingEt(final EditText ed, final ISearchService1 searchService, DisposableObserver<SearchResultPackage> observer) {
        final Disposable disposable = getEditTextObservable(ed)
                .flatMap(new Function<CharSequence, ObservableSource<SearchResultPackage>>() {
                    @Override
                    public ObservableSource<SearchResultPackage> apply(CharSequence charSequence) throws Exception {
                        //字符为空
                        if (TextUtils.isEmpty(charSequence)) {
                            return Observable.create(new ObservableOnSubscribe<SearchResultPackage>() {
                                @Override
                                public void subscribe(ObservableEmitter<SearchResultPackage> emitter) throws Exception {
                                    if (!emitter.isDisposed()) {
                                        SearchResultPackage resultPackage = new SearchResultPackage();
                                        resultPackage.code = 0;
                                        emitter.onNext(resultPackage);
                                        emitter.onComplete();
                                    }

                                }
                            });
                        }
                        //定向输入
                        if (charSequence.length() == 6) {
                            return searchService.getDetailResults(charSequence.toString());
                        }

                        //普通搜索
                        return searchService.getSearchResults(charSequence.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);

        return disposable;
    }


    /**
     * 此处采用了RxBinding：RxTextView.textChanges(name) = 对对控件数据变更进行监听（功能类似TextWatcher），需要引入依赖：compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
     * 获取标准通过 观察et的Observable，单独抽取是为了能够都Observable进行其他操作如字符判断等
     *
     * @param et
     * @return
     */
    public static Observable<CharSequence> getEditTextObservable(EditText et) {
        return RxTextView.textChanges(et) //绑定EditText
                .skip(1)//去掉第一次选中
                .debounce(300, TimeUnit.MILLISECONDS);//300毫秒防反跳
    }

    interface ISearchService1 {

        ObservableSource<SearchResultPackage> getSearchResults(final String regix);

        ObservableSource<SearchResultPackage> getDetailResults(final String regix);
    }
}
