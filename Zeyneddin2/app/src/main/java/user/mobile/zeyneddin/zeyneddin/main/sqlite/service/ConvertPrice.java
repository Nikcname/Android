package user.mobile.zeyneddin.zeyneddin.main.sqlite.service;

public class ConvertPrice implements IConvertPrice {

    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ConvertPrice(String price) {

        this.price = price;

    }

    @Override
    public String normalForm(){

        try {
            String[] p = price.split("\\.");

            System.out.println(p[0]);
            System.out.println(p[1]);

            if (p[0].equals("")){
                //если ввели ".5" то есть первая часть пуста
                if (p[1].length() == 1){
                    price = "0" + price + "0";
                } else {
                    price = "0" + price;
                }
            } else {
                if (p[1].length() == 1){
                    price = price + "0";
                } else if (p[1].length() > 2){
                    price = p[0] + "." + p[1].substring(0, 2);
                }
            }

        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            //когда ввели в пункт цена "."
            // когда "4." или целое число
            System.out.println(price.length());
            if (price.length() == 1 && price.charAt(0) == '.'){
                price = "0" + price + "00";

            } else if (price.charAt(price.length() -  1) == '.'){
                price = price + "00";
            } else {
                price = price + ".00";
            }
        }
        return price;
    }

}
