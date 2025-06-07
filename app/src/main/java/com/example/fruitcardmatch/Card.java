package com.example.fruitcardmatch;

public class Card {
    private int imageViewId, cardId;
    private boolean isFlipped = false, isMatched = false;

    public Card(int imageViewId, int cardId) {
        this.imageViewId = imageViewId;
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getImageViewId() {
        return imageViewId;
    }

    public void setImageViewId(int imageViewId) {
        this.imageViewId = imageViewId;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "imageViewId=" + imageViewId +
                ", cardId=" + cardId +
                ", isFlipped=" + isFlipped +
                ", isMatched=" + isMatched +
                '}';
    }

}
