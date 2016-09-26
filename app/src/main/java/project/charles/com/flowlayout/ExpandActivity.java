package project.charles.com.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ExpandActivity extends AppCompatActivity implements View.OnClickListener {
    private com.charles.expandablerelativelayout.ExpandableRelativeLayout mCollapseView1;
    private ExpandableRelativeLayout mCollapseView2;
    private ExpandableRelativeLayout mCollapseView3;
    Button btn;
    LinearLayout ll_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);
        mCollapseView1 = (com.charles.expandablerelativelayout.ExpandableRelativeLayout) findViewById(R.id.collapseView1);
        ll_1 = (LinearLayout) findViewById(R.id.ll_1);
        ll_1.setOnClickListener(this);


        mCollapseView2 = (ExpandableRelativeLayout) findViewById(R.id.collapseView2);
        mCollapseView2.setNumber("2");
        mCollapseView2.setTitle("妹子");
        mCollapseView2.setContent(R.layout.expand_2);


        mCollapseView3 = (ExpandableRelativeLayout) findViewById(R.id.collapseView3);
        mCollapseView3.setNumber("3");
        mCollapseView3.setTitle("美女");
        mCollapseView3.setContent(R.layout.expand_3);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            mCollapseView1.toggle();
        } else {
            Toast.makeText(this, "tossss", Toast.LENGTH_SHORT).show();
        }
    }
}
