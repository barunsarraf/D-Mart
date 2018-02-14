package deal.mart;

/**
 * Created by Admin on 10/1/2017.
 */

public class User {
    public String name,image,password,mail,mobile,address;

    public User() {
    }

    public User(String name,String image,String password, String mail, String mobile, String address) {
        this.name = name;
        this.image = image;
        this.password = password;
        this.mail = mail;
        this.mobile = mobile;
        this.address = address;
    }
}
