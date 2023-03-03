package com.example.gamecenter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class GameModeChooserDialog extends DialogFragment {

    private ImageButton preview;
    private ImageButton left_arrow_button;
    private ImageButton right_arrow_button;
    private TextView modeSelectedText;
    private int selectedMode;
    private String[] gameModes;

    public GameModeChooserDialog(String[] gameModes){
        this.gameModes = gameModes;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.game_mode_dialog,null);
        builder.setView(view);

        return builder.create();

    }

    /**
     * Overrided onStart method, create all the necessary things to start the dialog.
     */
    @Override
    public void onStart(){
        super.onStart();

        selectedMode = 0;

        preview = (ImageButton) getDialog().findViewById(R.id.preview_image);
        left_arrow_button = (ImageButton) getDialog().findViewById(R.id.left_arrow);
        right_arrow_button = (ImageButton) getDialog().findViewById(R.id.right_arrow);
        modeSelectedText = (TextView) getDialog().findViewById(R.id.text_mode);
        changeModeImage();

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Activity_2048.class);
                intent.putExtra("gameMode",modeSelectedText.getText().toString());
                startActivity(intent);
                getDialog().dismiss();
            }
        });
        left_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedMode = (selectedMode-1)%3;
                Log.i("MODE",String.valueOf(selectedMode));
                if(selectedMode<0){
                    selectedMode=2;
                    Log.i("MODE",String.valueOf(selectedMode));
                }
                Log.i("MODE",String.valueOf(selectedMode));
                modeSelectedText.setText(gameModes[selectedMode]);
                changeModeImage();
            }
        });
        right_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedMode = (selectedMode+1)%3;
                Log.i("MODE",String.valueOf(selectedMode));
                modeSelectedText.setText(gameModes[selectedMode]);
                changeModeImage();
            }
        });
    }

    /**
     * Changes the image of the game dialog selector
     */
    private void changeModeImage(){
        switch (selectedMode){
            case 0: preview.setBackgroundResource(R.drawable.image_game_3x3); break;
            case 1: preview.setBackgroundResource(R.drawable.image_game_4x4); break;
            case 2: preview.setBackgroundResource(R.drawable.image_game_5x5); break;
        }
    }
}
