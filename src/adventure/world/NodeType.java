package adventure.world;

/** types of locations on a stage path */
public enum NodeType {
    /** standard fight */ ENEMY,
    /** tougher fight */ ELITE,
    /** free gold */ TREASURE,
    /** healing location */ REST,
    /** healing shop */ SHOP,
    /** stage boss */ BOSS;

    /** gets readable node label
     * @return node description
     */
    public String getLabel() {
        return switch(this) {
            case ENEMY -> "Normal enemy";
            case ELITE -> "Elite enemy";
            case TREASURE -> "Treasure";
            case REST -> "Rest";
            case SHOP -> "Shop";
            case BOSS -> "Boss";
        };
    }
}
