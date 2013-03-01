package com.rosch.braineff;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EmulatorFragment extends Fragment
{
	public interface ProgramContentsProvider
	{
		public String getProgramContents();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.emulator_fragment, container, false);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		
		ProgramContentsProvider provider = (ProgramContentsProvider) getActivity();
		
		if (provider != null)
			runProgram(provider.getProgramContents());
	}
	
	public void runProgram(String programContents)
	{
		TextView consoleOutput = (TextView) getView().findViewById(R.id.console_output);
		consoleOutput.setText("");
		
		int[] data = new int [10000];
		int dataIndex = 0;	
		
		int programIndex = 0;
		int programLength = programContents.length();
		
		boolean active = (programLength > 0);
		
		while (active == true)
		{
			char command = programContents.charAt(programIndex);
			
			switch (command)
			{
			case '>':
				dataIndex++;
				break;
				
			case '<':
				dataIndex--;
				break;
				
			case '+':
				{
					data[dataIndex]++;
					
					if (data[dataIndex] > 255)
						data[dataIndex] = 0;
				}
				break;
				
			case '-':
				{
					data[dataIndex]--;
					
					if (data[dataIndex] < 0)
						data[dataIndex] = 255;
				}
				break;
				
			case '.':
				consoleOutput.append(String.valueOf((char) (data[dataIndex])));
				break;
				
			case ',':
				{
					// TODO:
				}
				break;
				
			case '[':
				{
					if (data[dataIndex] > 0)
						break;
					
					programIndex++;
					
					while (programIndex < programLength)
					{			
						if (programContents.charAt(programIndex) == ']')
							break;
						
						programIndex++;
					}										
					
					if (programIndex >= programLength)
						active = false;
				}				
				break;		

			case ']':
				{
					if (data[dataIndex] == 0)
						break;
					
					programIndex--;
					
					while (programIndex >= 0)
					{
						if (programContents.charAt(programIndex) == '[')
							break;
						
						programIndex--;
					}
					
					if (programIndex < 0)
						active = false;
				}
				break;
				
			default:
				break;
			}
			
			if (active == true)
			{			
				programIndex++;
			
				if (programIndex >= programLength)
					active = false;
			}
		}
	}
}
