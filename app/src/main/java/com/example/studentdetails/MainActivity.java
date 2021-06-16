package com.example.studentdetails;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText id ,name;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        textView = (TextView) findViewById(R.id.textView);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void loaddata(View view){
        DBhandler handler = new DBhandler(this,null,null,1);
        textView.setText(handler.loadHandler());
        id.setText("");
        name.setText("");
    }

    public void adddata(View view){
        DBhandler handler = new DBhandler(this,null,null,1);
        int identity = Integer.parseInt(id.getText().toString());
        String sname = name.getText().toString();
        Student student = new Student(identity,sname);
        handler.addHandler(student);
        id.setText("");
        name.setText("");
    }

    public void finddata(View view){
        DBhandler handler = new DBhandler(this,null,null,1);
        Student student = handler.findHandler(name.getText().toString());
        if(student!=null){
            textView.setText(String.valueOf(student.getId())+""+student.getName()+ System.getProperty("line.seperator"));
            id.setText("");
            name.setText("");
        }else{
            textView.setText("No Match found");
            id.setText("");
            name.setText("");
        }
    }

    public void deltedata(View view){
        DBhandler handler = new DBhandler(this,null,null,1);
        boolean result = handler.deleteHandler(Integer.parseInt(id.getText().toString()));
        if(result){
            textView.setText("Record Deleted");
        }else{
            textView.setText("Record not found");
        }
        id.setText("");
        name.setText("");
    }
    
    public  void updatedata(View view){
        DBhandler handler = new DBhandler(this,null,null,1);
        boolean result = handler.updateHandler(Integer.parseInt(id.getText().toString()),name.getText().toString());
        if(result){
            textView.setText("record updated");
        }else{
            textView.setText("No match found");
        }
        id.setText("");
        name.setText("");
    }


}