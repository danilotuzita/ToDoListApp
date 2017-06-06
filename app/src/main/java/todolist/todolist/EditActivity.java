package todolist.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private TextView txtData, txtDesc;
    private EditText txtNome;
    private int selectedID;
    private String selectedName;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtNome = (EditText) findViewById(R.id.txtName);
        txtData = (TextView) findViewById(R.id.txtData);
        txtDesc = (TextView) findViewById(R.id.txtDesc);

        dataBase = new DataBase(this);

        Intent recivedIntent = getIntent();

        selectedID = recivedIntent.getIntExtra("id", -1);

        Cursor data = dataBase.getItem(selectedID);

        while(data.moveToNext())
        {
            selectedName = data.getString(1);
            txtNome.setText(selectedName);//Nome da Tarefa

            String dataDeConclusao = data.getString(3);//Data da Tarefa
            int ano = Integer.parseInt(dataDeConclusao.substring(0, 4));
            int mes = Integer.parseInt(dataDeConclusao.substring(4, 6));
            int dia = Integer.parseInt(dataDeConclusao.substring(6, 8));
            txtData.setText(formatDate(ano, mes, dia));

            txtDesc.setText(data.getString(2));//Descricao da Tarefa
        }
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


    public void completaTarefa(View view)
    {
        dataBase.deleteItem(selectedID, selectedName);
        toastMessage("Deletando Tarefa: " + selectedName);
        finish();
    }

    public void cancela(View view)
    {
        finish();
    }

    public void atualizaTarefa(View view)
    {
        String novo = txtNome.getText().toString();
        if(novo.length() == 0 || novo.equals(selectedName))
            toastMessage("Por favor de um nome a sua tarefa!!!");
        else
        {
            dataBase.updateNome(novo, selectedID, selectedName);
            toastMessage("Tarefa Atualizada com Sucesso!!!");
            finish();
        }
    }
}
