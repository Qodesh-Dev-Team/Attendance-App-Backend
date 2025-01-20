package com.qcc.hallow.entity;

import com.qcc.hallow.entity.enums.UserAccountStatus;
import com.qcc.hallow.entity.enums.UserAccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends EntityModel {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserAccountType userAccountType;
    @Enumerated(EnumType.STRING)
    private UserAccountStatus userAccountStatus;

}
