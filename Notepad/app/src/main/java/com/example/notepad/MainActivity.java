package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    EditText txtwords;
    TextView txtSize;
    Button btnBig;
    Button btnSmall;
    CheckBox cbxBold;
    CheckBox cbxUnderLine;
    Spinner sColor;
    Spinner sFont;
    RadioButton rdoLTR;
    RadioButton rdoRTL;
    EditText txtFileName;
    Button btnNew;
    Button btnSave;
    Button btnGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtwords =(EditText) findViewById(R.id.txtWords);
        txtSize =(TextView) findViewById(R.id.txtSize);
        btnBig =(Button)  findViewById(R.id.btnBig);
        btnSmall =(Button)  findViewById(R.id.btnSmall);
        cbxUnderLine =(CheckBox)  findViewById(R.id.cbxUnderLine);
        cbxBold =(CheckBox) findViewById(R.id.cbxBold);
        sColor=(Spinner) findViewById(R.id.spinnerColor);
        sFont =(Spinner) findViewById(R.id.spinnerFont);
        rdoLTR =(RadioButton)  findViewById(R.id.rdoLTR);
        rdoRTL =(RadioButton) findViewById(R.id.rdoRTL);
        txtFileName=(EditText) findViewById(R.id.txtFileName);
        btnNew =(Button)findViewById(R.id.btnNew);
        btnSave =(Button)findViewById(R.id.btnSave);
        btnGet =(Button)findViewById(R.id.btnGet);
        fillColor(); //to fill the spiner of colores
        fillFonts();
        btnBig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWordsSize('+');
            }
        });
        btnSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWordsSize('-');
            }
        });
        cbxBold.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setBold();
            }
        });
        cbxUnderLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setUnderLine();
            }
        });
        sColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setWordsColor();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sFont.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setWordsFont();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rdoLTR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAlign();
            }
        });
        rdoRTL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setAlign();
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFile();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile();
            }
        });
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile();
            }
        });
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED) {
                String permissions[] = {WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(this, permissions, 1);
            } else {

            }
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void fillColor(){
        String [] colors={
          "Black","Red","Blue","Green","Grey","Orange","navy","Brown","yellow"
        };
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,colors);
        sColor.setAdapter(adapter);
    }
    protected void fillFonts(){
        String [] fonts={
                "sans-serif","Authentic Script Rough","Brilliant Signature","Gelowing","Angeline Vintage_Demo","RemachineScript_Personal_Use"
        };
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,fonts);
        sFont.setAdapter(adapter);
    }
    protected  void setWordsSize(char bigOrSmall){
        int size=Integer.parseInt(txtSize.getText().toString());
        if(bigOrSmall=='+'){
            size++;
        }else{
            size--;
        }
        if(size>99){
            size=99;
        }else if(size<6){
            size=6;
        }
        txtwords.setTextSize(size);
        txtSize.setText(size+"");
    }
    protected void setBold(){
        if(cbxBold.isChecked()){
            txtwords.setTypeface(null, Typeface.BOLD);
        }else{
            txtwords.setTypeface(null, Typeface.NORMAL);
        }
        setWordsFont();
    }
    protected void setUnderLine(){
        if(cbxUnderLine.isChecked()){
            txtwords.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }else{
            txtwords.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        }
    }
    protected void setWordsColor(){
        String strColor=sColor.getSelectedItem().toString();
        switch (strColor){
            case "Black" :
                txtwords.setTextColor(getResources().getColor(R.color.Black));
                break;
            case "Red" :
                txtwords.setTextColor(getResources().getColor(R.color.Red));
                break;
            case "Blue" :
                txtwords.setTextColor(getResources().getColor(R.color.Blue));
                break;
            case "Green" :
                txtwords.setTextColor(getResources().getColor(R.color.Green));
                break;
            case "Grey" :
                txtwords.setTextColor(getResources().getColor(R.color.Grey));
                break;
            case "Orange" :
                txtwords.setTextColor(getResources().getColor(R.color.Orange));
                break;
            case "navy" :
                txtwords.setTextColor(getResources().getColor(R.color.navy));
                break;
            case "Brown" :
                txtwords.setTextColor(getResources().getColor(R.color.Brown));
                break;
            case "yellow" :
                txtwords.setTextColor(getResources().getColor(R.color.yellow));
                break;

        }
    }
    protected  void setWordsFont(){
        String strFont=sFont.getSelectedItem().toString();
        Typeface tf=Typeface.SANS_SERIF;
        switch (strFont){
            case "sans-serif":
                tf=Typeface.SANS_SERIF;
                break;
            case "Authentic Script Rough":
                tf=Typeface.createFromAsset(getAssets(),"Authentic Script Rough.ttf");
                break;
            case "Brilliant Signature":
                tf=Typeface.createFromAsset(getAssets(),"Brilliant Signature.otf");
                break;
            case "Gelowing":
                tf=Typeface.createFromAsset(getAssets(),"Gelowing.otf");
                break;
            case "Angeline Vintage_Demo":
                tf=Typeface.createFromAsset(getAssets(),"Angeline Vintage_Demo.otf");
                break;
            case "RemachineScript_Personal_Use":
                tf=Typeface.createFromAsset(getAssets(),"RemachineScript_Personal_Use.ttf");
                break;
        }
        if(cbxBold.isChecked()){
            txtwords.setTypeface(tf,Typeface.BOLD);
        }else{
            txtwords.setTypeface(tf,Typeface.NORMAL);
        }

    }
    protected  void setAlign(){
        if(rdoLTR.isChecked()){
            txtwords.setGravity(Gravity.LEFT);
        }else{
            txtwords.setGravity(Gravity.RIGHT);
        }
    }
    protected void newFile(){
        txtwords.setText("");
        txtSize.setText("14");
        txtwords.setTextSize(14);
        cbxBold.setChecked(false);
        cbxUnderLine.setChecked(false);
        sColor.setSelection(0);
        sFont.setSelection(0);
        rdoLTR.setChecked(true);
        txtFileName.setText("FileName");
        txtwords.requestFocus();
    }
    protected void saveFile(){
        if("".equals(txtFileName.getText().toString().trim())){
            Toast.makeText(this, "File Name Is Empty!", Toast.LENGTH_SHORT).show();
            txtFileName.requestFocus();
        }else{
            try {
                String strPath = Environment.getExternalStorageDirectory().getPath()+"/NotePad";
                File f = new File(strPath);
                f.mkdir();


                PrintWriter pw = new PrintWriter(strPath + "/" + txtFileName.getText() + ".txt");
                pw.write(txtwords.getText().toString());
                pw.close();

                PrintWriter pwSet = new PrintWriter(strPath + "/" + txtFileName.getText() + "set.txt");
                String strSet=txtSize.getText()+"\n"+
                        cbxBold.isChecked()+"\n"+
                        cbxUnderLine.isChecked()+"\n"+
                        sColor.getSelectedItem()+"\n"+
                        sFont.getSelectedItem()+"\n"+
                        rdoLTR.isChecked()+"\n"+
                        rdoRTL.isChecked()+"\n";

                pwSet.write(strSet);
                pwSet.close();
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
            }catch (Exception ex){
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println(ex.getMessage());
            }
        }
    }
    protected  boolean isExternalStorageAvailable(){
        String checker=Environment.getExternalStorageState();
        if(checker.equals(Environment.MEDIA_MOUNTED))return true;
        return false;
    }
    protected void getFile(){
        try{
            String strPath=Environment.getExternalStorageDirectory().getPath()+"/"+"NotePad";
            FileReader fr=new FileReader(strPath+"/"+txtFileName.getText()+".txt");
            BufferedReader br=new BufferedReader(fr);
            String strContent="";
            String strline="";
            while((strline=br.readLine())!=null){
                strContent+=strline+"\n";
            }
            fr=new FileReader(strPath+"/"+txtFileName.getText()+"set.txt");
            br=new BufferedReader(fr);
            String strSet[]=new String[7];
            int counter=0;
            while((strline=br.readLine())!=null){
                strSet[counter++]=strline;
            }
            counter=0;
            br.close();
            fr.close();
            txtwords.setText(strContent);
            txtSize.setText(strSet[0]);
            txtwords.setTextSize(Integer.parseInt(strSet[0]));
            cbxBold.setChecked(Boolean.parseBoolean(strSet[1]));
            cbxUnderLine.setChecked(Boolean.parseBoolean(strSet[2]));
            sColor.setSelection(((ArrayAdapter<String>)sColor.getAdapter()).getPosition(strSet[3]));
            sFont.setSelection(((ArrayAdapter<String>)sFont.getAdapter()).getPosition(strSet[4]));
            if("true".equals(strSet[5])){
                rdoLTR.setChecked(true);
            }else{
                rdoRTL.setChecked(true);
            }
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1 :
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
