package com.rosch.braineff;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

public class SaveFileFragment extends DialogFragment implements OnClickListener
{
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new Builder(getActivity());
		
		builder.setTitle(R.string.save_file_fragment_title);
		builder.setView(getActivity().getLayoutInflater().inflate(R.layout.save_file_fragment, null));
		builder.setNegativeButton(android.R.string.cancel, null);
		builder.setPositiveButton(android.R.string.ok, this);
		
		return builder.create();
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		// TODO Auto-generated method stub
	}
}
