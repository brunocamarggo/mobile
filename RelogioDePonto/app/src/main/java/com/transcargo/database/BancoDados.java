package com.transcargo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.transcargo.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME_BANCO = "db_relogio";

    private static final String TABELA_FUNCIONARIOS = "tb_funcionarios";
    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_DATA_NASCIMENTO = "data_nascimento";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_SEXO = "sexo";
    private static final String COLUNA_CARGO = "cargo";
    private static final String COLUNA_ESTADO = "estado";


    public BancoDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String  QUERY = "CREATE TABLE " + TABELA_FUNCIONARIOS + " ("
                + COLUNA_ID + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT,"
                + COLUNA_DATA_NASCIMENTO + " TEXT, " + COLUNA_TELEFONE + " TEXT, " + COLUNA_SEXO + " INTEGER,"
                + COLUNA_CARGO + " INTEGER," + COLUNA_ESTADO + " INTEGER)";

        db.execSQL(QUERY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addFuncionario(Funcionario funcionario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, funcionario.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, funcionario.getDataNasc());
        values.put(COLUNA_TELEFONE, funcionario.getTelefone());
        values.put(COLUNA_SEXO, funcionario.getSexo());
        values.put(COLUNA_CARGO, funcionario.getCargo());
        values.put(COLUNA_ESTADO, funcionario.getEstado());

        db.insert(TABELA_FUNCIONARIOS, null, values);
        db.close();
    }

    public void removeFuncionario(Funcionario funcionario){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABELA_FUNCIONARIOS, COLUNA_ID + " =  ?", new String[] { String.valueOf(funcionario.getId())});
        db.close();

    }

    public Funcionario selecionarFuncionario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABELA_FUNCIONARIOS, new String[] {
                COLUNA_ID, COLUNA_NOME,
                COLUNA_DATA_NASCIMENTO,
                COLUNA_TELEFONE,
                COLUNA_SEXO,
                COLUNA_CARGO,
                        COLUNA_ESTADO}, COLUNA_ID + " = ?",
                new String[] {String.valueOf(id)},
                null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }
        Funcionario func = new Funcionario(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)));

        return func;
    }

    public void atualizaFuncionario(Funcionario funcionario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, funcionario.getNome());
        values.put(COLUNA_DATA_NASCIMENTO, funcionario.getDataNasc());
        values.put(COLUNA_TELEFONE, funcionario.getTelefone());
        values.put(COLUNA_SEXO, funcionario.getSexo());
        values.put(COLUNA_CARGO, funcionario.getCargo());
        values.put(COLUNA_ESTADO, funcionario.getEstado());

        db.update(TABELA_FUNCIONARIOS, values, COLUNA_ID + " = ?",
                new String[]{ String.valueOf(funcionario.getId()) });
        db.close();
    }

    public List<Funcionario> listaTodosFuncionarios() {
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        String query = "SELECT * FROM " + TABELA_FUNCIONARIOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()){
            do {
                int id = Integer.parseInt(c.getString(0));
                String nome = c.getString(1);
                String datanasc = c.getString(2);
                String telefone = c.getString(3);
                int sexo = Integer.parseInt(c.getString(4));
                int cargo = Integer.parseInt(c.getString(5));
                int estado = Integer.parseInt(c.getString(6));
              Funcionario funcionario = new Funcionario(id, nome, datanasc, telefone, sexo, cargo, estado);
              listaFuncionarios.add(funcionario);
            } while (c.moveToNext());
        }
        // db.execSQL("DROP TABLE "+ TABELA_FUNCIONARIOS);
        return listaFuncionarios;
    }
}
