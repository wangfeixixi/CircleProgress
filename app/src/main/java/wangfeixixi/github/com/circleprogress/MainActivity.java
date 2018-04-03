package wangfeixixi.github.com.circleprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wangfeixixi.utilcirclerprogress.CircleProgress;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient mHttpClient;
    private LottieAnimationView animationView;
    private CircleProgress circleProgress;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestBlog();

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgress.reset();
                if (animationView.isAnimating()) {
                    animationView.setVisibility(View.GONE);
                    animationView.cancelAnimation();
                }
            }
        });

        animationView = (LottieAnimationView)
                findViewById(R.id.animation_view);

        circleProgress = findViewById(R.id.pb_main_demo7);


        circleProgress.setOnProcessListener(new CircleProgress.OnProccessListener() {
            @Override
            public void proccess(int process) {
                if (process == 100) {
                    try {
                        String animate = SpBigUtil.getString("animate", null);
                        Log.d("aaaaaaaaaaa", "animate" + String.valueOf(animate == null));
                        if (animate == null) return;
                        LottieComposition.Factory.fromJson(getResources(), new JSONObject(animate),
                                new OnCompositionLoadedListener() {
                                    @Override
                                    public void onCompositionLoaded(LottieComposition composition) {
                                        animationView.setVisibility(View.VISIBLE);
                                        animationView.setComposition(composition);
                                        animationView.playAnimation();
                                    }
                                });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (process == 0) {
                    if (animationView.isAnimating()) {
                        animationView.setVisibility(View.GONE);
                        animationView.cancelAnimation();
                    }
                }
            }
        });
    }


    public void requestBlog() {
        String url = "http://p3tnh1dg6.bkt.clouddn.com/btn_bg.json";

        mHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
/* okhttp3.Response response = null;*/

         /*response = mHttpClient.newCall(request).execute();*/
        mHttpClient.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                json = response.body().string();
                SpBigUtil.putString("animate", json);
//                Log.d("aaaaaa", json);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                });

            }
        });


    }

}
