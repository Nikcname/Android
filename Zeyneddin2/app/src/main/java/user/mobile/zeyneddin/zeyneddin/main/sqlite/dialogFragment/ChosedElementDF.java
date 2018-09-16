package user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import user.mobile.zeyneddin.zeyneddin.R;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.SqliteActivity;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.service.ConvertPrice;

public class ChosedElementDF extends DialogFragment {

    String name;
    String amount;
    String price;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString("name");
        amount = getArguments().getString("amount");
        price = getArguments().getString("price");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("ChosenElement");
        View v = inflater.inflate(R.layout.activity_dialog_fragment, container, false);
        final TextView nameTv = v.findViewById(R.id.changingName);
        final TextView amountTv = v.findViewById(R.id.changingAmnt);
        final TextView priceTv = v.findViewById(R.id.changingPrice);

        Button btnDelete = v.findViewById(R.id.btnDeleteSQL);
        Button btnChange = v.findViewById(R.id.btnChangeSQL);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqliteActivity.bus.post(new CallbackChangeProduct(
                        name,
                        nameTv.getText().toString(),
                        amountTv.getText().toString(),
                        new ConvertPrice(priceTv.getText().toString()).normalForm()
                ));
                getDialog().dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SqliteActivity.bus.post(new CallbackDeleteProduct(
                        name,
                        amount,
                        price
                ));


                getDialog().dismiss();
            }
        });

        nameTv.setText(name);
        amountTv.setText(amount);
        priceTv.setText(price);

        return v;
    }

}
