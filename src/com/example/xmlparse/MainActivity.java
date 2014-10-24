package com.example.xmlparse;

import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class MainActivity extends Activity {
	List<Person> list;
	XMLContentHandler xmlContentHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InputStream inStream=null;
		inStream=getResources().openRawResource(R.raw.preson);
		xmlContentHandler=new XMLContentHandler();
		list=xmlContentHandler .readXML(inStream);
		TextView textView=(TextView) findViewById(R.id.TV);
		String[] strings=new String[list.size()];
		for(int i=0;i<list.size();i++){
			
			strings[i]=get(i);
		}
		textView.setText(strings[0]+"\n"+strings[1]);
	}
	
	public String get(int i) {
		Person person=list.get(i);
		String id=person.getId().toString();
		String name=person.getName().toString();
		String age=person.getAge().toString();
		String string=id+"  "+name+"  "+age;
		return string;
	}

}
