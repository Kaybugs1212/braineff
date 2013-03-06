package com.rosch.braineff;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity implements EditorFragment.EditorFragmentHandler
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
}
