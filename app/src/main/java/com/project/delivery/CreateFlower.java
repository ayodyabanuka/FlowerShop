package com.project.delivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.delivery.Model.flower;

public class CreateFlower extends AppCompatActivity {

    ImageView flowerImage;
    Button select,save;
    EditText name,price,cost;
    Uri imageUri;

    StorageReference imageStorage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference flowerref = database.getReference("Flowers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flower);

        flowerImage = findViewById(R.id.flowerImage);
        select =findViewById(R.id.selectflowerImage);
        name = findViewById(R.id.flowerName);
        price = findViewById(R.id.flowerPrice);
        save = findViewById(R.id.uploadflowerdata);
        cost=findViewById(R.id.flowerCost);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    public void uploadImage() {


        String flowerName = name.getText().toString().trim();
        String flowerPrice = price.getText().toString().trim();
        String flowerCost = cost.getText().toString().trim();


        imageStorage = FirebaseStorage.getInstance().getReference(flowerName);

        imageStorage.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                flowerImage.setImageURI(imageUri);


                imageStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        flower flowerdetails = new flower(flowerName,flowerPrice,flowerCost,uri.toString());
                        flowerref.child(flowerName).setValue(flowerdetails);


                    }
                });
                Toast.makeText(CreateFlower.this,"flower data uploaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateFlower.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectImage() {

        Intent selectimageIntent = new Intent();
        selectimageIntent.setType("image/*");
        selectimageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(selectimageIntent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            flowerImage.setImageURI(imageUri);

        }

    }
}