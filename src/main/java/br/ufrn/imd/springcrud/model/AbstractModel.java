package br.ufrn.imd.springcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractModel {
    @Id
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @JsonIgnore
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationDate = LocalDateTime.now();
    }

    @PreRemove
    public void preRemove() {
        this.deletionDate = LocalDateTime.now();
    }
}

