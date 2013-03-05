package com.rosch.braineff;

import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class EditorFragment extends Fragment implements View.OnClickListener
{
	public interface EditorFragmentHandler
	{
		public boolean onCompileProgram(Bundle arguments);
		public boolean onSaveProgram(Bundle arguments);
		public boolean onLoadProgram(Bundle arguments);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.editor_fragment, container, false);
		
		String programFilename = "untitled.bf";
		String programSource = "";		
		
		((TextView) view.findViewById(R.id.file_filename)).setText(programFilename);
		((EditText) view.findViewById(R.id.file_contents)).setText(programSource);
		
		// Easier to set each Buttons' listener than to setup a recursive loop.
		view.findViewById(R.id.kb_inc_ptr).setOnClickListener(this);
		view.findViewById(R.id.kb_dec_ptr).setOnClickListener(this);
		view.findViewById(R.id.kb_inc_data).setOnClickListener(this);
		view.findViewById(R.id.kb_dec_data).setOnClickListener(this);
		view.findViewById(R.id.kb_output_data).setOnClickListener(this);
		view.findViewById(R.id.kb_input_data).setOnClickListener(this);
		view.findViewById(R.id.kb_jump_forward).setOnClickListener(this);
		view.findViewById(R.id.kb_jump_backward).setOnClickListener(this);
		view.findViewById(R.id.kb_tab).setOnClickListener(this);
		view.findViewById(R.id.kb_backspace).setOnClickListener(this);
		view.findViewById(R.id.kb_space).setOnClickListener(this);
		view.findViewById(R.id.kb_enter).setOnClickListener(this);
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.editor_fragment, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		EditorFragmentHandler listener = (EditorFragmentHandler) getActivity();
		
		if (listener == null)
			return super.onOptionsItemSelected(item);
		
		switch (item.getItemId())
		{
		case R.id.menu_editor_compile:
			onEditorCompile();
			break;
			
		case R.id.menu_editor_save:
			onEditorSave();
			break;
			
		case R.id.menu_editor_load:
			onEditorLoad();
			break;
		}
		
		return true;
	}
		
	private boolean onEditorCompile()
	{
		EditorFragmentHandler handler = (EditorFragmentHandler) getActivity();
		
		if (handler == null)
			return false;
		
		EditText sourceView = (EditText) getView().findViewById(R.id.file_contents);
		
		Bundle arguments = new Bundle();		
		arguments.putString("file_contents", sourceView.getText().toString());
		
		return handler.onCompileProgram(arguments);
		
	}
	
	private boolean onEditorSave()
	{
		EditorFragmentHandler handler = (EditorFragmentHandler) getActivity();
		
		if (handler == null)
			return false;
		
		TextView filenameView = (TextView) getView().findViewById(R.id.file_filename);
		EditText sourceView = (EditText) getView().findViewById(R.id.file_contents);
		
		Bundle arguments = new Bundle();
		arguments.putString("file_filename", filenameView.getText().toString());
		arguments.putString("file_contents", sourceView.getText().toString());
		
		if (handler.onSaveProgram(arguments) == false)
			return false;
		
		filenameView.setText(arguments.getString("file_filename"));
		
		return true;
	}
	
	private boolean onEditorLoad()
	{
		EditorFragmentHandler handler = (EditorFragmentHandler) getActivity();
		
		if (handler == null)
			return false;
		
		Bundle arguments = new Bundle();
		
		if (handler.onLoadProgram(arguments) == false)
			return false;
		
		TextView filenameView = (TextView) getView().findViewById(R.id.file_filename);
		EditText sourceView = (EditText) getView().findViewById(R.id.file_contents);
		
		filenameView.setText(arguments.getString("program_filename"));
		sourceView.setText(arguments.getString("program_source"));
		
		return true;
	}
	
	public void setFilenameText(String text)
	{
		TextView filenameTextView = (TextView) getView().findViewById(R.id.file_filename);
		filenameTextView.setText(text);
	}

	@Override
	public void onClick(View view)
	{		
		EditText editorContents = (EditText) getView().findViewById(R.id.file_contents);
		String text = "";
		
		switch (view.getId())
		{
		case R.id.kb_inc_ptr:		text = ">";	break;			
		case R.id.kb_dec_ptr:		text = "<"; break;		
		case R.id.kb_inc_data:		text = "+"; break;			
		case R.id.kb_dec_data:		text = "-"; break;			
		case R.id.kb_output_data:	text = "."; break;			
		case R.id.kb_input_data:	text = ",";	break;			
		case R.id.kb_jump_forward:	text = "["; break;						
		case R.id.kb_jump_backward:	text = "]"; break;
		
		case R.id.kb_space:			text = "\u0020"; break;		
		case R.id.kb_tab:			text = "\u0009"; break;
		
		case R.id.kb_backspace:
			editorContents.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
			return;
			
		case R.id.kb_enter:
			editorContents.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
			return;
		
		default:
			return;
		}		
								
		if (text.isEmpty() == false)
			editorContents.getText().insert(editorContents.getSelectionStart(), text);
	}
}
