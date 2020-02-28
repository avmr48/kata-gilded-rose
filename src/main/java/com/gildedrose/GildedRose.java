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

    private void setQuality(Item item, int quality) {
        if (isQualityAlreadyOutOfBoundFor(item)) {
            return;
        }
        item.quality = quality < 0 ? 0 : Math.min(quality, 50);
    }

    private boolean isQualityAlreadyOutOfBoundFor(Item item) {
        return item.quality <= 0 || item.quality >= 50;
    }

    private void updateGeneric(Item item) {
        if (item.sellIn > 0) {
            setQuality(item, item.quality - 1);
        } else {
            setQuality(item, item.quality - 2);
        }
        item.sellIn--;
    }

    private void updateBackstage(Item item) {
        if (item.sellIn > 10) {
            setQuality(item, item.quality + 1);
        } else if (item.sellIn > 5) {
            setQuality(item, item.quality + 2);
        } else if (item.sellIn > 0) {
            setQuality(item, item.quality + 3);
        } else {
            setQuality(item, 0);
        }
        item.sellIn--;
    }

    private void updateAgedBrie(Item item) {
        if (item.sellIn > 0) {
            setQuality(item, item.quality + 1);
        } else {
            setQuality(item, item.quality + 2);
        }
        item.sellIn--;
    }
}
