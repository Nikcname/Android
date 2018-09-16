package user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment;

public class CallbackDeleteProduct {

    private String name;
    private String amount;
    private String price;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CallbackDeleteProduct(String name, String amount, String price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }
}
