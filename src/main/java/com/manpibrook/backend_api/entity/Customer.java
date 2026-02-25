package com.manpibrook.backend_api.entity;

import com.manpibrook.backend_api.entity.enums.EMembershipLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Customer extends Person {

    @Column(nullable = false)
    private int points = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_ship_level")
    private EMembershipLevel membershipLevel = EMembershipLevel.BRONZE;

    public void addPoints(int amount) {
        this.points += amount;
        updateMembershipLevel();
    }
    
    private void updateMembershipLevel() {
        if (points >= 10000) {
            this.membershipLevel = EMembershipLevel.PLATINUM;
        } else if (points >= 5000) {
            this.membershipLevel = EMembershipLevel.GOLD;
        } else if (points >= 2000) {
            this.membershipLevel = EMembershipLevel.SILVER;
        } else {
            this.membershipLevel = EMembershipLevel.BRONZE;
        }
    }
}