package weather.linksu.com.nethttplibrary.retrofit;

import android.content.Context;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * ================================================
 * 作    者：linksus
 * 版    本：1.0
 * 创建日期：9/12 0012
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private Disposable d;

    public BaseObserver() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onStartQuest();
        this.d = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        onQuestResult(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("Tamic", e.getMessage());
        // todo error somthing
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
        d.dispose();
    }

    @Override
    public void onComplete() {
        //todo miss loading
        onQuestComplete();
        d.dispose();
    }

    public abstract void onError(ExceptionHandle.ResponeThrowable e);

    public abstract void onQuestComplete();

    public abstract void onQuestResult(@NonNull T t);

    public abstract void onStartQuest();
}
