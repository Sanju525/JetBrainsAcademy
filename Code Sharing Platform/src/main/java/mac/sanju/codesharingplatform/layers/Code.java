package mac.sanju.codesharingplatform.layers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "codesharingplatform")
public class Code {
    @Id
    @Column(name = "uuid")
    @JsonIgnore
    private String uuid;

    @Column(length = 100000)
    private String code;

    @Column
    private String date;

    @Column
    private int time; // in seconds.

    @Column
    private int views; // number of times can be accessed.

    public Code() {
    }

    public Code(String uuid, String code, String date, int time, int views) {
        this.uuid = uuid;
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
