package com.transcargo.relogiodeponto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.transcargo.database.BancoDados;
import com.transcargo.model.Funcionario;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS = "PrefsColor";

    Button cadastrar;
    Button sobre;
    BancoDados db = new BancoDados(this);
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cadastrar = findViewById(R.id.main_cadastrar);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String cor = prefs.getString("color", "#FFFFFF");
        Log.e("RAG", "---> "+cor);



        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor(cor));

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCadastrar = new Intent(MainActivity.this, GerenciarActivity.class);
                startActivity(intentCadastrar);

            }
        });

        sobre = findViewById(R.id.btn_sobre);
        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intentCadastrar = new Intent(MainActivity.this, SobreActivity.class);
                // startActivity(intentCadastrar);
                Random random = new Random();
                int nextInt = random.nextInt(256*256*256);
                String colorCode = String.format("#%06x", nextInt);

                // print it
                System.out.println(colorCode);
                toolbar.setBackgroundColor(Color.parseColor(colorCode));

                SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
                editor.putString("color", colorCode);
                editor.apply();

            }
        });

        toolbar.setBackgroundColor(Color.parseColor("#80000000"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sobre) {
           Intent intent = new Intent(this, SobreActivity.class);
           startActivity(intent);
        }
        if (id == R.id.action_sair) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String cor = prefs.getString("color", "#FFFFFF");
        toolbar.setBackgroundColor(Color.parseColor(cor));
        super.onResume();
    }
}
