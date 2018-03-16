package com.berry_med.handsetmonitor;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.berry_med.handsetmonitor.bluetooth.BluetoothUtils;
import com.berry_med.handsetmonitor.wave.Wave;
import com.berry_med.handsetmonitor.wave.WaveParse;

/**
 * Created by YF on 2018/3/16.
 */

public class ScoreActivity extends Activity implements View.OnClickListener {


    public static TextView tvCancel;
    public static TextView tvOk;
    public static RadioButton eye1;
    public static RadioButton eye2;
    public static RadioButton eye3;
    public static RadioButton eye4;

    public static RadioButton langue1;
    public static RadioButton langue2;
    public static RadioButton langue3;
    public static RadioButton langue4;
    public static RadioButton langue5;

    public static RadioButton run1;
    public static RadioButton run2;
    public static RadioButton run3;
    public static RadioButton run4;
    public static RadioButton run5;
    public static RadioButton run6;


    public static RadioGroup rgEyes;

    private int score1 = 0;
    private int score2 = 0;
    private int score3 = 0;

    private int score5 = 0;//血压分数
    private int score6 = 0;//呼吸率分数

    private int sum = 0;

    private String[] type = new String[]{"轻伤", "中度伤", "重症伤", "危重症伤"};
    private String[] opinion = new String[]{"轻伤建\n议常规处置", "中度伤\n建议优先处置", "重症伤\n建议紧急处置", "危重症伤\n建议期待处置"};
    private String[] eyes = new String[]{"自动睁眼", "呼唤睁眼", "刺痛睁眼", "不睁眼"};
    private String[] langues = new String[]{"回答切题", "回答不切题", "答非所问", "只能发音", "不能言语"};
    private String[] runs = new String[]{"按吩咐动作", "刺痛定位", "刺痛躲避", "刺痛屈曲", "刺痛过度伸展", "不能活动"};

