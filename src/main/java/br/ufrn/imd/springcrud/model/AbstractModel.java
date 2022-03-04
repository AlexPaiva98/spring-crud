package br.ufrn.imd.springcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractModel<PK extends Serializable> {

    @Column(name = "creation_date", nullable = false, updatable = false)
    @CreatedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonIgnore
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "modification_date")
    @LastModifiedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonIgnore
    private LocalDateTime modificationDate;

    @Column(name = "deletion_date")
    @LastModifiedDate
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonIgnore
    private LocalDateTime deletionDate;

    @NotNull
    private Boolean active = true;

    public abstract PK getId();

    public abstract void setId(PK id);

    @JsonIgnore
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @JsonIgnore
    public LocalDateTime getModificationDate() {
        return this.modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    @JsonIgnore
    public LocalDateTime getDeletionDate() {
        return this.deletionDate;
    }

    public void setDeletionDate(LocalDateTime deletionDate) {
        this.deletionDate = deletionDate;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        this.deletionDate = LocalDateTime.now();
    }

    @JsonIgnore
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

