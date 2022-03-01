package io.github.angrylid.mall.dto.response;

public class AccountInformation {
    private int following;
    private int followed;

    private String name;
    private String telephone;

    private Integer favorite;

    public AccountInformation() {

    }

    public AccountInformation(int following, int followed, String name, String telephone) {
        this.following = following;
        this.followed = followed;
        this.name = name;
        this.telephone = telephone;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

}
