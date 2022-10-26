package com.tangzhangss.commonutils.uidgenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="worker_node")
public class WorkerNodeEntity {
    /**
     * Entity unique id (table unique)
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    /**
     * Type of CONTAINER: HostName, ACTUAL : IP.
     */
    private String hostName;
    /**
     * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
     */
    private String port;

    private Integer type;
    /**
     * Worker launch date, default now
     */
    private Date launchDate = new Date();
    /**
     * Created time
     */
    private Date created;
    /**
     * Last modified
     */
    private Date modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Override
    public String toString() {
        return "WorkerNodeEntity{" +
                "id=" + id +
                ", hostName='" + hostName + '\'' +
                ", port='" + port + '\'' +
                ", type=" + type +
                ", launchDate=" + launchDate +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
