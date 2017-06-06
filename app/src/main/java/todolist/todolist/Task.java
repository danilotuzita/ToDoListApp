package todolist.todolist;

public class Task
{
    private int codigo;
    private String nome;
    private String descricao;
    private int dia[];

    public Task()
    {
         dia = new int[3];
    }

    public Task(int c, String n, String d, int y, int m, int di)
    {
        this.codigo = c;
        this.nome = n;
        this.descricao = d;
        dia = new int[3];
        dia[0] = y;
        dia[1] = m;
        dia[2] = di;
    }

    public Task(String n, String d, int y, int m, int di)
    {
        this.nome = n;
        this.descricao = d;
        dia = new int[3];

        dia[0] = y;
        dia[1] = m;
        dia[2] = di;
    }


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDia(int i, int d)
    {
        this.dia[i] = d;
    }

    public int getDia(int i)
    {
        return dia[i];
    }
}
