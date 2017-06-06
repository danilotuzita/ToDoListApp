package todolist.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    String name, desc;
    EditText editName, editDesc;
    Button btnDate;
    int year, month, day;
    static final int DIALOG_ID = 0;

    DataBase db = new DataBase(this);////CONSERTAR, tenho que criar no main e mandar para essa activity!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);
        btnDate = (Button) findViewById(R.id.btnDate);
        editDesc = (EditText) findViewById(R.id.txtTaskDesc);
        editName = (EditText) findViewById(R.id.txtTaskName);
        btnDate.setText(formatDate(year, month + 1, day));

        showDialogButton();
    }

    public void showDialogButton()
    {
        btnDate.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    showDialog(DIALOG_ID);
                }
            }
        );
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id == DIALOG_ID)
            return new DatePickerDialog(this, dPickerListener, year, month, day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dPickerListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d)
        {
            year = y;
            day = d;
            month = m+1;
            btnDate.setText(formatDate(year, month, day));
        }
    };


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


    public void addTask(View view)
    {
        name = editName.getText().toString();
        desc = editDesc.getText().toString();
        if(name.equals("") || desc.equals(""))//Não Adiciona
        {
            if(name.equals(""))
                toastMessage("Por favor, dê um nome a sua tarefa.");
            else
                toastMessage("Por favor, coloque uma descrição a sua tarefa.");
            return;
        }

        Task t = new Task(name, desc, year, month, day);

        boolean result = db.addTask(t);
        if(result)
            toastMessage("SUCESSO NA INCLUSÃO!!!");
        else
            toastMessage("FALHA NA INCLUSÃO!!!");

        finish();
    }

    public void cancelTask(View view)
    {
        finish();
    }

    public void toastMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
