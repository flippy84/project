package Shared;

/**
 * This enum describes the users type of account, this needs to match
 * the contents of the database table UserTypes.
 */
public enum UserType
{
    Player(1),
    LeagueOwner(2),
    Operator(3),
    Spectator(4),
    Advertiser(5);

    private int value;

    UserType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        if (this.name().equals("LeagueOwner"))
            return "League owner";
        return super.toString();
    }
};
