package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] periodTypes={"days","weeks","months"};
    Integer period,elapseTime;
    String periodItem;
    EstimatorDatabase myDatabase;
    EditText name,age,dailyIncome,dailyPopulation,elapsedTime,rcases,population,hospitalbeds;
    Spinner pTypes;
    TextView impactCurrent,severeCurrent,impactICUBRT,impactIBRT,severeIBRT,impactCBRT,impactVent, severeVent,severeCBRT,impactHospBed,severeHospBed,severeICUBRT,impactDollar,severeDollar;
    Button estimate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase=new EstimatorDatabase(this);
        name=findViewById(R.id.regionname);
        age=findViewById(R.id.age);
        dailyIncome=findViewById(R.id.income);
        dailyPopulation=findViewById(R.id.avgpopulat);
        elapsedTime=findViewById(R.id.elapsedTime);
        //periodType=findViewById(R.id.pTypes);
        rcases=findViewById(R.id.reportedCases);
        hospitalbeds=findViewById(R.id.totalHospBeds);
        population=findViewById(R.id.population);
        estimate=findViewById(R.id.estimateData);
        pTypes.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,periodTypes);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pTypes.setAdapter(aa);
        Estimates();

    }
    public  void Estimates() {
        estimate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String regName = name.getText().toString();
                String avgage = age.getText().toString();
                Integer avgDailyIncome = Integer.parseInt(dailyIncome.getText().toString());
                Integer avgDailyPopulat = Integer.parseInt(dailyPopulation.getText().toString());
                Integer elapseTime = Integer.parseInt(elapsedTime.getText().toString());
                //String ptype=periodType.getT
                Integer reportedCases = Integer.parseInt(rcases.getText().toString());
                Integer populations = Integer.parseInt(population.getText().toString());
                Integer beds = Integer.parseInt(hospitalbeds.getText().toString());
                Integer factor=Integer.parseInt(String.valueOf((elapseTime/3)*period));
                Integer twoToFactor=Integer.parseInt(String.valueOf(Math.pow(2,factor)));
                //Computation of Impact
               Integer impactCurrentlyInfected=reportedCases*10;
               Integer impactInfections=impactCurrentlyInfected*twoToFactor;
               Integer impactCases=Integer.parseInt(String.valueOf(0.15*impactInfections));
               Integer impactHospitalBeds=beds-impactCases;
               Integer impactICUReqT=Integer.parseInt(String.valueOf(0.05*impactInfections));
               Integer impactVentilator=Integer.parseInt(String.valueOf(0.62*impactInfections));
               Integer impactDollarInFlight=Integer.parseInt(String.valueOf((impactInfections*populations*avgDailyIncome)/factor));
                 //Computation of Severe Impact
                Integer sCurrentlyInfected=reportedCases*10;
                Integer sInfections=sCurrentlyInfected*twoToFactor;
                Integer sCases=Integer.parseInt(String.valueOf(0.15*sInfections));
                Integer sHospitalBeds=beds-impactCases;
                Integer sICUReqT=Integer.parseInt(String.valueOf(0.05*sInfections));
                Integer sVent=Integer.parseInt(String.valueOf(0.62*sInfections));
                Integer sDollarInFlight=Integer.parseInt(String.valueOf((sInfections*populations*avgDailyIncome)/factor));
                boolean result=myDatabase.insertNewData(regName,avgage,avgDailyIncome,avgDailyPopulat,periodItem,elapseTime,reportedCases,beds,impactCurrentlyInfected,impactInfections,impactHospitalBeds,impactCases,impactICUReqT,impactVentilator,impactDollarInFlight,sCurrentlyInfected,sInfections,sHospitalBeds,sICUReqT,sVent,sCases,sDollarInFlight);
                if(result)
                    Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this,"Not successful",Toast.LENGTH_SHORT).show();

                //passing values to UI
                impactCurrent.setText(impactCurrentlyInfected);
                impactDollar.setText(impactDollarInFlight);
                impactCBRT.setText(impactCases);
                impactHospBed.setText(impactHospitalBeds);
                impactIBRT.setText(impactInfections);
                impactICUBRT.setText(impactICUReqT);
                impactVent.setText(impactVentilator);
                severeCurrent.setText(sCurrentlyInfected);
                severeDollar.setText(sDollarInFlight);
                severeCBRT.setText(sCases);
                severeHospBed.setText(sHospitalBeds);
                severeICUBRT.setText(sICUReqT);
                severeIBRT.setText(sInfections);
                severeVent.setText(sVent);
                //Saving to SQLite Database

            }
        }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (periodTypes[position]) {
                case "weeks":
                    periodItem="weeks";
                   period=7;
                   break;
                case "months":
                    periodItem="months";
                    period=30;
                    break;
                default:
                    periodItem="days";
                    period=1;
                    break;

            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
          Toast.makeText(this,"Choose a Period Type",Toast.LENGTH_SHORT).show();
    }
}
