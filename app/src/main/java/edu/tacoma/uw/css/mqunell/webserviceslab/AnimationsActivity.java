package edu.tacoma.uw.css.mqunell.webserviceslab;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AnimationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animations);
    }

    public void animate(View view) {
        View v = findViewById(R.id.animate_me);
        AnimatorSet set = null;

        switch (view.getId()) {
            case R.id.btn_spin:
                set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.rotate_and_spin);
                break;

            case R.id.btn_move:
                set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.move);
                break;

            case R.id.btn_fade:
                set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.fade);
                break;
        }

        if (set != null) {
            set.setTarget(v);
            set.start();
        }
    }
}
