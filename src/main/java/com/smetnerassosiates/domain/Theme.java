package com.smetnerassosiates.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id") @NoArgsConstructor @RequiredArgsConstructor
@Entity
@Table(name = "THEME", uniqueConstraints =
@UniqueConstraint(columnNames={"RENDERING_ENGINE", "BROWSER", "PLATFORM", "ENGINE_VERSION", "CSS_GRADE"}))
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "RENDERING_ENGINE")
    private String renderingEngine;

    @NonNull
    @Column(name = "BROWSER")
    private String browser;

    @NonNull
    @Column(name = "PLATFORM")
    private String platform;

    @NonNull
    @Column(name = "ENGINE_VERSION")
    private String engineVersion;

    @NonNull
    @Column(name = "CSS_GRADE")
    private String cssGrade;

}
