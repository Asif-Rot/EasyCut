package com.example.easycut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easycut.callInterface.callBackProudct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddDelHaircut extends AppCompatActivity {
    List<String> product_list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_del_haircut);
        Button back = (Button) findViewById(R.id.back_hsScreen);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDelHaircut.this, Screen_HS.class);
                startActivity(intent);
            }
        });

        Button seeHaircut = (Button) findViewById(R.id.see_haircut);
        /**
         * fun to see kind of hair cut for hairstyle
         */
        seeHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FireBaseService.get_set_Product(new callBackProudct() {
                    @Override
                    public void callBackProudct(List<String> list) {
                        product_list = list;
                        /**
                         * dialog for show kind of hair cut to hairstyle
                         */
                        AlertDialog.Builder mBulider = new AlertDialog.Builder(AddDelHaircut.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_show_prod, null);
                        TextView show = (TextView) mView.findViewById(R.id.showProd);
                        for (String n : product_list) {
                            show.setText(show.getText() + "   * " + n.toString() + "\n" + "\n");
                        }
                        mBulider.setView(mView);
                        AlertDialog dialog = mBulider.create();
                        dialog.show();
                    }
                });

            }
        });
        /**
         * part for add new hair cut to data base by hairstyle
         */
        EditText add_haircut = (EditText) findViewById(R.id.editText_addHairCut);
        Button ok_add = (Button) findViewById(R.id.ok_add);
        ok_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_haircut = add_haircut.getText().toString();
                if (TextUtils.isEmpty(new_haircut)) {
                    add_haircut.setError("Need to add new hair cut ");
                    add_haircut.requestFocus();
                } else {
                    //ֿFirebaseDatabase.getInstance().getReference().child("Product").child(new_haircut).setValue("1");
                }
            }
        });


    }
}