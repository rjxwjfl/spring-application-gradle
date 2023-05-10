package com.portfolio.springapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDetail {
    private int postId;
    private int userId;
    private int locId;
    private String name;
    private String imageUrl;
    private int postCount;
    private int picCount;
    private int flwCount;
    private String content;
    private int postViewCnt;
    private String evalIds;
    private String picDatas;
    private String createAt;
    private String updateAt;
}
