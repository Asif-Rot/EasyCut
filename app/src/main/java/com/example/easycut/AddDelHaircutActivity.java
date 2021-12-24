package com.example.easycut;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easycut.callInterface.callBackProudct;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

public class AddDelHaircutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<String> product_list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_del_haircut);

        //back button
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


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
                        AlertDialog.Builder mBulider = new AlertDialog.Builder(AddDelHaircutActivity.this);
                        View mView = getLayoutInflater().inflate(R.layout.dialog_show_prod, null);
                        TextView show = (TextView) mView.findViewById(R.id.showProd);
                        Button back=(Button) mView.findViewById(R.id.back);
                        for (String n : product_list) {
                            show.setText(show.getText() + "   * " + n.toString() + "\n" + "\n");
                        }
                        mBulider.setView(mView);
                        AlertDialog dialog = mBulider.create();
                        dialog.show();
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
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
                    FirebaseDatabase.getInstance().getReference().child("Product").child(new_haircut).setValue(new_haircut);
                    Toast.makeText(getApplicationContext(),"Add successful", Toast.LENGTH_SHORT).show();
                    add_haircut.setText(null);

                }
            }
        });
        /**
         * this func for button to remove type hair cut
         */
        Button ok_remove = (Button) findViewById(R.id.ok_remove);
        Spinner spinner_remove = (Spinner) findViewById(R.id.spinner_remove);
        spinner_remove.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                FireBaseService.get_set_Product(new callBackProudct() {
                    @Override
                    public void callBackProudct(List<String> list) {
                        ArrayAdapter<String> adapterT = new ArrayAdapter<>(AddDelHaircutActivity.this, android.R.layout.simple_spinner_item, list);
                        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_remove.setAdapter(adapterT);
                        spinner_remove.setOnItemSelectedListener(AddDelHaircutActivity.this);
                        ok_remove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String rem=spinner_remove.getSelectedItem().toString();
                                if(rem.equals("")){
                                    Toast.makeText(getApplicationContext(),"Please pick an haircut for remove", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    /**
                                     * get data from database and remove the correct type haircut
                                     */
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                    Query applesQuery = ref.child("Product").child(rem);
                                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                dataSnapshot.getRef().removeValue();
                                                Toast.makeText(getApplicationContext(),"Remove successful", Toast.LENGTH_SHORT).show();
                                                spinner_remove.setAdapter(null);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e(TAG, "onCancelled", databaseError.toException());
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
                return false;
            }

        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        System.out.println();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
