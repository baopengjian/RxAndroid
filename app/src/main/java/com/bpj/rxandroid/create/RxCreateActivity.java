package com.bpj.rxandroid.create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * @author Ray on 2018/2/13.
 */

public class RxCreateActivity extends AppCompatActivity {


    private static String TAG = "RxCreateActivity";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        just();
//        from();
//        create();
//        defer();
//        rang();
//        interval();
//        empty();
          never();
    }

    public void never(){
        Observable.never().subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe -> ");
            }

            @Override
            public void onNext(@NonNull Object s) {
                Log.i(TAG, "onNext -> s=" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError -> e=" + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete -> ");
            }
        });
        // RxCreateActivity: onSubscribe ->
    }


    public void empty(){
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe -> ");
                    }

                    @Override
                    public void onNext(@NonNull Object s) {
                        Log.i(TAG, "onNext -> s=" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError -> e=" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete -> ");

                    }
                });
        //执行结果
        //RxCreateActivity: onSubscribe ->
        //RxCreateActivity: onComplete ->
    }

    public void interval() {
        Observable.interval(5, 3, TimeUnit.SECONDS)
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(@NonNull Long o) {
                        Log.i(TAG, "onNext -> o =" + o);
                        if (10 == o) {
                            dispose();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dispose();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        //从1到3,5秒后开始周期为3s的循环
//        Observable.intervalRange(1, 3, 5, 3, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        Log.i(TAG, "onNext -> aLong =" + aLong);
//                    }
//                });
    }


    public void rang() {
        Observable.range(1, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "onNext -> integer =" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "onNext -> throwable =" + throwable);
            }
        });
    }

    public void defer() {
        Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return new ObservableSource() {

                    @Override
                    public void subscribe(@NonNull Observer observer) {
                        observer.onNext("111");
                        observer.onComplete();
                    }
                };
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe -> d =" + d);
                //没有被调用用
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onSubscribe -> s=" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError -> e =" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete -> ");
            }
        });
    }


    public void create() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter emitter) throws Exception {
                emitter.onNext("111");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe -> d =" + d);
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onSubscribe -> s=" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError -> e =" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete -> ");
            }
        });
    }


    public void from() {
        Observable.fromArray(new String[]{"0", "1", "2"})
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.i(TAG, "accept -> o =" + o);
                    }
                });
    }


    public void just() {
        Observable.just("111").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe -> ");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext -> s=" + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError -> e=" + e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete -> ");

            }
        });
    }
}
