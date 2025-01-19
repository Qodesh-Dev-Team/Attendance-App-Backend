package com.qcc.hallow.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "OTPRequest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OTPRequest extends EntityModel {

    private String email;
    private String otpCode;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isExpired;
}
