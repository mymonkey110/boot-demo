package com.netease.boot.dal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;

/**
 * Created by Michael Jiang on 16-12-20.
 */
public class Product implements Serializable {
    private static final long serialVersionUID = -5837342740172526607L;

    @Size(min = 1, max = 32)
    private String code;
    @Size(min = 1, max = 16)
    private String name;
    @Size(max = 255)
    private String description;
    @NotNull
    private EMailAddress principalEmail;

    public Product(String code, String name, String description, EMailAddress principalEmail) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.principalEmail = principalEmail;
    }

    public void changeName(String newName) {
        this.name = newName;
    }

    public void changeDescription(String newDescription) {
        this.description = newDescription;
    }

    public void changePrincipalEMail(EMailAddress newPrincipalEMail) {
        this.principalEmail = newPrincipalEMail;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public EMailAddress getPrincipalEmail() {
        return principalEmail;
    }

    @Override
    public String toString() {
        return "Product{" +
                "bizCode='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", principalEmail=" + principalEmail +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Product product = new Product("comb","蜂巢","云计算基础设施产品",new EMailAddress("hzxx@corp.netease.com"));
        /*FileOutputStream fileOutputStream = new FileOutputStream("/home/mj/work/product.data");
        fileOutputStream.write(SerializationUtils.serialize(policyContext));
        fileOutputStream.flush();
        fileOutputStream.close();*/
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/mj/work/product.data"));
        oos.writeObject(product);
        oos.flush();
        oos.close();

        /*FileInputStream fileInputStream=new FileInputStream("/home/mj/work/product.data");
        byte[] rawPolicyContext=new byte[fileInputStream.available()];
        fileInputStream.read(rawPolicyContext);
        PolicyContext pc = SerializationUtils.deserialize(rawPolicyContext);
        System.out.println(pc);*/
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/home/mj/work/product.data"));
        Product pc = (Product) ois.readObject();
        System.out.println(pc);
    }
}
