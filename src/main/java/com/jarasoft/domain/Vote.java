package com.jarasoft.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
@Table(name = "VOTE")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_ID")
    private long id;
    @ManyToOne
    @JoinColumn(name = "OPTION_ID")
    private Option option;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vote)) return false;
        Vote vote = (Vote) o;
        return getId() == vote.getId() && getOption().equals(vote.getOption());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOption());
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", option=" + option +
                '}';
    }
}
