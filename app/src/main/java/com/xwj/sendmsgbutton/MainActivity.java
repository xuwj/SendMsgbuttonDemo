package com.xwj.sendmsgbutton;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements SendMsgButton.OnSendClickListener {
    private EditText mMsgEt;
    private SendMsgButton mSendMsgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mMsgEt = (EditText) findViewById(R.id.et_msg_content);
        mSendMsgBtn = (SendMsgButton) findViewById(R.id.btnSendMsg);
        mSendMsgBtn.setOnSendClickListener(this);
    }

    private boolean validateComment() {
        if (TextUtils.isEmpty(mMsgEt.getText())) {
            mSendMsgBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendClickListener(View v) {
        if (validateComment()) {
            Toast.makeText(this,mMsgEt.getText(),SendMsgButton.RESET_STATE_DELAY_MILLIS).show();
            mMsgEt.setText(null);
            mSendMsgBtn.setCurrentState(SendMsgButton.STATE_DONE);
        }
    }
}
