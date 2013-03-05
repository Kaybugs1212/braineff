package com.rosch.braineff;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;

public class SaveFileFragment extends DialogFragment
{
	public interface OnSaveFileOKListener
	{
		public void onSaveFileOK(Bundle arguments);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new Builder(getActivity());
		
		builder.setTitle(R.string.save_file_fragment_title);
		builder.setView(getActivity().getLayoutInflater().inflate(R.layout.save_file_fragment, null));
		builder.setNegativeButton(android.R.string.cancel, null);
		
		builder.setPositiveButton(android.R.string.ok, new OnClickListener()
		{			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				OnSaveFileOKListener listener = (OnSaveFileOKListener) getActivity();
				
				if (listener == null)
					return;
				
				EditText filenameEditText = (EditText) ((AlertDialog) dialog).findViewById(R.id.save_file_filename);
				
				Bundle arguments = getArguments();
				arguments.putString("file_filename", filenameEditText.getText().toString());
				
				listener.onSaveFileOK(arguments);
			}
		});
		
		return builder.create();
	}
}
