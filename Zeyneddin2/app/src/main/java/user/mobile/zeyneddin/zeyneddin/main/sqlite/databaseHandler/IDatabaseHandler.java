package user.mobile.zeyneddin.zeyneddin.main.sqlite.databaseHandler;

import java.util.List;

import user.mobile.zeyneddin.zeyneddin.main.Product;

/**
 *      Создан 2018,05,19 Nikcname
 */

    /*
        Интерфейс с основными функциями который будет применён
        при работе с базой
     */

interface IDatabaseHandler {

    void addProduct(Product product);    //добавить товар
    Product getProduct(int id);          //получить товар по ид
    List<Product> getAllProduct();       //получить список всех товаров
    int getProductCount();               //получит количество товаров в базе
    int updateProduct(Product product);  //изменить товар
    void deleteProduct(Product product); //удалить товар из базы
    void deleteAll();                    //!!!удалить всю базу!!!

}
