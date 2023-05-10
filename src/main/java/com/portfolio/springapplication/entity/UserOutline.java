package com.portfolio.springapplication.entity;

import lombok.Data;

@Data
public class UserOutline {
    private int userId;
    private String name;
    private String introduce;
    private String imageUrl;
    private int postCount;
    private int picCount;
    private int followeeCount;
    private int followerCount;
}
