package todolist.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.mainList);
        dataBase = new DataBase(this);

        populateListView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        populateListView();
    }

    public void callAddActivity(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }


    public void populateListView()
    {
        Log.d("ListDataActivity", "populateListView: Displaying data in the ListView.");

        Cursor data = dataBase.getData();
        ArrayList<String> nomeTask = new ArrayList<>();
        while (data.moveToNext())
        {
            String dataDeConclusao = data.getString(3);
            int ano = Integer.parseInt(dataDeConclusao.substring(0, 4));
            int mes = Integer.parseInt(dataDeConclusao.substring(4, 6));
            int dia = Integer.parseInt(dataDeConclusao.substring(6, 8));
            nomeTask.add(data.getString(1) + " - " + formatDate(ano, mes, dia));
        }

        final ListAdapter adapterNome = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, nomeTask);
        list.setAdapter(adapterNome);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                String name = adapterView.getItemAtPosition(i).toString();
                String nome[] = name.split("-");//'nome - data' -> 'nome ', ' data'
                nome[0] = nome[0].substring(0, nome[0].length()-1);//'nome ' -> 'nome' (tirando espaço no final)
                Log.d("ListDataActivity", "onItemClick: You Clicked on " + nome[0]);

                Cursor data = dataBase.getItemID(nome[0]);
                int itemID = -1;
                while(data.moveToNext())
                    itemID = data.getInt(0);

                if(itemID > -1)
                {
                    Log.d("ListDataActivity", "onItemClick: The ID is: " + itemID);
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("id", itemID);
                    startActivity(intent);
                }
                else
                    toastMessage("Nenhum ID associado com esse nome");
            }
        });

    }

    public void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public String formatDate(int y, int m, int d)
    {
        String ret = Integer.toString(d);
        ret = ret.concat(" de ");
        if(m == 1)
            ret = ret.concat("Janeiro");
        if(m == 2)
            ret = ret.concat("Fevereiro");
        if(m == 3)
            ret = ret.concat("Março");
        if(m == 4)
            ret = ret.concat("Abril");
        if(m == 5)
            ret = ret.concat("Maio");
        if(m == 6)
            ret = ret.concat("Junho");
        if(m == 7)
            ret = ret.concat("Julho");
        if(m == 8)
            ret = ret.concat("Agosto");
        if(m == 9)
            ret = ret.concat("Setembro");
        if(m == 10)
            ret = ret.concat("Outubro");
        if(m == 11)
            ret = ret.concat("Novembro");
        if(m == 12)
            ret = ret.concat("Dezembro");

        ret = ret.concat(" de ");
        ret = ret.concat(Integer.toString(y));

        return ret;
    }
}
