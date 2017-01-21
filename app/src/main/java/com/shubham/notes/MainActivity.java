package com.shubham.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity
{

    static ArrayList<String> notes = new ArrayList<>();

    static ArrayAdapter arrayAdapter;

    static Set<String> set;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.shubham.notes", Context.MODE_PRIVATE);

         set =    sharedPreferences.getStringSet("notes",null);

        notes.clear();

        if (set !=null)
        {
            notes.addAll(set);
        }
        else
        {

            notes.add("Example note");

            set = new HashSet<String>();

            set.addAll(notes);
            sharedPreferences.edit().putStringSet("notes",set).apply();


        }

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {

                Intent i = new Intent(getApplicationContext(),EditYourNote.class);

                i.putExtra("noteId",position);

                startActivity(i);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l)
            {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                notes.remove(position);

                                SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("com.shubham.notes", Context.MODE_PRIVATE);


                                if(set == null)
                                {
                                    set = new HashSet<String>();
                                }
                                else
                                {
                                    set.clear();
                                }
                                set.addAll(notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                                arrayAdapter.notifyDataSetChanged();


                            }
                        })
                        .setNegativeButton("No",null)
                        .show();



                return false;
            }
        });





    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.add )

        {
            notes.add("");

            SharedPreferences sharedPreferences = this.getSharedPreferences("com.shubham.notes", Context.MODE_PRIVATE);


            if(set == null)
            {
                set = new HashSet<String>();
            }
            else
            {
                set.clear();
            }
            set.addAll(notes);

            arrayAdapter.notifyDataSetChanged();

            sharedPreferences.edit().putStringSet("notes",set).apply();
            sharedPreferences.edit().remove("notes").apply();



            Intent i = new Intent(getApplicationContext(),EditYourNote.class);
            i.putExtra("noteId", notes.size() -1);

            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }


}
