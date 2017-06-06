package todolist.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper{

    private static final int VERSAO = 1;
    private static final String BANCO_TASK = "db_tasks";
    private static final String NOME_TABELA = "db_tabela";

    private static final String COLUNA_0 = "codigo";
    private static final String COLUNA_1 = "nome";
    private static final String COLUNA_2 = "desc";
    private static final String COLUNA_3 = "data";



    public DataBase(Context context)
    {
        super(context, BANCO_TASK, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + NOME_TABELA + "(" + COLUNA_0 + " INTEGER PRIMARY KEY, " + COLUNA_1 + " TEXT, " +
                COLUNA_2 + " TEXT, " + COLUNA_3 + " TEXT)";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //==========CRUD===========//

    public boolean addTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUNA_1, task.getNome());
        cv.put(COLUNA_2, task.getDescricao());
        String data = Integer.toString(task.getDia(0));//ANO

        int mes = task.getDia(1); //Transformando o mes, por exemplo 5 para 05
        if(mes < 10)
            data = data.concat("0" + Integer.toString(mes));
        else
            data = data.concat(Integer.toString(mes));

        int dia = task.getDia(2);
        if(dia < 10)
            data = data.concat("0" + Integer.toString(dia));
        else
            data = data.concat(Integer.toString(dia));

        cv.put(COLUNA_3, data);

        long result = db.insert(NOME_TABELA, null, cv);

        db.close();
        if(result == -1)
            return false;
        return true;
    }

    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "SELECT * FROM " + NOME_TABELA;
        return db.rawQuery(QUERY, null);
    }

    public Cursor getItemID(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "SELECT " + COLUNA_0 + " FROM " + NOME_TABELA + " WHERE " + COLUNA_1 + " = '" + name + "'";

        return db.rawQuery(QUERY, null);
    }

    public Cursor getItem(int ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "SELECT * FROM " + NOME_TABELA + " WHERE " + COLUNA_0 + " = '" + ID + "'";

        return db.rawQuery(QUERY, null);
    }

    public void updateNome(String novoNome, int ID, String antigoNome)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "UPDATE " + NOME_TABELA + " SET " + COLUNA_1 + " = '" + novoNome + "' WHERE " + COLUNA_0 + " = '" + ID + "' AND " + COLUNA_1 + " = '" + antigoNome + "'";

        Log.d("ListDataActivity", "updateName: query: " + QUERY);
        Log.d("ListDataActivity", "updateName: Renaming: " + antigoNome + " to " + novoNome);
        db.execSQL(QUERY);
    }

    public void deleteItem(int ID, String nome)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String QUERY = "DELETE FROM " + NOME_TABELA + " WHERE " + COLUNA_0 + " = '" + ID + "' AND " + COLUNA_1 + " = '" + nome + "'";

        Log.d("ListDataActivity", "deleteName: query: " + QUERY);
        Log.d("ListDataActivity", "deleteName: Deleting: " + nome + "from database.");
        db.execSQL(QUERY);
    }

}
