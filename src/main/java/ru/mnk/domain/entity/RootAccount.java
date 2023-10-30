package ru.mnk.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("root")
public class RootAccount extends Account {

}
