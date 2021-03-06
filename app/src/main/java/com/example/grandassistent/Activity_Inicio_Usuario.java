package com.example.grandassistent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Activity_Inicio_Usuario extends AppCompatActivity {
    private ImageView imageView_perfil;
    private Button btn_actualizar,btn_cerrar_sesion,btn_eliminar;
    private TextView tv_id,tv_nombre,tv_correo,tv_telefono,tv_precio,tv_especializacion,tv_contraseña;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.color_btn_iniciar)));
        actionBar.setTitle("Mis Datos");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        builder = new AlertDialog.Builder(Activity_Inicio_Usuario.this);

        imageView_perfil = (ImageView) findViewById(R.id.Image_Datos);
        tv_id = (TextView) findViewById(R.id.Tv_UID_Datos);
        tv_nombre = (TextView) findViewById(R.id.Tv_Nombre);
        tv_correo = (TextView) findViewById(R.id.Tv_Correo);
        tv_telefono = (TextView) findViewById(R.id.Tv_Telefono);
        tv_precio = (TextView) findViewById(R.id.Tv_Precio);
        tv_especializacion = (TextView) findViewById(R.id.Tv_Especializacion);
        //tv_contraseña = (TextView) findViewById(R.id.Tv_Password);

        btn_actualizar = (Button) findViewById(R.id.btn_Acutalizar);
        btn_cerrar_sesion = (Button) findViewById(R.id.btn_Cerrar_Sesion);
        btn_eliminar = (Button) findViewById(R.id.btn_Eliminar);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference("USUARIOS_GRAND_ASSISTENT");

        mDatabase.child("Asistentes").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String uid = "" + snapshot.child("UId").getValue()  ;
                    String nombre = "" + snapshot.child("Nombre").getValue();
                    String correo = "" + snapshot.child("Correo").getValue();
                    String telefono = "" + snapshot.child("Telefono").getValue();
                    String precio = "" + snapshot.child("Precio_A").getValue();
                    String especializacion = "" + snapshot.child("Especializacion_A").getValue();
                    String password = "" + snapshot.child("Contraseña").getValue();

                    tv_id.setText("ID: " + uid);
                    tv_nombre.setText("Nombre: " + nombre);
                    tv_correo.setText("Correo: " + correo);
                    tv_telefono.setText("Telefono: " + telefono);
                    tv_precio.setText("Precio: " + precio);
                    tv_especializacion.setText("Especializacion: " + especializacion);
                    //tv_contraseña.setText("Contraseña: " + password);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Usuarios").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                if(snapshot2.exists()){
                    String uid2 = "" + snapshot2.child("UId").getValue()  ;
                    String nombre2 = "" + snapshot2.child("Nombre").getValue();
                    String correo2 = "" + snapshot2.child("Correo").getValue();
                    String telefono2 = "" + snapshot2.child("Telefono").getValue();
                    String password2 = "" + snapshot2.child("Contraseña").getValue();

                    tv_id.setText("ID: " + uid2);
                    tv_nombre.setText("Nombre: " + nombre2);
                    tv_correo.setText("Correo: " + correo2);
                    tv_telefono.setText("Telefono: " + telefono2);
                    tv_precio.setText("Precio: ");
                    tv_especializacion.setText("Especializacion: ");
                    //tv_contraseña.setText("Contraseña: " + password2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity_Inicio_Usuario.this,"ESTAMOS TRABAJANDO EN ELLO",Toast.LENGTH_SHORT).show();
            }
        });

        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cerrar();
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar();
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.color_btn_iniciar));
        }
    }//FIN DEL ONCREATE


    //METODO PARA REGRESAR AL ANTERIOR VENTANA
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    //METODO PARA CERRAR SESION
    public void Cerrar(){
        builder.setTitle("Cerrar Cuenta")
                .setMessage("¿Estas Seguro Que Quieres Cerrar Tu Cuenta?")
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Toast.makeText(Activity_Inicio_Usuario.this, "Ha cerrado Sesion",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_Inicio_Usuario.this,Login.class));
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface2, int i) {
                        dialogInterface2.cancel();
                    }
                }).show();
    }

    //METODO PARA ELIMINAR CUENTA
    public void Eliminar(){
        builder.setTitle("Eliminar Cuenta")
                .setMessage("¿Estas Seguro Que Quieres Eliminar Tu Cuenta?")
                .setCancelable(true)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        AuthCredential credential = EmailAuthProvider.getCredential("manuel@gmail.com","yosoymanuel");
                        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            mDatabase = FirebaseDatabase.getInstance().getReference("USUARIOS_GRAND_ASSISTENT");
                                            mDatabase.child("Asistentes").child(user.getUid()).removeValue();
                                            mDatabase.child("Usuarios").child(user.getUid()).removeValue();
                                            startActivity(new Intent(Activity_Inicio_Usuario.this,Login.class));
                                            Toast.makeText(Activity_Inicio_Usuario.this,"Se elimino Cuenta",Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(),"No se pudo Eliminar" + task.getException(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface2, int i) {
                        dialogInterface2.cancel();
                    }
                }).show();
    }
}