package user.mobile.zeyneddin.zeyneddin.main.sqlite;

import android.os.Bundle;
import android.app.DialogFragment;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;

import user.mobile.zeyneddin.zeyneddin.R;
import user.mobile.zeyneddin.zeyneddin.main.Product;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.databaseHandler.DatabaseHandler;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment.CallbackChangeProduct;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment.CallbackDeleteProduct;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.recycleView.CallbackFromRecycleView;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment.ChosedElementDF;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.recycleView.RecycleView;
import user.mobile.zeyneddin.zeyneddin.main.sqlite.service.ConvertPrice;

/**
 *      Создан 2018,05,19 Nikcname
 */

    /*
        Основное активити для базы в нем должно быть задействовано:

        1) Создание SQLite базы данных;
            это будет выполено автомачически при сздании объекта базы.
            20.05.2018 Done

        2) Запись данных в SQLite;
            Долно быть 3 TextVew, в первый вводиться имя, во второй
            количество, в третий цена. Эти данные должны записаться в базу.
            Если в базе уже есть такая запись с таким именем, то обновить не
            записывая её в качестве новой.
            20.05.2018 Done

        3) Чтение данных из SQLite и запис в RecycleView;
            Создаём RecycleView с тремя TextView, в певый записываем имя,
            второй количество, в третий цену из нашей базы данных.
            20.05.2018 Done

        4) Удаление данных из SQLite;
            {Вешаем контекстное меню на наш RecycleView, там будет функция
            "Удалить" при нажатии на которую мы берём имя и по нему найдя
            запись в базе удаляем её.}
            Реализовал всё в DialogFragment
            23.05.2018 Done


        5) Выборочное изменение данных;
            {В контекстное меню RecycleView добавляем ещё 1 функцию, "Изменить"
            при нажати на которую выходит DialogFragment с тремя PlainText,
            которые уже заполнены именем, количеством и ценой, также в нём
            содержиться кнопка подтверждения при нажатии на которую изменяется
            запись в базе}
            Реализовал всё в DialogFragment
            23.05.2018 Done

        6) Создать OptionMenu;
            Добавить в него "Переместить данные в Firebase" при нажатии на
            которую будет выполнена связь с базой данных в следствии которой
            все данные из SQLite будут скопированы в онлайн базу данных

            Добавить в него "Скачать данные из Firebase" при нажатии на которую
            всплывёт DialogFragment "Вы уверены? База на телефоне будет удалена"
            в нём будет две кнопки "Да" и "Нет", При выборе "Да" сначала вся
            база копируется в Firebase с датой выгрузки, и только потом удаляется
            база в телефоне и скачивается корневая база из Firebase

        Оставлю 6 пункт на потом 2018.05.26 закончил работу данного пакета
     */

public class SqliteActivity extends AppCompatActivity{

    List<String> listOfNames;
    List<String> listOfAmount;
    List<String> listOfPrice;
    DatabaseHandler db;
    RecyclerView productsRecycleView;
    RecyclerView.Adapter productsRecycleViewAdapter;
    RecyclerView.LayoutManager productsLayoutManager;
    DialogFragment chosenDialog;
    public static Bus bus;
    Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        bus = new Bus(ThreadEnforcer.MAIN);
        bus.register(this);

        listOfNames = new ArrayList<>();
        listOfAmount = new ArrayList<>();
        listOfPrice = new ArrayList<>();

