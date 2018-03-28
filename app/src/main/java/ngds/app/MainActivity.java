package ngds.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import cn.com.ngds.lib.network.Const;
import cn.com.ngds.lib.network.HttpTaskClient;
import ngds.app.retrofit.ApiRetrofit;
import rx.functions.Action0;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        request();
    }

    private void request() {
        HttpTaskClient.request(this, ApiRetrofit.getTest().Test2())
                .type(Const.CACHE_FIRST)
                .onStart(new Action0() {
                    @Override
                    public void call() {
                        textView.setText("onStart");
                        Log.e("wyt", "onStart ");
                    }
                })
                .onSuc(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        textView.setText(s);
                        Log.e("wyt", "onSuc " + s);
                    }
                })
                .onFail(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        textView.setText(throwable.getMessage());
                        Log.e("wyt", "onFail " + throwable.getMessage());
                    }
                }).call();
    }
}
