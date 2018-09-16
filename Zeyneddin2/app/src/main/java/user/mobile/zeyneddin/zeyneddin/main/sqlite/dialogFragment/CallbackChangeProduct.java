package user.mobile.zeyneddin.zeyneddin.main.sqlite.dialogFragment;

public class CallbackChangeProduct {

    private String name_old;
    private String name;
    private String amount;
    private String price;

    public String getName_old() {
        return name_old;
    }

    public void setName_old(String name_old) {
        this.name_old = name_old;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public CallbackChangeProduct(String name_old, String name, String amount, String price) {

        this.name_old = name_old;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }
}
