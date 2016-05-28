package throwdown.trotro.app.robin.util;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

public class RxEmitters {

    public static Observable<CharSequence> onTextChangeListener(@NonNull EditText editText) {
        return Observable.create(new TextViewTextOnSubscribe(editText));
    }


    static class TextViewTextOnSubscribe implements Observable.OnSubscribe<CharSequence> {
        final TextView view;

        TextViewTextOnSubscribe(TextView view) {
            this.view = view;
        }

        @Override
        public void call(final Subscriber<? super CharSequence> subscriber) {
            MainThreadSubscription.verifyMainThread();
            final TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(s);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            view.addTextChangedListener(watcher);

            subscriber.add(new MainThreadSubscription() {
                @Override
                protected void onUnsubscribe() {
                    view.removeTextChangedListener(watcher);
                }
            });
            subscriber.onNext(view.getText());
        }
    }

    public static Observable<CharSequence> textEmitter(@NonNull EditText editText) {
        return Observable.create(new TextViewTextOnSubscribe(editText));
    }
}
