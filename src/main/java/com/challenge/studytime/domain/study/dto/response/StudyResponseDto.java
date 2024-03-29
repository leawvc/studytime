package com.challenge.studytime.domain.study.dto.response;

import com.challenge.studytime.domain.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyResponseDto {
    private String title;
    private String content;
    private int membersCount;

    public static StudyResponseDto toDto(Study study) {
        return StudyResponseDto.builder()
                .content(study.getContent())
                .title(study.getTitle())
                .membersCount(study.getMembersCount())
                .build();
    }
}
