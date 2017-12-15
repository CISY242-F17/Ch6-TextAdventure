/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Darryl Hellams
 * @version 2017.12.11
 */

import java.util.Stack;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room lastRoom;
    private int timer = 0;
    private Room fail;
    private Stack multiLastRooms = new Stack();
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room DinningHall, MasterBedroom, library, basement, attic, laboratory, bathroom, parlor1,
             parlor2, gallery, bedroom1, bar, outside, garage, GreenHouse, kitchen, ToolRoom, CentralHall,
             StairCase, elevator, bedroom2, bedroom3, bedroom4, secret, balcony, fail;
      
        // create the rooms
        outside = new Room("you are outside the main entrance of the mansion");
        DinningHall = new Room("you have entered the room where dinner is served");
        MasterBedroom = new Room("you have entered the bedroom of home owner");
        library = new Room("you have entered the mansion's library");
        basement = new Room("you have entered the basement");
        attic = new Room("you have entered the attic");
        laboratory = new Room("you have entered the mansion's secret laboratory");
        bathroom = new Room("you have entered the the mansion's main bathroom");
        parlor1 = new Room("you have entered the the 2nd floor living room of the mansion");
        parlor2 = new Room("you have entered the the main living room of the mansion");
        gallery = new Room("you have entered the art room of the mansion");
        bedroom1 = new Room("in random bedroom");
        bar = new Room("you have entered the mansion's bar room");
        garage = new Room("you have entered the car garage");
        GreenHouse = new Room("in mansion's green house");
        kitchen = new Room("you have entered the kitchen");
        ToolRoom = new Room("you have entered the room where home owner keeps tools");
        CentralHall = new Room("you have entered the central hall");
        StairCase = new Room("you have entered the main stair case");
        elevator = new Room("you have entered the secret elevator in the green house");
        bedroom2 = new Room("you have entered a bedroom on the 2nd floor");
        bedroom3 = new Room("you have entered a bedroom on the 2nd floor");
        bedroom4 = new Room("you have entered a bedroom on the 2nd floor");
        secret = new Room("you have entered a secret room on the second floor");
        balcony = new Room("you have entered the 2nd floor balcony");
        fail = new Room("home owner has returned home and recaptured you");
        
        // initialise room exits
        outside.setExit("north", CentralHall);

        DinningHall.setExit("west", parlor1);
        DinningHall.setExit("east", CentralHall);
        DinningHall.setExit("north", bar);

        MasterBedroom.setExit("east", library);
        MasterBedroom.setExit("west", bathroom);
        MasterBedroom.setExit("north", balcony);
        MasterBedroom.setExit("south", gallery);

        library.setExit("north", attic);
        library.setExit("west", MasterBedroom);
        library.setExit("south", parlor2);

        basement.setExit("south", parlor1);
        
        attic.setExit("east", bedroom1);
        attic.setExit("south", library);
        
        laboratory.setExit("north", elevator);
        
        bathroom.setExit("east", MasterBedroom);
        
        parlor1.setExit("north", basement);
        parlor1.setExit("south", kitchen);
        
        gallery.setExit("north", MasterBedroom);
        gallery.setExit("east", parlor2);
        
        bedroom1.setExit("west", attic);
        
        bar.setExit("south", DinningHall);
        bar.setExit("north", bedroom2);
        
        garage.setExit("west", CentralHall);
        
        GreenHouse.setExit("north", ToolRoom);
        GreenHouse.setExit("east", elevator);
        GreenHouse.setExit("west", parlor2);
        
        kitchen.setExit("north", parlor1);
        kitchen.setExit("east", DinningHall);
        
        ToolRoom.setExit("south", GreenHouse);
        
        parlor2.setExit("north", library);
        parlor2.setExit("east", GreenHouse);
        parlor2.setExit("west", gallery);
        parlor2.setExit("south", StairCase);
        parlor2.setExit("southeast", bedroom2);
        
        CentralHall.setExit("north", StairCase);
        CentralHall.setExit("east", garage);
        CentralHall.setExit("west", DinningHall);
        CentralHall.setExit("south", outside);
        
        StairCase.setExit("south", CentralHall);
        StairCase.setExit("east", parlor2);
        StairCase.setExit("west", bar);
        StairCase.setExit("southeast", bedroom2);
        
        elevator.setExit("west", GreenHouse);
        elevator.setExit("south", laboratory);
        
        bedroom2.setExit("northwest", StairCase);
        bedroom2.setExit("north", parlor2);
        
        bedroom3.setExit("south", bar);
        bedroom3.setExit("west", bedroom4);
        
        bedroom4.setExit("north", secret);
        bedroom4.setExit("south", bedroom3);
        
        secret.setExit("north", balcony);
        secret.setExit("west", bedroom4);
        secret.setExit("south", bedroom4);
        
        balcony.setExit("southwest", secret);
        balcony.setExit("southeast", MasterBedroom);
        
        //Add items to rooms
        
        bedroom1.addItem(new Item ("bright lantern", 3.0));
        kitchen.addItem(new Item ("bag of chips", 0.5));
        MasterBedroom.addItem(new Item ("old journal", 1.0));
        laboratory.addItem(new Item ("weird substance with a strong scent", 3.5));
        
        currentRoom = bedroom1;  // start game in random bedroom
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        
        if (timer > 20)
        {
            currentRoom = fail;
            System.out.println(currentRoom.getLongDescription());
            finished = true;
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("You have woken up in a mansion you have never seen before");
        System.out.println("Search for a way out within the time limit.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                 look();
                 break; 
            
            case GRAB:
                 grab();
                 break;
                 
            case BACK:
                 back();
                 break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You have woken up in an unfamilar bedroom. Search around the mansion");
        System.out.println("and look for a way out.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    //Allows player to view room and get long description
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    //Allows player to pick up an item
    private void grab()
    {
        System.out.println("You have picked up the item");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no exit!");
        }
        else {
            lastRoom = currentRoom;
            multiLastRooms.push (lastRoom);
            currentRoom = nextRoom;
            timer = timer + 1;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    //Allows player to go back to the room they were just in
    private void back()
    {
        if (multiLastRooms.empty())
        {
            System.out.println("You wouldn't be lost right now if you had a good memory...");
        }
        else
        {
            currentRoom = (Room) multiLastRooms.pop();
            System.out.println("You retraced your foot steps and found your way back to where you were.");
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
