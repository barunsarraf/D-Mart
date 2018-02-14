package deal.mart;


public class Product {
    public String
            id,product,price,image,description,date,rating,
            seller;

    public Product() {
    }

    public Product(String id,String product, String price, String image, String description,String date,String rating, String seller) {
        this.id =id;
        this.product = product;
        this.price = price;
        this.image = image;
        this.description = description;
        this.date = date;
        this.rating = rating;
        this.seller = seller;
    }
}
