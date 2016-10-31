package com.example.filetest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    private Button read,write;
    private EditText readText,writeText;
    private String fileName="content.txt";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		read=(Button)findViewById(R.id.read);
		write=(Button)findViewById(R.id.write);
		readText=(EditText)findViewById(R.id.readText);
		writeText=(EditText)findViewById(R.id.writeText);
		read.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				readText.setText(read());
			}
		});
		write.setOnClickListener(new OnClickListener(){
			public void  onClick(View v){
				write(writeText.getText().toString());
			}
        });
		
	}
	public void write(String content){
		try{
			FileOutputStream fos=openFileOutput(fileName,Context.MODE_APPEND);
			PrintStream ps=new PrintStream(fos);
			ps.print(content);
			ps.close();
			fos.close();
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				File sdCardDir=Environment.getExternalStorageDirectory();
				File destFile=new File(sdCardDir.getCanonicalPath()+File.separator+fileName);
				RandomAccessFile raf=new RandomAccessFile(destFile,"rw");
				raf.seek(destFile.length());
				raf.write(content.getBytes());
				raf.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String read(){
		StringBuilder sbBuilder=new StringBuilder("");
		try{
			FileInputStream is=openFileInput(fileName);
			
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				File sdCard=Environment.getExternalStorageDirectory();
				File destFile=new File(sdCard.getCanonicalPath()+File.separator+fileName);
				FileInputStream fis=new FileInputStream(destFile);
				byte[]buffer=new byte[64];
				int  hasRead;
	            while((hasRead=is.read(buffer))!=-1){
				sbBuilder.append(new String(buffer,0,hasRead));
				
			}
	            return sbBuilder.toString();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			}
		return  null;
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
