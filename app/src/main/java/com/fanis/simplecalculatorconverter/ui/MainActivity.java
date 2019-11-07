package com.fanis.simplecalculatorconverter.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fanis.simplecalculatorconverter.R;
import com.fanis.simplecalculatorconverter.database.CurrencyEntity;
import com.fanis.simplecalculatorconverter.models.CurrencyDTO;
import com.fanis.simplecalculatorconverter.models.Rate;
import com.fanis.simplecalculatorconverter.viewmodels.ViewModelProviderFactory;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    private static final String TAG = "MainActivity";

    private MainViewModel viewModel;
    private ProgressBar progressBar;
    private Button btGetCurrencies;
    private TextView tvUpdatedAt;
    private Spinner spAvailableRates;

    private boolean lastNumeric = false;
    private boolean stateError = false;
    private boolean lastDot = false;

    private TextView tvInputNumber;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        btGetCurrencies = findViewById(R.id.bt_get_currencies);
        tvInputNumber = findViewById(R.id.tv_input_number);
        tvUpdatedAt = findViewById(R.id.tv_updated_at);
        spAvailableRates = findViewById(R.id.sp_available_rates);

        btGetCurrencies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getAllCurrencies();
            }
        });

        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);

        subscribeObservers();

        viewModel.getCurrenciesFromDB().observe(this, new Observer<CurrencyEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(CurrencyEntity currencyEntity) {
                Log.d(TAG, "onChanged: ");
                if (currencyEntity != null) {

                    final int[] check = {0};

                    List<String> rateNamesList = currencyEntity.getRates().stream()
                            .map(Rate::getCurrency)
                            .collect(Collectors.toList());

                    List<Double> rateValuesList = currencyEntity.getRates().stream()
                            .map(Rate::getValue)
                            .collect(Collectors.toList());

                    Rate currentRate = currencyEntity.getRates().stream()
                            .filter(currency -> "USD".equals(currency.getCurrency()))
                            .findAny()
                            .orElse(null);

                    spAvailableRates.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, rateNamesList));

                    spAvailableRates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override

                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (++check[0] > 1) {
                                tvInputNumber.setText(Double.parseDouble(tvInputNumber.getText().toString()) * rateValuesList.get(position) + "");
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    tvUpdatedAt.setText("Updated at: " + currencyEntity.getDate());
                }
            }
        });

    }

    public void onDigit(View v) {
        if (stateError) {
            tvInputNumber.setText(((Button) v).getText().toString());
            stateError = false;
        } else {
            tvInputNumber.append(((Button) v).getText().toString());
        }
        lastNumeric = true;
    }

    public void onDecimalPoint(View v) {
        if (lastNumeric && !stateError && !lastDot) {
            tvInputNumber.append(".");
            lastNumeric = false;
            lastDot = true;
        }
    }

    public void onOperator(View v) {
        if (lastNumeric && !stateError) {
            tvInputNumber.append(((Button) v).getText().toString());
            lastNumeric = false;
            lastDot = false;
        }
    }

    public void onClear(View v) {
        tvInputNumber.setText("");
        lastNumeric = false;
        stateError = false;
        lastDot = false;
    }

    public void onEqual(View v) {
        if (lastNumeric && !stateError) {
            String text = tvInputNumber.getText().toString();
            Expression expression = (new ExpressionBuilder(text)).build();

            try {
                double result = expression.evaluate();
                tvInputNumber.setText(result + "");
                lastDot = true;
            } catch (ArithmeticException exception) {
                tvInputNumber.setText("ERROR");
                stateError = true;
                lastNumeric = false;
            }
        }
    }

    private void subscribeObservers() {
        viewModel.observeCurrencies().observe(this, new Observer<MainResource<CurrencyDTO>>() {
            @Override
            public void onChanged(MainResource<CurrencyDTO> currencyMainResource) {
                if (currencyMainResource != null) {
                    switch (currencyMainResource.status) {

                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case SUCCESS: {
                            showProgressBar(false);
                            Log.d(TAG, "SUCCESS: got currencies!" + currencyMainResource.data.getBase());
//                            onLoginSuccess();
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(MainActivity.this, currencyMainResource.message
                                    + "\n There was a probleeem!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}