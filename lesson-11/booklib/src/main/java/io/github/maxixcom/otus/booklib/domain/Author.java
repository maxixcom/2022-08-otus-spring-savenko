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
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_author_id")
    @SequenceGenerator(name = "seq_author_id", sequenceName = "seq_author_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
}
