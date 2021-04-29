package com.wani.gym.member.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhoneNumber {

    @Column
    private String first;

    @Column
    private String middle;

    @Column
    private String last;

    protected PhoneNumber() {
    }

    public PhoneNumber(String first, String middle, String last) {
        this.first = first;
        this.middle = middle;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }
}
