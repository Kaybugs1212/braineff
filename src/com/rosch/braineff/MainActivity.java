package com.rosch.braineff;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.rosch.braineff.EditorFragment.EditorFragmentHandler;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements EditorFragmentHandler, EmulatorFragment.ProgramContentsProvider
{
	private String programContents;
	
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
	public String getProgramContents()
	{
		return programContents;
	}

	@Override
	public boolean onCompileProgram(Bundle arguments)
	{
		programContents = arguments.getString("program_source");
		
		if (getFragmentManager().findFragmentByTag("emulator_fragment") == null)
		{
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
	
			transaction.addToBackStack(null);
			transaction.replace(R.id.fragment_container, new EmulatorFragment(), "emulator_fragment");
			transaction.commit();
		}
		
		return false;
	}

	@Override
	public boolean onSaveProgram(Bundle arguments)
	{
		File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Braineff/blah.txt");
			
		FileWriter writer;
		try {
			file.createNewFile();
			writer = new FileWriter(file);
			writer.write(arguments.getString("program_source"));
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		MediaScannerConnection.scanFile(this, new String[] { file.getAbsolutePath() }, null, null);
		
		Toast.makeText(this, file.getPath(), Toast.LENGTH_SHORT).show();
		
		return false;
	}

	@Override
	public boolean onLoadProgram(Bundle arguments) {
		// TODO Auto-generated method stub
		return false;
	}
}
