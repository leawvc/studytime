package com.challenge.studytime.domain.couponhistory.entity;

import com.challenge.studytime.domain.coupon.entity.Coupon;
import com.challenge.studytime.domain.couponhistory.dto.CouponHistoryRequestDto;
import com.challenge.studytime.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
    private Boolean used;
    private UUID uuid;
    private Long memberId;
    public void setCoupon(Coupon coupon, CouponHistoryRequestDto requestDto) {
        this.coupon = coupon;
        this.used = false;
        this.uuid = UUID.randomUUID();
        this.memberId = requestDto.getUserId();
    }
}
