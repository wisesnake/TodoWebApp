package org.zerock.jdbcex.domain;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class todoVO {

    private Long tno;

    private String title;

    private LocalDate dueDate;

    private boolean finished;

}
