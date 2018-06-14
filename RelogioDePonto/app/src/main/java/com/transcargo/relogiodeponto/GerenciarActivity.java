package com.transcargo.relogiodeponto;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.transcargo.database.BancoDados;
import com.transcargo.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class GerenciarActivity extends AppCompatActivity {

    BancoDados db = new BancoDados(this);

    ListView listViewFuncionarios;
    TextView idView;
    TextView nomeView;
    TextView dataNascView;
    TextView telefoneView;

    RadioGroup radioGroup;
    CheckBox estadoView;

    Button limparBtnView;
    Button salvarBtnView;
    Button excluirBtnView;

    Spinner spinnerCargo;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idView = findViewById(R.id.edit_id);
        nomeView = findViewById(R.id.edit_nome);
        dataNascView = findViewById(R.id.edit_data_nasc);
        telefoneView = findViewById(R.id.edit_telefone);
        radioGroup = findViewById(R.id.radio_group);
        estadoView = findViewById(R.id.checkbox_estado);

        limparBtnView = findViewById(R.id.btn_limpar);
        limparBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpaCampos();
            }
        });

        excluirBtnView = findViewById(R.id.btn_excluir);
        excluirBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = idView.getText().toString();
                if (id.isEmpty()){
                    Toast.makeText(GerenciarActivity.this, getResources().getString(R.string.msg_nenhum_funcionario_selecionado), Toast.LENGTH_LONG).show();
                }
                else {

                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(GerenciarActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(GerenciarActivity.this);
                    }
                    builder.setTitle(getResources().getString(R.string.msg_deletar_funcionario))
                            .setMessage(getResources().getString(R.string.msg_confirmacao))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Funcionario funcionario = new Funcionario();
                                    funcionario.setId(Integer.parseInt(id));
                                    db.removeFuncionario(funcionario);
                                    Toast.makeText(GerenciarActivity.this, getResources().getString(R.string.msg_removido_com_sucesso), Toast.LENGTH_SHORT).show();
                                    limpaCampos();
                                    populaListViewFuncionarios();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                }

            }


        });

        salvarBtnView = findViewById(R.id.btn_salvar);
        salvarBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idView.getText().toString();
                String nome = nomeView.getText().toString();
                String dataNasc = dataNascView.getText().toString();
                String telefone = telefoneView.getText().toString();
                int sexo = -1;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.radio_masculino) {
                    sexo = R.id.radio_masculino;
                }
                else if (checkedRadioButtonId == R.id.radio_feminino){
                    sexo =  R.id.radio_feminino;
                }
                int cargo = spinnerCargo.getSelectedItemPosition();
                int estado = -1;
                if(estadoView.isChecked()){
                    estado = 1;
                }
                else {
                    estado = 0;
                }



                if(id.isEmpty()){
                    // inserir
                    if(nome.isEmpty()){
                        nomeView.setError(getResources().getString(R.string.msg_obrigatorio));
                    }
                    else{
                        db.addFuncionario(new Funcionario(nome, dataNasc, telefone, sexo, cargo, estado));
                        Toast.makeText(GerenciarActivity.this, getResources().getString(R.string.msg_cadastrado_com_sucesso), Toast.LENGTH_SHORT).show();
                        populaListViewFuncionarios();
                        limpaCampos();
                    }
                }
                else{
                    // atualizar
                    db.atualizaFuncionario(new Funcionario(Integer.parseInt(id), nome, dataNasc, telefone, sexo, cargo, estado));
                    Toast.makeText(GerenciarActivity.this, getResources().getString(R.string.msg_atualizado_com_sucesso), Toast.LENGTH_SHORT).show();
                    populaListViewFuncionarios();
                    limpaCampos();
                }

            }
        });


        listViewFuncionarios = findViewById(R.id.listViewFuncionarios);

        listViewFuncionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String conteudo = (String) listViewFuncionarios.getItemAtPosition(position);

                String codigo = conteudo.substring(0, conteudo.indexOf(" -"));

                Funcionario funcionario = db.selecionarFuncionario(Integer.parseInt(codigo));
                idView.setText(""+funcionario.getId());
                nomeView.setText(funcionario.getNome());
                dataNascView.setText(funcionario.getDataNasc());
                telefoneView.setText(funcionario.getTelefone());
                radioGroup.check(funcionario.getSexo());
                spinnerCargo.setSelection(funcionario.getCargo());
                if (funcionario.getEstado() == 1){
                    estadoView.setChecked(true);
                }




            }
        });



        spinnerCargo = (Spinner) findViewById(R.id.edit_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cargos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCargo.setAdapter(adapter);

        populaListViewFuncionarios();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void populaListViewFuncionarios(){
        List<Funcionario> funcionarioList = db.listaTodosFuncionarios();
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(GerenciarActivity.this, android.R.layout.simple_list_item_1, arrayList);
        listViewFuncionarios.setAdapter(adapter);

        for(Funcionario funcionario: funcionarioList) {
            arrayList.add(funcionario.getId() + " - " + funcionario.getNome());
        }

    }

    private void limpaCampos() {
        idView.setText("");
        nomeView.setText("");
        dataNascView.setText("");
        telefoneView.setText("");
        radioGroup.check(0);
        spinnerCargo.setSelection(0);
        estadoView.setChecked(false);
    }
}
