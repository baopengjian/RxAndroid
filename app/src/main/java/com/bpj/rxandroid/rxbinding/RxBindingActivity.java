package com.bpj.rxandroid.rxbinding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.bpj.rxandroid.R;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ray on 2018/3/8 .
 * compile 'com.jakewharton.rxbinding2:rxbinding:2.0.0'
 * 注意： EditText的搜索逻辑被封装到Search中去了，SearchService起到模拟网络的作用
 * 可以将Search和SearchService拷贝到一起方便理解
 */

public class RxBindingActivity extends AppCompatActivity {

    EditText ed, ed1;
    TextView tv, tv1;
    private String TAG = "RxBindingActivity";
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rxbinding_activity);
        ed = (EditText) findViewById(R.id.ed);
        tv = (TextView) findViewById(R.id.tv);
        ed1 = (EditText) findViewById(R.id.ed1);
        tv1 = (TextView) findViewById(R.id.tv1);
        compositeDisposable = new CompositeDisposable();


        initSearch();
        initSearch1();
    }

    /**
     * 普通联想搜索
     */
    private void initSearch() {
        //对搜索逻辑封装到了Search类中，SearchService模拟网络请求
        SearchService service = SearchService.getInstance();
        Disposable disposable = Search.rxBindingEt(ed, service, getDisposableObserver());
        compositeDisposable.add(disposable);
    }

    /**
     * 为空操作 + 联想搜索 + 定向搜索
     */
    private void initSearch1() {
        SearchService1 service1 = SearchService1.getInstance();
        Disposable disposable = Search1.rxBindingEt(ed1, service1, getDisposableObserver1());
        compositeDisposable.add(disposable);
    }

    /**
     * 定义搜索结果返回响应的Observer
     *
     * @return
     */
    private DisposableObserver<SearchResultPackage> getDisposableObserver1() {
        return new DisposableObserver<SearchResultPackage>() {
            @Override
            public void onNext(SearchResultPackage result) {
                switch (result.code) {
                    case 0:
                        tv1.setText("字符串为空操作");
                        break;
                    case 1:
                        tv1.setText("搜索列表：" + result.list.toString());
                        break;
                    case 2:
                        tv1.setText("定向搜索显示详情：" + result.detail.toString());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
    }

    /**
     * 定义搜索结果返回响应的Observer
     *
     * @return
     */
    private DisposableObserver<List<SearchResultBean>> getDisposableObserver() {
        return new DisposableObserver<List<SearchResultBean>>() {
            @Override
            public void onNext(List<SearchResultBean> strings) {
                Log.i(">>>", " strings=" + strings);
                if (strings.isEmpty()) {
                    tv.setText(null);
                } else {
                    tv.setText("发送给服务器的字符 = " + strings.toString());
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            compositeDisposable.clear();
        }
    }
}
