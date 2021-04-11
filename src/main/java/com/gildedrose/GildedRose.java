package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case Constant.AGED_BRIE:
                    updateAgedBrie(item);
                    break;
                case Constant.BACKSTAGE:
                    updateBackstage(item);
                    break;
                case Constant.SULFURAS:

                    break;
                default:
                    updateGeneric(item);
                    break;
            }
        }
    }

    private void updateGeneric(Item item) {
        if (item.sellIn > 0) {
            decreaseQuality(item, 1);
        } else {
            decreaseQuality(item, 2);
        }
        item.sellIn--;
    }

    private void updateBackstage(Item item) {
        if (item.sellIn > 10) {
            increaseQuality(item, 1);
        } else if (item.sellIn > 5) {
            increaseQuality(item, 2);
        } else if (item.sellIn > 0) {
            increaseQuality(item, 3);
        } else {
            setQualityToZero(item);
        }
        item.sellIn--;
    }

    private void updateAgedBrie(Item item) {
        if (item.sellIn > 0) {
            increaseQuality(item, 1);
        } else {
            increaseQuality(item, 2);
        }
        item.sellIn--;
    }

    private void setQualityToZero(Item item) {
        item.quality = 0;
    }

    private void decreaseQuality(Item item, int decrement) {
        // negative stay negative
        if (item.quality <= -1) {
            return;
        }

        int newQuality = item.quality - decrement;

        // quality never drop below 0
        if (newQuality <= -1) {
            setQualityToZero(item);
            return;
        }

        item.quality = newQuality;
    }

    private void increaseQuality(Item item, int increment) {
        int newQuality = item.quality + increment;

        // 50 is the max unless it was above already
        if (item.quality >= 51) {
            return;
        }

        item.quality = Math.min(newQuality, 50);
    }
}
