package com.rosch.braineff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements EditorFragment.EditorFragmentHandler, SaveFileFragment.OnSaveFileOKListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main_activity);
		
		if (getFragmentManager().findFragmentByTag("editor_fragment") == null)
		{
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
			transaction.replace(R.id.fragment_container, new EditorFragment(), "editor_fragment");
			transaction.commit();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if (super.onCreateOptionsMenu(menu) == false)
			return false;
		
		getMenuInflater().inflate(R.menu.main_activity, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{			
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCompileProgram(Bundle arguments)
	{	
		InterpreterFragment fragment = (InterpreterFragment) getFragmentManager().findFragmentByTag("interpreter_fragment");
		
		if (fragment == null)
		{
			fragment = new InterpreterFragment();
			fragment.setArguments(arguments);
			
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
	
			transaction.addToBackStack(null);
			transaction.replace(R.id.fragment_container, fragment, "interpreter_fragment");			
			transaction.commit();
		}
		else
		{
			fragment.getArguments().putAll(arguments);
		}
		
		return false;
	}

	@Override
	public boolean onSaveProgram(Bundle arguments)
	{
		SaveFileFragment fragment = new SaveFileFragment();
		fragment.setArguments(arguments);
		
		fragment.show(getFragmentManager(), "save_file_fragment");
		
		return false;
	}

	@Override
	public boolean onLoadProgram(Bundle arguments)
	{
		return false;
	}

	@Override
	public void onSaveFileOK(Bundle arguments)
	{
		String filename = arguments.getString("file_filename");
		
		if (filename.isEmpty() == true)
			filename = "untitled.bf";
		
		if (filename.endsWith(".bf") == false)
			filename += ".bf";
		
		File file = new File(Environment.getExternalStorageDirectory() + "//Braineff//", filename);
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			
			writer.write(arguments.getString("file_contents"));
			writer.close();
			
			Toast.makeText(this, "File saved as " + filename, Toast.LENGTH_SHORT).show();
		}
		catch (IOException exception)
		{
			Toast.makeText(this, "An error occured while trying to save the file.", Toast.LENGTH_SHORT).show();
			
			return;
		}
		
		// Work-around as referenced here: code.google.com/p/android/issues/detail?id=38282
		MediaScannerConnection.scanFile(this, new String[] { file.getAbsolutePath() }, null, null);
		
		EditorFragment editorFragment = (EditorFragment) getFragmentManager().findFragmentByTag("editor_fragment");
		
		if (editorFragment == null)
			return;
		
		editorFragment.setFilenameText(filename);
	}
}
