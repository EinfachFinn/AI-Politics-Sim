package Player;

public class Player_stats {

    private String name;
    private int age = 50;
    private int yearsInOffice = 0;

    private int popularity = 60;
    private int trustParliament = 70;

    private int mediaApproval = 50;
    private int coalitionStability = 80;

    private int health = 100;
    private int stressLevel = 20;

    private String ownPartyName;
    private String PoliticalIdeology;
    private String coalitionParties;


    private int passedLaws = 0;
    private int crisesSurvived = 0;



    public String toString() {
        return "player_stats{" +
                ", yearsInOffice=" + yearsInOffice +
                ", popularity=" + popularity +
                ", trustParliament=" + trustParliament +
                ", mediaApproval=" + mediaApproval +
                ", coalitionStability=" + coalitionStability +
                ", health=" + health +
                ", stressLevel=" + stressLevel +
                ", passedLaws=" + passedLaws +
                ", crisesSurvived=" + crisesSurvived +
                '}';
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public int getYearsInOffice() {return yearsInOffice;}
    public void setYearsInOffice(int yearsInOffice) {this.yearsInOffice = yearsInOffice;}

    public int getPopularity() {return popularity;}
    public void setPopularity(int popularity) {this.popularity = popularity;}

    public int getTrustParliament() {return trustParliament;}
    public void setTrustParliament(int trustParliament) {this.trustParliament = trustParliament;}

    public int getMediaApproval() {return mediaApproval;}
    public void setMediaApproval(int mediaApproval) {this.mediaApproval = mediaApproval;}

    public int getCoalitionStability() {return coalitionStability;}
    public void setCoalitionStability(int coalitionStability) {this.coalitionStability = coalitionStability;}

    public int getHealth() {return health;}

    public void setHealth(int health) {this.health = health;}

    public int getStressLevel() {return stressLevel;}
    public void setStressLevel(int stressLevel) {this.stressLevel = stressLevel;}

    public String getOwnPartyName() {return ownPartyName;}
    public void setOwnPartyName(String ownPartyName) {this.ownPartyName = ownPartyName;}

    public String getPoliticalIdeology() {return PoliticalIdeology;}
    public void setPoliticalIdeology(String politicalIdeology) {PoliticalIdeology = politicalIdeology;}

    public String getCoalitionParties() {return coalitionParties;}
    public void setCoalitionParties(String coalitionParties) {this.coalitionParties = coalitionParties;}

    public int getPassedLaws() {return passedLaws;}
    public void setPassedLaws(int passedLaws) {this.passedLaws = passedLaws;}

    public int getCrisesSurvived() {return crisesSurvived;}
    public void setCrisesSurvived(int crisesSurvived) {this.crisesSurvived = crisesSurvived;}


}
