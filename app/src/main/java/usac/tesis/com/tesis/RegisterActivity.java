package usac.tesis.com.tesis;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import usac.tesis.com.tesis.utils.WSCaller;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailView;
    private EditText nombreView;
    private EditText pass1View;
    private EditText pass2View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setConfig();
    }

    private void setConfig(){

        this.emailView = (EditText)findViewById(R.id.editEmail);
        this.nombreView = (EditText)findViewById(R.id.editName);
        this.pass1View = (EditText)findViewById(R.id.editPass1);
        this.pass2View = (EditText)findViewById(R.id.editPass2);

        Button cancel = (Button)findViewById(R.id.regCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button register = (Button)findViewById(R.id.regAceptar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister(){
        boolean cancel = false;
        View focus = null;

        this.emailView.setError(null);
        this.nombreView.setError(null);
        this.pass1View.setError(null);
        this.pass2View.setError(null);

        String email = this.emailView.getText().toString();
        String nombre = this.nombreView.getText().toString();
        String pass1 = this.pass1View.getText().toString();
        String pass2 = this.pass2View.getText().toString();

        if(!TextUtils.equals(pass1, pass2)){
            this.pass2View.setError("Las contraseñas no coinciden");
            focus = this.pass2View;
            cancel = true;
        }

        if(TextUtils.isEmpty(pass1)){
            this.pass1View.setError("Este campo es obligatorio");
            focus = this.pass1View;
            cancel = true;
        }

        if(TextUtils.isEmpty(pass2)){
            this.pass2View.setError("Este campo es obligatorio");
            focus = this.pass2View;
            cancel = true;
        }

        if(TextUtils.isEmpty(nombre)){
            this.nombreView.setError("Este campo es obligatorio");
            focus = this.nombreView;
            cancel = true;
        }

        if(TextUtils.isEmpty(email)){
            this.emailView.setError("Este campo es obligatorio");
            focus = this.emailView;
            cancel = true;
        }

        if(cancel){
            focus.requestFocus();
        }else{
            new AsyncCallWS().execute(email, pass1, nombre);
        }
    }

    private class AsyncCallWS extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            Log.d("ASYNC","PRE");
        }

        @Override
        protected String doInBackground(String... params) {
            return registrarUsuario(params[0], params[1], params[2]);
        }

        @Override
        protected void onPostExecute(String result) {
            if(TextUtils.equals(result,"error")){
                Toast.makeText(getApplicationContext(),"Fallo el registro",Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        private String registrarUsuario(String correo, String pass, String nombre){
            WSCaller caller = new WSCaller("registro");
            caller.addStringParam("arg0",correo);
            caller.addStringParam("arg1",pass);
            caller.addStringParam("arg2",nombre);

            return caller.call();
        }
    }
}