    int eye = 0;
    int langue = 0;
    int run = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score);

        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvOk = (TextView) findViewById(R.id.tvOk);

        eye1 = (RadioButton) findViewById(R.id.eye1);
        eye2 = (RadioButton) findViewById(R.id.eye2);
        eye3 = (RadioButton) findViewById(R.id.eye3);
        eye4 = (RadioButton) findViewById(R.id.eye4);

        langue1 = (RadioButton) findViewById(R.id.langue1);
        langue2 = (RadioButton) findViewById(R.id.langue2);
        langue3 = (RadioButton) findViewById(R.id.langue3);
        langue4 = (RadioButton) findViewById(R.id.langue4);
        langue5 = (RadioButton) findViewById(R.id.langue5);

        run1 = (RadioButton) findViewById(R.id.run1);
        run2 = (RadioButton) findViewById(R.id.run2);
        run3 = (RadioButton) findViewById(R.id.run3);
        run4 = (RadioButton) findViewById(R.id.run4);
        run5 = (RadioButton) findViewById(R.id.run5);
        run6 = (RadioButton) findViewById(R.id.run6);

        rgEyes = (RadioGroup) findViewById(R.id.eye_rg);

        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                //Log.e("TAGG", "" + rgEyes.getCheckedRadioButtonId());

                MainActivity.vwDivider0.setVisibility(View.VISIBLE);
                MainActivity.vwDivider1.setVisibility(View.VISIBLE);
                MainActivity.vwDivider2.setVisibility(View.VISIBLE);
                MainActivity.rlSpO2.setVisibility(View.VISIBLE);
                MainActivity.rlECG.setVisibility(View.VISIBLE);
                MainActivity.llNTC.setVisibility(View.VISIBLE);
                MainActivity.rlNIBP_TEMP.setVisibility(View.VISIBLE);
                MainActivity.llBCR.setVisibility(View.VISIBLE);
                MainActivity.llBT.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                MainActivity.llCFG.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                MainActivity.llBTSelect.setVisibility(View.GONE);
                MainActivity.llCfgDedail.setVisibility(View.GONE);
                MainActivity.mECGWaveDraw.setVisibility(true);
                MainActivity.mSpO2WaveDraw.setVisibility(true);

                MainActivity.llConsciousness.setVisibility(View.VISIBLE);
                MainActivity.llScore.setVisibility(View.VISIBLE);
                MainActivity.llEcg.setVisibility(View.VISIBLE);
                finish();
                break;
            case R.id.tvOk:
                if (MainActivity.tvHighPressure.getText().toString().equals("--.-") || MainActivity.tvResp1.getText().toString().equals("--")) {
                    Toast.makeText(ScoreActivity.this, "血压或呼吸率没有值！", Toast.LENGTH_LONG).show();
                } else {
                    //region 睁眼动作
                    if (eye1.isChecked()) {
                        score1 = 4;
                        eye = 0;
                    }
                    if (eye2.isChecked()) {
                        score1 = 3;
                        eye = 1;
                    }
                    if (eye3.isChecked()) {
                        score1 = 2;
                        eye = 2;
                    }
                    if (eye4.isChecked()) {
                        score1 = 1;
                        eye = 3;
                    }
                    //endregion

                    //region 语言反应
                    if (langue1.isChecked()) {
                        score2 = 5;
                        langue = 0;
                    }
                    if (langue2.isChecked()) {
                        score2 = 4;
                        langue = 1;
                    }
                    if (langue3.isChecked()) {
                        score2 = 3;
                        langue = 2;
                    }
                    if (langue4.isChecked()) {
                        score2 = 2;
                        langue = 3;
                    }
                    if (langue5.isChecked()) {
                        score2 = 1;
                        langue = 4;
                    }

                    //endregion

                    //region 运动反应
                    if (run1.isChecked()) {
                        score3 = 5;
                        run = 0;
                    }
                    if (run2.isChecked()) {
                        score3 = 4;
                        run = 1;
                    }
                    if (run3.isChecked()) {
                        score3 = 3;
                        run = 2;
                    }
                    if (run4.isChecked()) {
                        score3 = 2;
                        run = 3;
                    }
                    if (run5.isChecked()) {
                        score3 = 1;
                        run = 4;
                    }
                    //endregion

                    if (!MainActivity.tvHighPressure.getText().toString().equals("---")) {
                        int bp = Integer.valueOf(MainActivity.tvHighPressure.getText().toString());
                        if (bp > 89)
                            score5 = 4;
                        if (bp <= 89 && bp >= 76)
                            score5 = 3;
                        if (bp <= 75 && bp >= 50)
                            score5 = 2;
                        if (bp <= 49 && bp >= 1)
                            score5 = 1;
                        if (bp < 1)
                            score5 = 0;
                    }
                    if (!MainActivity.tvResp1.getText().toString().equals("--")) {
                        int resp = Integer.valueOf(MainActivity.tvResp1.getText().toString());

                        if (resp > 29)
                            score6 = 3;
                        if (resp <= 29 && resp >= 6)
                            score6 = 2;
                        if (resp <= 5 && resp >= 1)
                            score6 = 1;
                        if (resp == 0)
                            score6 = 0;
                    }
                    sum = score1 + score2 + score3 + score5 + score6;

                    MainActivity.tvScore.setText(String.valueOf(sum));

                    MainActivity.tvEyes.setText(eyes[eye]);
                    MainActivity.tvLangue.setText(langues[langue]);
                    MainActivity.tvRun.setText(runs[run]);

                    if (sum >= 12)
                        MainActivity.tvAdvise.setText(opinion[0]);
                    if (sum >= 10 && sum <= 11)
                        MainActivity.tvAdvise.setText(opinion[1]);
                    if (sum >= 6 && sum <= 9)
                        MainActivity.tvAdvise.setText(opinion[2]);
                    if (sum <= 5)
                        MainActivity.tvAdvise.setText(opinion[3]);

                    MainActivity.vwDivider0.setVisibility(View.VISIBLE);
                    MainActivity.vwDivider1.setVisibility(View.VISIBLE);
                    MainActivity.vwDivider2.setVisibility(View.VISIBLE);
                    MainActivity.rlSpO2.setVisibility(View.VISIBLE);
                    MainActivity.rlECG.setVisibility(View.VISIBLE);
                    MainActivity.llNTC.setVisibility(View.VISIBLE);
                    MainActivity.rlNIBP_TEMP.setVisibility(View.VISIBLE);
                    MainActivity.llBCR.setVisibility(View.VISIBLE);
                    MainActivity.llBT.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    MainActivity.llCFG.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f));
                    MainActivity.llBTSelect.setVisibility(View.GONE);
                    MainActivity.llCfgDedail.setVisibility(View.GONE);
                    MainActivity.mECGWaveDraw.setVisibility(true);
                    MainActivity.mSpO2WaveDraw.setVisibility(true);

                    MainActivity.llConsciousness.setVisibility(View.VISIBLE);
                    MainActivity.llScore.setVisibility(View.VISIBLE);
                    MainActivity.llEcg.setVisibility(View.VISIBLE);
                    finish();
                }
                break;
        }
    }

}
