package net.unadeca.arbolito.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import net.unadeca.arbolito.R;
import net.unadeca.arbolito.database.models.Arbolito;
import net.unadeca.arbolito.database.models.Arbolito_Table;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private CoordinatorLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lista = findViewById(R.id.lista);
        view = findViewById(R.id.content);
        setAdapter();

//        Arbolito pino = new Arbolito();
//        pino.altura = 4;
//        pino.fecha_plantado = "2019-01-01";
//        pino.fecha_ultima_revision = "2019-02-01";
//        pino.plantado_por = "Juan Martinez";
//        pino.save();


//        Arbolito cedro = new Arbolito();
//        cedro.altura = 10;
//        cedro.fecha_plantado = "2017-01-01";
   //     cedro.fecha_ultima_revision = "2019-02-01";
 //       cedro.plantado_por = "Martin Perez";
//        cedro.save();

       //borrarArbolito();





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ej 1
//                Arbolito  test = SQLite.select().from(Arbolito.class).querySingle();
//                Arbolito cedro = SQLite.select().from(Arbolito.class).where(Arbolito_Table.altura.eq(10)).querySingle();
//                Snackbar.make(view, cedro.altura + " "+ cedro.plantado_por, Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                cedro.plantado_por = "Pablito";
//                cedro.save();

                //ej 2
//                long contadorArbolitos = SQLite.selectCountOf().from(Arbolito.class).count();
//                Snackbar.make(view, "en este momento hay "+ contadorArbolitos +" arbolitos registrados", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                //implementacion del dialogo
                mostrarDialogo();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private String[] getArbolitos(){

        List<Arbolito> listado = SQLite.select().from(Arbolito.class).queryList();
        String[] array = new String[listado.size()];
        for (int c=0; c < listado.size(); c++){
            array[c] = listado.get(c).toString();
        }
        return array;
    }
    //para estblecer el adaptador
    private void setAdapter(){
        lista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getArbolitos()));
    }

    public void mostrarDialogo(){
        //dialogo personalizado
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View formulario = layoutInflater.inflate(R.layout.formulario, null);

        //para crear alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //creo el dialogo formulario
        builder.setView(formulario);
        final TextInputLayout altura = formulario.findViewById(R.id.txtAltura);
        final TextInputLayout siembra = formulario.findViewById(R.id.txtFecha_siembra);
        final TextInputLayout revision = formulario.findViewById(R.id.txtCheck);
        final TextInputLayout encargado = formulario.findViewById(R.id.txtEncargado);

        builder.setMessage("Rellene toda la infromacion solicitada")
                .setTitle("Crear nuevo arbolito").setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    validate(altura, siembra, revision, encargado);
                    guardarABD(altura, siembra, revision, encargado);
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                dialog.dismiss();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialogo = builder.create();
        dialogo.show();
    }
    //funcion para validar los campos
    private void validate(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e)throws Exception{
        if (a.getEditText().getText().toString().isEmpty())
            throw  new Exception("Necesita escribir la altura del arbolito");

        if (s.getEditText().getText().toString().isEmpty())
            throw  new Exception("Necesita escribir la fecha de siembra del arbolito");

        if (c.getEditText().getText().toString().isEmpty())
            throw  new Exception("Necesita escribir la fecha de la ultima revision del arbolito");

        if (e.getEditText().getText().toString().isEmpty())
            throw  new Exception("Necesita escribir el encargado del arbolito");
    }

    //funcion para guardar
    private void guardarABD(TextInputLayout a, TextInputLayout s, TextInputLayout c, TextInputLayout e){
        Arbolito arbolito = new Arbolito();
        arbolito.altura = Integer.parseInt(a.getEditText().getText().toString());
        arbolito.fecha_plantado = s.getEditText().getText().toString();
        arbolito.fecha_ultima_revision = c.getEditText().getText().toString();
        arbolito.plantado_por = e.getEditText().getText().toString();
        arbolito.save();

        Snackbar.make(view, "Se ha guarado el arbolito", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        setAdapter();
    }
    private void borrarArbolito(){
        //Delete.table(Arbolito.class);
        SQLite.delete().from(Arbolito.class).where(Arbolito_Table.altura.between(1).and(10)).execute();
        setAdapter();

        Snackbar.make(view, "Hemos borrado el listado de arbolitos!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
