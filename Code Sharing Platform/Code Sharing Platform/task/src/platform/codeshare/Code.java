package platform.codeshare;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "code")
public class Code {

    @Id
    @Column(name = "uuid")
    @JsonIgnore
    private String uuid;

    @Column
    private String date;

    @Column(length = 1000000)
    private String code;

    @Column
    private String time; // Global
//    A limit on the viewing time will allow viewing a code snippet for a certain period of time,
//    and after its expiration, the code snippet is deleted from the database.

    @Column
    private String views; // in GUI; !in API
//    A limit on the number of views will allow viewing the snippet only a certain number of times,
//    after which the snippet is deleted from the database.

//    If both restrictions are applied to a certain code snippet,
//    it has to be deleted when at least one of these limits is reached.



    public Code() {
        super();
    }

    public Code(String uuid, String date, String code, String time, String views) {
        this.uuid = uuid;
        this.date = date;
        this.code = code;
        this.time = time;
        this.views = views;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Code{" +
                "uuid='" + uuid + '\'' +
                ", date='" + date + '\'' +
                ", code='" + code + '\'' +
                ", time=" + time +
                ", views=" + views +
                '}';
    }


}