package user.mobile.zeyneddin.zeyneddin.main;

/**
 *      Создан 2018,05,19 Nikcname
 */

    /*
        Класс для товара, долен определить основные критерии продукта:
        0) Ид товара
        1) Имя товара
        2) Количество товара
        3) Цену товара
     */

public class Product {

    private int productId;
    private String productName;
    private String productAmount;
    private String productPrice;

    public Product(String productName, String productAmount, String productPrice) {
        /*
            Конструктор для создния объекта продукта, используется когда
            мы создаём обхъект для нового продукта
         */
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public Product(int productId, String productName, String productAmount, String productPrice) {
        /*
            Конструктор для создния объекта продукта, используется когда
            мы получаем объект из базы данных
         */
        this.productId = productId;
        this.productName = productName;
        this.productAmount = productAmount;
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
