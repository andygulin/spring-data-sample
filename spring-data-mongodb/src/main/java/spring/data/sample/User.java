package spring.data.sample;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 5799249583604983141L;

    @Id
    private String id;
    @Indexed
    private String name;
    @Indexed
    private Integer age;
    @Indexed
    private String address;
    @Indexed
    private Date createAt;

    public User() {
        super();
    }

    public User(String name, Integer age, String address, Date createAt) {
        super();
        this.name = name;
        this.age = age;
        this.address = address;
        this.createAt = createAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
