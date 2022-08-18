package test.demo.enums;

public enum SeasonEnum {
    //this.name,...,;
    SPRING(1, "春天"),SUMMER(2, "夏天"),AUTUMN(3, "秋天"),WINTER(4, "冬天");
    private String seasonName;
    private int seq;
    SeasonEnum(int seq, String seasonName) {
        this.seq = seq;
        this.seasonName = seasonName;
    }

    public static String getName(int seq) {
        for (SeasonEnum s : SeasonEnum.values()) {
            if (s.getSeq() == seq){
                return s.toString();
            }
        }
        return "火星季节是吧？";
    }

    public int getSeq() {
        return seq;
    }

    @Override
    public String toString() {
        return this.name() + ":" + this.seq + this.seasonName;
    }
}
