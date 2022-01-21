package com.example.evaluacion_rogerac;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.evaluacion_rogerac.service.entity.service.Cliente;
import com.example.evaluacion_rogerac.service.entity.service.File;
import com.example.evaluacion_rogerac.service.entity.service.Usuario;
import com.example.evaluacion_rogerac.service.viewmodel.ClienteViewModel;
import com.example.evaluacion_rogerac.service.viewmodel.FileViewModel;
import com.example.evaluacion_rogerac.service.viewmodel.UsuarioViewModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegistrarUsuarioActivity extends AppCompatActivity {
    private java.io.File f;
    private ClienteViewModel clienteViewModel;
    private UsuarioViewModel usuarioViewModel;
    private FileViewModel fileViewModel;
    private Button btnSubirImagen, btnGuardarDatos;
    private CircleImageView imageUser;
    private EditText edtNameUser, edtNumDocU, edtTelefonoU,
            edtDireccionU, edtPasswordUser, edtEmailUser;
    private TextInputLayout txtInputNameUser, txtInputNumeroDocU,
            txtInputTelefonoU, txtInputDireccionU, txtInputEmailUser, txtInputPasswordUser;
    //SOLICITAR PERMISOS PARA IMAGENES
    private final static int LOCATION_REQUEST_CODE = 23;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        this.init();
        this.initViewModel();
    }

    private void initViewModel() {
        final ViewModelProvider vmp = new ViewModelProvider(this);
        this.clienteViewModel = vmp.get(ClienteViewModel.class);
        this.usuarioViewModel = vmp.get(UsuarioViewModel.class);
        this.fileViewModel = vmp.get(FileViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LOCATION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder los permisos para " +
                            "leer el almacenamiento, estos permisos son necesarios para poder " +
                            "escoger tu foto de perfil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podemos realizar el registro si no nos concedes los permisos para leer el almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void init() {
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
        btnSubirImagen = findViewById(R.id.btnSubirImagen);
        imageUser = findViewById(R.id.imageUser);
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarCamara();
            }
        });
        edtNameUser = findViewById(R.id.edtNameUser);
        edtNumDocU = findViewById(R.id.edtNumDocU);
        edtTelefonoU = findViewById(R.id.edtTelefonoU);
        edtDireccionU = findViewById(R.id.edtDireccionU);
        edtPasswordUser = findViewById(R.id.edtPasswordUser);
        edtEmailUser = findViewById(R.id.edtEmailUser);
        txtInputNameUser = findViewById(R.id.txtInputNameUser);
        txtInputNumeroDocU = findViewById(R.id.txtInputNumeroDocU);
        txtInputTelefonoU = findViewById(R.id.txtInputTelefonoU);
        txtInputDireccionU = findViewById(R.id.txtInputDireccionU);
        txtInputEmailUser = findViewById(R.id.txtInputEmailUser);
        txtInputPasswordUser = findViewById(R.id.txtInputPasswordUser);
        btnSubirImagen.setOnClickListener(v -> {
            this.cargarImagen();


        });
        btnGuardarDatos.setOnClickListener(v -> {
            this.guardarDatos();
        });
    }

    private void cargarCamara(){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,1);

    }

    private void guardarDatos() {
        Cliente c;
        if (validar()) {
            c = new Cliente();
            try {
                c.setNombres(edtNameUser.getText().toString());
                c.setNumDoc(edtNumDocU.getText().toString());
                c.setTelefono(edtTelefonoU.getText().toString());
                c.setDireccion(edtDireccionU.getText().toString());
                c.setId(0);
                LocalDateTime ldt = LocalDateTime.now(); //Para generar el nombre al archivo en base a la fecha, hora, año
                RequestBody rb = RequestBody.create(f, MediaType.parse("multipart/form-data")), somedata; //Le estamos enviando un archivo (imagen) desde el formulario
                String filename = "" + ldt.getDayOfMonth() + (ldt.getMonthValue() + 1) +
                        ldt.getYear() + ldt.getHour()
                        + ldt.getMinute() + ldt.getSecond(); //Asignar un nombre al archivo (imagen)
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", f.getName(), rb);
                somedata = RequestBody.create("profilePh" + filename, MediaType.parse("text/plain")); //Le estamos enviando un nombre al archivo.
                this.fileViewModel.save(part, somedata).observe(this, response -> {
                    if (response.getRpta() == 1) {
                        c.setFoto(new File());
                        c.getFoto().setId(response.getBody().getId());//Asignamos la foto al cliente
                        this.clienteViewModel.guardarCliente(c).observe(this, cResponse -> {
                            if (cResponse.getRpta() == 1) {
                                //Toast.makeText(this, response.getMessage() + ", ahora procederemos a registrar sus credenciales.", Toast.LENGTH_SHORT).show();
                                int idc = cResponse.getBody().getId();//Obtener el id del cliente.
                                Usuario u = new Usuario();
                                u.setEmail(edtEmailUser.getText().toString());
                                u.setClave(edtPasswordUser.getText().toString());
                                u.setVigencia(true);
                                u.setCliente(new Cliente(idc));
                                this.usuarioViewModel.save(u).observe(this, uResponse -> {
                                    //Toast.makeText(this, uResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    if (uResponse.getRpta() == 1) {
                                        //Toast.makeText(this, "Sus Datos y credenciales fueron creados correctamente", Toast.LENGTH_SHORT).show();
                                        successMessage("Estupendo! " + "Su información ha sido guardada con éxito en el sistema.");
                                        //this.finish();
                                    } else {
                                        Toast.makeText(this, "No se ha podido guardar los datos, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(this, "No se ha podido guardar los datos, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "No se ha podido guardar los datos, intentelo de nuevo", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                warningMessage("Se ha producido un error : " + e.getMessage());
            }
        } else {
            errorMessage("Por favor, complete todos los campos del formulario");
        }

    }



    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Seleccione la Aplicación"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (requestCode== 1 && resultCode == RESULT_OK){  ///SIN CONECTAR // NO REGISTRAR
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageUser.setImageBitmap(imageBitmap);
        }else if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.f = new java.io.File(realPath);
            this.imageUser.setImageURI(uri);

        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private boolean validar() {
        boolean retorno = true;
        String nombres, numDoc, telefono, direccion, correo, clave;
        nombres = edtNameUser.getText().toString();
        numDoc = edtNumDocU.getText().toString();
        telefono = edtTelefonoU.getText().toString();
        direccion = edtDireccionU.getText().toString();
        correo = edtEmailUser.getText().toString();
        clave = edtPasswordUser.getText().toString();
        if (this.f == null) {
            errorMessage("debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombres.isEmpty()) {
            txtInputNameUser.setError("Ingresar nombres");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (numDoc.isEmpty()) {
            txtInputNumeroDocU.setError("Ingresar número documento");
            retorno = false;
        } else {
            txtInputNumeroDocU.setErrorEnabled(false);
        }
        if (telefono.isEmpty()) {
            txtInputTelefonoU.setError("Ingresar número telefónico");
            retorno = false;
        } else {
            txtInputTelefonoU.setErrorEnabled(false);
        }
        if (direccion.isEmpty()) {
            txtInputDireccionU.setError("Ingresar dirección de su casa");
            retorno = false;
        } else {
            txtInputDireccionU.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            txtInputEmailUser.setError("Ingresar correo electrónico");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (clave.isEmpty()) {
            txtInputPasswordUser.setError("Ingresar clave para el inicio de sesión");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }
        return retorno;
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

}