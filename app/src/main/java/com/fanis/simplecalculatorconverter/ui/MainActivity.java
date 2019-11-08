package com.fanis.simplecalculatorconverter.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
    private boolean hasError = false;
    private boolean lastDot = false;

    private TextView tvInputNumber;
    private TextView tvConvertedNumber;
    private Button btConvertValue;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        btGetCurrencies = findViewById(R.id.bt_get_currencies);
        tvInputNumber = findViewById(R.id.tv_input_number);
        tvConvertedNumber = findViewById(R.id.tv_converted_number);
        btConvertValue = findViewById(R.id.bt_convert);
        tvUpdatedAt = findViewById(R.id.tv_updated_at);
        spAvailableRates = findViewById(R.id.sp_available_rates);

        btGetCurrencies.setOnClickListener(v -> viewModel.getAllCurrencies());

        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel.class);

        subscribeObservers();

        viewModel.getCurrenciesFromDB().observe(this, new Observer<CurrencyEntity>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(CurrencyEntity currencyEntity) {
                if (currencyEntity != null) {

                    List<String> rateNamesList = currencyEntity.getRates().stream()
                            .map(Rate::getCurrency)
                            .collect(Collectors.toList());

                    List<Double> rateValuesList = currencyEntity.getRates().stream()
                            .map(Rate::getValue)
                            .collect(Collectors.toList());

                    spAvailableRates.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, rateNamesList));

                    btConvertValue.setOnClickListener(v -> {
                        if (!tvInputNumber.getText().toString().equals("") && !hasError && lastNumeric &&
                                tvInputNumber.getText().toString().matches("^[^+\\-/*]+$")
                        ) {
                            tvConvertedNumber.setText(Double.parseDouble(tvInputNumber.getText().toString()) * rateValuesList.get(spAvailableRates.getSelectedItemPosition()) + "");
                        }
                    });

                    tvUpdatedAt.setText(getString(R.string.update_at, currencyEntity.getDate()));

                } else {
                    tvUpdatedAt.setText(getString(R.string.update_at_never));
                }
            }
        });
    }

    public void onDigit(View v) {
        if (hasError) {
            tvInputNumber.setText(((Button) v).getText().toString());
            hasError = false;
        } else {
            tvInputNumber.append(((Button) v).getText().toString());
        }
        lastNumeric = true;
    }

    public void onDecimalPoint(View v) {
        if (lastNumeric && !hasError && !lastDot) {
            tvInputNumber.append(".");
            lastNumeric = false;
            lastDot = true;
        }
    }

    public void onOperator(View v) {
        if (lastNumeric && !hasError) {
            tvInputNumber.append(((Button) v).getText().toString());
            lastNumeric = false;
            lastDot = false;
        }
    }

    public void onClear(View v) {
        tvInputNumber.setText("");
        tvConvertedNumber.setText("");
        lastNumeric = false;
        hasError = false;
        lastDot = false;
    }

    public void onEqual(View v) {
        if (lastNumeric && !hasError) {
            String text = tvInputNumber.getText().toString();
            Expression expression = (new ExpressionBuilder(text)).build();

            try {
                double result = expression.evaluate();
                tvInputNumber.setText(result + "");
                lastDot = true;
            } catch (ArithmeticException exception) {
                tvInputNumber.setText(R.string.error);
                hasError = true;
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
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(MainActivity.this, currencyMainResource.message
                                    + getString(R.string.error_encounter) + "", Toast.LENGTH_SHORT).show();
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