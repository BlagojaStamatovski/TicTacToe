package com.netcetera.codingchallenge.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Proxy(lazy = false)
public class UserAuthority implements Serializable {
    private static final long serialVersionUID = 576554460937494653L;

    @Id
    private String name;
}
