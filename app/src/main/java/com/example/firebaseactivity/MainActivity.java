package com.example.firebaseactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewTreeViewModelKt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailSenha";
    private FirebaseAuth mAuth;
    private EditText edSenha, edEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);

    }


    @Override
    protected void  onStart(){
        super.onStart();
        FirebaseUser currentUser =  mAuth.getCurrentUser();
        if(currentUser != null){
            abrirMenu(currentUser);
        }
    }

    public void criarUsuario(View view){
        mAuth.createUserWithEmailAndPassword(edEmail.getText().toString(), edSenha.getText().toString())
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Log.d(TAG, "createUserWithMail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                          Toast.makeText(MainActivity.this, "Usuário criado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            abrirMenu(user);
                        } else {
                            Log.w(TAG, "createUserWithMail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Falha na criação do usuário!" +
                                            "\n" + task.getException().getMessage(),
                                   Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void abrirMenu(FirebaseUser user){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
    }



    public void loginUsuario(View view){
        mAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edSenha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            abrirMenu(user);
                        } else {
                            Log.w(TAG, "signInWithEmail:failure");
                            Toast.makeText(MainActivity.this, "Falha na autenticação!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}