        btnAddProduct = findViewById(R.id.btnAppProduct);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*
            1) Идёт проверка, есть ли данный продукт в базе, если да
            то обновляется он, если нет то создаётся новый продукт

            2) Сабирается ListView из базы и передаётся в RecycleView

            3) Пересоздаётся адаптер RecycleView
            */
                TextView productName = findViewById(R.id.ptName);
                TextView productAmount = findViewById(R.id.ptAmount);
                TextView productPrice = findViewById(R.id.ptPrice);

                String name = productName.getText().toString();
                String amount = productAmount.getText().toString();
                String price = productPrice.getText().toString();

                if (
                        !name.equals("") &&
                        !amount.equals("") &&
                        !price.equals("")
                        ){

                    price = new ConvertPrice(price).normalForm();
                    boolean updated = false;
                    List<Product> products = db.getAllProduct();
                    for (Product product : products) {

                        if (product.getProductName().contentEquals(name)){
                            db.updateProduct(new Product(product.getProductId(),
                                    name, amount, price));
                            updated = true;
                            break;
                        }
                    }

                    if (!updated){
                        db.addProduct(new Product(name, amount, price));
                    }

                    showDB();

                    productName.setText("");
                    productPrice.setText("");
                    productAmount.setText("");
                    productName.requestFocus();

                } else if (
                        productName.getText().toString().equals("")
                        ){
                    Toast.makeText(getApplicationContext(), "Product name is Empty",
                            Toast.LENGTH_SHORT).show();
                    productName.requestFocus();
                } else if (
                        productAmount.getText().toString().equals("")
                        ){
                    Toast.makeText(getApplicationContext(), "Product amount is Empty",
                            Toast.LENGTH_SHORT).show();
                    productAmount.requestFocus();
                } else if (
                        productPrice.getText().toString().equals("")
                        ) {
                    Toast.makeText(getApplicationContext(), "Product price is Empty",
                            Toast.LENGTH_SHORT).show();
                    productPrice.requestFocus();
                }
            }
        });

        db = new DatabaseHandler(this);

        productsRecycleView = findViewById(R.id.recycleViewProducts);
        productsLayoutManager = new LinearLayoutManager(this);
        productsRecycleView.setLayoutManager(productsLayoutManager);

        showDB();

    }

    void showDB(){
        /*
            Метод для того чтобы передать всё что находиться в базе
            в RecycleView
         */
        listOfNames.clear();
        listOfAmount.clear();
        listOfPrice.clear();

        List<Product> products = db.getAllProduct();
        for (Product product : products) {

            listOfNames.add(product.getProductName());
            listOfAmount.add(product.getProductAmount());
            listOfPrice.add(product.getProductPrice());

            String log = "Id: " +    product.getProductId()+
                    " ,Name: "  +    product.getProductName()+
                    " ,Amount: "+    product.getProductAmount()+
                    "Price: "   +    product.getProductPrice();
//            System.out.println(log);
        }
//        db.deleteAll();
        productsRecycleViewAdapter = new RecycleView(listOfNames, listOfAmount, listOfPrice);
        productsRecycleView.setAdapter(productsRecycleViewAdapter);
    }

    @Subscribe
    public void CREATE_DIALOG_FRAGMENT(CallbackFromRecycleView callback){

        chosenDialog = new ChosedElementDF();
        Bundle arguments = new Bundle();
        arguments.putString("name", callback.getName());
        arguments.putString("amount", callback.getAmount());
        arguments.putString("price", callback.getPrice());
        chosenDialog.setArguments(arguments);
        chosenDialog.show(getFragmentManager(), "DialogChosen");
    }

    @Subscribe
    public void CHANGE_PRODUCT_PARAMETERS(CallbackChangeProduct callback){

        String productOldName = callback.getName_old();
        String productNewName = callback.getName();
        String productNewAmount = callback.getAmount();
        String productNewPrice = callback.getPrice();

        List<Product> products = db.getAllProduct();
        for (Product product : products) {

            if (product.getProductName().contentEquals(productOldName)){
                db.updateProduct(new Product(product.getProductId(),
                        productNewName,
                        productNewAmount,
                        productNewPrice)
                );
                break;
            }
        }
        showDB();
    }

    @Subscribe
    public void DELETE_PRODUCT(CallbackDeleteProduct callback){

        String productNewName = callback.getName();
        String productNewAmount = callback.getAmount();
        String productNewPrice = callback.getPrice();

        List<Product> products = db.getAllProduct();
        for (Product product : products) {

            if (product.getProductName().contentEquals(productNewName)){
                db.deleteProduct(new Product(product.getProductId(),
                        productNewName,
                        productNewAmount,
                        productNewPrice)
                );
                break;
            }
        }
        showDB();
    }
}
