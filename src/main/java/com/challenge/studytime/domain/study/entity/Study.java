package com.challenge.studytime.domain.study.entity;

import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @Column(name = "study_title", length = 100, nullable = false)
    private String title;

    @Column(name = "study_content", length = 255, nullable = false)
    private String content;

    @Column
    private int membersCount;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    private boolean deleteStudy = false;

    public void changeStudy() {
        deleteStudy = true;
    }

    //연관관계 편의 메소드
    public void changeMember(Member member) {
        this.member = member;
        member.getStudyList().add(this);
    }

}
