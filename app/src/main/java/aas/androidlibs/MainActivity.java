package aas.androidlibs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1= (Button) findViewById(R.id.button1);
        button2= (Button) findViewById(R.id.button2);
        button1.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,MultiItemActivity.class);
            startActivity(intent);
        });
        button2.setOnClickListener(v -> {
            Intent intent=new Intent(MainActivity.this,PullToRefreshActivity.class);
            startActivity(intent);
        });
    }
}
