package io.github.maxixcom.otus.booklib.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_genre_id")
    @SequenceGenerator(name = "seq_genre_id", sequenceName = "seq_genre_id", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;
}
