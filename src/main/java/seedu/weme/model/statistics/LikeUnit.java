package seedu.weme.model.statistics;

public class LikeUnit {

    private int likeCount;
    private int dislikeCount;

    public LikeUnit(int likeCount, int dislikeCount) {
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    @Override
    public String toString() {
        return "like: " + likeCount + " dislike:" + dislikeCount;
    }
}
