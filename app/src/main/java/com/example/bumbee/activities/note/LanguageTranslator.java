package com.example.bumbee.activities.note;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bumbee.R;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;
import java.util.ArrayList;
import java.util.Objects;

public class LanguageTranslator extends AppCompatActivity
{
    public Spinner fromSpinner ,ToSpinner;
    public EditText sourceEdit;
    public static ArrayList<VocabModel> list_vocab = new ArrayList<>();
    public Button translate;
    public TextView  translated;
    public String [] fromLanguage = {"English", "Vietnamese"};
    public String [] toLanguage = {"Vietnamese" , "English"};
    public String  fromlanguagecode , tolanguagecode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_translator);
        fromSpinner = findViewById(R.id.idFromSpinner);
        ToSpinner = findViewById(R.id.idToSpinner);
        sourceEdit = findViewById(R.id.editView);
        translate = findViewById(R.id.idTranslate);
        translated = findViewById(R.id.idTranslated);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                fromlanguagecode = getlanguagetext(fromLanguage[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, fromLanguage);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);
        ToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tolanguagecode = getlanguagetext(toLanguage[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, toLanguage);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ToSpinner.setAdapter(toAdapter);
        translate.setOnClickListener(view -> {
            translated.setText("");
            if (Objects.requireNonNull(sourceEdit.getText()).toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please enter to translate", Toast.LENGTH_SHORT).show();
            } else if (fromlanguagecode == null) {
                Toast.makeText(getApplicationContext(), "Please choose the language to translate", Toast.LENGTH_SHORT).show();
            } else if (tolanguagecode == null) {
                Toast.makeText(getApplicationContext(), "Please choose the translated language", Toast.LENGTH_SHORT).show();
            } else {
                translateText(fromlanguagecode, tolanguagecode, sourceEdit.getText().toString());
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void translateText(String fromlanguagecode , String tolanguagecode , String sourcetext)
    {
        TranslatorOptions options = new TranslatorOptions.Builder().setSourceLanguage(fromlanguagecode).setTargetLanguage(tolanguagecode).build();
        final Translator translator = Translation.getClient(options);
        translated.setText("Downloading Model...");
        DownloadConditions conditions = new DownloadConditions.Builder().build();
        translator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(unused -> { translated.setText("Translating...");
                            translator.translate(sourcetext).addOnSuccessListener(s -> {
                                translated.setText(s);
                                list_vocab.add(new VocabModel(sourcetext , s));
                            });
                        })
                .addOnFailureListener(
                        e -> translated.setText("Fail to download the language model."));

    }

    public String getlanguagetext (String language)
    {
        String language_text;
        switch (language)
        {
            case "English":
                language_text = TranslateLanguage.ENGLISH;
                break;
            case "Vietnamese":
                language_text =TranslateLanguage.VIETNAMESE;
                break;
            default:
                language_text = "";
        }
        return language_text;
    }
}