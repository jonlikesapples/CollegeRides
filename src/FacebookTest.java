import java.awt.List;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Comment;
import com.restfb.types.Group;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.sun.tools.doclint.Entity;

/**
 * TO PRINT POST: LINES 64-66
 * TO PRINT COMMENTS: LINES 79-81 AND 132/133
 */

//to do list: user add afterwards also
public class FacebookTest {
    @SuppressWarnings({"deprecation"})
    public static void main(String[] args) {
        //STOP HERE. READ ME.
        //PREREQ: HAVE TO BE ADMIN OF LBCSJ PATHLIGHT COLLEGE RIDES GROUP
        //have to refresh access token every 2 hours [note to self: find way to make token perm]
        //get token from:
        //https://developers.facebook.com/tools/explorer?method=GET&path=780831138696665%2Fname&version=v2.8
        //steps: get user token -> user_about_me[x] -> get access token
        //after v2.8, delete everything and replace with "me", and submit.
        //replace with this       VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
        String MY_ACCESS_TOKEN = "EAACEdEose0cBAIPqChjTDM6ZBxGuXkDN39qxzQam3LjN2iUqcygAMCWmlvdH3170ZAozk5eUOLZA1MuI8k7AnyhNuNJM4qGTDJv8l5rGORdWtx88mpk0zCZBGiL9PRNTLk22w7qplTkl7DZClwM8a7XFX4q1MkE9p7jYr50ZA3lAZDZD";
        FacebookClient fbclient = new DefaultFacebookClient(MY_ACCESS_TOKEN);

            /*
            To do list 11/2/2016, DO IN THIS ORDER

            2. edit a person. i.e. edit jon wong's status before/after (case of comment isn't explicit enough), NOT UPDATING FULL DATABASE, maybe make update() method????
               edit if a person can drive too

            3. in user choice, make user choice to add person's status after, and add to each array accordingly

            4**. put people in cars according to the code of the driver,
            handle beforehand first, then afterwards
            ex: michael fong(driver) code: 10, enoch(rider) code: 10

            5. prompt most common people and their trends? see if need to be added? brit, isabel, elisa, eric, etc...
             */


        try {
            Scanner reader = new Scanner(System.in);
            PrintWriter out = new PrintWriter("posts.txt");
            PrintWriter importantStuff = new PrintWriter("importantstuff.txt");
            ArrayList<String> nameArray = new ArrayList<String>();
            ArrayList<String> commentArray = new ArrayList<String>();

            User me = fbclient.fetchObject("me", User.class);
            System.out.println("Welcome to " + me.getName() + " LBCSJ College Rides App!");
            System.out.println("FB profile: fb.com/" + me.getId());
            System.out.println("");

//            System.out.print("Fetching Facebook comments ");
//            for (int i = 0; i < 3; i++)
//            {
//                System.out.print(". ");
//                TimeUnit.MILLISECONDS.sleep(800);
//            }
//
//            System.out.println("\n");

            Connection<Group> groupsFeed = fbclient.fetchConnection("me/groups", Group.class);
            for (java.util.List<Group> page : groupsFeed) {
                for (Group aGroup : page) {

                    if (aGroup.getName().contains("LBCSJ")) {
                        System.out.println("PAGE:" + aGroup.getName());

                        Connection<Post> postFeed = fbclient.fetchConnection(aGroup.getId() + "/feed", Post.class);

                        outerloop:
                        for (java.util.List<Post> postPage : postFeed) {
                            for (Post aPost : postPage) {

                                //only get the first post about needing rides on Sunday
                                String POST_MESSAGE = "This week's question: In the spirit of Halloween, whats your favorite and least favorite candy bar?";


                                if (aPost.getMessage().contains(POST_MESSAGE)) {
                                    Connection<Comment> commentFeed = fbclient.fetchConnection(aPost.getId() + "/comments", Comment.class);
                                    System.out.println("Author: " + aPost.getFrom().getName());
                                    System.out.println("Created at: " + aPost.getCreatedTime());
                                    System.out.println("LINK: fb.com/" + aPost.getId() + "\n");
                                    System.out.println("--> TEXT OF POST: " + aPost.getMessage());


                                    out.print("Author: " + aPost.getId() + "\n");
                                    out.print("Comments from post: " + aPost.getMessage() + "\n\n");
                                    out.print("picture: " + aPost.getPicture() + "\n");
                                    System.out.println("FOLLOWING ARE COMMENTS");
                                    out.print("FOLLOWING ARE COMMENTS \n\n");

                                    int numComments = 0;
                                    for (java.util.List<Comment> commentPage : commentFeed) {
                                        for (Comment aComment : commentPage) {
                                            numComments++;
                                            System.out.println("Commenter: " + aComment.getFrom().getName());
                                            System.out.println(aComment.getCreatedTime());
                                            System.out.println("--------> COMMENT ON POST: " + aComment.getMessage() + "\n");

                                            nameArray.add(aComment.getFrom().getName());
                                            commentArray.add(aComment.getMessage());

                                            out.print("NAME: " + aComment.getFrom().getName() + "   \n");
                                            out.print("TIME_CREATED: " + aComment.getCreatedTime() + "\n");
                                            out.print(aComment.getMessage() + "\n");
                                            out.print("\n");
                                            //out.print("------------------------------------------------------------------------------------------");
                                        }

                                    }
                                    System.out.println("END OF COMMENTS");
                                    System.out.println("Total number of comments: " + numComments);
                                    out.print("Total number of comments: " + numComments);
                                    out.print("\nEND OF COMMENTS");
                                    out.print("\n------------------------------------------------------------------------------------------");

                                    break outerloop;

//						counter++;
//						if (counter >= 3) 
//						{System.out.println("too many posts..."); 
//						break outerloop;}

                                }
                            }
                        }
                    }
                }
            }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            String[] earlyKeywords = {"early", "915", "845", "9:15", "8:45", "900", "9:00", "FOF", "equipping"};
            String[] driveKeywords = {"drive", "driver"};
            String[] rideKeywords = {"drive", "church", "rides"};
            String[] campusKeywords = {"campus"};
            String[] lunchKeywords = {"lunch"};


            ArrayList<String> allPeople = new ArrayList<>();
            ArrayList<String> earlyRideToChurch = new ArrayList<>();
            ArrayList<String> earlyDriveToChurch = new ArrayList<>();
            ArrayList<String> regRideToChurch = new ArrayList<>();
            ArrayList<String> regDriveToChurch = new ArrayList<>();
            ArrayList<String> afterCampus = new ArrayList<>();
            ArrayList<String> afterLunch = new ArrayList<>();
            ArrayList<String> unsortedBefore = new ArrayList<>();
            ArrayList<String> unsortedAfter = new ArrayList<>();

            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////// ADD PEOPLE HERE //////////////////////////////////
              allPeople.add("Jon Wong");
              earlyDriveToChurch.add("Jon Wong");
              afterLunch.add("Jon Wong");
//            earlyDriveToChurch.add("Jon WongD2");
//            earlyRideToChurch.add("Jon Wong1");
//            earlyRideToChurch.add("Jon Wong2");
//            earlyRideToChurch.add("Jon Wong3");
//            earlyRideToChurch.add("Jon Wong4");
//            earlyRideToChurch.add("Jon Wong5");
//            earlyRideToChurch.add("Jon Wong6");
//            earlyRideToChurch.add("Jon Wong7");
//            earlyRideToChurch.add("Jon Wong8");
//            regRideToChurch.add("Jon Wong");
//            regRideToChurch.add("Mark Chin");
            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////


            for (int i = 0; i < nameArray.size(); i++) {
//                System.out.println("\n" + nameArray.get(i));
//                System.out.println(commentArray.get(i) + "\n");
                String checkComment = commentArray.get(i).toLowerCase();

                //handles before church, early or regular time, ride or drive
                if (stringContainsItemFromList(checkComment, earlyKeywords)) {
                    if (stringContainsItemFromList(checkComment, driveKeywords)) {
                        earlyDriveToChurch.add(nameArray.get(i));
                    } else {
                        earlyRideToChurch.add(nameArray.get(i));
                    }
                }
                //adds people to
                else if (stringContainsItemFromList(checkComment, rideKeywords)) {
                    if (stringContainsItemFromList(checkComment, driveKeywords)) {
                        regDriveToChurch.add(nameArray.get(i));
                    } else {
                        regRideToChurch.add(nameArray.get(i));
                    }
                } else {
                    unsortedBefore.add(nameArray.get(i));
                }

                //handles after church, either
                if (stringContainsItemFromList(checkComment, campusKeywords)) {
                    afterCampus.add(nameArray.get(i));
                } else if (stringContainsItemFromList(checkComment, lunchKeywords)) {
                    afterLunch.add(nameArray.get(i));
                } else {
                    unsortedAfter.add(nameArray.get(i));
                }


                allPeople.add(nameArray.get(i));
            }

            allPeople.remove("Sydney Leung");
            regRideToChurch.remove("Sydney Leung");

            ArrayList<Person> classifiedAllPeople = new ArrayList<>();
            ArrayList<String> earlyPeople = new ArrayList<>();
            earlyPeople.addAll(earlyDriveToChurch);
            earlyPeople.addAll(earlyRideToChurch);

            ArrayList<String> regPeople = new ArrayList<>();
            regPeople.addAll(regDriveToChurch);
            regPeople.addAll(regRideToChurch);

            ArrayList<String> allDrivers = new ArrayList<>();
            allDrivers.addAll(earlyDriveToChurch);
            allDrivers.addAll(regDriveToChurch);

            ArrayList<String> afterCampusDrivers = new ArrayList<>();
            ArrayList<String> afterLunchDrivers = new ArrayList<>();

            for (String person: allPeople)
            {
                Person addPerson = new Person(person, -1, -1);
                if (earlyPeople.contains(person)) {addPerson.beforeChurch = 0;}
                else if (regPeople.contains(person)) {addPerson.beforeChurch = 1;}
                else if (unsortedBefore.contains(person)) {addPerson.beforeChurch = -1;}

                if (afterCampus.contains(person)) {addPerson.afterChurch = 0;}
                else if (afterLunch.contains(person)) {addPerson.afterChurch = 1;}
                else if (unsortedAfter.contains(person)) {addPerson.afterChurch = -1;}

                classifiedAllPeople.add(addPerson);


                if (allDrivers.contains(person) && afterCampus.contains(person))
                {
                    println("allDrivers.contains(person) && afterCampus.contains(person)");
                    afterCampus.remove(person);
                    afterCampusDrivers.add(person);
                }
                else if (allDrivers.contains(person) && (afterLunch.contains(person)))
                {
                    println("allDrivers.contains(person) && afterLunch.contains(person)");
                    afterLunch.remove(person);
                    afterLunchDrivers.add(person);
                }
            }



            importantStuff.println("allPeople: " + Arrays.toString(allPeople.toArray()) + " size: " + allPeople.size());
            importantStuff.println("earlyPeople: " + Arrays.toString(earlyPeople.toArray()) + " size: " + earlyPeople.size());
            importantStuff.println("regPeople: " + Arrays.toString(regPeople.toArray()) + " size: " + regPeople.size());
            importantStuff.println("allDrivers: " + Arrays.toString(allDrivers.toArray()) + " size: " + allDrivers.size());
            importantStuff.println("earlyDriveToChurch: " + Arrays.toString(earlyDriveToChurch.toArray()) + " size: " + earlyDriveToChurch.size());
            importantStuff.println("earlyRideToChurch: " + Arrays.toString(earlyRideToChurch.toArray()) + " size: " + earlyRideToChurch.size());
            importantStuff.println("regDriveToChurch: " + Arrays.toString(regDriveToChurch.toArray()) + " size: " + regDriveToChurch.size());
            importantStuff.println("regRideToChurch: " + Arrays.toString(regRideToChurch.toArray()) + " size: " + regRideToChurch.size());
            importantStuff.println("afterCampusDrivers: " + Arrays.toString(afterCampusDrivers.toArray()) + " size: " + afterCampusDrivers.size());
            importantStuff.println("afterLunchDrivers: " + Arrays.toString(afterLunchDrivers.toArray()) + " size: " + afterLunchDrivers.size());
            importantStuff.println("afterCampus: " + Arrays.toString(afterCampus.toArray()) + " size: " + afterCampus.size());
            importantStuff.println("afterLunch: " + Arrays.toString(afterLunch.toArray()) + " size: " + afterLunch.size());
            importantStuff.println("unsortedBefore: " + Arrays.toString(unsortedBefore.toArray()) + " size: " + unsortedBefore.size());
            importantStuff.println("unsortedAfter: " + Arrays.toString(unsortedAfter.toArray()) + " size: " + unsortedAfter.size() + "\n");


            System.out.println("allPeople: " + Arrays.toString(allPeople.toArray()) + " size: " + allPeople.size());
            System.out.println("earlyPeople: " + Arrays.toString(earlyPeople.toArray()) + " size: " + earlyPeople.size());
            System.out.println("regPeople: " + Arrays.toString(regPeople.toArray()) + " size: " + regPeople.size());
            System.out.println("allDrivers: " + Arrays.toString(allDrivers.toArray()) + " size: " + allDrivers.size());
            System.out.println("earlyDriveToChurch: " + Arrays.toString(earlyDriveToChurch.toArray()) + " size: " + earlyDriveToChurch.size());
            System.out.println("earlyRideToChurch: " + Arrays.toString(earlyRideToChurch.toArray()) + " size: " + earlyRideToChurch.size());
            System.out.println("regDriveToChurch: " + Arrays.toString(regDriveToChurch.toArray()) + " size: " + regDriveToChurch.size());
            System.out.println("regRideToChurch: " + Arrays.toString(regRideToChurch.toArray()) + " size: " + regRideToChurch.size());
            System.out.println("afterCampusDrivers: " + Arrays.toString(afterCampusDrivers.toArray()) + " size: " + afterCampusDrivers.size());
            System.out.println("afterLunchDrivers: " + Arrays.toString(afterLunchDrivers.toArray()) + " size: " + afterLunchDrivers.size());
            System.out.println("afterCampus: " + Arrays.toString(afterCampus.toArray()) + " size: " + afterCampus.size());
            System.out.println("afterLunch: " + Arrays.toString(afterLunch.toArray()) + " size: " + afterLunch.size());
            System.out.println("unsortedBefore: " + Arrays.toString(unsortedBefore.toArray()) + " size: " + unsortedBefore.size());
            System.out.println("unsortedAfter: " + Arrays.toString(unsortedAfter.toArray()) + " size: " + unsortedAfter.size() + "\n");

            //add more keywords, sort more, add scanner (Maybe GUI?)

            boolean editFlag = true;
            while (editFlag) {
                int EARLY_MAX = earlyDriveToChurch.size() * 4;
                int REG_MAX = regDriveToChurch.size() * 4;
                if (earlyRideToChurch.size() > EARLY_MAX) {
                    System.out.println("Not enough early drivers. Maximum of " + EARLY_MAX +
                            " seats. Currently have " + earlyRideToChurch.size() + ". Please add early drivers or remove early riders.");
                }


                if (regRideToChurch.size() > REG_MAX) {
                    System.out.println("Not enough regular drivers. Maximum of " + REG_MAX +
                            " seats. Currently have " + regRideToChurch.size() + ". Please add regular drivers or remove regular riders.");
                }

                System.out.println("[A]dd person, [R]emove Person, [S]witch people, [P]rint contents, [E]dit Person, or [C]ontinue?");
                String editUserChoice = reader.nextLine().toLowerCase();

                if (editUserChoice.equals("e"))
                {
                        println("Who would you like to edit? Use in the event that the comment isn't explicit enough. Q to quit.");
                        String yiiUserChoice = reader.nextLine();

                        if (!allPeople.contains(yiiUserChoice)) {
                            println("Person doesn't exist.");
                        } else {
                            for (Person p : classifiedAllPeople) {
                                if (p.name.equals(yiiUserChoice)) {

                                    boolean flag = true;
                                    while (flag) {
                                        println("current: " + p.toString());
                                        println("What would you like to change? [b]eforeChurch or [a]fterChurch, or [Q]uit");
                                        String yiiEditUserChoice = reader.nextLine().toLowerCase();


                                        if (yiiEditUserChoice.equals("b")) {
                                            println("current: " + p.getStatusBeforeChurch());
                                            println("What would you like to change it to? [R]egular Time, [E]arly, or [N]o ride before.");
                                            String yiiBeeUserChoice = reader.nextLine().toLowerCase();

                                            if (yiiBeeUserChoice.equals("r")) {
                                                p.beforeChurch = 1;
                                            } else if (yiiBeeUserChoice.equals("e")) {
                                                p.beforeChurch = 0;
                                            } else if (yiiBeeUserChoice.equals("n")) {
                                                p.beforeChurch = -1;
                                            } else {
                                                println("command not recognized.");
                                            }
                                        } else if (yiiEditUserChoice.equals("a")) {
                                            println("current: " + p.getStatusAfterChurch());
                                            println("What would you like to change it to? [L]unch, [C]ampus, or [N]o ride after.");
                                            String yiiAyyUserChoice = reader.nextLine().toLowerCase();
                                            if (yiiAyyUserChoice.equals("l")) {
                                                p.afterChurch = 1;
                                            } else if (yiiAyyUserChoice.equals("c")) {
                                                p.afterChurch = 0;
                                            } else if (yiiAyyUserChoice.equals("n")) {
                                                p.afterChurch = -1;
                                            } else {
                                                println("command not recognized.");
                                            }
                                        } else if (yiiEditUserChoice.equals("q")) {
                                            flag = false;
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        for (Person p: classifiedAllPeople)
                        {
                            //lunch and driver and afterLunchDrivers doesnt already contain, add
                            if (allDrivers.contains(p.name) && (p.afterChurch == 1) && (!afterLunchDrivers.contains(p.name)))
                            {
                                afterLunchDrivers.add(p.name);
                            }
                            //campus and driver and afterCampusDrivers doesnt already contain, add
                            else if (allDrivers.contains(p.name) && (p.afterChurch == 0) && (!afterCampusDrivers.contains(p.name)))
                            {
                                afterCampusDrivers.add(p.name);
                            }
                            //unsorted and driver and unsortedAfter doesnt already contain, add
                            else if (allDrivers.contains(p.name) && (p.afterChurch == -1) && (!unsortedAfter.contains(p.name)))
                            {
                                unsortedAfter.add(p.name);
                            }
                            //not done updating
                        }


                }
                else if (editUserChoice.equals("q"))
                {
                    System.out.println("exiting...");
                    System.exit(0);
                }
                else if (editUserChoice.equals("a")) //add someone (someone didn't comment? but still want a ride.)
                {
                    System.out.println("Who would you like to add? (Name) ");
                    //no toLowerCase(), want to keep capital of first leter of each name
                    String ayyUserChoiceName = reader.nextLine();
                    System.out.println("Before: [E]arly, [R]egular, or [N]o ride?");
                    String ayyUserChoiceTime = reader.nextLine().toLowerCase();


                    System.out.println("[R]ider or [D]river?");
                    String ayyUserChoiceRideOrDrive = reader.nextLine().toLowerCase();
                    //System.out.println("After: [")

                    allPeople.add(ayyUserChoiceName);

                    if (ayyUserChoiceTime.equals("e") && (ayyUserChoiceRideOrDrive.equals("r"))) //earlyRide
                    {
                        earlyRideToChurch.add(ayyUserChoiceName);
                        System.out.println("Successfully added " + ayyUserChoiceName);
                    } else if (ayyUserChoiceTime.equals("e") && (ayyUserChoiceRideOrDrive.equals("d"))) //earlyDrive
                    {
                        earlyDriveToChurch.add(ayyUserChoiceName);
                        System.out.println("Successfully added " + ayyUserChoiceName);
                    } else if (ayyUserChoiceTime.equals("r") && (ayyUserChoiceRideOrDrive.equals("r"))) //regRide
                    {
                        regRideToChurch.add(ayyUserChoiceName);
                        System.out.println("Successfully added " + ayyUserChoiceName);
                    } else if (ayyUserChoiceTime.equals("r") && (ayyUserChoiceRideOrDrive.equals("d"))) //regDrive
                    {
                        regDriveToChurch.add(ayyUserChoiceName);
                        System.out.println("Successfully added " + ayyUserChoiceName);
                    } else {
                        System.out.println("Commands not recognized. Try again.");
                    }

                }
                else if (editUserChoice.equals("r")) //remove someone
                {
                    System.out.println("Who would you like to remove?");
                    String arrUserChoice = reader.nextLine();

                    if (!allPeople.contains(arrUserChoice)) {
                        System.out.println("Person: " + arrUserChoice + " doesn't exist. Try again.");
                    } else {
                        String removeString = "Successfully removed " + arrUserChoice + " from ";
                        allPeople.remove(arrUserChoice);

                        if (regDriveToChurch.contains(arrUserChoice)) {
                            regDriveToChurch.remove(arrUserChoice);
                            removeString += "regular drivers.";
                        }
                        else if (regRideToChurch.contains(arrUserChoice)) {
                            regRideToChurch.remove(arrUserChoice);
                            removeString += "regular riders.";
                        } else if (earlyDriveToChurch.contains(arrUserChoice)) {
                            earlyDriveToChurch.remove(arrUserChoice);
                            removeString += "early drivers.";
                        } else if (earlyRideToChurch.remove(arrUserChoice)) {
                            earlyRideToChurch.remove(arrUserChoice);
                            removeString += "early riders.";
                        }

                        System.out.println(removeString);
                    }
                }
                //switch two people? ex: switch a driver and rider, only for regular leave time
                else if (editUserChoice.equals("s")) {
                    System.out.println("Note: can only switch for regular time.");
                    System.out.println("Who would you like to switch around? Person 1: ");
                    String personOne = reader.nextLine();

                    System.out.println("Person 2: ");
                    String personTwo = reader.nextLine();

                    if (!(regDriveToChurch.contains(personOne) && regRideToChurch.contains(personTwo))) {
                        System.out.println("One of the people doesn't exist. Try again.");
                    } else if (!(regRideToChurch.contains(personOne) && regDriveToChurch.contains(personTwo))) {
                        System.out.println("One of the people doesn't exist. Try again.");
                    } else {
                        regDriveToChurch.remove(personOne);
                        regRideToChurch.add(personOne);
                        regRideToChurch.remove(personTwo);
                        regDriveToChurch.add(personTwo);
                        System.out.println("Switched " + personOne + " and " + personTwo + "!");
                    }
                } else if (editUserChoice.equals("p")) //print contents of
                {
                    System.out.println("allPeople: " + Arrays.toString(allPeople.toArray()) + " size: " + allPeople.size());
                    System.out.println("earlyPeople: " + Arrays.toString(earlyPeople.toArray()) + " size: " + earlyPeople.size());
                    System.out.println("regPeople: " + Arrays.toString(regPeople.toArray()) + " size: " + regPeople.size());
                    System.out.println("allDrivers: " + Arrays.toString(allDrivers.toArray()) + " size: " + allDrivers.size());
                    System.out.println("earlyDriveToChurch: " + Arrays.toString(earlyDriveToChurch.toArray()) + " size: " + earlyDriveToChurch.size());
                    System.out.println("earlyRideToChurch: " + Arrays.toString(earlyRideToChurch.toArray()) + " size: " + earlyRideToChurch.size());
                    System.out.println("regDriveToChurch: " + Arrays.toString(regDriveToChurch.toArray()) + " size: " + regDriveToChurch.size());
                    System.out.println("regRideToChurch: " + Arrays.toString(regRideToChurch.toArray()) + " size: " + regRideToChurch.size());
                    System.out.println("afterCampusDrivers: " + Arrays.toString(afterCampusDrivers.toArray()) + " size: " + afterCampusDrivers.size());
                    System.out.println("afterLunchDrivers: " + Arrays.toString(afterLunchDrivers.toArray()) + " size: " + afterLunchDrivers.size());
                    System.out.println("afterCampus: " + Arrays.toString(afterCampus.toArray()) + " size: " + afterCampus.size());
                    System.out.println("afterLunch: " + Arrays.toString(afterLunch.toArray()) + " size: " + afterLunch.size());
                    System.out.println("unsortedBefore: " + Arrays.toString(unsortedBefore.toArray()) + " size: " + unsortedBefore.size());
                    System.out.println("unsortedAfter: " + Arrays.toString(unsortedAfter.toArray()) + " size: " + unsortedAfter.size() + "\n");

                } else if (editUserChoice.equals("c")) {

                    if (earlyRideToChurch.size() > EARLY_MAX) {
                        System.out.println("Cannot continue.");
                    } else if (regRideToChurch.size() > REG_MAX) {
                        System.out.println("Cannot continue.");
                    } else {
                        editFlag = false;
                        break;
                    }

                } else {
                    System.out.println("Command not recognized. Try again.");
                }
            }


            System.out.println("ALL DRIVERS");
            for (String p: allDrivers)
            {
                for (Person person: classifiedAllPeople)
                {
                    if (person.name.equals(p)) {println("Driver: " + person.toString());}
                }
            }

            for (Person p: classifiedAllPeople)
            {
                System.out.println(p.toString());
            }


            /*
            Collections.shuffle(earlyDriveToChurch);
            Collections.shuffle(regDriveToChurch);
            Collections.shuffle(earlyRideToChurch);
            Collections.shuffle(regRideToChurch);
            */

            /*
            String[][] earlyCars = assignRiders(earlyDriveToChurch, earlyRideToChurch);
            System.out.println("Early cars to church: ");
            print2DArray(earlyCars);
            */

            println("Early Drivers: ");
            int numDrivers = earlyDriveToChurch.size();
            String[][] earlyCars = new String[numDrivers][5];
            //String[][] earlyCars = assignRiders(earlyDriveToChurch, earlyRideToChurch);

            for (int x = 0; x < numDrivers; x++)
            { earlyCars[x][0] = earlyDriveToChurch.get(x); }

            print2DArray(earlyCars);

            //this is a comment
            println("Regular Drivers: ");
            numDrivers = regDriveToChurch.size();
            String[][] regCars = new String[numDrivers][5];
            //String[][] regCars = assignRiders(regDriveToChurch, regRideToChurch);

            for (int x = 0; x <numDrivers; x++)
            {
                regCars[x][0] = regDriveToChurch.get(x);
            }

            print2DArray(regCars);

            /*
            String[][] regCars = assignRiders(regDriveToChurch, regRideToChurch);
            System.out.println("\nRegular cars to church: ");

            System.out.println();

            print2DArray(regCars2);

            //switchPeople(regCars, "Garrett Tong", "Tim Liu");
            System.out.println();
            print2DArray(regCars);
            */

            /*lol this block doesn't do anything useful
            ArrayList<String> campusRideAfter = new ArrayList<>();
            ArrayList<String> lunchRideAfter = new ArrayList<>();
            ArrayList<String> noRideAfter = new ArrayList<>();
            for (String person : allPeople) {
                if (afterCampus.contains(person)) {
                    campusRideAfter.add(person);
                } else if (afterLunch.contains(person)) {
                    lunchRideAfter.add(person);
                } else {
                    noRideAfter.add(person);
                }
            }
            end block */


            reader.close();
            out.close();
            importantStuff.close();
            System.out.println("End of code.");
        } //end bracket for try
        catch (Exception E) {
            System.out.println("EXCEPTION!: " + E.toString());
        }
    }
    /**
     * short way to doing System.out.println(Object o), has a return
     * i'm too lazy to type out System.out.println
     * zzzzzzz
     * @param o Object to print
     */
    private static void println(Object o) {System.out.println(o);}

    /**
     * Operates the same as pl(Object o), without a return character afterwards
     * @param o Object to print
     */
    private static void print(Object o) {System.out.print(o);}

    /**
     * check if the string contains any of the keywords from the array items
     * @param inputString the string(comment) to find keywords
     * @param items array of keywords
     * @return true if contains keywords, false if otherwise
     */
    private static boolean stringContainsItemFromList(String inputString, String[] items) {
        for (int i = 0; i < items.length; i++) {
            if (inputString.contains(items[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * prints a 2D array, format:
     * [1] [2] [3]
     * [4] [5] [6]
     * @param array array to print, type String
     */
    private static void print2DArray(String[][] array) {
        int rows = array.length;
        int columns = array[0].length;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                System.out.print("[" + array[x][y] + "] ");
            }
            System.out.println();
        }
    }

    /**
     * assigns riders to a driver, fills first two seats first, if necessary, third seat, then fourth seat
     *
     * @param drivers ArrayList list of drivers
     * @param riders  list of riders
     * @return 2D array type string of all assigned cars
     */
    private static String[][] assignRiders(ArrayList<String> drivers, ArrayList<String> riders) {
        int numDrivers = drivers.size();
        String[][] assignedCars = new String[numDrivers][5];

        //assign drivers here
        for (int x = 0; x < numDrivers; x++) {
            assignedCars[x][0] = drivers.get(x);
        }

        int count = 0;

        outerloop:
        for (int x = 0; x < assignedCars.length; x++) {
            for (int y = 1; y < assignedCars[0].length - 2; y++) {
                if (count >= riders.size()) {
                    break outerloop;
                }
                assignedCars[x][y] = riders.get(count);
                count++;
            }
        }

        if (count < riders.size()) //still more people need to be sorted, assign to second to last column car[x][3]
        {
            for (int x = 0; x < assignedCars[x][0].length() - 1; x++) {
                if (count >= riders.size()) {
                    break;
                }
                ArrayList<Integer> randArr = new ArrayList<>();
                for (int i = 0; i < numDrivers; i++) {
                    randArr.add(i);
                }
                Collections.shuffle(randArr);
                assignedCars[randArr.get(x)][3] = riders.get(count);
                count++;
            }
        }

        if (count < riders.size()) //STILL MORE PEOPLE??, distribute to last column car[x][4]
        {
            for (int x = 0; x < assignedCars[x][0].length() - 1; x++) {
                if (count >= riders.size()) {
                    break;
                }
                ArrayList<Integer> randArr = new ArrayList<>();
                for (int i = 0; i < numDrivers; i++) {
                    randArr.add(i);
                }
                Collections.shuffle(randArr);
                assignedCars[randArr.get(x)][4] = riders.get(count);
                count++;
            }
        }
        return assignedCars;


    }

    /**
     * check if all the values are equal to a determined value
     * @param value value to be checked if equal to
     * @param values values to be checked against 'value'
     * @return true if all values equal value, false otherwise
     */
    private static boolean areAllEqual(int value, int... values)
    {
        if (values.length == 1)
        {return true;}

        for (int i = 0; i < values.length; i++) {
            if (values[i] != value)
            {return false;}
        }

        return true;
    }

    /**
     * switches the positions of two people in the 2D array, used in the cars
     * @param array array to search
     * @param person1 person1 to switch
     * @param person2 person2 to switch
     */
    private static void switchPeople(String[][] array, String person1, String person2) {
        int person1x = -1;
        int person1y = -1;
        int person2x = -1;
        int person2y = -1;


        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                //System.out.println("array["+x+"]["+y+"] = " + array[x][y]);
                if (array[x][y] != null) {

                    if (array[x][y].equals(person1)) {

                        System.out.println("found person1");
                        person1x = x;
                        person1y = y;
                    }

                    if (array[x][y].equals(person2)) {
                        System.out.print("found person2");
                        person2x = x;
                        person2y = y;
                    }
                }
            }
        }

        if (areAllEqual(-1, person1x, person1y, person2x, person2y)) {
            System.out.println("people can't be found.");
            return;
        }

        array[person1x][person1y] = person2;
        array[person2x][person2y] = person1;
    }


    static class Person {
        int beforeChurch;
        int afterChurch;
        String name;

        /**
         * Creates a new Person, beforeChurch/afterChurch each assigned an int
         * corresponding to their need beforehand/afterwards.
         * @param name name of person
         * @param beforeChurch 1 if regular, 0 if early, -1 if no ride early
         * @param afterChurch 1 if lunch, 0 if campus, -1 if no ride after
         */
        public Person(String name, int beforeChurch, int afterChurch) {
            this.name = name;
            this.beforeChurch = beforeChurch;
            this.afterChurch = afterChurch;

        }

        /**
         * Returns the corresponding "code" of the person
         * For example: Person may "code" 10, meaning that he/she needs to go to
         * church on time, and back to campus afterwards.
         * @return the code : String
         */
        public String getCode() {return "" + beforeChurch + afterChurch;}

        /**
         * Status before church.
         * @return 1 if regular, 0 if early, -1 if no ride early
         */
        public int getBeforeChurch() {return beforeChurch;}



        public String getStatusBeforeChurch() {
            String rtn = "";
            if (beforeChurch == 1)  {rtn+="REGULAR";}
            else if (beforeChurch == 0) {rtn += "EALRY";}
            else if (beforeChurch == -1) {rtn += "NO RIDE BEFORE";}
            else {rtn += "NO BEFORE DATA";}
            return rtn;
        }


        public String getStatusAfterChurch() {
            String rtn = "";
            if (afterChurch == 1)  {rtn+="LUNCH";}
            else if (afterChurch == 0) {rtn += "CAMPUS";}
            else if (afterChurch == -1) {rtn += "NO RIDE AFTER";}
            else {rtn += "NO AFTER DATA";}
            return rtn;
        }

        /**
         * Status after church.
         * @return 1 if lunch, 0 if campus, -1 if no ride after
         */
        public int getAfterChurch() {return afterChurch;}

        /**
         * Prints the person's status and their corresponding code.
         * @return the Person's status/code
         */
        public String toString() {

            String beforeRtn = "";
            String afterRtn = "";

            if (beforeChurch == 1) {beforeRtn += " ON TIME";}
            else if (beforeChurch == 0) {beforeRtn += " EARLY";}
            else if (beforeChurch == -1) {beforeRtn += " NO EARLY RIDE";}


            if (afterChurch == 1) {afterRtn += "LUNCH.";}
            else if (afterChurch == 0) {afterRtn += "CAMPUS.";}
            else if (afterChurch == -1) {afterRtn += "NO AFTER RIDE.";}

            return " Name: " + this.name + beforeRtn + "/" + afterRtn + "\n [Code: " + getCode() + "]";
        }
    }
    class Car {
        ArrayList<Person> car = new ArrayList<Person>();
        int maxSize;
        int count = 0;

        /**
         * default maxSize is 4, can instantiate as another maxSize
         */
        public Car() {
            this.maxSize = 4;
        }

        public Car(int maxSize) {
            this.maxSize = maxSize;
        }

        public void add(Person p) {
            if (!(isFull())) {
                car.add(p);
            } else {
                System.out.println("car is full!");
            }
        }

        /**
         * @return true if the car is full
         */
        public boolean isFull() {
            if (count >= maxSize) {
                return true;
            }
            return false;
        }

        public String toString() {
            String rtn = "";
            for (Person p : car) {
                if (p == null) {
                    rtn += "null person";
                } else {
                    rtn += "[" + p.toString() + "] ";
                }
            }
            return rtn;
        }


    }
}
