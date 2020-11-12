package io.spring.guides.mbg.entity;

import java.util.Date;

public class Message {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.sno
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    private Integer sno;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.content
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.apply_date
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    private Date applyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.user_id
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column message.approved
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    private Byte approved;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.sno
     *
     * @return the value of message.sno
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public Integer getSno() {
        return sno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.sno
     *
     * @param sno the value for message.sno
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public void setSno(Integer sno) {
        this.sno = sno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.content
     *
     * @return the value of message.content
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.content
     *
     * @param content the value for message.content
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.apply_date
     *
     * @return the value of message.apply_date
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.apply_date
     *
     * @param applyDate the value for message.apply_date
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.user_id
     *
     * @return the value of message.user_id
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.user_id
     *
     * @param userId the value for message.user_id
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column message.approved
     *
     * @return the value of message.approved
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public Byte getApproved() {
        return approved;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column message.approved
     *
     * @param approved the value for message.approved
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    public void setApproved(Byte approved) {
        this.approved = approved;
    }
}