package com.naes0.madassignment;

public class DatabaseSchema
{
    public static class PlayerTable
    {
        public static final String NAME = "players";
        public static class Cols
        {
            public static final String ID = "player_id";
            public static final String ROW = "row";
            public static final String COL = "col";
            public static final String CASH = "cash";
            public static final String HEALTH = "health";
            public static final String EQUIPMASS = "equipmass";
            public static final String EQUIPMENT = "equipment";
        }
    }

    public static class AreaTable
    {
        public static final String NAME = "areas";
        public static class Cols
        {
            public static final String ID = "area_id";
            public static final String TOWN = "town";
            public static final String DESC = "desc";
            public static final String STARRED = "starred";
            public static final String EXPLORED = "explored";
            public static final String UNEXP = "unexplored";
            public static final String UNSTAR = "unstarred";
            public static final String ITEMS = "items";
        }
    }

    public static class ItemTable
    {
        public static final String NAME = "items";
        public static class Cols
        {
            public static final String ID = "item_id";
            public static final String DESC = "desc";
            public static final String VALUE = "value";
            public static final String TYPE = "type";
        }
    }
}
