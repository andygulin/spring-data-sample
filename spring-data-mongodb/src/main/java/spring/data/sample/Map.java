package spring.data.sample;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "map")
public class Map implements Serializable {

    private static final long serialVersionUID = -16233110177608822L;

    @Id
    private String id;

    // 注解方式好像创建不了2d索引
    // 用命令方式创建
    // db.map.ensureIndex({"location":"2d",{min:-100,max:100}});
    @GeoSpatialIndexed(max = 100, min = -100)
    private double[] position;

    public Map() {
        super();
    }

    public Map(double[] position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
