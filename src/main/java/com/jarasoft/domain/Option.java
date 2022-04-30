package com.jarasoft.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OPTION")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Long id;
    @Column(name = "OPTION_VALUE")
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;
        Option option = (Option) o;
        return getId().equals(option.getId()) && getValue().equals(option.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue());
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}
