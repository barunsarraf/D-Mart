package deal.mart;

/**
 * Created by Admin on 10/13/2017.
 */

public class Notification {

    public String product,content,user,date;

    public Notification() {}

    public Notification(String product, String content, String user, String date) {
        this.product = product;
        this.content = content;
        this.user = user;
        this.date = date;
    }
}
