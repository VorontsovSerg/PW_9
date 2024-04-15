package com.example.pw9;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText editTextFileName;
    private EditText editTextFileContent;
    private TextView textViewFileContent;

    private static final String KEY_FILE_NAME = "fileName";
    private static final String KEY_FILE_CONTENT = "fileContent";
    private static final String KEY_TEXT_VIEW_CONTENT = "textViewContent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFileName = findViewById(R.id.editTextFileName);
        editTextFileContent = findViewById(R.id.editTextFileContent);
        textViewFileContent = findViewById(R.id.textViewFileContent);

        Button buttonCreateFile = findViewById(R.id.buttonCreateFile);
        Button buttonReadFile = findViewById(R.id.buttonReadFile);
        Button buttonAppendToFile = findViewById(R.id.buttonAppendToFile);
        Button buttonDeleteFile = findViewById(R.id.buttonDeleteFile);

        buttonCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFile();
            }
        });

        buttonReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile();
            }
        });

        buttonAppendToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendToFile();
            }
        });

        buttonDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCustomFile(editTextFileName.getText().toString());
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FILE_NAME, editTextFileName.getText().toString());
        outState.putString(KEY_FILE_CONTENT, editTextFileContent.getText().toString());
        outState.putString(KEY_TEXT_VIEW_CONTENT, textViewFileContent.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editTextFileName.setText(savedInstanceState.getString(KEY_FILE_NAME));
        editTextFileContent.setText(savedInstanceState.getString(KEY_FILE_CONTENT));
        textViewFileContent.setText(savedInstanceState.getString(KEY_TEXT_VIEW_CONTENT));
    }

    private void createFile() {
        String fileName = editTextFileName.getText().toString() + ".txt";
        String fileContent = editTextFileContent.getText().toString();

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Files");
        if (!directory.exists()) {
            directory.mkdirs(); // Создание директории, если она не существует
        }

        File file = new File(directory, fileName);

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(fileContent.getBytes());
            outputStream.close();
            showToast("Файл создан");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        String fileName = editTextFileName.getText().toString() + ".txt";
        StringBuilder fileContent = new StringBuilder();

        try {
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Files");
            File file = new File(directory, fileName);

            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            textViewFileContent.setText(fileContent.toString());

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendToFile() {
        String fileName = editTextFileName.getText().toString() + ".txt";
        String fileContent = editTextFileContent.getText().toString();

        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Files");
        if (!directory.exists()) {
            directory.mkdirs(); // Создание директории, если она не существует
        }

        File file = new File(directory, fileName);

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file, true);
            outputStream.write(fileContent.getBytes());
            outputStream.close();
            showToast("Данные добавлены в файл");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCustomFile(String fileName) {
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Files");
        File file = new File(directory, fileName + ".txt");

        if (file.exists()) {
            if (file.delete()) {
                showToast("Файл удален");
            } else {
                showToast("Ошибка при удалении файла");
            }
        } else {
            showToast("Файл не найден");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}